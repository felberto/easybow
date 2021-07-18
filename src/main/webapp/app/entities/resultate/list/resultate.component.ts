import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IResultate } from '../resultate.model';
import { ResultateService } from '../service/resultate.service';
import { ResultateDeleteDialogComponent } from '../delete/resultate-delete-dialog.component';

@Component({
  selector: 'jhi-resultate',
  templateUrl: './resultate.component.html',
})
export class ResultateComponent implements OnInit {
  resultates?: IResultate[];
  isLoading = false;

  constructor(protected resultateService: ResultateService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.resultateService.query().subscribe(
      (res: HttpResponse<IResultate[]>) => {
        this.isLoading = false;
        this.resultates = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IResultate): number {
    return item.id!;
  }

  delete(resultate: IResultate): void {
    const modalRef = this.modalService.open(ResultateDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.resultate = resultate;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
