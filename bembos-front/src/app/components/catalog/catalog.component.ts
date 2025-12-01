import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { trigger, transition, style, animate } from '@angular/animations';
import { BURGERS, Burger } from '../../data/burgers.data';
import { CartService } from '../../services/cart.service';

@Component({
  selector: 'bembos-catalog',
  standalone: true,
  imports: [CommonModule],
  styleUrls: ['./catalog.component.css'],
  template: `
    <section class="container">
      <h2>Menú</h2>
      <p>Selecciona tu hamburguesa favorita.</p>

      <div class="product-grid">
        <div class="product" *ngFor="let b of burgers">
          <img [src]="b.img" [alt]="b.name" (click)="openDetail(b.name)" style="cursor: pointer;" />
          <div class="content" (click)="openDetail(b.name)" style="cursor: pointer;">
            <div>
              <h4>{{ b.name }}</h4>
              <p class="desc">{{ b.desc }}</p>
            </div>
            <div class="meta">
              <div class="price">S/{{ b.price }}</div>
            </div>
          </div>
          <button class="add-btn" (click)="addToCart($event, b)" aria-label="Agregar al carrito">+</button>
        </div>
      </div>

      <!-- Notificación flotante -->
      <div class="cart-notification" *ngIf="showNotification" [@slideIn]>
        <span>✅ {{ lastAdded }} agregado al carrito</span>
      </div>
    </section>
  `,
  animations: [
    trigger('slideIn', [
      transition(':enter', [
        style({ transform: 'translateY(-100%)', opacity: 0 }),
        animate('300ms ease-out', style({ transform: 'translateY(0)', opacity: 1 }))
      ]),
      transition(':leave', [
        animate('300ms ease-in', style({ transform: 'translateY(-100%)', opacity: 0 }))
      ])
    ])
  ]
})
export class CatalogComponent {
  burgers: Burger[] = BURGERS;
  showNotification = false;
  lastAdded = '';

  constructor(private router: Router, private cart: CartService) {}

  openDetail(name: string) {
    this.router.navigate(['/catalog', name]);
  }

  addToCart(event: Event, burger: Burger) {
    event.stopPropagation();
    
    // Verificar si está logueado
    const token = typeof localStorage !== 'undefined' ? localStorage.getItem('bembos_token') : null;
    if (!token) {
      alert('⚠️ Debes iniciar sesión para agregar productos al carrito');
      this.router.navigate(['/login']);
      return;
    }

    this.cart.addItem({
      id: `preset-${burger.name}`,
      name: burger.name,
      price: parseFloat(burger.price),
      img: burger.img,
      type: 'preset'
    });

    this.lastAdded = burger.name;
    this.showNotification = true;
    
    setTimeout(() => {
      this.showNotification = false;
    }, 2500);
  }
}
