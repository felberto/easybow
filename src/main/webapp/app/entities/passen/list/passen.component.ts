import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPassen } from '../passen.model';
import { PassenService } from '../service/passen.service';
import { PassenDeleteDialogComponent } from '../delete/passen-delete-dialog.component';

@Component({
  selector: 'jhi-passen',
  templateUrl: './passen.component.html',
})
export class PassenComponent implements OnInit {
  passens?: IPassen[];
  isLoading = false;

  constructor(protected passenService: PassenService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.passenService.query().subscribe(
      (res: HttpResponse<IPassen[]>) => {
        this.isLoading = false;
        this.passens = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IPassen): number {
    return item.id!;
  }

  delete(passen: IPassen): void {
    const modalRef = this.modalService.open(PassenDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.passen = passen;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
