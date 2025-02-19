import { Component, OnInit, ViewChild } from '@angular/core';
import { IGuest } from '../../guest.model';
import { GuestService } from '../../guest.service';
import { IFetchParams, IPageRES } from '../../../../types';
import { finalize } from 'rxjs';
import { NotificationService } from '../../../shared/services/notification.service';
import { DeleteModalComponent } from '../../../shared/delete-modal/delete-modal.component';

@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.css'],
})
export class ListComponent implements OnInit {
  constructor(
    private guestService: GuestService,
    private notificationService: NotificationService
  ) {}

  @ViewChild(DeleteModalComponent) deleteModal!: DeleteModalComponent;
  guests: IGuest[] = [];
  fetchParams: IFetchParams = {
    page: 1,
    size: 5,
    sortBy: 'id',
    orderBy: 'desc',
  };
  isLoading: boolean = false;
  totalPages: number = 0;
  guestIdToDelete: number | null = null;

  ngOnInit(): void {
    this.fetchGuests(this.fetchParams);
    // this.notificationService.showTestNotification();
  }

  fetchGuests(params: IFetchParams): void {
    this.isLoading = true;
    this.guestService
      .fetchAllGuests(params)
      .pipe(
        finalize(() => {
          this.isLoading = false;
        })
      )
      .subscribe({
        next: (res: IPageRES<IGuest>) => {
          this.guests = res.content;
          this.totalPages = res.totalPages; // Ensure totalPages is updated dynamically
          this.fetchParams = { ...params }; // Maintain pagination state
        },
        error: (err) => {
          console.log(err);
        },
      });
  }

  onPageChange(newPage: number): void {
    this.fetchGuests({ ...this.fetchParams, page: newPage });
  }

  openDeleteModal(guestId: number): void {
    this.guestIdToDelete = guestId;
    this.deleteModal.openModal();
  }

  onDeleteConfirmed(): void {
    if (this.guestIdToDelete !== null) {
      this.guestService.deleteGuest(this.guestIdToDelete).subscribe({
        next: () => {
          this.notificationService.showNotification(
            'success',
            'Guest deleted successfully'
          );
          this.fetchGuests(this.fetchParams);
        },
        error: (err) => {
          console.error(err);
          this.notificationService.showNotification(
            'error',
            'Failed to delete guest'
          );
        },
      });
    }
  }
}
