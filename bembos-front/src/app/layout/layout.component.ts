import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet, RouterLink, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';
import { CartService } from '../services/cart.service';

@Component({
  selector: 'bembos-layout',
  standalone: true,
  imports: [CommonModule, RouterOutlet, RouterLink],
  templateUrl: './layout.component.html',
  styleUrls: ['./layout.component.sass']
})
export class LayoutComponent implements OnInit {
  userEmail: string = '';
  cartTotal: number = 0;
  cartItems: number = 0;

  constructor(
    private auth: AuthService, 
    private router: Router,
    private cart: CartService
  ) {}

  ngOnInit() {
    this.updateUserInfo();
    
    // Suscribirse a cambios del carrito
    this.cart.cartItems$.subscribe(items => {
      this.cartTotal = this.cart.getTotal();
      this.cartItems = this.cart.getItemCount();
    });
  }

  get isLoggedIn(): boolean {
    try { return Boolean(localStorage.getItem('bembos_token')); } catch { return false; }
  }

  updateUserInfo() {
    if (this.isLoggedIn) {
      try {
        const email = localStorage.getItem('user_email');
        if (email) this.userEmail = email;
      } catch {}
    }
  }

  navigateProtected(route: string) {
    if (!this.isLoggedIn) {
      alert('⚠️ Debes iniciar sesión para acceder a esta función');
      this.router.navigate(['/login']);
      return;
    }
    this.router.navigate([route]);
  }

  logout() {
    this.auth.logout().subscribe(() => {
      try {
        localStorage.removeItem('bembos_token');
        localStorage.removeItem('user_email');
      } catch {}
      this.cart.clearCart();
      this.router.navigate(['/login']);
    });
  }
}
