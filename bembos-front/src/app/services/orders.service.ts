import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { environment } from '../../environments/environment';

export interface Order {
  id: string;
  nombre: string;
  precioTotal: number;
  ingredientes?: any[];
  usuarioId?: string;
  imagenUrl?: string;
}

@Injectable({ providedIn: 'root' })
export class OrdersService {
  private http = inject(HttpClient);
  private apiUrl = `${environment.apiUrl}/hamburguesas`;

  // Crear hamburguesa (orden)
  create(order: Partial<Order>): Observable<Order> {
    const dto = {
      nombre: order.nombre,
      ingredienteIds: order.ingredientes?.map((i: any) => i.id) || [],
      usuarioId: order.usuarioId
    };
    return this.http.post<Order>(this.apiUrl, dto);
  }

  // Listar hamburguesas del usuario
  list(usuarioId?: string): Observable<Order[]> {
    if (usuarioId) {
      return this.http.get<Order[]>(`${this.apiUrl}/usuario/${usuarioId}`);
    }
    return this.http.get<Order[]>(this.apiUrl);
  }

  // Obtener hamburguesa por ID
  getById(id: string): Observable<Order> {
    return this.http.get<Order>(`${this.apiUrl}/${id}`);
  }

  // Listar hamburguesas populares
  getPopular(): Observable<Order[]> {
    return this.http.get<Order[]>(`${this.apiUrl}/populares`);
  }
}
