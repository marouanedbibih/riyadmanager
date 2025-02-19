import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

export type NotificationType = 'success' | 'warning' | 'error';

export interface Notification {
  type: NotificationType;
  message: string;
}

@Injectable({
  providedIn: 'root',
})
export class NotificationService {
  private notificationSubject = new BehaviorSubject<Notification | null>(null);
  notification$ = this.notificationSubject.asObservable();

  showNotification(type: NotificationType, message: string): void {
    this.notificationSubject.next({ type, message });
    setTimeout(() => {
      this.clearNotification();
    }, 5000);

    this.notificationSubject.next({ type, message });
  }

  clearNotification(): void {
    this.notificationSubject.next(null);
  }

  
}
