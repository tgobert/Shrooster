import { HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, UntypedFormControl, UntypedFormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { AppComponent } from 'src/app/app.component';
import { User } from 'src/app/models/user';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  errorMessage: string = "";
  loginForm = new UntypedFormGroup({
    email: new UntypedFormControl(''),
    password: new UntypedFormControl('')
  })

  hide = true;

  constructor(private authService: AuthService, private router: Router) { }

  user: User = new User(0, "", "", "", "", "");

  ngOnInit(): void {
  }

  onSubmit(): void {
    console.log(this.loginForm.get('email')?.value);
    console.log(this.loginForm.get('password')?.value);
    this.authService.login(this.loginForm.get('email')?.value, this.loginForm.get('password')?.value).subscribe({
      next: (response: HttpResponse<any>) => {
        console.log(response);
        this.authService.loggedIn=true;
        window.sessionStorage.setItem("isLoggedIn", String(true));
        let token = response.body.tokenId;
        console.log(token);
        if (token != null){
          window.sessionStorage.setItem('token', String(token));
        }
        let userId = response.body?.userId;
        console.log(userId);
        if (userId != null){
          window.sessionStorage.setItem('userId', String(userId));
        }
      },
      error: (err) => {console.log(err); this.errorMessage="Invalid Email and Password"},
      complete: () => this.router.navigate(['home'])
    });
  }

  register(): void {
    this.router.navigate(['register']);
  }

}
