import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { RoomService } from '../../service/room.service';
import { Room } from '../../room.model';
import { finalize } from 'rxjs';
import { NotificationService } from '../../../shared/services/notification.service';

@Component({
  selector: 'app-room-list',
  templateUrl: './room-list.component.html',
  styleUrls: ['./room-list.component.css'],
})
export class RoomListComponent implements OnInit {
  @Output() editRoom = new EventEmitter<Room>();

  rooms: Room[] = [];
  isLoading: boolean = false;

  constructor(
    private roomService: RoomService,
    private notificationService: NotificationService
  ) {}

  ngOnInit(): void {
    this.onFetchRooms();
  }

  onFetchRooms(): void {
    this.isLoading = true;
    this.roomService
      .listRooms()
      .pipe(
        finalize(() => {
          this.isLoading = false;
        })
      )
      .subscribe({
        next: (rooms) => {
          this.rooms = rooms;
        },
        error: (error) => {
          console.error(error);
        },
      });
  }

  onEditRoom(room: Room): void {
    this.editRoom.emit(room);
  }

  onDeleteRoom(roomId: number): void {
    this.roomService
      .deleteRoom(roomId)
      .pipe(
        finalize(() => {
          this.isLoading = false;
        })
      )
      .subscribe({
        next: () => {
          this.onFetchRooms();
          this.notificationService.showNotification('success', 'Room deleted');
        },
        error: (error) => {
          console.error(error);
          this.notificationService.showNotification('error', 'Failed to delete room');
        },
      });
  }
}
