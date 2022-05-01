import { Component, Inject, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { IResultate } from 'app/entities/resultate/resultate.model';
import { IWettkampf } from '../wettkampf.model';
import { WettkampfService } from '../service/wettkampf.service';
import { IPassen } from '../../passen/passen.model';
import { PassenService } from 'app/entities/passen/service/passen.service';
import { ResultateService } from 'app/entities/resultate/service/resultate.service';
import { FormControl, FormGroup } from '@angular/forms';
import { TuiNotification, TuiNotificationsService } from '@taiga-ui/core';

@Component({
  selector: 'jhi-passen-dialog',
  templateUrl: './passen-dialog.component.html',
  styleUrls: ['./passen-dialog.component.scss'],
})
export class PassenDialogComponent implements OnInit {
  isDataAvailable: boolean;
  resultat?: IResultate | null;
  wettkampf?: IWettkampf | null;
  passe1: number[] = [];
  passe2: number[] = [];
  passe3: number[] = [];
  passe4: number[] = [];

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
    private wettkampfService: WettkampfService,
    private passenService: PassenService,
    private resultateService: ResultateService,
    @Inject(TuiNotificationsService)
    private readonly notificationsService: TuiNotificationsService
  ) {
    this.isDataAvailable = false;
  }

  cancel(): void {
    this.activeModal.close('');
  }

  ngOnInit(): void {
    if (this.resultat?.wettkampf?.id !== undefined && this.resultat.runde !== undefined) {
      this.wettkampfService.find(this.resultat.wettkampf.id).subscribe(
        result => {
          this.wettkampf = result.body;
          this.resultateService.find(<number>this.resultat?.id).subscribe(r => {
            this.resultat = r.body;
            // passe1
            this.passe1.push(<number>this.resultat?.passe1?.p1);
            this.passe1.push(<number>this.resultat?.passe1?.p2);
            this.passe1.push(<number>this.resultat?.passe1?.p3);
            this.passe1.push(<number>this.resultat?.passe1?.p4);
            this.passe1.push(<number>this.resultat?.passe1?.p5);
            this.passe1.push(<number>this.resultat?.passe1?.p6);
            this.passe1.push(<number>this.resultat?.passe1?.p7);
            this.passe1.push(<number>this.resultat?.passe1?.p8);
            this.passe1.push(<number>this.resultat?.passe1?.p9);
            this.passe1.push(<number>this.resultat?.passe1?.p10);
            // passe2
            this.passe2.push(<number>this.resultat?.passe2?.p1);
            this.passe2.push(<number>this.resultat?.passe2?.p2);
            this.passe2.push(<number>this.resultat?.passe2?.p3);
            this.passe2.push(<number>this.resultat?.passe2?.p4);
            this.passe2.push(<number>this.resultat?.passe2?.p5);
            this.passe2.push(<number>this.resultat?.passe2?.p6);
            this.passe2.push(<number>this.resultat?.passe2?.p7);
            this.passe2.push(<number>this.resultat?.passe2?.p8);
            this.passe2.push(<number>this.resultat?.passe2?.p9);
            this.passe2.push(<number>this.resultat?.passe2?.p10);
            // passe3
            this.passe3.push(<number>this.resultat?.passe3?.p1);
            this.passe3.push(<number>this.resultat?.passe3?.p2);
            this.passe3.push(<number>this.resultat?.passe3?.p3);
            this.passe3.push(<number>this.resultat?.passe3?.p4);
            this.passe3.push(<number>this.resultat?.passe3?.p5);
            this.passe3.push(<number>this.resultat?.passe3?.p6);
            this.passe3.push(<number>this.resultat?.passe3?.p7);
            this.passe3.push(<number>this.resultat?.passe3?.p8);
            this.passe3.push(<number>this.resultat?.passe3?.p9);
            this.passe3.push(<number>this.resultat?.passe3?.p10);
            // passe4
            this.passe4.push(<number>this.resultat?.passe4?.p1);
            this.passe4.push(<number>this.resultat?.passe4?.p2);
            this.passe4.push(<number>this.resultat?.passe4?.p3);
            this.passe4.push(<number>this.resultat?.passe4?.p4);
            this.passe4.push(<number>this.resultat?.passe4?.p5);
            this.passe4.push(<number>this.resultat?.passe4?.p6);
            this.passe4.push(<number>this.resultat?.passe4?.p7);
            this.passe4.push(<number>this.resultat?.passe4?.p8);
            this.passe4.push(<number>this.resultat?.passe4?.p9);
            this.passe4.push(<number>this.resultat?.passe4?.p10);
            // set form values
            this.testForm.setValue({
              number1: this.resultat?.passe1?.p1,
              number2: this.resultat?.passe1?.p2,
              number3: this.resultat?.passe1?.p3,
              number4: this.resultat?.passe1?.p4,
              number5: this.resultat?.passe1?.p5,
              number6: this.resultat?.passe1?.p6,
              number7: this.resultat?.passe1?.p7,
              number8: this.resultat?.passe1?.p8,
              number9: this.resultat?.passe1?.p9,
              number10: this.resultat?.passe1?.p10,
              number11: this.resultat?.passe2?.p1,
              number12: this.resultat?.passe2?.p2,
              number13: this.resultat?.passe2?.p3,
              number14: this.resultat?.passe2?.p4,
              number15: this.resultat?.passe2?.p5,
              number16: this.resultat?.passe2?.p6,
              number17: this.resultat?.passe2?.p7,
              number18: this.resultat?.passe2?.p8,
              number19: this.resultat?.passe2?.p9,
              number20: this.resultat?.passe2?.p10,
            });
          });
        },
        err => console.error(err),
        () => (this.isDataAvailable = true)
      );
    }
  }

  save(): void {
    switch (this.wettkampf?.anzahlPassen) {
      case 1:
        this.saveResult(this.resultat?.passe1, this.passe1, 1);
        this.cancel();
        break;
      case 2:
        this.saveResult(this.resultat?.passe1, this.passe1, 1);
        this.saveResult(this.resultat?.passe2, this.passe2, 2);
        this.cancel();
        break;
      case 3:
        this.saveResult(this.resultat?.passe1, this.passe1, 1);
        this.saveResult(this.resultat?.passe2, this.passe2, 2);
        this.saveResult(this.resultat?.passe3, this.passe3, 3);
        this.cancel();
        break;
      case 4:
        this.saveResult(this.resultat?.passe1, this.passe1, 1);
        this.saveResult(this.resultat?.passe2, this.passe2, 2);
        this.saveResult(this.resultat?.passe3, this.passe3, 3);
        this.saveResult(this.resultat?.passe4, this.passe4, 4);
        this.cancel();
        break;
    }
  }

  saveResult(passe: IPassen | null | undefined, numbers: number[], passeNumber: number): void {
    const iPassen: IPassen = {
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
    let tempPasse1: number[] = [];
    tempPasse1 = this.replacingElmnts(numbers, 11, 10);
    iPassen.resultat = tempPasse1.reduce((a, b) => a + b, 0);
    switch (passeNumber) {
      case 1:
        if (this.resultat?.passe1 !== undefined) {
          this.resultat.passe1 = iPassen;
          this.resultateService.update(this.resultat).subscribe(res => {
            if (res.status) {
              const content = `Passe 1 mit Resultat ${res.body!.passe1!.resultat!} wurde gespeichert`;
              this.notificationsService
                .show(content, {
                  label: 'Erfolgreich gespeichert',
                  status: TuiNotification.Success,
                })
                .subscribe();
            } else {
              this.notificationsService
                .show('Fehler beim Speichern von Passe 1 aufgetreten', {
                  label: 'Fehler beim Speichern',
                  status: TuiNotification.Error,
                })
                .subscribe();
            }
          });
        }
        break;
      case 2:
        if (this.resultat?.passe2 !== undefined) {
          this.resultat.passe2 = iPassen;
          this.resultateService.update(this.resultat).subscribe(res => {
            if (res.status) {
              const content = `Passe 2 mit Resultat ${res.body!.passe2!.resultat!} wurde gespeichert`;
              this.notificationsService
                .show(content, {
                  label: 'Erfolgreich gespeichert',
                  status: TuiNotification.Success,
                })
                .subscribe();
            } else {
              this.notificationsService
                .show('Fehler beim Speichern von Passe 2 aufgetreten', {
                  label: 'Fehler beim Speichern',
                  status: TuiNotification.Error,
                })
                .subscribe();
            }
          });
        }
        break;
      case 3:
        if (this.resultat?.passe3 !== undefined) {
          this.resultat.passe3 = iPassen;
          this.resultateService.update(this.resultat).subscribe(res => {
            if (res.status) {
              const content = `Passe 3 mit Resultat ${res.body!.passe3!.resultat!} wurde gespeichert`;
              this.notificationsService
                .show(content, {
                  label: 'Erfolgreich gespeichert',
                  status: TuiNotification.Success,
                })
                .subscribe();
            } else {
              this.notificationsService
                .show('Fehler beim Speichern von Passe 3 aufgetreten', {
                  label: 'Fehler beim Speichern',
                  status: TuiNotification.Error,
                })
                .subscribe();
            }
          });
        }
        break;
      case 4:
        if (this.resultat?.passe4 !== undefined) {
          this.resultat.passe4 = iPassen;
          this.resultateService.update(this.resultat).subscribe(res => {
            if (res.status) {
              const content = `Passe 4 mit Resultat ${res.body!.passe4!.resultat!} wurde gespeichert`;
              this.notificationsService
                .show(content, {
                  label: 'Erfolgreich gespeichert',
                  status: TuiNotification.Success,
                })
                .subscribe();
            } else {
              this.notificationsService
                .show('Fehler beim Speichern von Passe 4 aufgetreten', {
                  label: 'Fehler beim Speichern',
                  status: TuiNotification.Error,
                })
                .subscribe();
            }
          });
        }
        break;
    }
  }

  getPasse(passeNumber: number): number[] {
    const passe: number[] = [];
    if (passeNumber === 1) {
      passe.push(
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
    } else if (passeNumber === 2) {
      passe.push(
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
    return passe;
  }

  getSumPassen(passeNumber: number): any {
    const passe = this.getPasse(passeNumber);
    if (passe.includes(11)) {
      let tempPasse1: number[] = [];
      tempPasse1 = this.replacingElmnts(passe, 11, 10);
      return tempPasse1.reduce((a, b) => a + b, 0);
    } else {
      return passe.reduce((a, b) => a + b, 0);
    }
  }

  getSum(): any {
    this.passe1 = this.getPasse(1);
    this.passe2 = this.getPasse(2);
    switch (this.wettkampf?.anzahlPassen) {
      case 1:
        if (this.passe1.includes(11)) {
          let tempPasse1: number[] = [];
          tempPasse1 = this.replacingElmnts(this.passe1, 11, 10);
          return tempPasse1.reduce((a, b) => a + b, 0);
        } else {
          return this.passe1.reduce((a, b) => a + b, 0);
        }
      case 2:
        if (this.passe1.includes(11) || this.passe2.includes(11)) {
          let tempPasse1: number[] = [];
          let tempPasse2: number[] = [];
          tempPasse1 = this.replacingElmnts(this.passe1, 11, 10);
          tempPasse2 = this.replacingElmnts(this.passe2, 11, 10);
          return tempPasse1.reduce((a, b) => a + b, 0) + tempPasse2.reduce((a, b) => a + b, 0);
        } else {
          return this.passe1.reduce((a, b) => a + b, 0) + this.passe2.reduce((a, b) => a + b, 0);
        }
      case 3:
        if (this.passe1.includes(11) || this.passe2.includes(11) || this.passe3.includes(11)) {
          let tempPasse1: number[] = [];
          let tempPasse2: number[] = [];
          let tempPasse3: number[] = [];
          tempPasse1 = this.replacingElmnts(this.passe1, 11, 10);
          tempPasse2 = this.replacingElmnts(this.passe2, 11, 10);
          tempPasse3 = this.replacingElmnts(this.passe3, 11, 10);
          return tempPasse1.reduce((a, b) => a + b, 0) + tempPasse2.reduce((a, b) => a + b, 0) + tempPasse3.reduce((a, b) => a + b, 0);
        } else {
          return this.passe1.reduce((a, b) => a + b, 0) + this.passe2.reduce((a, b) => a + b, 0) + this.passe3.reduce((a, b) => a + b, 0);
        }
      case 4:
        if (this.passe1.includes(11) || this.passe2.includes(11) || this.passe3.includes(11) || this.passe4.includes(11)) {
          let tempPasse1: number[] = [];
          let tempPasse2: number[] = [];
          let tempPasse3: number[] = [];
          let tempPasse4: number[] = [];
          tempPasse1 = this.replacingElmnts(this.passe1, 11, 10);
          tempPasse2 = this.replacingElmnts(this.passe2, 11, 10);
          tempPasse3 = this.replacingElmnts(this.passe3, 11, 10);
          tempPasse4 = this.replacingElmnts(this.passe4, 11, 10);
          return (
            tempPasse1.reduce((a, b) => a + b, 0) +
            tempPasse2.reduce((a, b) => a + b, 0) +
            tempPasse3.reduce((a, b) => a + b, 0) +
            tempPasse4.reduce((a, b) => a + b, 0)
          );
        } else {
          return (
            this.passe1.reduce((a, b) => a + b, 0) +
            this.passe2.reduce((a, b) => a + b, 0) +
            this.passe3.reduce((a, b) => a + b, 0) +
            this.passe4.reduce((a, b) => a + b, 0)
          );
        }
    }
  }

  replacingElmnts(nums: number[], oldNum: number, newNum: number): any {
    return nums.map(e => (e === oldNum ? newNum : e));
  }
}
