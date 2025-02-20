import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { env } from '../../env/env';
import { IReservationRequest, ReservationDetails } from '../models/reservation.modal';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ReservationService {

  apiBaseUrl: string =  `${env.api}/api/reservations`;

  constructor(private http: HttpClient) { }

  reserve(req: IReservationRequest): Observable<ReservationDetails>{
    return this.http.post<ReservationDetails>(`${this.apiBaseUrl}/guest`, req);
  }
}
