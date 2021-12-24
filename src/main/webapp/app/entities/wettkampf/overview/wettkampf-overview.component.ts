import { Component, OnInit } from '@angular/core';
import { IWettkampf } from '../wettkampf.model';
import { ActivatedRoute, Router } from '@angular/router';
import { ResultateService } from 'app/entities/resultate/service/resultate.service';
import { HttpResponse } from '@angular/common/http';
import { IResultate } from 'app/entities/resultate/resultate.model';
import { ISchuetze } from 'app/entities/schuetze/schuetze.model';
import { ResultateDialogComponent } from '../resultate-dialog/resultate-dialog.component';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { PassenDialogComponent } from '../passen-dialog/passen-dialog.component';
import * as dayjs from 'dayjs';
import { RundeService } from '../../runde/service/runde.service';
import { AlertService } from '../../../core/util/alert.service';
import { AccountService } from '../../../core/auth/account.service';
import { RanglisteService } from '../service/rangliste.service';

@Component({
  selector: 'jhi-overview',
  templateUrl: './wettkampf-overview.component.html',
  styleUrls: ['./wettkampf-overview.component.scss'],
})
export class WettkampfOverviewComponent implements OnInit {
  wettkampf!: IWettkampf;
  liveviewPath = '';

  resultate: Array<IResultate> | null = [];
  schuetzen: Array<ISchuetze> | null = [];

  constructor(
    protected activatedRoute: ActivatedRoute,
    private resultateService: ResultateService,
    private ranglisteService: RanglisteService,
    protected modalService: NgbModal,
    private rundeService: RundeService,
    private alertService: AlertService,
    private accountService: AccountService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.liveviewPath = window.location.origin + '/liveview/';

    this.activatedRoute.data.subscribe(({ wettkampf }) => {
      this.wettkampf = wettkampf;
      this.resultateService.findByWettkampf(wettkampf).subscribe(res => {
        console.log(res.body);
        this.resultate = res.body;

        const tempSchuetzen: Array<ISchuetze> = [];
        this.resultate?.forEach(value => {
          if (value.schuetze) {
            console.log(value.schuetze);
            console.log(value.schuetze.verein?.name);
            tempSchuetzen.push(value.schuetze);
          }
        });

        this.schuetzen = tempSchuetzen.filter((s, i, arr) => arr.indexOf(<ISchuetze>arr.find(t => t.id === s.id)) === i);
        //filter only verein schützen
        this.accountService.getAuthorites().forEach(role => {
          if (role !== 'ROLE_USER' && role !== 'ROLE_VEREIN' && role !== 'ROLE_ADMIN' && role !== 'ROLE_ZSAV') {
            this.schuetzen = this.schuetzen!.filter(schuetze => schuetze.rolle === role);
          }
        });
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
    if (resultat.wettkampf?.id !== undefined && resultat.runde !== undefined) {
      this.rundeService.findByRundeAndWettkampf(resultat.runde, resultat.wettkampf.id).subscribe(res => {
        if (res.body?.datum?.toDate() !== undefined) {
          if (res.body.datum.toDate() < dayjs().toDate()) {
            this.alertService.addAlert({
              type: 'warning',
              message: 'Resultateingabe bereits geschlossen',
            });
          } else {
            const modalRef = this.modalService.open(PassenDialogComponent, {
              size: 'xl',
              backdrop: 'static',
            });
            modalRef.componentInstance.resultat = resultat;
          }
        }
      });
    }
  }

  getResultateBySchuetze(schuetze: ISchuetze): Array<IResultate> {
    if (this.resultate !== null) {
      return this.resultate.filter(s => s.schuetze?.id === schuetze.id).sort((a, b) => <number>a.runde - <number>b.runde);
    } else {
      return [];
    }
  }

  createFinal(wettkampf: IWettkampf): void {
    this.ranglisteService.createFinal(wettkampf, 99).subscribe(res => {
      if (res.ok) {
        this.alertService.addAlert({
          type: 'success',
          message: 'Finalvorbereitung erfolgreich abgeschlossen',
        });
      } else {
        this.alertService.addAlert({
          type: 'warning',
          message: 'Finalvorbereitung konnte nicht ausgeführt werden',
        });
      }
    });
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
