import { Component, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { IResultate } from 'app/entities/resultate/resultate.model';
import { IWettkampf } from '../wettkampf.model';
import { WettkampfService } from '../service/wettkampf.service';
import { IPassen } from '../../passen/passen.model';
import { PassenService } from 'app/entities/passen/service/passen.service';
import { ResultateService } from 'app/entities/resultate/service/resultate.service';

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

  constructor(
    protected activeModal: NgbActiveModal,
    private wettkampfService: WettkampfService,
    private passenService: PassenService,
    private resultateService: ResultateService
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
          console.log(this.wettkampf);
          this.resultateService.find(<number>this.resultat?.id).subscribe(r => {
            this.resultat = r.body;
            //passe1
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
            //passe2
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
            //passe3
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
            //passe4
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
            console.log(this.resultat);
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
        break;
      case 2:
        this.saveResult(this.resultat?.passe1, this.passe1, 1);
        this.saveResult(this.resultat?.passe2, this.passe2, 2);
        break;
      case 3:
        this.saveResult(this.resultat?.passe1, this.passe1, 1);
        this.saveResult(this.resultat?.passe2, this.passe2, 2);
        this.saveResult(this.resultat?.passe3, this.passe3, 3);
        break;
      case 4:
        this.saveResult(this.resultat?.passe1, this.passe1, 1);
        this.saveResult(this.resultat?.passe2, this.passe2, 2);
        this.saveResult(this.resultat?.passe3, this.passe3, 3);
        this.saveResult(this.resultat?.passe4, this.passe4, 4);
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
          this.resultateService.update(this.resultat).subscribe();
        }
        break;
      case 2:
        if (this.resultat?.passe2 !== undefined) {
          this.resultat.passe2 = iPassen;
          this.resultateService.update(this.resultat).subscribe();
        }
        break;
      case 3:
        if (this.resultat?.passe3 !== undefined) {
          this.resultat.passe3 = iPassen;
          this.resultateService.update(this.resultat).subscribe();
        }
        break;
      case 4:
        if (this.resultat?.passe4 !== undefined) {
          this.resultat.passe4 = iPassen;
          this.resultateService.update(this.resultat).subscribe();
        }
        break;
    }
  }

  getSumPassen(passe: number[]): any {
    if (passe.includes(11)) {
      let tempPasse1: number[] = [];
      tempPasse1 = this.replacingElmnts(passe, 11, 10);
      return tempPasse1.reduce((a, b) => a + b, 0);
    } else {
      return passe.reduce((a, b) => a + b, 0);
    }
  }

  getSum(): any {
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
