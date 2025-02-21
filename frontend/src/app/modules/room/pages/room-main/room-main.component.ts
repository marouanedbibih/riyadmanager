import { Component, OnInit, ViewChild } from '@angular/core';
import { RoomListComponent } from '../../components/room-list/room-list.component';
import { Room } from '../../room.model';

@Component({
  selector: 'app-room-main',
  templateUrl: './room-main.component.html',
  styleUrls: ['./room-main.component.css']
})
export class RoomMainComponent implements OnInit {
  @ViewChild(RoomListComponent) roomListComponent!: RoomListComponent;
  roomToEdit: Room | null = null;

  constructor() {}

  ngOnInit(): void {}

  onFetchRooms(): void {
    this.roomListComponent.onFetchRooms();
  }

  onEditRoom(room: Room): void {
    this.roomToEdit = room;
  }
}
