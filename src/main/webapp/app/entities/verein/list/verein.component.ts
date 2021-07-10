import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IVerein } from '../verein.model';
import { VereinService } from '../service/verein.service';
import { VereinDeleteDialogComponent } from '../delete/verein-delete-dialog.component';

@Component({
  selector: 'jhi-verein',
  templateUrl: './verein.component.html',
})
export class VereinComponent implements OnInit {
  vereins?: IVerein[];
  isLoading = false;

  constructor(protected vereinService: VereinService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.vereinService.query().subscribe(
      (res: HttpResponse<IVerein[]>) => {
        this.isLoading = false;
        this.vereins = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IVerein): number {
    return item.id!;
  }

  delete(verein: IVerein): void {
    const modalRef = this.modalService.open(VereinDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.verein = verein;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
