import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterModule, Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { getBurger } from '../../data/burgers.data';

@Component({
  selector: 'bembos-burger-detail',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule],
  template: `
    <section class="detail-root">
      <div class="detail container">
        <div class="left">
          <div class="image-card">
            <img [src]="imgFor(name)" [alt]="name" class="hero-img" />
          </div>
        </div>

        <div class="right">
          <div class="top-row">
            <button class="back" (click)="goBack()">← Volver</button>
            <div class="badge">Producto</div>
          </div>

          <h1 class="title">{{ name }}</h1>
          <p class="subtitle">{{ descriptionFor(name) }}</p>

          <div class="price">S/{{ price }}</div>

          <div class="meta-row">
            <div class="sizes">
              <label>Sabor / Tamaño</label>
              <div class="size-options">
                <button class="size">Mediana</button>
                <button class="size">Grande <span class="add">+S/4.00</span></button>
              </div>
            </div>
          </div>

          <div class="controls">
            <label class="qty">
              <div class="label-row">Cantidad</div>
              <div class="qty-controls">
                <button (click)="decreaseQty()" aria-label="Reducir">−</button>
                <input type="number" [value]="quantity" (input)="onQtyChange($event)" min="1" />
                <button (click)="increaseQty()" aria-label="Aumentar">+</button>
              </div>
            </label>

            <label class="notes">
              <div class="label-row">Observaciones (opcional)</div>
              <textarea [(ngModel)]="notes" rows="3" placeholder="Ej: sin cebolla, salsas aparte..."></textarea>
            </label>

            <div class="add-row">
              <button class="add-btn" (click)="add()">Agregar al carrito</button>
            </div>
          </div>

          <div class="suggestions">
            <h4>Te puede interesar</h4>
            <div class="extras">
              <div class="extra" *ngFor="let e of extras">
                <img [src]="e.img" [alt]="e.name" />
                <div class="e-info">
                  <div class="e-name">{{ e.name }}</div>
                  <div class="e-price">S/{{ e.price }}</div>
                </div>
                <button (click)="addExtra(e)">+</button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>
  `,
  styleUrls: ['./burger-detail.component.css']
})
export class BurgerDetailComponent {
  name = '';
  price = '';
  quantity = 1;
  notes = '';
  extras = [
    { name: 'Extra Queso', price: '3.00', img: 'Multimedia/burguer1.webp' },
    { name: 'Bacon', price: '4.00', img: 'Multimedia/ROYAL.webp' },
    { name: 'Papas Fritas', price: '6.00', img: 'Multimedia/burguer1.webp' }
  ];

  constructor(private route: ActivatedRoute, private router: Router) {
    const nameParam = this.route.snapshot.paramMap.get('name');
    this.name = nameParam || 'Hamburguesa';

    const b = getBurger(this.name);
    if (b) {
      this.price = b.price;
    }
  }

  imgFor(name: string) {
    const b = getBurger(name);
    return b ? b.img : `Multimedia/${encodeURIComponent(name)}.webp`;
  }
  descriptionFor(name: string) {
    const b = getBurger(name);
    return b ? b.desc : `Deliciosa ${name}`;
  }

  goBack() {
    this.router.navigate(['/catalog']);
  }

  add() {
    console.log('Agregar', this.name, 'qty', this.quantity, 'notes', this.notes);
    alert(`${this.name} (x${this.quantity}) agregado.`);
  }

  increaseQty() { this.quantity = Number(this.quantity) + 1; }
  decreaseQty() { if (this.quantity > 1) this.quantity = Number(this.quantity) - 1; }
  onQtyChange(e: Event) { const v = Number((e.target as HTMLInputElement).value || 1); this.quantity = v < 1 ? 1 : v; }

  addExtra(e: any) {
    // simplistic behavior: alert and log
    console.log('Agregar extra', e.name);
    alert(`Extra agregado: ${e.name}`);
  }
}
