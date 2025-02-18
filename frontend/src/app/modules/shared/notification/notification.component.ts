import { Component } from '@angular/core';
import { NotificationService, Notification } from '../services/notification.service';

@Component({
  selector: 'app-notification',
  templateUrl: './notification.component.html',
  styleUrls: ['./notification.component.scss'],
})
export class NotificationComponent {
  notification: Notification | null = null;

  constructor(private notificationService: NotificationService) {
    this.notificationService.notification$.subscribe((notification) => {
      this.notification = notification;
    });
  }
}
