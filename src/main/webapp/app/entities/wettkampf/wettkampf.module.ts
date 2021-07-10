import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { WettkampfComponent } from './list/wettkampf.component';
import { WettkampfDetailComponent } from './detail/wettkampf-detail.component';
import { WettkampfUpdateComponent } from './update/wettkampf-update.component';
import { WettkampfDeleteDialogComponent } from './delete/wettkampf-delete-dialog.component';
import { WettkampfRoutingModule } from './route/wettkampf-routing.module';

@NgModule({
  imports: [SharedModule, WettkampfRoutingModule],
  declarations: [WettkampfComponent, WettkampfDetailComponent, WettkampfUpdateComponent, WettkampfDeleteDialogComponent],
  entryComponents: [WettkampfDeleteDialogComponent],
})
export class WettkampfModule {}
