import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

export interface Ingredient {
  id: string;
  nombre: string;
  tipo?: string;
  precio: number;
  descripcion?: string;
  imagenUrl?: string;
}

@Injectable({ providedIn: 'root' })
export class IngredientsService {
  private http = inject(HttpClient);
  private apiUrl = `${environment.apiUrl}/ingredientes`;

  list(): Observable<Ingredient[]> {
    return this.http.get<Ingredient[]>(this.apiUrl);
  }

  getById(id: string): Observable<Ingredient> {
    return this.http.get<Ingredient>(`${this.apiUrl}/${id}`);
  }

  create(ingredient: Partial<Ingredient>): Observable<Ingredient> {
    return this.http.post<Ingredient>(this.apiUrl, ingredient);
  }

  update(id: string, ingredient: Partial<Ingredient>): Observable<Ingredient> {
    return this.http.put<Ingredient>(`${this.apiUrl}/${id}`, ingredient);
  }

  delete(id: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
