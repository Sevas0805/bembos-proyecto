import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, Validators, FormGroup, AbstractControl, ValidationErrors } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';

function passwordComplexityValidator(control: AbstractControl): ValidationErrors | null {
  const v = String(control.value || '');
  const rules = {
    length: v.length >= 8,
    lower: /[a-z]/.test(v),
    upper: /[A-Z]/.test(v),
    number: /\d/.test(v),
    special: /[!@#$%^&*(),.?"':{}|<>]/.test(v)
  };
  const all = Object.values(rules).every(Boolean);
  return all ? null : { passwordComplexity: rules };
}

@Component({
  selector: 'bembos-register',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  styleUrls: ['./register.component.css'],
  template: `
  <section class="auth-wrap container">
    <div class="auth-grid">
      <div class="auth-card auth-register-only">
        <h1 class="auth-title">CREAR CUENTA</h1>
        <form [formGroup]="registerForm" class="form" (ngSubmit)="onRegister()">
          <label class="label">Nombre completo <span class="req">*</span></label>
          <input class="input" type="text" formControlName="name" placeholder="Tu nombre" [class.invalid]="regName.invalid && (regName.dirty || regName.touched)" />
          <div class="error-message" *ngIf="regName.invalid && (regName.dirty || regName.touched)">
            <div *ngIf="regName.errors?.['required']">El nombre es requerido.</div>
            <div *ngIf="regName.errors?.['minlength']">El nombre debe tener al menos 2 caracteres.</div>
          </div>

          <label class="label">Correo electrónico <span class="req">*</span></label>
          <input class="input" type="email" formControlName="email" placeholder="Ej. nombre@mail.com" [class.invalid]="regEmail.invalid && (regEmail.dirty || regEmail.touched)" />
          <div class="error-message" *ngIf="regEmail.invalid && (regEmail.dirty || regEmail.touched)">
            <div *ngIf="regEmail.errors?.['required']">El correo es requerido.</div>
            <div *ngIf="regEmail.errors?.['email']">Ingresa un correo válido.</div>
          </div>

          <label class="label">Contraseña <span class="req">*</span></label>
          <div class="input-with-icon">
            <input class="input" type="password" formControlName="password" placeholder="Tu contraseña" [class.invalid]="regPassword.invalid && (regPassword.dirty || regPassword.touched)" />
          </div>
          <div class="error-message" *ngIf="registerForm.hasError('passwordsMismatch') && (regConfirm.dirty || regConfirm.touched)">
            Las contraseñas no coinciden.
          </div>
          <div class="error-message" *ngIf="regPassword.invalid && (regPassword.dirty || regPassword.touched)">
            <div *ngIf="regPassword.errors?.['required']">La contraseña es requerida.</div>
            <div *ngIf="regPassword.errors?.['minlength']">La contraseña debe tener al menos 8 caracteres.</div>
            <div *ngIf="regPassword.errors?.['passwordComplexity']">La contraseña no cumple los requisitos mínimos.</div>
          </div>

          <div class="password-checklist">
            <div [class.ok]="regHasLength">• Mínimo 8 caracteres</div>
            <div [class.ok]="regHasLower">• Una letra minúscula</div>
            <div [class.ok]="regHasUpper">• Una letra mayúscula</div>
            <div [class.ok]="regHasNumber">• Un número</div>
            <div [class.ok]="regHasSpecial">• Un carácter especial (ej. !@#$%)</div>
          </div>

          <div class="strength-meter">
            <div class="strength-label">Fuerza: {{ regStrengthLabel }}</div>
            <div class="strength-bar"><div class="strength-fill" [style.width.%]="(regStrengthScore / 5) * 100"></div></div>
          </div>

          <label class="label">Confirmar contraseña <span class="req">*</span></label>
          <input class="input" type="password" formControlName="confirm" placeholder="Repite tu contraseña" [class.invalid]="registerForm.hasError('passwordsMismatch') && (regConfirm.dirty || regConfirm.touched)" />

          <div class="error-message" *ngIf="regError">{{ regError }}</div>

          <button class="btn btn--primary btn-full" type="submit" [disabled]="registerForm.invalid || regLoading">{{ regLoading ? 'Registrando...' : 'Crear cuenta' }}</button>
        </form>
      </div>
    </div>
  </section>
  `
})
export class RegisterComponent {
  registerForm!: FormGroup;
  regLoading = false;
  regError: string | null = null;

  constructor(private fb: FormBuilder, private auth: AuthService, private router: Router) {
    this.registerForm = this.fb.group({
      name: ['', [Validators.required, Validators.minLength(2)]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(8), passwordComplexityValidator]],
      confirm: ['', [Validators.required]]
    }, { validators: this.passwordsMatch });
  }

  get regName() { return this.registerForm.get('name')!; }
  get regEmail() { return this.registerForm.get('email')!; }
  get regPassword() { return this.registerForm.get('password')!; }
  get regConfirm() { return this.registerForm.get('confirm')!; }

  passwordsMatch(group: AbstractControl) {
    const p = group.get('password')?.value;
    const c = group.get('confirm')?.value;
    return p === c ? null : { passwordsMismatch: true };
  }

  regPwdValue() { return String(this.regPassword.value || ''); }
  get regHasLength() { return this.regPwdValue().length >= 8; }
  get regHasLower() { return /[a-z]/.test(this.regPwdValue()); }
  get regHasUpper() { return /[A-Z]/.test(this.regPwdValue()); }
  get regHasNumber() { return /\d/.test(this.regPwdValue()); }
  get regHasSpecial() { return /[!@#$%^&*(),.?"':{}|<>]/.test(this.regPwdValue()); }
  get regStrengthScore() {
    return [this.regHasLength, this.regHasLower, this.regHasUpper, this.regHasNumber, this.regHasSpecial].filter(Boolean).length;
  }
  get regStrengthLabel() {
    const s = this.regStrengthScore;
    if (s <= 2) return 'Débil';
    if (s === 3 || s === 4) return 'Media';
    return 'Fuerte';
  }

  onRegister() {
    this.regError = null;
    if (this.registerForm.invalid) {
      this.registerForm.markAllAsTouched();
      return;
    }
    this.regLoading = true;
    const name = String(this.regName.value || '');
    const email = String(this.regEmail.value || '');
    const password = String(this.regPassword.value || '');
    this.auth.register(name, email, password).subscribe({
      next: (res) => {
        this.regLoading = false;
        console.log('Registro exitoso:', res);
        this.router.navigateByUrl('/welcome');
      },
      error: (err) => {
        this.regLoading = false;
        console.error('Error en registro:', err);
        // Extraer mensaje de error más específico
        let errorMsg = 'Error al registrarse';
        if (err?.error) {
          if (typeof err.error === 'string') {
            errorMsg = err.error;
          } else if (err.error.message) {
            errorMsg = err.error.message;
          } else if (err.error.error) {
            errorMsg = err.error.error;
          }
        } else if (err?.message) {
          errorMsg = err.message;
        }
        this.regError = errorMsg;
      }
    });
  }
}
