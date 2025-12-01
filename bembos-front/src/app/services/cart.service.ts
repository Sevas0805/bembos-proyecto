import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

export interface CartItem {
  id: string;
  name: string;
  price: number;
  quantity: number;
  img?: string;
  type: 'preset' | 'custom'; // preset = del menú, custom = personalizada
}

@Injectable({
  providedIn: 'root'
})
export class CartService {
  private cartItems = new BehaviorSubject<CartItem[]>([]);
  public cartItems$ = this.cartItems.asObservable();

  constructor() {
    this.loadCart();
  }

  private loadCart() {
    try {
      const saved = localStorage.getItem('bembos_cart');
      if (saved) {
        this.cartItems.next(JSON.parse(saved));
      }
    } catch (e) {
      console.error('Error loading cart:', e);
    }
  }

  private saveCart() {
    try {
      localStorage.setItem('bembos_cart', JSON.stringify(this.cartItems.value));
    } catch (e) {
      console.error('Error saving cart:', e);
    }
  }

  getItems(): CartItem[] {
    return this.cartItems.value;
  }

  addItem(item: Omit<CartItem, 'quantity'>) {
    const items = this.cartItems.value;
    const existing = items.find(i => i.id === item.id);
    
    if (existing) {
      existing.quantity++;
    } else {
      items.push({ ...item, quantity: 1 });
    }
    
    this.cartItems.next([...items]);
    this.saveCart();
  }

  updateQuantity(id: string, quantity: number) {
    const items = this.cartItems.value;
    const item = items.find(i => i.id === id);
    
    if (item) {
      if (quantity <= 0) {
        this.removeItem(id);
      } else {
        item.quantity = quantity;
        this.cartItems.next([...items]);
        this.saveCart();
      }
    }
  }

  removeItem(id: string) {
    const items = this.cartItems.value.filter(i => i.id !== id);
    this.cartItems.next(items);
    this.saveCart();
  }

  clearCart() {
    this.cartItems.next([]);
    this.saveCart();
  }

  getTotal(): number {
    return this.cartItems.value.reduce((sum, item) => sum + (item.price * item.quantity), 0);
  }

  getItemCount(): number {
    return this.cartItems.value.reduce((sum, item) => sum + item.quantity, 0);
  }
}
