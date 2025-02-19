import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavbarComponent } from './navbar/navbar.component';
import { PaginationComponent } from './pagination/pagination.component';
import { NotificationComponent } from './notification/notification.component';
import { DeleteModalComponent } from './delete-modal/delete-modal.component';



@NgModule({
  declarations: [
    NavbarComponent,
    PaginationComponent,
    NotificationComponent,
    DeleteModalComponent,
  ],
  imports: [
    CommonModule
  ],
  exports: [
    NavbarComponent,
    PaginationComponent,
    NotificationComponent,
    DeleteModalComponent
  ]
})
export class SharedModule { }
