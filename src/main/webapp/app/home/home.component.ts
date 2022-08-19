import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';

import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/auth/account.model';
import { Competition, ICompetition } from '../entities/competition/competition.model';
import { CompetitionService } from '../entities/competition/service/competition.service';
import { RoundService } from '../entities/round/service/round.service';
import { IRound, Round } from '../entities/round/round.model';
import { CompetitionType } from '../entities/enumerations/competitionType.model';

@Component({
  selector: 'jhi-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
})
export class HomeComponent implements OnInit, OnDestroy {
  account: Account | null = null;
  competitionList: Array<Competition> | null = null;
  roundList: Array<Round> = [];
  breakpoint: any;

  open = false;

  index = 0;

  get background(): string {
    switch (this.index) {
      case 0:
        return 'url(../../content/images/anleitung/Wettkampf_auswaehlen.jpg)';
      case 1:
        return 'url(../../content/images/anleitung/Ubersicht_Schuetzen.jpg)';
      case 2:
        return 'url(../../content/images/anleitung/Runde_auswaehlen.jpg)';
      case 3:
        return 'url(../../content/images/anleitung/Resultat_eingeben.jpg)';
      case 4:
        return 'url(../../content/images/anleitung/Resultat_eingegeben.jpg)';
      default:
        return 'url(../../content/images/anleitung/Wettkampf_auswaehlen.jpg)';
    }
  }

  private readonly destroy$ = new Subject<void>();

  constructor(
    private accountService: AccountService,
    private router: Router,
    private competitionService: CompetitionService,
    private roundService: RoundService
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
    this.competitionService.findByYear(fullYear).subscribe(result => {
      this.competitionList = result.body;

      this.competitionList?.forEach(competition => {
        if (competition.id !== undefined) {
          this.roundService.findByCompetition(competition.id).subscribe(round => {
            if (round.body !== null) {
              round.body.forEach(round1 => {
                this.roundList.push(round1);
              });
            }
          });
        }
      });
    });
  }

  routeTo(competitionId: number | undefined): void {
    this.competitionService.find(competitionId!).subscribe(res => {
      if (res.body!.competitionType === CompetitionType.ZSAV_NAWU) {
        this.router.navigate(['/competition', competitionId, 'overview']);
      } else if (res.body!.competitionType === CompetitionType.ZSAV_NAWU_GM) {
        this.router.navigate(['/competition', competitionId, 'overview-zsav-nawu-gm']);
      } else if (res.body!.competitionType === CompetitionType.EASV_WORLDCUP) {
        this.router.navigate(['/competition', competitionId, 'overview-easv-worldcup']);
      }
    });
  }

  openAnleitung(): void {
    this.index = 0;
    this.open = true;
  }

  navigate(delta: number): void {
    this.index = (this.index + delta) % 5;
  }

  openLiveView(competition: ICompetition): void {
    if (competition.id !== undefined) {
      if (competition.competitionType === CompetitionType.ZSAV_NAWU) {
        this.router.navigate([`/live/${competition.id}`]);
      } else if (competition.competitionType === CompetitionType.EASV_WORLDCUP) {
        this.router.navigate(['/live', competition.id, 'live-easv-worldcup']);
      } else if (competition.competitionType === CompetitionType.ZSAV_NAWU_GM) {
        this.router.navigate(['/live', competition.id, 'live-zsav-nawu-gm']);
      }
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

  getList(competition: Competition): IRound[] {
    return this.roundList.filter(round => round.competition!.id === competition.id);
  }
}
