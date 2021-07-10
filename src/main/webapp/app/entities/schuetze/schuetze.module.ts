import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { SchuetzeComponent } from './list/schuetze.component';
import { SchuetzeDetailComponent } from './detail/schuetze-detail.component';
import { SchuetzeUpdateComponent } from './update/schuetze-update.component';
import { SchuetzeDeleteDialogComponent } from './delete/schuetze-delete-dialog.component';
import { SchuetzeRoutingModule } from './route/schuetze-routing.module';

@NgModule({
  imports: [SharedModule, SchuetzeRoutingModule],
  declarations: [SchuetzeComponent, SchuetzeDetailComponent, SchuetzeUpdateComponent, SchuetzeDeleteDialogComponent],
  entryComponents: [SchuetzeDeleteDialogComponent],
})
export class SchuetzeModule {}
