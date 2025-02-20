import { ReservationService } from './../../../../services/reservation.service';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { IBookingResponse } from '../../../../models/booking.modal';
import { IReservationRequest } from '../../../../models/reservation.modal';
import { NotificationService } from '../../../shared/services/notification.service';

@Component({
  selector: 'app-reservation',
  templateUrl: './reservation.component.html',
  styleUrls: ['./reservation.component.css'],
})
export class ReservationComponent implements OnInit {
  bookingData: IBookingResponse | null = null;
  reservationRequest: IReservationRequest = {
    lastName: '',
    firstName: '',
    email: '',
    phoneNumber: '',
    dateIn: '',
    dateOut: '',
    roomId: 0,
  };
  bookingForm: FormGroup;

  constructor(
    private route: ActivatedRoute,
    private fb: FormBuilder,
    private reservationService: ReservationService,
    private router: Router,
    private notificationService: NotificationService
  ) {
    this.bookingData = history.state.params;
    console.log(this.bookingData);

    this.bookingForm = this.fb.group({
      lastName: ['', Validators.required],
      firstName: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      phoneNumber: ['', Validators.required],
    });

    if (this.bookingData) {
      this.reservationRequest = {
        lastName: '',
        firstName: '',
        email: '',
        phoneNumber: '',
        dateIn: this.bookingData.checkIn,
        dateOut: this.bookingData.checkOut,
        roomId: this.bookingData.room.id,
      };
    }
  }

  ngOnInit(): void {}

  onSubmit(): void {
    if (this.bookingForm.valid) {
      const formValues = this.bookingForm.value;
      if (this.reservationRequest) {
        this.reservationRequest = {
          ...this.reservationRequest,
          ...formValues,
        };
        console.log('Reservation Request:', this.reservationRequest);
        this.reservationService.reserve(this.reservationRequest).subscribe({
          next: (res) => {
            console.log('Reservation created successfully:', res);
            this.notificationService.showNotification('success', 'Reservation created successfully');
            this.router.navigate(['/']);
          },
          error: (err) => {
            console.log('Error creating reservation:', err);
            this.notificationService.showNotification('error', 'Error creating reservation');
          },
        });
      }
    } else {
      console.log('Form is invalid');
    }
  }
}
