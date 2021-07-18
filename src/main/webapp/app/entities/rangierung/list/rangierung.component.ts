import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IRangierung } from '../rangierung.model';
import { RangierungService } from '../service/rangierung.service';
import { RangierungDeleteDialogComponent } from '../delete/rangierung-delete-dialog.component';

@Component({
  selector: 'jhi-rangierung',
  templateUrl: './rangierung.component.html',
})
export class RangierungComponent implements OnInit {
  rangierungs?: IRangierung[];
  isLoading = false;

  constructor(protected rangierungService: RangierungService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.rangierungService.query().subscribe(
      (res: HttpResponse<IRangierung[]>) => {
        this.isLoading = false;
        this.rangierungs = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IRangierung): number {
    return item.id!;
  }

  delete(rangierung: IRangierung): void {
    const modalRef = this.modalService.open(RangierungDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.rangierung = rangierung;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
