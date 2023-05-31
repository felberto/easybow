import { Component, Inject, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { IResults, Results } from 'app/entities/results/results.model';
import { ICompetition } from '../competition.model';
import { CompetitionService } from '../service/competition.service';
import { ISeries } from '../../series/series.model';
import { SeriesService } from 'app/entities/series/service/series.service';
import { ResultsService } from 'app/entities/results/service/results.service';
import { FormControl, FormGroup } from '@angular/forms';
import { TuiNotification, TuiNotificationsService } from '@taiga-ui/core';
import { IAthlete } from '../../athlete/athlete.model';
import { AthleteService } from '../../athlete/service/athlete.service';
import { map } from 'rxjs/operators';
import { HttpResponse } from '@angular/common/http';
import { IRound } from '../../round/round.model';
import { RoundService } from '../../round/service/round.service';
import { AccountService } from '../../../core/auth/account.service';
import * as dayjs from 'dayjs';
import * as isSameOrBefore from 'dayjs/plugin/isSameOrBefore';

@Component({
  selector: 'jhi-new-series-dialog-2',
  templateUrl: './new-series-dialog-2.component.html',
  styleUrls: ['./new-series-dialog-2.component.scss'],
})
export class NewSeriesDialog2Component implements OnInit {
  competition?: ICompetition | null;
  final?: boolean;
  serie1: number[] = [];
  serie2: number[] = [];
  athletesSharedCollection: IAthlete[] = [];
  athletesSharedCollectionTmp: IAthlete[] = [];
  roundsSharedCollection: IRound[] = [];

  testForm = new FormGroup({
    number1: new FormControl(),
    number2: new FormControl(),
    number3: new FormControl(),
    number4: new FormControl(),
    number5: new FormControl(),
    number6: new FormControl(),
    number7: new FormControl(),
    number8: new FormControl(),
    number9: new FormControl(),
    number10: new FormControl(),
    number11: new FormControl(),
    number12: new FormControl(),
    number13: new FormControl(),
    number14: new FormControl(),
    number15: new FormControl(),
    number16: new FormControl(),
    number17: new FormControl(),
    number18: new FormControl(),
    number19: new FormControl(),
    number20: new FormControl(),
  });

  editForm = new FormGroup({
    athlete: new FormControl(),
  });

  roundForm = new FormGroup({
    round: new FormControl(),
  });

  numberForm = new FormGroup({
    athlete_number: new FormControl(''),
  });

  constructor(
    protected activeModal: NgbActiveModal,
    protected athleteService: AthleteService,
    protected roundService: RoundService,
    private competitionService: CompetitionService,
    private seriesService: SeriesService,
    private resultsService: ResultsService,
    private accountService: AccountService,
    @Inject(TuiNotificationsService)
    private readonly notificationsService: TuiNotificationsService
  ) {}

  cancel(): void {
    this.activeModal.close('');
  }

  nextField(currentIndex: number): void {
    const nextField = document.querySelector(`[tabindex='${currentIndex}']`) as HTMLElement;
    nextField.focus();
  }

  ngOnInit(): void {
    if (this.userIsVerein()) {
      this.loadRelationshipsOptionsAthlete(true);
      this.loadRelationshipsOptionsRound();
    } else {
      this.loadRelationshipsOptionsAthlete(false);
      this.loadRelationshipsOptionsRound();
    }
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

  trackAthleteById(index: number, item: IAthlete): number {
    return item.id!;
  }

  trackRoundById(index: number, item: IRound): number {
    return item.id!;
  }

  save(): void {
    const newSerie1: ISeries = {
      p1: this.testForm.get('number1')!.value,
      p2: this.testForm.get('number2')!.value,
      p3: this.testForm.get('number3')!.value,
      p4: this.testForm.get('number4')!.value,
      p5: this.testForm.get('number5')!.value,
      p6: this.testForm.get('number6')!.value,
      p7: this.testForm.get('number7')!.value,
      p8: this.testForm.get('number8')!.value,
      p9: this.testForm.get('number9')!.value,
      p10: this.testForm.get('number10')!.value,
      result: this.getSumSeries(1),
    };

    const newSerie2: ISeries = {
      p1: this.testForm.get('number11')!.value,
      p2: this.testForm.get('number12')!.value,
      p3: this.testForm.get('number13')!.value,
      p4: this.testForm.get('number14')!.value,
      p5: this.testForm.get('number15')!.value,
      p6: this.testForm.get('number16')!.value,
      p7: this.testForm.get('number17')!.value,
      p8: this.testForm.get('number18')!.value,
      p9: this.testForm.get('number19')!.value,
      p10: this.testForm.get('number20')!.value,
      result: this.getSumSeries(2),
    };

    let newResult: IResults;
    if (this.final) {
      newResult = {
        competition: this.competition,
        athlete: this.editForm.get('athlete')!.value,
        athleteNumber: this.numberForm.get('athlete_number')!.value,
        round: 99,
        serie1: newSerie1,
        serie2: newSerie2,
        result: this.getSum(),
      };
    } else {
      newResult = {
        competition: this.competition,
        athlete: this.editForm.get('athlete')!.value,
        round: this.roundForm.get('round')!.value.round,
        serie1: newSerie1,
        serie2: newSerie2,
        result: this.getSum(),
      };
    }

    console.log(newResult);
    this.saveResult(newResult);
    this.cancel();
  }

  saveResult(newResult: Results): void {
    this.resultsService.create(newResult).subscribe(res => {
      if (res.status) {
        const content = `Series mit Resultat ${res.body!.result!} wurde gespeichert`;
        this.notificationsService
          .show(content, {
            label: 'Erfolgreich gespeichert',
            status: TuiNotification.Success,
          })
          .subscribe();
      } else {
        this.notificationsService
          .show('Fehler beim Speichern aufgetreten', {
            label: 'Fehler beim Speichern',
            status: TuiNotification.Error,
          })
          .subscribe();
      }
    });
  }

  getSerie(serieNumber: number): number[] {
    const serie: number[] = [];
    if (serieNumber === 1) {
      serie.push(
        this.testForm.get('number1')!.value,
        this.testForm.get('number2')!.value,
        this.testForm.get('number3')!.value,
        this.testForm.get('number4')!.value,
        this.testForm.get('number5')!.value,
        this.testForm.get('number6')!.value,
        this.testForm.get('number7')!.value,
        this.testForm.get('number8')!.value,
        this.testForm.get('number9')!.value,
        this.testForm.get('number10')!.value
      );
    } else if (serieNumber === 2) {
      serie.push(
        this.testForm.get('number11')!.value,
        this.testForm.get('number12')!.value,
        this.testForm.get('number13')!.value,
        this.testForm.get('number14')!.value,
        this.testForm.get('number15')!.value,
        this.testForm.get('number16')!.value,
        this.testForm.get('number17')!.value,
        this.testForm.get('number18')!.value,
        this.testForm.get('number19')!.value,
        this.testForm.get('number20')!.value
      );
    } else if (serieNumber === 3) {
      serie.push(
        this.testForm.get('number21')!.value,
        this.testForm.get('number22')!.value,
        this.testForm.get('number23')!.value,
        this.testForm.get('number24')!.value,
        this.testForm.get('number25')!.value,
        this.testForm.get('number26')!.value,
        this.testForm.get('number27')!.value,
        this.testForm.get('number28')!.value,
        this.testForm.get('number29')!.value,
        this.testForm.get('number30')!.value
      );
    } else if (serieNumber === 4) {
      serie.push(
        this.testForm.get('number31')!.value,
        this.testForm.get('number32')!.value,
        this.testForm.get('number33')!.value,
        this.testForm.get('number34')!.value,
        this.testForm.get('number35')!.value,
        this.testForm.get('number36')!.value,
        this.testForm.get('number37')!.value,
        this.testForm.get('number38')!.value,
        this.testForm.get('number39')!.value,
        this.testForm.get('number40')!.value
      );
    }

    return serie;
  }

  getSumSeries(serieNumber: number): any {
    const serie = this.getSerie(serieNumber);
    if (serie.includes(11)) {
      let tempSerie1: number[] = [];
      tempSerie1 = this.replacingElmnts(serie, 11, 10);
      return tempSerie1.reduce((a, b) => a + b, 0);
    } else {
      return serie.reduce((a, b) => a + b, 0);
    }
  }

  getSum(): any {
    this.serie1 = this.getSerie(1);
    this.serie2 = this.getSerie(2);
    if (this.serie1.includes(11) || this.serie2.includes(11)) {
      let tempSerie1: number[] = [];
      let tempSerie2: number[] = [];
      tempSerie1 = this.replacingElmnts(this.serie1, 11, 10);
      tempSerie2 = this.replacingElmnts(this.serie2, 11, 10);
      return tempSerie1.reduce((a, b) => a + b, 0) + tempSerie2.reduce((a, b) => a + b, 0);
    } else {
      return this.serie1.reduce((a, b) => a + b, 0) + this.serie2.reduce((a, b) => a + b, 0);
    }
  }

  replacingElmnts(nums: number[], oldNum: number, newNum: number): any {
    return nums.map(e => (e === oldNum ? newNum : e));
  }

  protected loadRelationshipsOptionsAthlete(isVerein: boolean): void {
    this.athleteService
      .query({
        page: 0,
        size: 100,
      })
      .pipe(map((res: HttpResponse<IAthlete[]>) => res.body ?? []))
      .pipe(
        map((athletes: IAthlete[]) => this.athleteService.addAthleteToCollectionIfMissing(athletes, this.editForm.get('athlete')!.value))
      )
      .subscribe((athletes: IAthlete[]) => {
        if (isVerein) {
          this.athletesSharedCollectionTmp = athletes.filter(value => value.role === this.accountService.getClubAuthority());
        } else {
          this.athletesSharedCollectionTmp = athletes;
        }
        return (this.athletesSharedCollection = athletes);
      });
  }

  protected loadRelationshipsOptionsRound(): void {
    dayjs.extend(isSameOrBefore);
    this.roundService
      .query({
        page: 0,
        size: 100,
      })
      .pipe(map((res: HttpResponse<IRound[]>) => res.body ?? []))
      .pipe(map((rounds: IRound[]) => this.roundService.addRoundToCollectionIfMissing(rounds, this.roundForm.get('round')!.value)))
      .subscribe(
        (rounds: IRound[]) =>
          (this.roundsSharedCollection = rounds.filter(
            value => value.round !== 99 && value.competition?.id === this.competition?.id && dayjs().isSameOrBefore(value.date!, 'day')
          ))
      );
  }
}
