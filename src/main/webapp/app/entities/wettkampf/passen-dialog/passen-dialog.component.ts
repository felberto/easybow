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
  resultat?: IResultate | null;
  wettkampf?: IWettkampf | null;
  shoots: number[] = [];

  constructor(
    protected activeModal: NgbActiveModal,
    private wettkampfService: WettkampfService,
    private passenService: PassenService,
    private resultateService: ResultateService
  ) {}

  cancel(): void {
    this.activeModal.close('');
  }

  ngOnInit(): void {
    if (this.resultat?.wettkampf?.id !== undefined) {
      this.wettkampfService.find(this.resultat.wettkampf.id).subscribe(result => {
        this.wettkampf = result.body;
        console.log(this.resultat);
        this.resultateService.find(<number>this.resultat?.id).subscribe(r => {
          this.resultat = r.body;
          console.log(this.resultat);
          if (this.resultat !== null) {
            if (this.resultat.passe1 !== null && this.resultat.passe1 !== undefined) {
              this.passenService.find(<number>this.resultat.passe1.id).subscribe(r1 => {
                this.shoots.push(<number>r1.body?.p1);
                this.shoots.push(<number>r1.body?.p2);
                this.shoots.push(<number>r1.body?.p3);
                this.shoots.push(<number>r1.body?.p4);
                this.shoots.push(<number>r1.body?.p5);
                this.shoots.push(<number>r1.body?.p6);
                this.shoots.push(<number>r1.body?.p7);
                this.shoots.push(<number>r1.body?.p8);
                this.shoots.push(<number>r1.body?.p9);
                this.shoots.push(<number>r1.body?.p10);
                if (this.resultat !== null) {
                  if (this.resultat?.passe2 !== null && this.resultat !== undefined && this.resultat.passe2 !== undefined) {
                    this.passenService.find(<number>this.resultat.passe2.id).subscribe(r2 => {
                      this.shoots.push(<number>r2.body?.p1);
                      this.shoots.push(<number>r2.body?.p2);
                      this.shoots.push(<number>r2.body?.p3);
                      this.shoots.push(<number>r2.body?.p4);
                      this.shoots.push(<number>r2.body?.p5);
                      this.shoots.push(<number>r2.body?.p6);
                      this.shoots.push(<number>r2.body?.p7);
                      this.shoots.push(<number>r2.body?.p8);
                      this.shoots.push(<number>r2.body?.p9);
                      this.shoots.push(<number>r2.body?.p10);
                      if (this.resultat !== null) {
                        if (this.resultat?.passe3 !== null && this.resultat !== undefined && this.resultat.passe3 !== undefined) {
                          this.passenService.find(<number>this.resultat.passe3.id).subscribe(r3 => {
                            this.shoots.push(<number>r3.body?.p1);
                            this.shoots.push(<number>r3.body?.p2);
                            this.shoots.push(<number>r3.body?.p3);
                            this.shoots.push(<number>r3.body?.p4);
                            this.shoots.push(<number>r3.body?.p5);
                            this.shoots.push(<number>r3.body?.p6);
                            this.shoots.push(<number>r3.body?.p7);
                            this.shoots.push(<number>r3.body?.p8);
                            this.shoots.push(<number>r3.body?.p9);
                            this.shoots.push(<number>r3.body?.p10);
                            if (this.resultat !== null) {
                              if (this.resultat?.passe4 !== null && this.resultat !== undefined && this.resultat.passe4 !== undefined) {
                                this.passenService.find(<number>this.resultat.passe4.id).subscribe(r4 => {
                                  this.shoots.push(<number>r4.body?.p1);
                                  this.shoots.push(<number>r4.body?.p2);
                                  this.shoots.push(<number>r4.body?.p3);
                                  this.shoots.push(<number>r4.body?.p4);
                                  this.shoots.push(<number>r4.body?.p5);
                                  this.shoots.push(<number>r4.body?.p6);
                                  this.shoots.push(<number>r4.body?.p7);
                                  this.shoots.push(<number>r4.body?.p8);
                                  this.shoots.push(<number>r4.body?.p9);
                                  this.shoots.push(<number>r4.body?.p10);
                                });
                              }
                            }
                          });
                        }
                      }
                    });
                  }
                }
              });
            }
          }
        });
      });
    }
  }

  getShootNumber(): any {
    if (this.wettkampf?.anzahlPassen !== undefined) {
      return this.wettkampf.anzahlPassen * 10;
    }
  }

  save(): void {
    const tempArray = this.shoots;
    if (this.wettkampf?.anzahlPassen !== undefined) {
      for (let i = 0; i < this.wettkampf.anzahlPassen; i++) {
        switch (i) {
          case 0:
            this.saveResult(this.resultat?.passe1, tempArray.slice(0, 10), 1);
            break;
          case 1:
            this.saveResult(this.resultat?.passe2, tempArray.slice(10, 20), 2);
            break;
          case 2:
            this.saveResult(this.resultat?.passe3, tempArray.slice(20, 30), 3);
            break;
          case 3:
            this.saveResult(this.resultat?.passe4, tempArray.slice(30, 40), 4);
            break;
        }
      }
    }
  }

  saveResult(passe: IPassen | null | undefined, numbers: number[], passeNumber: number): void {
    //TODO error beim updaten, da alle anderen passen immer null sind ausser die welche geupdatet wird
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
      resultat:
        numbers[0] + numbers[1] + numbers[2] + numbers[3] + numbers[4] + numbers[5] + numbers[6] + numbers[7] + numbers[8] + numbers[9],
    };
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

  getSum(): any {
    return this.shoots.reduce((a, b) => a + b, 0);
  }
}
