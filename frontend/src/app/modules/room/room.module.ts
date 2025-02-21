import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RoomMainComponent } from './pages/room-main/room-main.component';
import { RoomViewComponent } from './pages/room-view/room-view.component';
import { MainComponent } from './pages/main/main.component';
import { RoomRoutingModule } from './room-routing.module';
import { SharedModule } from '../shared/shared.module';
import { RoomListComponent } from './components/room-list/room-list.component';
import { RoomFormComponent } from './components/room-form/room-form.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

@NgModule({
  declarations: [
    RoomMainComponent,
    RoomViewComponent,
    MainComponent,
    RoomListComponent,
    RoomFormComponent,
  ],
  imports: [
    CommonModule,
    RoomRoutingModule,
    SharedModule,
    FormsModule,
    ReactiveFormsModule,
  ],
})
export class RoomModule {}
