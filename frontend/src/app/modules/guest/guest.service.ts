import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthService } from '../auth/auth.service';
import { env } from '../../../env/env';
import { IFetchParams, IPageRES } from '../../types';
import { IGuest, IGuestREQ } from './guest.model';

@Injectable({
  providedIn: 'root',
})
export class GuestService {
  private apiUrl = `${env.api}/api/guests`;

  constructor(private http: HttpClient, private authService: AuthService) {}

  private getAuthHeaders(): HttpHeaders {
    const token = this.authService.getToken();
    console.log(token);
    return token ? new HttpHeaders().set('Authorization', `Bearer ${token}`) : new HttpHeaders();

  }


  fetchAllGuests(params: IFetchParams): Observable<IPageRES<IGuest>> {
    let httpParams = new HttpParams()
      .set('page', params.page?.toString() || '1')
      .set('size', params.size?.toString() || '5')
      .set('sortBy', params.sortBy || 'id')
      .set('orderBy', params.orderBy || 'desc');

    if (params.search) {
      httpParams = httpParams.set('search', params.search);
    }

    return this.http.get<IPageRES<IGuest>>(this.apiUrl, {
      headers: this.getAuthHeaders(),
      params: httpParams,
    });
  }

  fetchGuestById(id: number): Observable<IGuest> {
    return this.http.get<IGuest>(`${this.apiUrl}/${id}`, {
      headers: this.getAuthHeaders(),
    });
  }

  createGuest(req: IGuestREQ): Observable<IGuest> {
    return this.http.post<IGuest>(this.apiUrl, req, {
      headers: this.getAuthHeaders(),
    });
  }

  updateGuest(id: number, req: IGuestREQ): Observable<IGuest> {
    return this.http.put<IGuest>(`${this.apiUrl}/${id}`, req, {
      headers: this.getAuthHeaders(),
    });
  }

  deleteGuest(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`, {
      headers: this.getAuthHeaders(),
    });
  }

  fetchAllGuestsList(): Observable<IGuest[]> {
    return this.http.get<IGuest[]>(`${this.apiUrl}/list`, {
      headers: this.getAuthHeaders(),
    });
  }
}
