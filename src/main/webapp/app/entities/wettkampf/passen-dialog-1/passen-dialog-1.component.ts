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
  selector: 'jhi-passen-dialog-1',
  templateUrl: './passen-dialog-1.component.html',
  styleUrls: ['./passen-dialog-1.component.scss'],
})
export class PassenDialog1Component implements OnInit {
  isDataAvailable: boolean;
  resultat?: IResultate | null;
  wettkampf?: IWettkampf | null;
  passe1: number[] = [];

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
            });
          });
        },
        err => console.error(err),
        () => (this.isDataAvailable = true)
      );
    }
  }

  save(): void {
    this.updatePasseOnResult(this.resultat?.passe1, this.passe1);
    this.saveResultat();
    this.cancel();
  }

  saveResultat(): void {
    this.resultateService.update(this.resultat!).subscribe(res => {
      if (res.status) {
        const content = `Passen mit Resultat ${res.body!.resultat!} wurde gespeichert`;
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

  updatePasseOnResult(passe: IPassen | null | undefined, numbers: number[]): void {
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
    if (this.resultat?.passe1 !== undefined) {
      this.resultat.passe1 = iPassen;
    }
  }

  getPasse(passeNumber: number): number[] {
    const passe: number[] = [];
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
    if (this.passe1.includes(11)) {
      let tempPasse1: number[] = [];
      tempPasse1 = this.replacingElmnts(this.passe1, 11, 10);
      return tempPasse1.reduce((a, b) => a + b, 0);
    } else {
      return this.passe1.reduce((a, b) => a + b, 0);
    }
  }

  replacingElmnts(nums: number[], oldNum: number, newNum: number): any {
    return nums.map(e => (e === oldNum ? newNum : e));
  }
}
