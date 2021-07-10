import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IWettkampf } from '../wettkampf.model';
import { WettkampfService } from '../service/wettkampf.service';

@Component({
  templateUrl: './wettkampf-delete-dialog.component.html',
})
export class WettkampfDeleteDialogComponent {
  wettkampf?: IWettkampf;

  constructor(protected wettkampfService: WettkampfService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.wettkampfService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
