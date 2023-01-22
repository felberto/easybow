import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ViewComponent } from './view/view.component';
import { SharedModule } from '../../shared/shared.module';
import { LiveRoutingModule } from './route/live-routing.module';
import { LiveEasvWorldcupComponent } from './live-easv-worldcup/live-easv-worldcup.component';
import { TuiTabsModule } from '@taiga-ui/kit';
import { LiveZsavNawuGmComponent } from './live-zsav-nawu-gm/live-zsav-nawu-gm.component';
import { LiveEasvStaendematchComponent } from './live-easv-staendematch/live-easv-staendematch.component';
import { LiveEasvNawuGmComponent } from './live-easv-nawu-gm/live-easv-nawu-gm.component';
import { LiveEasvSm10mComponent } from './live-easv-sm-10m/live-easv-sm-10m.component';

@NgModule({
  declarations: [
    ViewComponent,
    LiveEasvWorldcupComponent,
    LiveEasvSm10mComponent,
    LiveEasvNawuGmComponent,
    LiveZsavNawuGmComponent,
    LiveEasvStaendematchComponent,
  ],
  imports: [CommonModule, SharedModule, LiveRoutingModule, TuiTabsModule],
})
export class LiveModule {}
