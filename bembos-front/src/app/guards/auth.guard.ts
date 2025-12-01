import { Injectable } from '@angular/core';
import { CanActivate, Router, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';

@Injectable({ providedIn: 'root' })
export class AuthGuard implements CanActivate {
  constructor(private router: Router) {}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    const token = typeof localStorage !== 'undefined' ? localStorage.getItem('bembos_token') : null;
    if (token) return true;
    
    // Guardar la URL que intentaba acceder para redireccionar después del login
    if (typeof sessionStorage !== 'undefined') {
      sessionStorage.setItem('redirectUrl', state.url);
    }
    
    // Mostrar alerta al usuario
    if (typeof window !== 'undefined') {
      alert('⚠️ Debes iniciar sesión para acceder a esta función');
    }
    
    this.router.navigate(['/login']);
    return false;
  }
}
