import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { VerbandComponent } from './list/verband.component';
import { VerbandDetailComponent } from './detail/verband-detail.component';
import { VerbandUpdateComponent } from './update/verband-update.component';
import { VerbandDeleteDialogComponent } from './delete/verband-delete-dialog.component';
import { VerbandRoutingModule } from './route/verband-routing.module';

@NgModule({
  imports: [SharedModule, VerbandRoutingModule],
  declarations: [VerbandComponent, VerbandDetailComponent, VerbandUpdateComponent, VerbandDeleteDialogComponent],
  entryComponents: [VerbandDeleteDialogComponent],
})
export class VerbandModule {}
