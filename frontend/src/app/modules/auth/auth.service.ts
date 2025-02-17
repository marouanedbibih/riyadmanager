import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { IAuthRES, ILoginREQ, IUser } from './auth.model';
import { Observable } from 'rxjs';
import { env } from '../../../env/env';
import { JwtHelperService } from '@auth0/angular-jwt';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private jwtHelper: JwtHelperService = new JwtHelperService();

  constructor(private http: HttpClient) {}

  login(req: ILoginREQ): Observable<IAuthRES> {
    return this.http.post<IAuthRES>(`${env.api}/api/login`, req);
  }

  saveToken(token: string): void {
    localStorage.setItem('token', token);
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }

  isTokenExpired(): boolean {
    const token = this.getToken();
    return token ? this.jwtHelper.isTokenExpired(token) : true;
  }

  getAuthHeaders(): HttpHeaders {
    const token = this.getToken();
    return token
      ? new HttpHeaders().set('Authorization', `Bearer ${token}`)
      : new HttpHeaders();
  }

  logout(): void {
    localStorage.removeItem('access_token');
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
}
