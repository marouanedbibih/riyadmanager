import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { RoomStatus, RoomType, Room } from '../../room.model';
import { RoomService } from '../../service/room.service';
import { finalize } from 'rxjs';
import { NotificationService } from '../../../shared/services/notification.service';

@Component({
  selector: 'app-room-form',
  templateUrl: './room-form.component.html',
  styleUrls: ['./room-form.component.css'],
})
export class RoomFormComponent implements OnInit {
  @Output() roomCreated = new EventEmitter<void>();
  @Input() set roomToEdit(room: Room | null) {
    if (room) {
      this.roomIdToUpdate = room.id;
      this.roomForm.patchValue(room);
    } else {
      this.roomIdToUpdate = null;
      this.roomForm.reset();
    }
  }

  roomForm!: FormGroup;
  roomTypes = Object.values(RoomType);
  roomStatuses = Object.values(RoomStatus);
  formLoading = { button: false };
  roomIdToUpdate: number | null = null;

  constructor(
    private fb: FormBuilder,
    private roomService: RoomService,
    private notificationService: NotificationService
  ) {}

  ngOnInit(): void {
    this.roomForm = this.fb.group({
      number: ['', Validators.required],
      roomType: ['', Validators.required],
      status: ['', Validators.required],
    });
  }

  onSubmit(): void {
    if (this.roomForm.valid) {
      if (this.roomIdToUpdate) {
        this.onUpdate();
      } else {
        this.onCreate();
      }
    }
  }

  onCreate(): void {
    this.formLoading.button = true;
    this.roomService
      .createRoom(this.roomForm.value)
      .pipe(
        finalize(() => {
          this.formLoading.button = false;
        })
      )
      .subscribe({
        next: (response) => {
          console.log(response);
          this.roomForm.reset();
          this.notificationService.showNotification("success", "Room created successfully");
          this.roomCreated.emit();
        },
        error: (error) => {
          console.error(error);
          this.notificationService.showNotification("error", "Failed to create room");
        },
      });
  }

  onUpdate(): void {
    this.formLoading.button = true;
    this.roomService
      .updateRoom(this.roomIdToUpdate!, this.roomForm.value)
      .pipe(
        finalize(() => {
          this.formLoading.button = false;
        })
      )
      .subscribe({
        next: (response) => {
          console.log(response);
          this.roomForm.reset();
          this.notificationService.showNotification("success", "Room updated successfully");
          this.roomCreated.emit();
        },
        error: (error) => {
          console.error(error);
          this.notificationService.showNotification("error", "Failed to update room");
        },
      });
  }
}
