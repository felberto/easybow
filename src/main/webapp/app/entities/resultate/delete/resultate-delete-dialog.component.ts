import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IResultate } from '../resultate.model';
import { ResultateService } from '../service/resultate.service';

@Component({
  templateUrl: './resultate-delete-dialog.component.html',
})
export class ResultateDeleteDialogComponent {
  resultate?: IResultate;

  constructor(protected resultateService: ResultateService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.resultateService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
