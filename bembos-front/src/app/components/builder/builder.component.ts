import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'bembos-builder',
  template: `
    <section class="container">
      <h2>Crear tu hamburguesa</h2>
      <p>Selecciona ingredientes y calcula precio.</p>
      <div class="card">
        <label>Nombre de tu burger</label>
        <input #name type="text" placeholder="Mi burger especial" />
        <h4>Ingredientes</h4>
        <div *ngFor="let ing of ingredients">
          <label><input type="checkbox" (change)="toggle(ing)" /> {{ing.name}} (+S/ {{ing.price}})</label>
        </div>
        <div style="margin-top:1rem; font-weight:700">Total: S/ {{ total }}</div>
        <div style="margin-top:0.75rem">
          <button class="btn btn--primary" (click)="placeOrder()">Finalizar y Comprar</button>
        </div>
      </div>
    </section>
  `,
  standalone: true,
  imports: [CommonModule]
})
export class BuilderComponent {
  ingredients = [
    { name: 'Clásico', price: 1, active: true },
    { name: 'Res', price: 6, active: true },
    { name: 'Queso', price: 1.5, active: false }
  ];

  get total() {
    return this.ingredients.filter(i => i.active).reduce((s, i) => s + i.price, 0).toFixed(2);
  }

  toggle(ing: any) {
    ing.active = !ing.active;
  }

  placeOrder() {
    alert('Pedido generado.');
  }
}
