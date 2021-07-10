import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IGruppen } from '../gruppen.model';
import { GruppenService } from '../service/gruppen.service';
import { GruppenDeleteDialogComponent } from '../delete/gruppen-delete-dialog.component';

@Component({
  selector: 'jhi-gruppen',
  templateUrl: './gruppen.component.html',
})
export class GruppenComponent implements OnInit {
  gruppens?: IGruppen[];
  isLoading = false;

  constructor(protected gruppenService: GruppenService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.gruppenService.query().subscribe(
      (res: HttpResponse<IGruppen[]>) => {
        this.isLoading = false;
        this.gruppens = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IGruppen): number {
    return item.id!;
  }

  delete(gruppen: IGruppen): void {
    const modalRef = this.modalService.open(GruppenDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.gruppen = gruppen;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
