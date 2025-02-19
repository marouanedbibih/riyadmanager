import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { of, throwError } from 'rxjs';
import { FormComponent } from './form.component';
import { GuestService } from '../../guest.service';
import { NotificationService } from '../../../shared/services/notification.service';
import { IGuest } from '../../guest.model';

describe('FormComponent', () => {
  let component: FormComponent;
  let fixture: ComponentFixture<FormComponent>;
  let guestService: jasmine.SpyObj<GuestService>;
  let notificationService: jasmine.SpyObj<NotificationService>;

  beforeEach(async () => {
    const guestServiceSpy = jasmine.createSpyObj('GuestService', ['createGuest', 'updateGuest', 'fetchGuestById']);
    const notificationServiceSpy = jasmine.createSpyObj('NotificationService', ['showNotification']);

    await TestBed.configureTestingModule({
      declarations: [FormComponent],
      imports: [ReactiveFormsModule],
      providers: [
        { provide: GuestService, useValue: guestServiceSpy },
        { provide: NotificationService, useValue: notificationServiceSpy }
      ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FormComponent);
    component = fixture.componentInstance;
    guestService = TestBed.inject(GuestService) as jasmine.SpyObj<GuestService>;
    notificationService = TestBed.inject(NotificationService) as jasmine.SpyObj<NotificationService>;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should initialize the form', () => {
    expect(component.guestForm).toBeDefined();
    expect(component.guestForm.controls['firstName']).toBeDefined();
    expect(component.guestForm.controls['lastName']).toBeDefined();
    expect(component.guestForm.controls['username']).toBeDefined();
    expect(component.guestForm.controls['email']).toBeDefined();
    expect(component.guestForm.controls['phone']).toBeDefined();
  });

  it('should show notification on invalid form submission', () => {
    component.onSubmit();
    expect(notificationService.showNotification).toHaveBeenCalledWith('error', 'Please fill out the form correctly.');
  });

  it('should call createGuest on valid form submission', () => {
    component.guestForm.setValue({
      firstName: 'John',
      lastName: 'Doe',
      username: 'johndoe',
      email: 'john.doe@example.com',
      phone: '+1234567890'
    });
    guestService.createGuest.and.returnValue(of({
      id: 1,
      firstName: 'John',
      lastName: 'Doe',
      username: 'johndoe',
      email: 'john.doe@example.com',
      phone: '+1234567890'
    }));

    component.onSubmit();

    expect(guestService.createGuest).toHaveBeenCalled();
    expect(notificationService.showNotification).toHaveBeenCalledWith('success', 'Guest created successfully');
  });

  it('should call updateGuest on valid form submission when updating', () => {
    component.isOnUpdate = true;
    component.guestId = 1;
    component.guestForm.setValue({
      firstName: 'John',
      lastName: 'Doe',
      username: 'johndoe',
      email: 'john.doe@example.com',
      phone: '+1234567890'
    });
    guestService.updateGuest.and.returnValue(of({
      id: 1,
      firstName: 'John',
      lastName: 'Doe',
      username: 'johndoe',
      email: 'john.doe@example.com',
      phone: '+1234567890'
    }));

    component.onSubmit();

    expect(guestService.updateGuest).toHaveBeenCalledWith(1, component.guestForm.value);
    expect(notificationService.showNotification).toHaveBeenCalledWith('success', 'Guest updated successfully');
  });

  it('should handle error on form submission', () => {
    component.guestForm.setValue({
      firstName: 'John',
      lastName: 'Doe',
      username: 'johndoe',
      email: 'john.doe@example.com',
      phone: '+1234567890'
    });
    guestService.createGuest.and.returnValue(throwError({ error: [{ message: 'Error' }] }));

    component.onSubmit();

    expect(notificationService.showNotification).toHaveBeenCalledWith('error', 'Error');
  });

  it('should open and close the modal', () => {
    const modal = document.createElement('dialog');
    modal.id = 'guest_form_modal';
    document.body.appendChild(modal);

    component.openModal();
    expect(modal.open).toBeTrue();

    component.closeModal();
    expect(modal.open).toBeFalse();

    document.body.removeChild(modal);
  });

  it('should fetch guest by id and patch the form', () => {
    const guest: IGuest = {
      id: 1,
      firstName: 'John',
      lastName: 'Doe',
      username: 'johndoe',
      email: 'john.doe@example.com',
      phone: '+1234567890'
    };
    guestService.fetchGuestById.and.returnValue(of(guest));

    component.onInitUpdate(1);

    expect(guestService.fetchGuestById).toHaveBeenCalledWith(1);
    expect(component.guestForm.value).toEqual(guest);
    expect(component.isOnUpdate).toBeTrue();
    expect(component.guestId).toBe(1);
  });
});
