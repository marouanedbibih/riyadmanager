import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { IBookingRequest, IBookingRoom } from '../models/booking.modal';
import { Observable } from 'rxjs';
import { env } from '../../env/env';

@Injectable({
  providedIn: 'root',
})
export class BookingService {
  apiBaseUrl: String = `${env.api}/api/booking`;

  constructor(private http: HttpClient) {}

  bookingAPI(req: IBookingRequest): Observable<IBookingRoom[]> {
    return this.http.get<IBookingRoom[]>(
      `${this.apiBaseUrl}/available-rooms/?checkIn=${req.checkIn}&checkOut=${req.checkOut}&roomType=${req.type}`);
  }
}
