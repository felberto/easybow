import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISchuetze } from '../schuetze.model';
import { SchuetzeService } from '../service/schuetze.service';
import { SchuetzeDeleteDialogComponent } from '../delete/schuetze-delete-dialog.component';

@Component({
  selector: 'jhi-schuetze',
  templateUrl: './schuetze.component.html',
})
export class SchuetzeComponent implements OnInit {
  schuetzes?: ISchuetze[];
  isLoading = false;

  constructor(protected schuetzeService: SchuetzeService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.schuetzeService.query().subscribe(
      (res: HttpResponse<ISchuetze[]>) => {
        this.isLoading = false;
        this.schuetzes = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: ISchuetze): number {
    return item.id!;
  }

  delete(schuetze: ISchuetze): void {
    const modalRef = this.modalService.open(SchuetzeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.schuetze = schuetze;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
