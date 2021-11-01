import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IRunde } from '../runde.model';
import { RundeService } from '../service/runde.service';

@Component({
  templateUrl: './runde-delete-dialog.component.html',
})
export class RundeDeleteDialogComponent {
  runde?: IRunde;

  constructor(protected rundeService: RundeService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.rundeService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
