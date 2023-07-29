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
import { AccountService } from '../../../core/auth/account.service';
import { RankingListService } from '../service/rankingList.service';
import { TuiNotificationsService } from '@taiga-ui/core';
import { CompetitionService } from '../service/competition.service';
import { SeriesDialog4Component } from '../series-dialog-4/series-dialog-4.component';
import { SeriesDialog6Component } from '../series-dialog-6/series-dialog-6.component';

@Component({
  selector: 'jhi-overview-easv-worldcup-30m',
  templateUrl: './competition-overview-easv-worldcup-30m.component.html',
  styleUrls: ['./competition-overview-easv-worldcup-30m.component.scss'],
})
export class CompetitionOverviewEasvWorldcup30mComponent implements OnInit {
  competition!: ICompetition;

  results: Array<IResults> | null = [];
  athletes: Array<IAthlete> | null = [];

  constructor(
    protected activatedRoute: ActivatedRoute,
    private resultsService: ResultsService,
    private rankingListService: RankingListService,
    private competitionService: CompetitionService,
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
      this.resultsService.findByCompetition(competition).subscribe(res => {
        this.results = res.body;

        this.results!.sort((n1, n2) => {
          if (n1.athleteNumber! > n2.athleteNumber!) {
            return 1;
          }

          if (n1.athleteNumber! < n2.athleteNumber!) {
            return -1;
          }

          return 0;
        });

        const tempAthlete: Array<IAthlete> = [];
        this.results?.forEach(value => {
          if (value.athlete) {
            tempAthlete.push(value.athlete);
          }
        });

        this.athletes = tempAthlete.filter((s, i, arr) => arr.indexOf(<IAthlete>arr.find(t => t.id === s.id)) === i);
      });
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

  openSeriesDialog(result: IResults): void {
    this.openDialog(result);
  }

  openDialog(result: IResults): void {
    const modalRef = this.modalService.open(SeriesDialog6Component, {
      size: 'xl',
      backdrop: 'static',
    });
    modalRef.componentInstance.result = result;
    modalRef.closed.subscribe(() => {
      this.loadPage();
    });
  }

  getResultsByAthlete(athlete: IAthlete): Array<IResults> {
    if (this.results !== null) {
      return this.results.filter(s => s.athlete?.id === athlete.id).sort((a, b) => <number>a.round - <number>b.round);
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
