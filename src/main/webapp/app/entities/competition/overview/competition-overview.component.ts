import { Component, Inject, OnInit } from '@angular/core';
import { ICompetition } from '../competition.model';
import { ActivatedRoute, Router } from '@angular/router';
import { ResultsService } from 'app/entities/results/service/results.service';
import { IResults } from 'app/entities/results/results.model';
import { IAthlete } from 'app/entities/athlete/athlete.model';
import { ResultsDialogComponent } from '../results-dialog/results-dialog.component';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { SeriesDialog2Component } from '../series-dialog-2/series-dialog-2.component';
import * as dayjs from 'dayjs';
import { RoundService } from '../../round/service/round.service';
import { AlertService } from '../../../core/util/alert.service';
import { AccountService } from '../../../core/auth/account.service';
import { RankingListService } from '../service/rankingList.service';
import { TuiNotification, TuiNotificationsService } from '@taiga-ui/core';
import { ConfirmDialogComponent } from '../confirm-dialog/confirm-dialog.component';
import { CompetitionService } from '../service/competition.service';
import { SeriesDialog1Component } from '../series-dialog-1/series-dialog-1.component';
import { CreateAthleteDialogComponent } from '../create-athete-dialog/create-athlete-dialog.component';
import { NewSeriesDialog1Component } from '../new-series-dialog-1/new-series-dialog-1.component';
import { NewSeriesDialog2Component } from '../new-series-dialog-2/new-series-dialog-2.component';
import { UserManagementService } from '../../../admin/user-management/service/user-management.service';

@Component({
  selector: 'jhi-overview',
  templateUrl: './competition-overview.component.html',
  styleUrls: ['./competition-overview.component.scss'],
})
export class CompetitionOverviewComponent implements OnInit {
  competition!: ICompetition;
  liveviewPath = '';

  results: Array<IResults> | null = [];
  resultsRound1: Array<IResults> | null = [];
  resultsRound2: Array<IResults> | null = [];
  resultsRound99: Array<IResults> | null = [];
  athletes: Array<IAthlete> | null = [];

  showFinal = false;

  constructor(
    protected activatedRoute: ActivatedRoute,
    private resultsService: ResultsService,
    private rankingListService: RankingListService,
    private competitionService: CompetitionService,
    protected modalService: NgbModal,
    private roundService: RoundService,
    private alertService: AlertService,
    private accountService: AccountService,
    private userManagementService: UserManagementService,
    private router: Router,
    @Inject(TuiNotificationsService)
    private readonly notificationsService: TuiNotificationsService
  ) {}

  ngOnInit(): void {
    this.liveviewPath = window.location.origin + '/liveview/';

    console.log(this.accountService.getClub());
    console.log(this.accountService.getAuthorites());

    //TODO test this
    this.accountService.identity(true).subscribe(value => console.log(value?.club));
    this.userManagementService.find(this.accountService.getLogin()).subscribe(value => console.log(value.club));

    this.activatedRoute.data.subscribe(({ competition }) => {
      this.competition = competition;
      if (this.userIsVerein()) {
        this.showFinal = false;
        this.resultsService.findByCompetitionAndClub(competition, this.accountService.getClub()).subscribe(res => {
          this.results = res.body;
          this.resultsRound1 = res.body!.filter(results => results.round === 1);
          this.resultsRound2 = res.body!.filter(results => results.round === 2);
          this.resultsRound99 = res.body!.filter(results => results.round === 99);
        });
      } else {
        this.showFinal = true;
        this.resultsService.findByCompetition(competition).subscribe(res => {
          this.results = res.body;
          this.resultsRound1 = res.body!.filter(results => results.round === 1);
          this.resultsRound2 = res.body!.filter(results => results.round === 2);
          this.resultsRound99 = res
            .body!.filter(results => results.round === 99)
            .sort((n1, n2) => {
              if (n1.athleteNumber! > n2.athleteNumber!) {
                return 1;
              }

              if (n1.athleteNumber! < n2.athleteNumber!) {
                return -1;
              }

              return 0;
            });
        });
      }
    });
  }

  createAthlete(competition: ICompetition): void {
    const modalRef = this.modalService.open(CreateAthleteDialogComponent, { size: 'xl', backdrop: 'static' });
    modalRef.componentInstance.competition = competition;
    modalRef.componentInstance.accountClub = this.accountService.getClub();
    modalRef.closed.subscribe(() => {
      this.loadPage();
    });
  }

  adminAthlete(competition: ICompetition): void {
    const modalRef = this.modalService.open(ResultsDialogComponent, { size: 'xl', backdrop: 'static' });
    modalRef.componentInstance.competition = competition;
    modalRef.closed.subscribe(() => {
      this.loadPage();
    });
  }

  exportCompetition(competition: ICompetition): void {
    this.competitionService.exportData(competition).subscribe(data => {
      const blob = new Blob([data], {
        type: 'application/json', // must match the Accept type
      });
      const link = document.createElement('a');
      link.href = window.URL.createObjectURL(blob);
      link.target = '_blank';
      link.download = competition.name! + '_' + competition.year!.toString();
      link.click();
      window.URL.revokeObjectURL(link.href);
    });
  }

  createResult(competition: ICompetition): void {
    if (competition.numberOfSeries === 1) {
      const modalRef = this.modalService.open(NewSeriesDialog1Component, {
        size: 'xl',
        backdrop: 'static',
      });
      modalRef.componentInstance.competition = this.competition;
      modalRef.closed.subscribe(() => {
        this.loadPage();
      });
    } else if (competition.numberOfSeries === 2) {
      const modalRef = this.modalService.open(NewSeriesDialog2Component, {
        size: 'xl',
        backdrop: 'static',
      });
      modalRef.componentInstance.competition = this.competition;
      modalRef.closed.subscribe(() => {
        this.loadPage();
      });
    }
  }

