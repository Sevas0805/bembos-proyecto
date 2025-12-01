import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable, throwError, of } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { environment } from '../../environments/environment';

export interface AuthResponse {
  token: string;
  role: string;
  user: {
    id: string;
    name: string;
    email: string;
  };
}

export interface RegisterResponse {
  ok: boolean;
  user: {
    id: string;
    name: string;
    email: string;
  };
}

@Injectable({ providedIn: 'root' })
export class AuthService {
  private http = inject(HttpClient);
  private apiUrl = environment.apiUrl;

  login(email: string, password: string): Observable<AuthResponse> {
    const params = new HttpParams()
      .set('email', email)
      .set('password', password);

    return this.http.post<any>(`${this.apiUrl}/usuario/authenticate`, null, { params }).pipe(
      tap(response => {
        // Guardar token y email en localStorage
        if (response.token) {
          localStorage.setItem('bembos_token', response.token);
          localStorage.setItem('user_email', email);
        }
      }),
      catchError(error => {
        console.error('Error en login:', error);
        return throwError(() => new Error(error.error?.message || 'Credenciales inválidas. Revisa tu email o contraseña.'));
      })
    );
  }

  register(name: string, email: string, password: string): Observable<RegisterResponse> {
    // Dividir nombre en nombre y apellido (simplificado: toma primer palabra como nombre, resto como apellido)
    const parts = name.trim().split(/\s+/);
    const nombre = parts[0] || '';
    const apellido = parts.slice(1).join(' ') || 'Usuario';

    const body = {
      nombre: nombre,
      apellido: apellido,
      email: email,
      password: password
    };

    return this.http.post(`${this.apiUrl}/usuario/register`, body, { responseType: 'text' }).pipe(
      tap((response: any) => {
        console.log('Respuesta del backend:', response);
        // El backend devuelve: "Usuario registrado con éxito. Token: eyJ..."
        if (typeof response === 'string' && response.includes('Token:')) {
          const tokenMatch = response.match(/Token:\s*(\S+)/);
          if (tokenMatch && tokenMatch[1]) {
            localStorage.setItem('bembos_token', tokenMatch[1]);
            localStorage.setItem('user_email', email);
            console.log('Token y email guardados correctamente');
          }
        }
      }),
      catchError(error => {
        console.error('Error en registro:', error);
        let errorMsg = 'Error al registrar usuario.';
        
        if (error.error) {
          if (typeof error.error === 'string') {
            errorMsg = error.error;
          } else if (error.error.message) {
            errorMsg = error.error.message;
          }
        } else if (error.message) {
          errorMsg = error.message;
        } else if (error.status === 0) {
          errorMsg = 'No se pudo conectar con el servidor. Verifica que el backend esté ejecutándose.';
        }
        
        return throwError(() => ({ message: errorMsg }));
      })
    ) as Observable<RegisterResponse>;
  }

  logout() {
    localStorage.removeItem('bembos_token');
    return of(true);
  }

  profile() {
    // Implementar cuando tengas endpoint de perfil en el backend
    const token = localStorage.getItem('bembos_token');
    if (!token) {
      return throwError(() => new Error('No autenticado'));
    }
    // Por ahora retorna mock, actualiza cuando tengas /api/usuario/me
    return of({ id: 'u1', name: 'Usuario', role: 'CLIENTE' });
  }

  isAuthenticated(): boolean {
    return !!localStorage.getItem('bembos_token');
  }
}
