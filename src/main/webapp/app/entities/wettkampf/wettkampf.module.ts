import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { WettkampfComponent } from './list/wettkampf.component';
import { WettkampfDetailComponent } from './detail/wettkampf-detail.component';
import { WettkampfUpdateComponent } from './update/wettkampf-update.component';
import { WettkampfDeleteDialogComponent } from './delete/wettkampf-delete-dialog.component';
import { WettkampfRoutingModule } from './route/wettkampf-routing.module';
import { WettkampfRangierungComponent } from './rangierung/wettkampf-rangierung.component';
import { DragDropModule } from '@angular/cdk/drag-drop';

@NgModule({
  imports: [SharedModule, WettkampfRoutingModule, DragDropModule],
  declarations: [
    WettkampfComponent,
    WettkampfDetailComponent,
    WettkampfUpdateComponent,
    WettkampfDeleteDialogComponent,
    WettkampfRangierungComponent,
  ],
  entryComponents: [WettkampfDeleteDialogComponent],
})
export class WettkampfModule {}
