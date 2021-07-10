import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { GruppenComponent } from './list/gruppen.component';
import { GruppenDetailComponent } from './detail/gruppen-detail.component';
import { GruppenUpdateComponent } from './update/gruppen-update.component';
import { GruppenDeleteDialogComponent } from './delete/gruppen-delete-dialog.component';
import { GruppenRoutingModule } from './route/gruppen-routing.module';

@NgModule({
  imports: [SharedModule, GruppenRoutingModule],
  declarations: [GruppenComponent, GruppenDetailComponent, GruppenUpdateComponent, GruppenDeleteDialogComponent],
  entryComponents: [GruppenDeleteDialogComponent],
})
export class GruppenModule {}
