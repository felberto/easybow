import { Component, Inject, OnInit } from '@angular/core';
import { ICompetition } from '../competition.model';
import { ActivatedRoute, Router } from '@angular/router';
import { ResultsService } from 'app/entities/results/service/results.service';
import { IResults } from 'app/entities/results/results.model';
import { IAthlete } from 'app/entities/athlete/athlete.model';
import { ResultsDialogComponent } from '../results-dialog/results-dialog.component';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { RoundService } from '../../round/service/round.service';
import { AlertService } from '../../../core/util/alert.service';
import { Account } from 'app/core/auth/account.model';
import { AccountService } from '../../../core/auth/account.service';
import { RankingListService } from '../service/rankingList.service';
import { TuiNotification, TuiNotificationsService } from '@taiga-ui/core';
import { CompetitionService } from '../service/competition.service';
import { Group, IGroup } from '../../group/group.model';
import { GroupService } from '../../group/service/group.service';
import { SeriesDialog2GroupComponent } from '../series-dialog-2-group/series-dialog-2.component';
import { IRound } from '../../round/round.model';
import { CreateAthleteDialogComponent } from '../create-athete-dialog/create-athlete-dialog.component';
import { ClubService } from '../../club/service/club.service';
import * as dayjs from 'dayjs';
import { SeriesDialog1Component } from '../series-dialog-1/series-dialog-1.component';
import { SeriesDialog2Component } from '../series-dialog-2/series-dialog-2.component';
import { NewSeriesDialog2Component } from '../new-series-dialog-2/new-series-dialog-2.component';
import { NewGroupSeriesDialog1Component } from '../new-group-series-dialog-1/new-group-series-dialog-1.component';

@Component({
  selector: 'jhi-overview-zsav-nawu-gm',
  templateUrl: './competition-overview-zsav-nawu-gm.component.html',
  styleUrls: ['./competition-overview-zsav-nawu-gm.component.scss'],
})
export class CompetitionOverviewZsavNawuGmComponent implements OnInit {
  competition!: ICompetition;

  results: Array<IResults> | null = [];
  athletes: Array<IAthlete> | null = [];
  groups: Array<IGroup> | null = [];
  groups1: Array<IGroup> | null = [];
  groups2: Array<IGroup> | null = [];
  groups99: Array<IGroup> | null = [];
  rounds: Array<IRound> | null = [];

  account: Account | null = null;

