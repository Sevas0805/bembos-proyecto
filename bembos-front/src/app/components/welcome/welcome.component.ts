import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'bembos-welcome',
  standalone: true,
  imports: [CommonModule],
  styleUrls: ['./welcome.component.css'],
  template: `
    <section class="welcome-wrap container">
      <div class="welcome-card">
        <h2 class="welcome-title">¡Registro exitoso!</h2>
        <p class="welcome-copy">Gracias por crear tu cuenta. Ahora puedes iniciar sesión y empezar a hacer pedidos.</p>
        <a routerLink="/login" class="btn btn--primary btn-full">Ir a iniciar sesión</a>
      </div>
    </section>
  `
})
export class WelcomeComponent {}
