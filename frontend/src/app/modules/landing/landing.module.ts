import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HomeComponent } from './pages/home/home.component';
import { AboutComponent } from './pages/about/about.component';
import { LandingNavbarComponent } from './components/landing-navbar/landing-navbar.component';
import { HeroComponent } from './components/hero/hero.component';
import { FooterComponent } from './components/footer/footer.component';
import { BookingComponent } from './components/booking/booking.component';
import { ReactiveFormsModule } from '@angular/forms';



@NgModule({
  declarations: [
    HomeComponent,
    AboutComponent,
    LandingNavbarComponent,
    HeroComponent,
    FooterComponent,
    BookingComponent,
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule
  ],
  exports: [
    HomeComponent,
    AboutComponent,
    LandingNavbarComponent,
    HeroComponent,
    FooterComponent,
    BookingComponent,
  ]
})
export class LandingModule { }
