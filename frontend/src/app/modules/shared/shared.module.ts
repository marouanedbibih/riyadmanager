import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavbarComponent } from './navbar/navbar.component';
import { PaginationComponent } from './pagination/pagination.component';
import { NotificationComponent } from './notification/notification.component';



@NgModule({
  declarations: [
    NavbarComponent,
    PaginationComponent,
    NotificationComponent,
  ],
  imports: [
    CommonModule
  ],
  exports: [
    NavbarComponent,
    PaginationComponent,
    NotificationComponent
  ]
})
export class SharedModule { }
