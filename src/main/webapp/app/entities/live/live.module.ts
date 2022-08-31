import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ViewComponent } from './view/view.component';
import { SharedModule } from '../../shared/shared.module';
import { LiveRoutingModule } from './route/live-routing.module';
import { LiveEasvWorldcupComponent } from './live-easv-worldcup/live-easv-worldcup.component';
import { TuiTabsModule } from '@taiga-ui/kit';
import { LiveZsavNawuGmComponent } from './live-zsav-nawu-gm/live-zsav-nawu-gm.component';
import { LiveEasvStaendematchComponent } from './live-easv-staendematch/live-easv-staendematch.component';

@NgModule({
  declarations: [ViewComponent, LiveEasvWorldcupComponent, LiveZsavNawuGmComponent, LiveEasvStaendematchComponent],
  imports: [CommonModule, SharedModule, LiveRoutingModule, TuiTabsModule],
})
export class LiveModule {}
