import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';

import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/auth/account.model';
import { IWettkampf, Wettkampf } from '../entities/wettkampf/wettkampf.model';
import { WettkampfService } from '../entities/wettkampf/service/wettkampf.service';
import { RundeService } from '../entities/runde/service/runde.service';
import { IRunde, Runde } from '../entities/runde/runde.model';

@Component({
  selector: 'jhi-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
})
export class HomeComponent implements OnInit, OnDestroy {
  account: Account | null = null;
  wettkampfList: Array<Wettkampf> | null = null;
  rundeList: Array<Runde> = [];
  breakpoint: any;

  private readonly destroy$ = new Subject<void>();

  constructor(
    private accountService: AccountService,
    private router: Router,
    private wettkampfService: WettkampfService,
    private rundeService: RundeService
  ) {}

  ngOnInit(): void {
    this.breakpoint = window.innerWidth <= 990 ? 1 : 3;
    this.accountService
      .getAuthenticationState()
      .pipe(takeUntil(this.destroy$))
      .subscribe(account => {
        this.account = account;
      });
    const fullYear = new Date().getFullYear();
    this.wettkampfService.findByJahr(fullYear).subscribe(result => {
      this.wettkampfList = result.body;

      this.wettkampfList?.forEach(wettkampf => {
        if (wettkampf.id !== undefined) {
          this.rundeService.findByWettkampf(wettkampf.id).subscribe(runde => {
            if (runde.body !== null) {
              runde.body.forEach(round => {
                this.rundeList.push(round);
              });
            }
          });
        }
      });
    });
  }

  openLiveView(wettkampf: IWettkampf): void {
    if (wettkampf.id !== undefined) {
      this.router.navigate([`/live/${wettkampf.id}`]);
    }
  }

  onResize(event: any): any {
    this.breakpoint = event.target.innerWidth <= 400 ? 1 : 6;
  }

  login(): void {
    this.router.navigate(['/login']);
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  getList(wettkampf: Wettkampf): IRunde[] {
    return this.rundeList.filter(runde => runde.wettkampf!.id === wettkampf.id);
  }
}
