import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { GuestService } from '../../guest.service';
import { NotificationService } from '../../../shared/services/notification.service';
import { finalize } from 'rxjs';
import { IGuest, IGuestREQ } from '../../guest.model';

@Component({
  selector: 'app-form',
  templateUrl: './form.component.html',
  styleUrls: ['./form.component.css'],
})
export class FormComponent implements OnInit {
  guestForm!: FormGroup;
  formLoading = {
    button: false,
    body: false,
  };
  isOnUpdate = false;
  guestId: number | null = null;

  constructor(
    private fb: FormBuilder,
    private guestService: GuestService,
    private notificationService: NotificationService
  ) {}

  ngOnInit(): void {
    this.initializeForm();
  }

  initializeForm(): void {
    this.guestForm = this.fb.group({
      firstName: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(50)]],
      lastName: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(50)]],
      username: ['', [Validators.required, Validators.minLength(4), Validators.maxLength(30)]],
      email: ['', [Validators.required, Validators.email]],
      phone: ['', [Validators.required, Validators.pattern('^\\+?[0-9]{10,15}$')]],
    });
  }

  onSubmit(): void {
    if (this.guestForm.valid) {
      this.isOnUpdate ? this.onUpdateGuest(this.guestForm.value) : this.onCreateGuest(this.guestForm.value);
    } else {
      this.notificationService.showNotification('error', 'Please fill out the form correctly.');
    }
  }

  onCreateGuest(req: IGuestREQ): void {
    this.setLoadingState(true);
    this.guestService.createGuest(req)
      .pipe(finalize(() => this.setLoadingState(false)))
      .subscribe({
        next: () => this.handleSuccess('Guest created successfully'),
        error: (err) => this.handleError(err),
      });
  }

  onUpdateGuest(req: IGuestREQ): void {
    if (this.guestId === null) return;
    this.setLoadingState(true);
    this.guestService.updateGuest(this.guestId, req)
      .pipe(finalize(() => this.setLoadingState(false)))
      .subscribe({
        next: () => this.handleSuccess('Guest updated successfully'),
        error: (err) => this.handleError(err),
      });
  }

  onInitUpdate(id: number): void {
    this.setLoadingState(true, 'body');
    this.guestService.fetchGuestById(id)
      .pipe(finalize(() => this.setLoadingState(false, 'body')))
      .subscribe({
        next: (res) => {
          this.guestForm.patchValue(res);
          this.isOnUpdate = true;
          this.guestId = id;
          this.openModal();
        },
        error: (err) => this.handleError(err),
      });
  }

  setLoadingState(state: boolean, type: 'button' | 'body' = 'button'): void {
    this.formLoading[type] = state;
  }

  handleSuccess(message: string): void {
    this.notificationService.showNotification('success', message);
    this.onReset();
    this.closeModal();
  }

  handleError(err: any): void {
    console.error(err);
    this.notificationService.showNotification('error', err.error[0].message);
  }

  onReset(): void {
    this.guestForm.reset();
    this.isOnUpdate = false;
    this.guestId = null;
  }

  closeModal(): void {
    const modal = document.getElementById('guest_form_modal') as HTMLDialogElement;
    modal.close();
  }

  openModal(): void {
    const modal = document.getElementById('guest_form_modal') as HTMLDialogElement;
    modal.showModal();
  }
}
