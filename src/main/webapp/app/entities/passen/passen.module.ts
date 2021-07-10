import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PassenComponent } from './list/passen.component';
import { PassenDetailComponent } from './detail/passen-detail.component';
import { PassenUpdateComponent } from './update/passen-update.component';
import { PassenDeleteDialogComponent } from './delete/passen-delete-dialog.component';
import { PassenRoutingModule } from './route/passen-routing.module';

@NgModule({
  imports: [SharedModule, PassenRoutingModule],
  declarations: [PassenComponent, PassenDetailComponent, PassenUpdateComponent, PassenDeleteDialogComponent],
  entryComponents: [PassenDeleteDialogComponent],
})
export class PassenModule {}
