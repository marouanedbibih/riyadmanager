import { Component, OnInit } from '@angular/core';
import { IGuest } from '../../guest.model';
import { GuestService } from '../../guest.service';
import { IFetchParams, IPageRES } from '../../../../types';

@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrl: './list.component.css',
})
export class ListComponent implements OnInit {
  constructor(private guestService: GuestService) {}

  guests: IGuest[] = [];
  fetchParams: IFetchParams = {};

  ngOnInit(): void {
    // fetch all guests
  }

  fetchGuests(): void {
    this.guestService.fetchAllGuests(this.fetchParams).subscribe({
      next: (res: IPageRES<IGuest>) => {
        this.guests = res.content;
      },
      error: (err) => {
        console.log(err);
      },
    });
  }
}
