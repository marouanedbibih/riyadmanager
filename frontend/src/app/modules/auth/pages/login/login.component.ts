import { Component, OnInit } from '@angular/core';
import { ILoginREQ } from '../../auth.model';
import { AuthService } from '../../auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
})
export class LoginComponent implements OnInit {
  constructor(private authService: AuthService, private router: Router) {}

  request: ILoginREQ = {
    username: '',
    password: '',
  };

  ngOnInit(): void {}

  onSubmit(): void {
    console.log(this.request);
    this.authService.login(this.request).subscribe({
      next: (res) => {
        console.log(res);
        this.authService.saveToken(res.token);
        this.router.navigate(['/guests']);
      },
      error: (err) => {
        console.log(err);
      },
    });
  }
}
