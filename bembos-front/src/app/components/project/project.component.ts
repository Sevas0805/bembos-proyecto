import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'bembos-project',
  standalone: true,
  imports: [CommonModule],
  template: `
    <section class="container">
      <h2>Bienvenido a Bembos</h2>
      <p>(¡Crea tu hamburguesa!)</p>
    </section>
  `
})
export class ProjectComponent {}
