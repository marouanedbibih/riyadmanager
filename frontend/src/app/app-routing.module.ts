import { Register } from './../../node_modules/any-promise/register.d';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './modules/auth/pages/login/login.component';
import { RegisterComponent } from './modules/auth/pages/register/register.component';
import { HomeComponent } from './modules/landing/pages/home/home.component';
import { AboutComponent } from './modules/landing/pages/about/about.component';
import { ReservationComponent } from './modules/landing/pages/reservation/reservation.component';
import { NoAuthGuard } from './guard/no-auth.guard';
import { RoleGuard } from './guard/role.guard';

const routes: Routes = [
  { path: 'login', component: LoginComponent, canActivate: [NoAuthGuard] },
  {
    path: 'register',
    component: RegisterComponent,
    canActivate: [NoAuthGuard],
  },
  { path: '', component: HomeComponent },
  { path: 'about', component: AboutComponent },
  { path: 'reservation', component: ReservationComponent },
  {
    path: 'guests',
    loadChildren: () =>
      import('./modules/guest/guest.module').then((m) => m.GuestModule),
    canActivate: [RoleGuard],
    data: { roles: ['ADMIN', 'MANAGER'] },
  },
  {
    path: 'room',
    loadChildren: () =>
      import('./modules/room/room.module').then((m) => m.RoomModule),
    canActivate: [RoleGuard],
    data: { roles: ['ADMIN', 'MANAGER'] },
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
