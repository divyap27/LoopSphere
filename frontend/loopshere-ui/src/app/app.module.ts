import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms'; // For ngModel
import { App } from './app'; // Import standalone AppComponent
import { Signup } from './signup/signup'; // Import standalone SignupComponent

@NgModule({
  declarations: [], // Remove App and Signup from declarations
  imports: [
    BrowserModule,
    FormsModule, // Required for ngModel
    App, // Import standalone component
    Signup // Import standalone component
  ],
  bootstrap: [App]
})
export class AppModule { }