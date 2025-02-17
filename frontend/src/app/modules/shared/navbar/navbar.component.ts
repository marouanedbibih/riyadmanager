import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../auth/auth.service';
import { IUser } from '../../auth/auth.model';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css',
})
export class NavbarComponent implements OnInit {
  constructor(private authService: AuthService) {}

  user: IUser = {
    username: '',
    lastName: '',
    firstName: '',
    role: null,
  };

  logout() {
    this.authService.logout();
  }

  ngOnInit(): void {
    const user = this.authService.getUserFromToken();
    if (user) {
      this.user = user;
    }
  }
}
