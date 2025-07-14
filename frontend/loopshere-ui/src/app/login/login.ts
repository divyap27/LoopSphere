import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common'; // ✅ Needed for *ngIf

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule, CommonModule], // ✅ Add CommonModule
  templateUrl: './login.html',
  styleUrls: ['./login.css']
})
export class LoginComponent {
  loginData = {
    email: '',
    password: '',
    rememberMe: false
  };

  onLogin() {
    if (this.loginData.email && this.loginData.password) {
      console.log('✅ Logging in with:', this.loginData);
      // TODO: Backend login API
    } else {
      console.warn('❌ Email and password are required');
    }
  }
}
