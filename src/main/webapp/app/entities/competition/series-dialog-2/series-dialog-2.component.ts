import { Component, Inject, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { IResults } from 'app/entities/results/results.model';
import { ICompetition } from '../competition.model';
import { CompetitionService } from '../service/competition.service';
import { ISeries } from '../../series/series.model';
import { SeriesService } from 'app/entities/series/service/series.service';
import { ResultsService } from 'app/entities/results/service/results.service';
import { FormControl, FormGroup } from '@angular/forms';
import { TuiNotification, TuiNotificationsService } from '@taiga-ui/core';

@Component({
  selector: 'jhi-series-dialog-2',
  templateUrl: './series-dialog-2.component.html',
  styleUrls: ['./series-dialog-2.component.scss'],
})
export class SeriesDialog2Component implements OnInit {
  isDataAvailable: boolean;
  result?: IResults | null;
  competition?: ICompetition | null;
  serie1: number[] = [];
  serie2: number[] = [];

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

  constructor(
    protected activeModal: NgbActiveModal,
    private competitionService: CompetitionService,
    private seriesService: SeriesService,
    private resultsService: ResultsService,
    @Inject(TuiNotificationsService)
    private readonly notificationsService: TuiNotificationsService
  ) {
    this.isDataAvailable = false;
  }

  cancel(): void {
    this.activeModal.close('');
  }

  ngOnInit(): void {
    if (this.result?.competition?.id !== undefined && this.result.round !== undefined) {
      this.competitionService.find(this.result.competition.id).subscribe(
        result => {
          this.competition = result.body;
          this.resultsService.find(<number>this.result?.id).subscribe(r => {
            this.result = r.body;
            // serie1
            this.serie1.push(<number>this.result?.serie1?.p1);
            this.serie1.push(<number>this.result?.serie1?.p2);
            this.serie1.push(<number>this.result?.serie1?.p3);
            this.serie1.push(<number>this.result?.serie1?.p4);
            this.serie1.push(<number>this.result?.serie1?.p5);
            this.serie1.push(<number>this.result?.serie1?.p6);
            this.serie1.push(<number>this.result?.serie1?.p7);
            this.serie1.push(<number>this.result?.serie1?.p8);
            this.serie1.push(<number>this.result?.serie1?.p9);
            this.serie1.push(<number>this.result?.serie1?.p10);
            // serie2
            this.serie2.push(<number>this.result?.serie2?.p1);
            this.serie2.push(<number>this.result?.serie2?.p2);
            this.serie2.push(<number>this.result?.serie2?.p3);
            this.serie2.push(<number>this.result?.serie2?.p4);
            this.serie2.push(<number>this.result?.serie2?.p5);
            this.serie2.push(<number>this.result?.serie2?.p6);
            this.serie2.push(<number>this.result?.serie2?.p7);
            this.serie2.push(<number>this.result?.serie2?.p8);
            this.serie2.push(<number>this.result?.serie2?.p9);
            this.serie2.push(<number>this.result?.serie2?.p10);
            // set form values
            this.testForm.setValue({
              number1: this.result?.serie1?.p1,
              number2: this.result?.serie1?.p2,
              number3: this.result?.serie1?.p3,
              number4: this.result?.serie1?.p4,
              number5: this.result?.serie1?.p5,
              number6: this.result?.serie1?.p6,
              number7: this.result?.serie1?.p7,
              number8: this.result?.serie1?.p8,
              number9: this.result?.serie1?.p9,
              number10: this.result?.serie1?.p10,
              number11: this.result?.serie2?.p1,
              number12: this.result?.serie2?.p2,
              number13: this.result?.serie2?.p3,
              number14: this.result?.serie2?.p4,
              number15: this.result?.serie2?.p5,
              number16: this.result?.serie2?.p6,
              number17: this.result?.serie2?.p7,
              number18: this.result?.serie2?.p8,
              number19: this.result?.serie2?.p9,
              number20: this.result?.serie2?.p10,
            });
          });
        },
        err => console.error(err),
        () => (this.isDataAvailable = true)
      );
    }
  }

  save(): void {
    this.updateSerieOnResult(this.result?.serie1, this.serie1, 1);
    this.updateSerieOnResult(this.result?.serie2, this.serie2, 2);
    this.saveResult();
    this.cancel();
  }

  saveResult(): void {
    this.resultsService.update(this.result!).subscribe(res => {
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

  updateSerieOnResult(serie: ISeries | null | undefined, numbers: number[], serieNumber: number): void {
    const iSeries: ISeries = {
      p1: numbers[0],
      p2: numbers[1],
      p3: numbers[2],
      p4: numbers[3],
      p5: numbers[4],
      p6: numbers[5],
      p7: numbers[6],
      p8: numbers[7],
      p9: numbers[8],
      p10: numbers[9],
    };
    let tempSerie1: number[] = [];
    tempSerie1 = this.replacingElmnts(numbers, 11, 10);
    iSeries.result = tempSerie1.reduce((a, b) => a + b, 0);
    switch (serieNumber) {
      case 1:
        if (this.result?.serie1 !== undefined) {
          this.result.serie1 = iSeries;
        }
        break;
      case 2:
        if (this.result?.serie2 !== undefined) {
          this.result.serie2 = iSeries;
        }
        break;
    }
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
}
