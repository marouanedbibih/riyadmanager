import { IFormLoading } from './../../../../types';
import { Component } from '@angular/core';
import { IGuestREQ } from '../../guest.model';
import { GuestService } from '../../guest.service';
import { finalize } from 'rxjs';
import { NotificationService } from '../../../shared/services/notification.service';

@Component({
  selector: 'app-form',
  templateUrl: './form.component.html',
  styleUrl: './form.component.css',
})
export class FormComponent {
  request: IGuestREQ = {
    email: '',
    username: '',
    firstName: '',
    lastName: '',
    phone: '',
  };

  formLoading: IFormLoading = {
    button: false,
    body: false,
  };

  constructor(
    private guestService: GuestService,
    private notifcationService: NotificationService
  ) {}

  onSubmit(): void {
    console.log(this.request);
    this.onCreateGuest(this.request);
  }

  onCreateGuest(req: IGuestREQ): void {
    this.formLoading.button = true;
    this.guestService
      .createGuest(req)
      .pipe(finalize(() => (this.formLoading.button = false)))
      .subscribe({
        next: (res) => {
          // this.onReset();
          this.notifcationService.showNotification("success", "Guest created successfully");
          console.log(res);
        },
        error: (error) => {
          console.error(error);
        }
      });
  }

  onReset(): void {
    this.request = {
      email: '',
      username: '',
      firstName: '',
      lastName: '',
      phone: '',
    };
  }
}
