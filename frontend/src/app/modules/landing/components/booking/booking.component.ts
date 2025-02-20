import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { BookingService } from '../../../../services/booking.service';
import { IBookingRequest, RoomType } from '../../../../models/booking.modal';

@Component({
  selector: 'app-booking',
  templateUrl: './booking.component.html',
  styleUrls: ['./booking.component.css'],
})
export class BookingComponent implements OnInit {
  bookingForm: FormGroup;
  roomTypes = Object.values(RoomType);
  modalMessage: string = '';
  showReservationButton: boolean = false;

  @ViewChild('modal') modal!: ElementRef;

  constructor(private fb: FormBuilder, private bookingService: BookingService) {
    this.bookingForm = this.fb.group({
      checkIn: [new Date(), Validators.required],
      checkOut: [new Date(), Validators.required],
      type: [RoomType.SINGLE, Validators.required],
    });
  }

  ngOnInit(): void {}

  onSubmit(): void {
    this.showReservationButton = false;
    if (this.bookingForm.valid) {
      const request: IBookingRequest = this.bookingForm.value;
      this.bookingService.bookingAPI(request).subscribe(
        (rooms) => {
          if (rooms.length > 0) {
            this.modalMessage = 'Rooms available. Reserve now!';
            this.showReservationButton = true;
          }
          this.modal.nativeElement.showModal();
        },
        (error) => {
          this.modalMessage = 'No rooms available for the selected dates.';
          this.modal.nativeElement.showModal();
        }
      );
    } else {
      console.log('Form is invalid');
    }
  }

  closeModal(): void {
    this.modal.nativeElement.close();
  }
}
