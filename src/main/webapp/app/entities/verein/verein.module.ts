import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { VereinComponent } from './list/verein.component';
import { VereinDetailComponent } from './detail/verein-detail.component';
import { VereinUpdateComponent } from './update/verein-update.component';
import { VereinDeleteDialogComponent } from './delete/verein-delete-dialog.component';
import { VereinRoutingModule } from './route/verein-routing.module';

@NgModule({
  imports: [SharedModule, VereinRoutingModule],
  declarations: [VereinComponent, VereinDetailComponent, VereinUpdateComponent, VereinDeleteDialogComponent],
  entryComponents: [VereinDeleteDialogComponent],
})
export class VereinModule {}
