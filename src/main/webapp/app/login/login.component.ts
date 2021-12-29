import { AfterViewInit, Component, ElementRef, Inject, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

import { LoginService } from 'app/login/login.service';
import { AccountService } from 'app/core/auth/account.service';
import { TuiNotification, TuiNotificationsService } from '@taiga-ui/core';

@Component({
  selector: 'jhi-login',
  templateUrl: './login.component.html',
})
export class LoginComponent implements OnInit, AfterViewInit {
  @ViewChild('username', { static: false })
  username!: ElementRef;

  authenticationError = false;

  loginForm = this.fb.group({
    username: [null, [Validators.required]],
    password: [null, [Validators.required]],
    rememberMe: [false],
  });

  testForm = new FormGroup({
    usernameValue: new FormControl('', Validators.required),
    passwordValue: new FormControl('', Validators.required),
    rememberMeValue: new FormControl(false),
  });

  constructor(
    private accountService: AccountService,
    private loginService: LoginService,
    private router: Router,
    private fb: FormBuilder,
    @Inject(TuiNotificationsService)
    private readonly notificationsService: TuiNotificationsService
  ) {}

  ngOnInit(): void {
    // if already authenticated then navigate to home page
    this.accountService.identity().subscribe(() => {
      if (this.accountService.isAuthenticated()) {
        this.router.navigate(['']);
      }
    });
  }

  ngAfterViewInit(): void {
    this.username.nativeElement.focus();
  }

  login(): void {
    this.loginService
      .login({
        username: this.testForm.get('usernameValue')!.value,
        password: this.testForm.get('passwordValue')!.value,
        rememberMe: this.testForm.get('rememberMeValue')!.value,
      })
      .subscribe(
        res => {
          this.authenticationError = false;
          if (!this.router.getCurrentNavigation()) {
            // There were no routing during login (eg from navigationToStoredUrl)
            this.router.navigate(['']);
          }
          this.notificationsService
            .show(`Angemeldet mit User ${res!.login}`, {
              label: 'Erfolgreich angemeldet',
              status: TuiNotification.Success,
            })
            .subscribe();
        },
        res => {
          this.authenticationError = true;
          this.notificationsService
            .show('', {
              label: `Fehler beim Anmelden`,
              status: TuiNotification.Error,
            })
            .subscribe();
        }
      );
  }
}
