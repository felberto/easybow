import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IResults } from '../results.model';
import { ResultsService } from '../service/results.service';

@Component({
  templateUrl: './results-delete-dialog.component.html',
})
export class ResultsDeleteDialogComponent {
  results?: IResults;

  constructor(protected resultsService: ResultsService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.resultsService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
