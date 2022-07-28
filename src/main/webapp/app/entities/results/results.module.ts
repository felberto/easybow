import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ResultsComponent } from './list/results.component';
import { ResultsDetailComponent } from './detail/results-detail.component';
import { ResultsUpdateComponent } from './update/results-update.component';
import { ResultsDeleteDialogComponent } from './delete/results-delete-dialog.component';
import { ResultsRoutingModule } from './route/results-routing.module';

@NgModule({
  imports: [SharedModule, ResultsRoutingModule],
  declarations: [ResultsComponent, ResultsDetailComponent, ResultsUpdateComponent, ResultsDeleteDialogComponent],
  entryComponents: [ResultsDeleteDialogComponent],
})
export class ResultsModule {}
