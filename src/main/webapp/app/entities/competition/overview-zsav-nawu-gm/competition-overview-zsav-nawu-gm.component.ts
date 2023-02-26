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
  rounds: Array<IRound> | null = [];

  account: Account | null = null;

  constructor(
    protected activatedRoute: ActivatedRoute,
    private resultsService: ResultsService,
    private rankingListService: RankingListService,
    private competitionService: CompetitionService,
    private groupService: GroupService,
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

        this.roundService.findByCompetition(competition.id!).subscribe(value => {
          this.rounds = value.body;
        });

        /*todo get all groups*/
        //todo ÜBERARBEITEN
        //todo gruppen direkt holen, da in der gruppe neu auch runde, competition und club gespeichert ist
        this.groupService.findByCompetition(competition).subscribe(res => {
          this.groups = res.body;

          this.accountService.getAuthenticationState().subscribe(account => {
            this.account = account;
            if (this.account?.club != null) {
              //todo group add club, competition, round
              this.groups = this.groups!.filter(value => value.id === this.account?.club?.id);
            }
          });
        });
      });
    });
  }

  addNewGroup(): void {
    //TODO fertigstellen
    const group: Group = new Group();
    group.name = 'test';
    group.club = this.account?.club;
    group.competition = this.competition;
    this.groupService.create(group).subscribe(value => {
      if (value.status) {
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
      this.loadPage();
    });
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
