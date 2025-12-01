import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { CartService, CartItem } from '../../services/cart.service';

@Component({
  selector: 'bembos-order',
  templateUrl: './order.component.html',
  styleUrls: ['./order.component.css'],
  standalone: true,
  imports: [CommonModule]
})
export class OrderComponent implements OnInit {
  cartItems: CartItem[] = [];
  subtotal = 0;
  delivery = 5.00;
  total = 0;
  totalItems = 0;

  constructor(
    private cartService: CartService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.cartService.cartItems$.subscribe(items => {
      this.cartItems = items;
      this.updateTotals();
    });
  }

  updateTotals(): void {
    this.subtotal = this.cartService.getTotal();
    this.total = this.subtotal + this.delivery;
    this.totalItems = this.cartService.getItemCount();
  }

  increaseQty(item: CartItem): void {
    this.cartService.updateQuantity(item.id, item.quantity + 1);
  }

  decreaseQty(item: CartItem): void {
    if (item.quantity > 1) {
      this.cartService.updateQuantity(item.id, item.quantity - 1);
    } else {
      this.removeItem(item);
    }
  }

  removeItem(item: CartItem): void {
    if (confirm(`¿Eliminar ${item.name} del carrito?`)) {
      this.cartService.removeItem(item.id);
    }
  }

  clearCart(): void {
    if (confirm('¿Vaciar todo el carrito?')) {
      this.cartService.clearCart();
    }
  }

  checkout(): void {
    alert('🚀 Función de pago en desarrollo. Próximamente podrás completar tu pedido.');
  }

  goToCatalog(): void {
    this.router.navigate(['/catalog']);
  }
}