  constructor(
    protected activatedRoute: ActivatedRoute,
    private resultsService: ResultsService,
    private rankingListService: RankingListService,
    private competitionService: CompetitionService,
    private groupService: GroupService,
    private clubService: ClubService,
    protected modalService: NgbModal,
    private roundService: RoundService,
    private alertService: AlertService,
    private accountService: AccountService,
    private router: Router,
    @Inject(TuiNotificationsService)
    private readonly notificationsService: TuiNotificationsService
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ competition }) => {
      this.competition = competition;
      this.resultsService.findByCompetition(competition).subscribe(re => {
        this.results = re.body;

        if (this.userIsVerein()) {
          this.groupService.findByCompetitionAndClub(competition, this.accountService.getClubAuthority()).subscribe(res => {
            this.groups = res.body;
            console.log(res.body);
            this.groups1 = res.body!.filter(value => value.round === 1);
            this.groups2 = res.body!.filter(value => value.round === 2);
            this.groups99 = res.body!.filter(value => value.round === 99);
          });
        } else {
          this.groupService.findByCompetition(competition).subscribe(res => {
            this.groups = res.body;
            console.log(res.body);
            this.groups1 = res.body!.filter(value => value.round === 1);
            this.groups2 = res.body!.filter(value => value.round === 2);
            this.groups99 = res.body!.filter(value => value.round === 99);
          });
        }
      });
    });
  }

  createResult(competition: ICompetition, group: IGroup, round: number): void {
    const modalRef = this.modalService.open(NewGroupSeriesDialog1Component, {
      size: 'xl',
      backdrop: 'static',
    });
    modalRef.componentInstance.competition = this.competition;
    modalRef.componentInstance.group = group;
    modalRef.componentInstance.round = round;
    modalRef.closed.subscribe(() => {
      this.loadPage();
    });
  }

  createResultFinal(competition: ICompetition, group: IGroup): void {
    /* todo newGroupSeriesDialog2 erstellen und Rangliste erstellen mit verschiendenen Runden wie Einzel */
    const modalRef = this.modalService.open(NewSeriesDialog2Component, {
      size: 'xl',
      backdrop: 'static',
    });
    modalRef.componentInstance.competition = this.competition;
    modalRef.componentInstance.group = group;
    modalRef.componentInstance.final = true;
    modalRef.closed.subscribe(() => {
      this.loadPage();
    });
  }

  getResultsPerGroup(group: IGroup, round: number): any {
    return this.results?.filter(value => value.round === round).filter(value => value.group!.name === group.name);
  }

  createAthlete(competition: ICompetition): void {
    const modalRef = this.modalService.open(CreateAthleteDialogComponent, { size: 'xl', backdrop: 'static' });
    modalRef.componentInstance.competition = competition;
    modalRef.componentInstance.accountClub = this.accountService.getClub();
    modalRef.closed.subscribe(() => {
      this.loadPage();
    });
  }

  addNewGroup(): void {
    this.clubService.findByName(this.accountService.getClubAuthority()).subscribe(value => {
      this.groupService.findByCompetitionAndClubOnlyOne(this.competition, value.body!).subscribe(value1 => {
        for (let i = 0; i < 3; i++) {
          const group: Group = new Group();
          // eslint-disable-next-line @typescript-eslint/restrict-template-expressions
          group.name = `${value.body!.name} ${value1.body!.length + 1}`;
          group.club = value.body;
          group.competition = this.competition;
          if (i === 2) {
            group.round = 99;
          } else {
            group.round = i + 1;
          }
          this.groupService.create(group).subscribe(v1 => {
            if (v1.status) {
              const content = `Gruppe wurde erstellt`;
              this.notificationsService
                .show(content, {
                  label: 'Erfolgreich erstellt',
                  status: TuiNotification.Success,
                })
                .subscribe();
            } else {
              this.notificationsService
                .show('Fehler beim Erstellen aufgetreten', {
                  label: 'Fehler beim Erstellen',
                  status: TuiNotification.Error,
                })
                .subscribe();
            }
          });
        }
      });
    });

    this.loadPage();
  }

  openSeriesDialog(result: IResults): void {
    if (result.competition?.id !== undefined && result.round !== undefined) {
      this.roundService.findByRoundAndCompetition(result.round, result.competition.id).subscribe(res => {
        if (this.userIsZsavOrAdmin()) {
          if (result.round !== 99) {
            this.openDialog(result);
          } else {
            this.openDialogFinal(result);
          }
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

  openDialogFinal(result: IResults): void {
    if (result.competition?.numberOfFinalSeries === 1) {
      const modalRef = this.modalService.open(SeriesDialog1Component, {
        size: 'xl',
        backdrop: 'static',
      });
      modalRef.componentInstance.result = result;
      modalRef.closed.subscribe(() => {
        this.loadPage();
      });
    } else if (result.competition?.numberOfFinalSeries === 2) {
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

  deleteGroup(group: Group): void {
    this.groupService.delete(group.id!).subscribe(value => {
      if (value.status) {
        const content = `Gruppe wurde gelöscht`;
        this.notificationsService
          .show(content, {
            label: 'Löschen erfolgreich',
            status: TuiNotification.Success,
          })
          .subscribe();
      } else {
        this.notificationsService
          .show('Fehler beim Löschen aufgetreten', {
            label: 'Fehler beim Löschen',
            status: TuiNotification.Error,
          })
          .subscribe();
      }
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

  openSeriesDialogGroup(group: IGroup, round: IRound): void {
    this.openDialogGroup(group, round);
  }

  openDialogGroup(group: IGroup, round: IRound): void {
    const modalRef = this.modalService.open(SeriesDialog2GroupComponent, {
      size: 'xl',
      backdrop: 'static',
    });
    modalRef.componentInstance.group = group;
    modalRef.componentInstance.round = round;
    modalRef.closed.subscribe(() => {
      this.loadPage();
    });
  }

  getResultsByGroup(groupId: number): Array<IResults> {
    if (this.results !== null) {
      return this.results.filter(s => s.group?.id === groupId);
    } else {
      return [];
    }
  }

  private loadPage(): void {
    const currentUrl = this.router.url;
    this.router.navigateByUrl('/', { skipLocationChange: true }).then(() => {
      this.router.navigate([currentUrl]);
    });
  }
}
