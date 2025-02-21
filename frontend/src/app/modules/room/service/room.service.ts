import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Room, RoomRequest } from '../room.model';
import { env } from '../../../../env/env';
import { AuthService } from '../../auth/auth.service';

@Injectable({
  providedIn: 'root'
})
export class RoomService {
  private apiUrl = `${env.api}/api/rooms`;

  constructor(private http: HttpClient, private authService: AuthService) {}

  private getAuthHeaders(): HttpHeaders {
    return this.authService.getAuthHeaders();
  }

  createRoom(roomRequest: RoomRequest): Observable<Room> {
    return this.http.post<Room>(`${this.apiUrl}`, roomRequest, { headers: this.getAuthHeaders() });
  }

  updateRoom(id: number, roomRequest: RoomRequest): Observable<Room> {
    return this.http.put<Room>(`${this.apiUrl}/${id}`, roomRequest, { headers: this.getAuthHeaders() });
  }

  deleteRoom(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`, { headers: this.getAuthHeaders() });
  }

  getRoom(id: number): Observable<Room> {
    return this.http.get<Room>(`${this.apiUrl}/${id}`, { headers: this.getAuthHeaders() });
  }

  listRooms(): Observable<Room[]> {
    return this.http.get<Room[]>(`${this.apiUrl}/list`, { headers: this.getAuthHeaders() });
  }

  listAvailableRooms(startDate: string, endDate: string, type: string): Observable<Room[]> {
    return this.http.get<Room[]>(`${this.apiUrl}/available`, {
      headers: this.getAuthHeaders(),
      params: {
        startDate,
        endDate,
        type
      }
    });
  }
}
