import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AuthModule } from './modules/auth/auth.module';
import { JwtModule } from '@auth0/angular-jwt';


@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    AuthModule,
    // JwtModule.forRoot({
    //   config: {
    //     tokenGetter: () => localStorage.getItem('token'),
    //   },
    // }),
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
