import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ResultateComponent } from './list/resultate.component';
import { ResultateDetailComponent } from './detail/resultate-detail.component';
import { ResultateUpdateComponent } from './update/resultate-update.component';
import { ResultateDeleteDialogComponent } from './delete/resultate-delete-dialog.component';
import { ResultateRoutingModule } from './route/resultate-routing.module';

@NgModule({
  imports: [SharedModule, ResultateRoutingModule],
  declarations: [ResultateComponent, ResultateDetailComponent, ResultateUpdateComponent, ResultateDeleteDialogComponent],
  entryComponents: [ResultateDeleteDialogComponent],
})
export class ResultateModule {}
