import { Component, OnInit } from '@angular/core';
import { IWettkampf } from '../wettkampf.model';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ISchuetze } from '../../schuetze/schuetze.model';
import { SchuetzeService } from '../../schuetze/service/schuetze.service';
import { IResultate } from 'app/entities/resultate/resultate.model';
import { ResultateService } from 'app/entities/resultate/service/resultate.service';
import { HttpResponse } from '@angular/common/http';

@Component({
  selector: 'jhi-resultate-dialog',
  templateUrl: './resultate-dialog.component.html',
})
export class ResultateDialogComponent implements OnInit {
  wettkampf?: IWettkampf;

  resultate: Array<IResultate> | null = [];
  schuetzes?: Array<ISchuetze> | null;
  addedSchuetzen?: Array<ISchuetze> | null;

  constructor(
    protected activeModal: NgbActiveModal,
    protected schuetzeService: SchuetzeService,
    private resultateService: ResultateService
  ) {}

  cancel(): void {
    this.activeModal.close('deleted');
  }

  ngOnInit(): void {
    this.schuetzeService.findAll().subscribe(result => {
      this.schuetzes = result.body;

      if (this.wettkampf) {
        this.resultateService.findByWettkampf(this.wettkampf).subscribe((res: HttpResponse<Array<IResultate>>) => {
          console.log(res.body);
          this.resultate = res.body;

          const tempSchuetzen: Array<ISchuetze> = [];
          this.resultate?.forEach(value => {
            if (value.schuetze) {
              tempSchuetzen.push(value.schuetze);
            }
          });

          this.addedSchuetzen = tempSchuetzen.filter((s, i, arr) => arr.indexOf(<ISchuetze>arr.find(t => t.id === s.id)) === i);
        });
      }
    });
  }

  addSchuetze(schuetze: ISchuetze): void {
    if (this.wettkampf?.anzahlPassen !== undefined) {
      for (let i = 0; i < this.wettkampf.anzahlPassen; i++) {
        const result: IResultate = {
          wettkampf: this.wettkampf,
          schuetze,
          runde: i + 1,
        };
        this.resultateService.create(result).subscribe();
      }
    }
    if (this.addedSchuetzen !== undefined && this.addedSchuetzen !== null) {
      this.addedSchuetzen.push(schuetze);
    }
  }

  removeSchuetze(schuetze: ISchuetze): void {
    console.log(schuetze);
    console.log(this.addedSchuetzen);

    if (this.addedSchuetzen !== null && this.addedSchuetzen !== undefined) {
      this.addedSchuetzen.forEach(s => {
        if (s.id === schuetze.id) {
          console.log(s);
          console.log(schuetze);
          console.log('true 1');
          this.addedSchuetzen?.splice(this.addedSchuetzen.indexOf(s), 1);
          this.resultateService.deleteBySchuetze(schuetze).subscribe();
        }
      });
    }
  }
}
