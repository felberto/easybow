import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IVerband } from '../verband.model';
import { VerbandService } from '../service/verband.service';
import { VerbandDeleteDialogComponent } from '../delete/verband-delete-dialog.component';

@Component({
  selector: 'jhi-verband',
  templateUrl: './verband.component.html',
})
export class VerbandComponent implements OnInit {
  verbands?: IVerband[];
  isLoading = false;

  constructor(protected verbandService: VerbandService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.verbandService.query().subscribe(
      (res: HttpResponse<IVerband[]>) => {
        this.isLoading = false;
        this.verbands = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IVerband): number {
    return item.id!;
  }

  delete(verband: IVerband): void {
    const modalRef = this.modalService.open(VerbandDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.verband = verband;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
