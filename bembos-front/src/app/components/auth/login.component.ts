import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { ReactiveFormsModule, FormBuilder, Validators, FormGroup } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';

import { AbstractControl, ValidationErrors } from '@angular/forms';

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
  selector: 'bembos-login',
  template: `
  <section class="auth-wrap container">
    <div class="auth-grid">
      <div class="auth-card auth-login">
        <h1 class="auth-title">INICIAR SESIÓN</h1>
        <form [formGroup]="form" class="form" (ngSubmit)="onSubmit()">
          <label class="label">Correo electrónico <span class="req">*</span></label>
          <input class="input" type="email" formControlName="email" placeholder="Ingrese su correo" [class.invalid]="email.invalid && (email.dirty || email.touched)" />
          <div class="error-message" *ngIf="email.invalid && (email.dirty || email.touched)">
            <div *ngIf="email.errors?.['required']">El correo es requerido.</div>
            <div *ngIf="email.errors?.['email']">Ingresa un correo válido.</div>
          </div>

          <label class="label">Contraseña <span class="req">*</span></label>
          <div class="input-with-icon">
            <input class="input" type="password" formControlName="password" placeholder="Ingrese su contraseña" [class.invalid]="password.invalid && (password.dirty || password.touched)" />
            <button type="button" class="eye" aria-label="Mostrar contraseña">"*"</button>
          </div>
          <div class="error-message" *ngIf="password.invalid && (password.dirty || password.touched)">
            <div *ngIf="password.errors?.['required']">La contraseña es requerida.</div>
            <div *ngIf="password.errors?.['minlength']">La contraseña debe tener al menos 8 caracteres.</div>
            <div *ngIf="password.errors?.['passwordComplexity']">La contraseña no cumple los requisitos mínimos.</div>
          </div>

          <div class="password-checklist">
            <div [class.ok]="hasLength">• Mínimo 8 caracteres</div>
            <div [class.ok]="hasLower">• Una letra minúscula</div>
            <div [class.ok]="hasUpper">• Una letra mayúscula</div>
            <div [class.ok]="hasNumber">• Un número</div>
            <div [class.ok]="hasSpecial">• Un carácter especial (ej. !@#$%)</div>
          </div>

          <!-- strength meter removed from login (only in register) -->

          <a class="forgot" href="#">Olvidé mi contraseña</a>

          <div class="error-message" *ngIf="clientError">{{ clientError }}</div>
          <div class="error-message" *ngIf="serverError">{{ serverError }}</div>

          <button class="btn btn-full" type="submit" [disabled]="loading">
            {{ loading ? 'Validando...' : 'Iniciar sesión' }}
          </button>
        </form>
      </div>

      <div class="auth-card auth-register">
        <h1 class="auth-title">CREAR CUENTA</h1>
        <div class="register-copy">
          <p>Crea una y aprovecha los beneficios:</p>
          <ul>
            <li>Realiza tus compras de manera más ágil.</li>
            <li>Guarda múltiples direcciones de envío y facturación.</li>
            <li>¡Crea tu propia hamburguesa BEMBOS!.</li>
            <li>Unete a la comunidad BEMBOS y comparte tus creaciones.</li>
          </ul>
          <a class="btn btn--primary btn-full" routerLink="/register">Crear cuenta</a>
        </div>
      </div>
    </div>
  </section>
  `,
  styleUrls: ['./login.component.sass'],
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule]
})
export class LoginComponent {
  form!: FormGroup;

  loading = false;
  serverError: string | null = null;
  clientError: string | null = null;

  constructor(private fb: FormBuilder, private auth: AuthService, private router: Router) {
    this.form = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(8), passwordComplexityValidator]]
    });
    // register form
    this.registerForm = this.fb.group({
      name: ['', [Validators.required, Validators.minLength(2)]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(8), passwordComplexityValidator]],
      confirm: ['', [Validators.required]]
    }, { validators: this.passwordsMatch });
  }

  get email() { return this.form.get('email')!; }
  get password() { return this.form.get('password')!; }


  private pwdValue() { return String(this.password.value || ''); }
  get hasLength() { return this.pwdValue().length >= 8; }
  get hasLower() { return /[a-z]/.test(this.pwdValue()); }
  get hasUpper() { return /[A-Z]/.test(this.pwdValue()); }
  get hasNumber() { return /\d/.test(this.pwdValue()); }
  get hasSpecial() { return /[!@#$%^&*(),.?"':{}|<>]/.test(this.pwdValue()); }
  get strengthScore() {
    return [this.hasLength, this.hasLower, this.hasUpper, this.hasNumber, this.hasSpecial].filter(Boolean).length;
  }
  get strengthLabel() {
    const s = this.strengthScore;
    if (s <= 2) return 'Débil';
    if (s === 3 || s === 4) return 'Media';
    return 'Fuerte';
  }

  onSubmit() {
    this.serverError = null;
    this.clientError = null;
    if (this.form.invalid) {
      this.clientError = 'Por favor corrige los campos marcados antes de continuar.';
      this.form.markAllAsTouched();
      return;
    }
    this.loading = true;
    const email = String(this.email.value || '');
    const password = String(this.password.value || '');
    this.auth.login(email, password).subscribe({
      next: (res) => {
        this.loading = false;
        try {
          if (res?.token) {
            localStorage.setItem('bembos_token', String(res.token));
          }
        } catch (e) {
          console.warn('Could not store token', e);
        }
        
        // Redirigir a la página que intentaba visitar o al catálogo
        try {
          const redirectUrl = sessionStorage.getItem('redirectUrl');
          if (redirectUrl) {
            sessionStorage.removeItem('redirectUrl');
            this.router.navigateByUrl(redirectUrl);
          } else {
            this.router.navigateByUrl('/catalog');
          }
        } catch {
          this.router.navigateByUrl('/catalog');
        }
      },
      error: (err) => {
        this.loading = false;
        this.serverError = err?.message ?? 'Error en el servidor';
      }
    });
  }


  registerForm!: FormGroup;
  regLoading = false;
  regError: string | null = null;

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
  get regHasSpecial() { return /[!@#$%^&*(),.?\"':{}|<>]/.test(this.regPwdValue()); }
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
        this.router.navigateByUrl('/welcome');
      },
      error: (err) => {
        this.regLoading = false;
        this.regError = err?.message ?? 'Error al registrarse';
      }
    });
  }
}