  createResultFinal(competition: ICompetition): void {
    if (competition.numberOfSeries === 1) {
      const modalRef = this.modalService.open(NewSeriesDialog1Component, {
        size: 'xl',
        backdrop: 'static',
      });
      modalRef.componentInstance.competition = this.competition;
      modalRef.componentInstance.final = true;
      modalRef.closed.subscribe(() => {
        this.loadPage();
      });
    } else if (competition.numberOfSeries === 2) {
      const modalRef = this.modalService.open(NewSeriesDialog2Component, {
        size: 'xl',
        backdrop: 'static',
      });
      modalRef.componentInstance.competition = this.competition;
      modalRef.componentInstance.final = true;
      modalRef.closed.subscribe(() => {
        this.loadPage();
      });
    }
  }

  openSeriesDialog(result: IResults): void {
    if (result.competition?.id !== undefined && result.round !== undefined) {
      this.roundService.findByRoundAndCompetition(result.round, result.competition.id).subscribe(res => {
        if (this.userIsZsavOrAdmin()) {
          this.openDialog(result);
        } else if (res.body?.date?.toDate() !== undefined) {
          if (res.body.date.toDate() < dayjs().toDate()) {
            this.notificationsService
              .show(`Resultateingabe geschlossen seit ${res.body.date.toDate().toLocaleDateString('de-DE')}`, {
                label: 'Resultateingabe bereits geschlossen',
                status: TuiNotification.Warning,
              })
              .subscribe();
          } else {
            if (result.round !== 99) {
              this.openDialog(result);
            } else {
              this.notificationsService
                .show('Nicht berechtigt Finalresultat zu bearbeiten', {
                  label: 'Keine Berechtigung',
                  status: TuiNotification.Warning,
                })
                .subscribe();
            }
          }
        }
      });
    }
  }

  openDialog(result: IResults): void {
    if (result.competition?.numberOfSeries === 1) {
      const modalRef = this.modalService.open(SeriesDialog1Component, {
        size: 'xl',
        backdrop: 'static',
      });
      modalRef.componentInstance.result = result;
      modalRef.closed.subscribe(() => {
        this.loadPage();
      });
    } else if (result.competition?.numberOfSeries === 2) {
      const modalRef = this.modalService.open(SeriesDialog2Component, {
        size: 'xl',
        backdrop: 'static',
      });
      modalRef.componentInstance.result = result;
      modalRef.closed.subscribe(() => {
        this.loadPage();
      });
    }
  }

  userIsZsavOrAdmin(): boolean {
    let isZsavOrAdmin = false;
    this.accountService.getAuthorites().forEach(role => {
      if (role === 'ROLE_ADMIN' || role === 'ROLE_ZSAV') {
        isZsavOrAdmin = true;
      }
    });
    return isZsavOrAdmin;
  }

  userIsVerein(): boolean {
    let isVerein = false;
    this.accountService.getAuthorites().forEach(role => {
      if (role === 'ROLE_VEREIN') {
        isVerein = true;
      }
    });
    return isVerein;
  }

  display(b: boolean): boolean {
    return !b;
  }

  getResultsByAthlete(athlete: IAthlete): Array<IResults> {
    if (this.results !== null) {
      return this.results.filter(s => s.athlete?.id === athlete.id).sort((a, b) => <number>a.round - <number>b.round);
    } else {
      return [];
    }
  }

  getResultsByAthleteAndRound(athlete: IAthlete, round: number): number {
    let returnValue = 0;
    if (this.results !== null) {
      this.results
        .filter(s => s.athlete?.id === athlete.id)
        .filter(r => r.round === round)
        .map(value => {
          if (typeof value.result === 'number') {
            returnValue = value.result;
          }
        });
    }
    return returnValue;
  }

  getResultsByAthleteIfRound99(athlete: IAthlete): boolean {
    let returnValue = false;
    if (this.results !== null) {
      returnValue = this.results.filter(s => s.athlete?.id === athlete.id).find(e => e.round === 99) !== undefined;
    }
    return returnValue;
  }

  isFinalReady(): boolean {
    let returnValue = false;
    if (this.results !== null) {
      returnValue = this.results.find(e => e.round === 99) !== undefined;
    }
    return returnValue;
  }

  createFinal(competition: ICompetition): void {
    const modalRef = this.modalService.open(ConfirmDialogComponent, { size: 'xl', backdrop: 'static' });
    modalRef.componentInstance.competition = competition;
    modalRef.closed.subscribe(() => {
      this.loadPage();
    });
  }

  private loadPage(): void {
    const currentUrl = this.router.url;
    this.router.navigateByUrl('/', { skipLocationChange: true }).then(() => {
      this.router.navigate([currentUrl]);
    });
    /*this.activatedRoute.data.subscribe(({ competition }) => {
      this.competition = competition;
      this.resultsService.findByCompetition(competition).subscribe((res: HttpResponse<Array<IResultate>>) => {
        this.resultate = res.body;

        const tempSchuetzen: Array<ISchuetze> = [];
        this.resultate?.forEach(value => {
          if (value.schuetze) {
            tempSchuetzen.push(value.schuetze);
          }
        });

        this.schuetzen = tempSchuetzen.filter((s, i, arr) => arr.indexOf(<ISchuetze>arr.find(t => t.id === s.id)) === i);
      });
    });*/
  }
}
