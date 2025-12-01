import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'bembos-share',
  template: `
    <section class="container">
      <h2>Compartir creación</h2>
      <p>Publica tu hamburguesa y recibe votos y reseñas.</p>
      <div class="card">
        <label>Título</label>
        <input type="text" placeholder="Mi burger espectacular" />
        <label>Descripción</label>
        <textarea rows="3"></textarea>
        <div style="margin-top:0.75rem"><button class="btn btn--primary">Publicar</button></div>
      </div>
    </section>
  `,
  standalone: true,
  imports: [CommonModule]
})
export class ShareComponent {}
