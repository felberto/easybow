import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { RundeComponent } from './list/runde.component';
import { RundeDetailComponent } from './detail/runde-detail.component';
import { RundeUpdateComponent } from './update/runde-update.component';
import { RundeDeleteDialogComponent } from './delete/runde-delete-dialog.component';
import { RundeRoutingModule } from './route/runde-routing.module';

@NgModule({
  imports: [SharedModule, RundeRoutingModule],
  declarations: [RundeComponent, RundeDetailComponent, RundeUpdateComponent, RundeDeleteDialogComponent],
  entryComponents: [RundeDeleteDialogComponent],
})
export class RundeModule {}
