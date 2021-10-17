import { Component, OnInit } from '@angular/core';
import { IWettkampf } from '../wettkampf.model';
import { ActivatedRoute } from '@angular/router';
import { ResultateService } from 'app/entities/resultate/service/resultate.service';
import { HttpResponse } from '@angular/common/http';
import { IResultate } from 'app/entities/resultate/resultate.model';
import { ISchuetze } from 'app/entities/schuetze/schuetze.model';
import { ResultateDialogComponent } from '../resultate-dialog/resultate-dialog.component';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { PassenDialogComponent } from '../passen-dialog/passen-dialog.component';
import { RanglisteService } from '../../rangliste/service/rangliste.service';
import { GenerateRanglisteDialogComponent } from '../generate-rangliste-dialog/generate-rangliste-dialog.component';

@Component({
  selector: 'jhi-overview',
  templateUrl: './wettkampf-overview.component.html',
  styleUrls: ['./wettkampf-overview.component.scss'],
})
export class WettkampfOverviewComponent implements OnInit {
  wettkampf!: IWettkampf;

  resultate: Array<IResultate> | null = [];
  schuetzen: Array<ISchuetze> | null = [];

  constructor(
    protected activatedRoute: ActivatedRoute,
    private resultateService: ResultateService,
    private ranglisteService: RanglisteService,
    protected modalService: NgbModal
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ wettkampf }) => {
      this.wettkampf = wettkampf;
      this.resultateService.findByWettkampf(wettkampf).subscribe((res: HttpResponse<Array<IResultate>>) => {
        console.log(res.body);
        this.resultate = res.body;

        const tempSchuetzen: Array<ISchuetze> = [];
        this.resultate?.forEach(value => {
          if (value.schuetze) {
            tempSchuetzen.push(value.schuetze);
          }
        });

        this.schuetzen = tempSchuetzen.filter((s, i, arr) => arr.indexOf(<ISchuetze>arr.find(t => t.id === s.id)) === i);
      });
    });
  }

  adminSchuetze(wettkampf: IWettkampf): void {
    const modalRef = this.modalService.open(ResultateDialogComponent, { size: 'xl', backdrop: 'static' });
    modalRef.componentInstance.wettkampf = wettkampf;
    modalRef.closed.subscribe(reason => {
      this.loadPage();
    });
  }

  openPassenDialog(resultat: IResultate): void {
    const modalRef = this.modalService.open(PassenDialogComponent, { size: 'xl', backdrop: 'static' });
    modalRef.componentInstance.resultat = resultat;
  }

  getResultateBySchuetze(schuetze: ISchuetze): Array<IResultate> {
    if (this.resultate !== null) {
      return this.resultate.filter(s => s.schuetze?.id === schuetze.id).sort((a, b) => <number>a.runde - <number>b.runde);
    } else {
      return [];
    }
  }

  generateRangliste(wettkampf: IWettkampf): void {
    const modalRef = this.modalService.open(GenerateRanglisteDialogComponent, { size: 'l', backdrop: 'static' });
    modalRef.componentInstance.wettkampf = wettkampf;
  }

  private loadPage(): void {
    this.activatedRoute.data.subscribe(({ wettkampf }) => {
      this.wettkampf = wettkampf;
      this.resultateService.findByWettkampf(wettkampf).subscribe((res: HttpResponse<Array<IResultate>>) => {
        console.log(res.body);
        this.resultate = res.body;

        const tempSchuetzen: Array<ISchuetze> = [];
        this.resultate?.forEach(value => {
          if (value.schuetze) {
            tempSchuetzen.push(value.schuetze);
          }
        });

        this.schuetzen = tempSchuetzen.filter((s, i, arr) => arr.indexOf(<ISchuetze>arr.find(t => t.id === s.id)) === i);
      });
    });
  }
}
