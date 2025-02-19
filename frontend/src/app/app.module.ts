import { SharedModule } from './modules/shared/shared.module';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AuthModule } from './modules/auth/auth.module';
import { JwtModule } from '@auth0/angular-jwt';
import { LandingModule } from './modules/landing/landing.module';


@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    AuthModule,
    SharedModule,
    LandingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
