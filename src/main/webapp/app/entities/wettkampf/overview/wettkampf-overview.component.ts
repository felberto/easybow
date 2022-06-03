import { Component, Inject, OnInit } from '@angular/core';
import { IWettkampf } from '../wettkampf.model';
import { ActivatedRoute, Router } from '@angular/router';
import { ResultateService } from 'app/entities/resultate/service/resultate.service';
import { IResultate } from 'app/entities/resultate/resultate.model';
import { ISchuetze } from 'app/entities/schuetze/schuetze.model';
import { ResultateDialogComponent } from '../resultate-dialog/resultate-dialog.component';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { PassenDialog2Component } from '../passen-dialog-2/passen-dialog-2.component';
import * as dayjs from 'dayjs';
import { RundeService } from '../../runde/service/runde.service';
import { AlertService } from '../../../core/util/alert.service';
import { AccountService } from '../../../core/auth/account.service';
import { RanglisteService } from '../service/rangliste.service';
import { TuiNotification, TuiNotificationsService } from '@taiga-ui/core';
import { ConfirmDialogComponent } from '../confirm-dialog/confirm-dialog.component';
import { WettkampfService } from '../service/wettkampf.service';
import { PassenDialog1Component } from '../passen-dialog-1/passen-dialog-1.component';

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
    private wettkampfService: WettkampfService,
    protected modalService: NgbModal,
    private rundeService: RundeService,
    private alertService: AlertService,
    private accountService: AccountService,
    private router: Router,
    @Inject(TuiNotificationsService)
    private readonly notificationsService: TuiNotificationsService
  ) {}

  ngOnInit(): void {
    this.liveviewPath = window.location.origin + '/liveview/';

    this.activatedRoute.data.subscribe(({ wettkampf }) => {
      this.wettkampf = wettkampf;
      this.resultateService.findByWettkampf(wettkampf).subscribe(res => {
        this.resultate = res.body;

        const tempSchuetzen: Array<ISchuetze> = [];
        this.resultate?.forEach(value => {
          if (value.schuetze) {
            tempSchuetzen.push(value.schuetze);
          }
        });

        this.schuetzen = tempSchuetzen.filter((s, i, arr) => arr.indexOf(<ISchuetze>arr.find(t => t.id === s.id)) === i);
        // filter only verein schützen
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
    modalRef.closed.subscribe(() => {
      this.loadPage();
    });
  }

  exportWettkampf(wettkampf: IWettkampf): void {
    this.wettkampfService.exportData(wettkampf).subscribe(data => {
      const blob = new Blob([data], {
        type: 'application/json', // must match the Accept type
      });
      const link = document.createElement('a');
      link.href = window.URL.createObjectURL(blob);
      link.target = '_blank';
      link.download = wettkampf.name! + '_' + wettkampf.jahr!.toString();
      link.click();
      window.URL.revokeObjectURL(link.href);
    });
  }

  openPassenDialog(resultat: IResultate): void {
    if (resultat.wettkampf?.id !== undefined && resultat.runde !== undefined) {
      this.rundeService.findByRundeAndWettkampf(resultat.runde, resultat.wettkampf.id).subscribe(res => {
        if (this.userIsZsavOrAdmin()) {
          this.openDialog(resultat);
        } else if (res.body?.datum?.toDate() !== undefined) {
          if (res.body.datum.toDate() < dayjs().toDate()) {
            this.notificationsService
              .show(`Resultateingabe geschlossen seit ${res.body.datum.toDate().toLocaleDateString('de-DE')}`, {
                label: 'Resultateingabe bereits geschlossen',
                status: TuiNotification.Warning,
              })
              .subscribe();
          } else {
            if (resultat.runde !== 99) {
              this.openDialog(resultat);
            } else {
              this.notificationsService
                .show('Nicht berechtigt Finalresultat zu bearbeiten', {
                  label: 'Keine Berechtigung',
                  status: TuiNotification.Warning,
                })
                .subscribe();
            }
          }
        }
      });
    }
  }

  openDialog(resultat: IResultate): void {
    if (resultat.wettkampf?.anzahlPassen === 1) {
      const modalRef = this.modalService.open(PassenDialog1Component, {
        size: 'xl',
        backdrop: 'static',
      });
      modalRef.componentInstance.resultat = resultat;
      modalRef.closed.subscribe(() => {
        this.loadPage();
      });
    } else if (resultat.wettkampf?.anzahlPassen === 2) {
      const modalRef = this.modalService.open(PassenDialog2Component, {
        size: 'xl',
        backdrop: 'static',
      });
      modalRef.componentInstance.resultat = resultat;
      modalRef.closed.subscribe(() => {
        this.loadPage();
      });
    }
  }

  userIsZsavOrAdmin(): boolean {
    let isZsavOrAdmin = false;
    this.accountService.getAuthorites().forEach(role => {
      if (role === 'ROLE_ADMIN' || role === 'ROLE_ZSAV') {
        isZsavOrAdmin = true;
      }
    });
    return isZsavOrAdmin;
  }

  getResultateBySchuetze(schuetze: ISchuetze): Array<IResultate> {
    if (this.resultate !== null) {
      return this.resultate.filter(s => s.schuetze?.id === schuetze.id).sort((a, b) => <number>a.runde - <number>b.runde);
    } else {
      return [];
    }
  }

  getResultateBySchuetzeAndRunde(schuetze: ISchuetze, runde: number): number {
    let returnValue = 0;
    if (this.resultate !== null) {
      this.resultate
        .filter(s => s.schuetze?.id === schuetze.id)
        .filter(r => r.runde === runde)
        .map(value => {
          if (typeof value.resultat === 'number') {
            returnValue = value.resultat;
          }
        });
    }
    return returnValue;
  }

  getResultateBySchuetzeIfRunde99(schuetze: ISchuetze): boolean {
    let returnValue = false;
    if (this.resultate !== null) {
      returnValue = this.resultate.filter(s => s.schuetze?.id === schuetze.id).find(e => e.runde === 99) !== undefined;
    }
    return returnValue;
  }

  isFinalReady(): boolean {
    let returnValue = false;
    if (this.resultate !== null) {
      returnValue = this.resultate.find(e => e.runde === 99) !== undefined;
    }
    return returnValue;
  }

  createFinal(wettkampf: IWettkampf): void {
    const modalRef = this.modalService.open(ConfirmDialogComponent, { size: 'xl', backdrop: 'static' });
    modalRef.componentInstance.wettkampf = wettkampf;
    modalRef.closed.subscribe(() => {
      this.loadPage();
    });
  }

  private loadPage(): void {
    const currentUrl = this.router.url;
    this.router.navigateByUrl('/', { skipLocationChange: true }).then(() => {
      this.router.navigate([currentUrl]);
    });
    /*this.activatedRoute.data.subscribe(({ wettkampf }) => {
      this.wettkampf = wettkampf;
      this.resultateService.findByWettkampf(wettkampf).subscribe((res: HttpResponse<Array<IResultate>>) => {
        this.resultate = res.body;

        const tempSchuetzen: Array<ISchuetze> = [];
        this.resultate?.forEach(value => {
          if (value.schuetze) {
            tempSchuetzen.push(value.schuetze);
          }
        });

        this.schuetzen = tempSchuetzen.filter((s, i, arr) => arr.indexOf(<ISchuetze>arr.find(t => t.id === s.id)) === i);
      });
    });*/
  }
}
