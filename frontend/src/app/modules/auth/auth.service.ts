import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { IAuthRES, ILoginREQ, IUser } from './auth.model';
import { Observable } from 'rxjs';
import { env } from '../../../env/env';
import { JwtHelperService } from '@auth0/angular-jwt';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private jwtHelper: JwtHelperService = new JwtHelperService();

  constructor(private http: HttpClient,private router: Router) {}

  login(req: ILoginREQ): Observable<IAuthRES> {
    return this.http.post<IAuthRES>(`${env.api}/api/login`, req);
  }

  saveToken(token: string): void {
    localStorage.setItem('token', token);
  }

  getToken(): string | null {
    try {
      return localStorage.getItem('token');
    } catch (error) {
      console.error("Error accessing localStorage", error);
      return null;
    }
  }


  isTokenExpired(): boolean {
    const token = this.getToken();
    return token ? this.jwtHelper.isTokenExpired(token) : true;
  }

  getAuthHeaders(): HttpHeaders {
    const token = this.getToken();
    console.log(token);
    return token
      ? new HttpHeaders().set('Authorization', `Bearer ${token}`)
      : new HttpHeaders();
  }

  logout(): void {
    localStorage.removeItem('token');
    this.router.navigate(['/login']);
  }

  getUserFromToken(): IUser | null {
    const token = this.getToken();
    if (token) {
      const decodedToken = this.jwtHelper.decodeToken(token);
      if (decodedToken && decodedToken.user) {
        const { username, lastName, firstName, role } = decodedToken.user;
        return { username, lastName, firstName, role };
      }
    }
    return null;
  }

  hasRole(role: string): boolean {
    const user = this.getUserFromToken();
    return user ? user.role === role : false;
  }

  isAuthenticated(): boolean {
    return !this.isTokenExpired();
  }
}
