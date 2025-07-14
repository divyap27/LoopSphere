import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common'; // ✅ Needed for *ngIf

@Component({
  selector: 'app-signup',
  standalone: true,
  imports: [FormsModule, CommonModule], // ✅ Add CommonModule here
  templateUrl: './signup.html',
  styleUrls: ['./signup.css']
})
export class Signup {
  signupData = {
    fullName: '',
    email: '',
    phone: '',
    password: '',
    confirmPassword: '',
    termsAccepted: false
  };

  onSubmit() {
    if (
      this.signupData.fullName &&
      this.signupData.email &&
      this.signupData.phone &&
      this.signupData.password &&
      this.signupData.confirmPassword &&
      this.signupData.password === this.signupData.confirmPassword &&
      this.signupData.termsAccepted
    ) {
      console.log('✅ Signup successful', this.signupData);
    } else {
      console.warn('❌ Invalid form data');
    }
  }
}
