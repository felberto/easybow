import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { RangierungComponent } from './list/rangierung.component';
import { RangierungDetailComponent } from './detail/rangierung-detail.component';
import { RangierungUpdateComponent } from './update/rangierung-update.component';
import { RangierungDeleteDialogComponent } from './delete/rangierung-delete-dialog.component';
import { RangierungRoutingModule } from './route/rangierung-routing.module';

@NgModule({
  imports: [SharedModule, RangierungRoutingModule],
  declarations: [RangierungComponent, RangierungDetailComponent, RangierungUpdateComponent, RangierungDeleteDialogComponent],
  entryComponents: [RangierungDeleteDialogComponent],
})
export class RangierungModule {}
