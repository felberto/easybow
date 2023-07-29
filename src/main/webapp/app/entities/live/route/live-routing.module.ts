import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ViewComponent } from '../view/view.component';
import { CompetitionRoutingResolveService } from '../../competition/route/competition-routing-resolve.service';
import { LiveEasvWorldcupComponent } from '../live-easv-worldcup/live-easv-worldcup.component';
import { LiveZsavNawuGmComponent } from '../live-zsav-nawu-gm/live-zsav-nawu-gm.component';
import { LiveEasvStaendematchComponent } from '../live-easv-staendematch/live-easv-staendematch.component';
import { LiveEasvNawuGmComponent } from '../live-easv-nawu-gm/live-easv-nawu-gm.component';
import { LiveEasvSm10mComponent } from '../live-easv-sm-10m/live-easv-sm-10m.component';
import { LiveEasvVerbaendefinalComponent } from '../live-easv-verbaendefinal/live-easv-verbaendefinal.component';
import { LiveEasvWorldcup30mComponent } from '../live-easv-worldcup-30m/live-easv-worldcup-30m.component';

const athleteRoute: Routes = [
  {
    path: ':id',
    component: ViewComponent,
    resolve: {
      competition: CompetitionRoutingResolveService,
    },
  },
  {
    path: ':id/live-easv-worldcup',
    component: LiveEasvWorldcupComponent,
    resolve: {
      competition: CompetitionRoutingResolveService,
    },
  },
  {
    path: ':id/live-easv-worldcup-30m',
    component: LiveEasvWorldcup30mComponent,
    resolve: {
      competition: CompetitionRoutingResolveService,
    },
  },
  {
    path: ':id/live-easv-sm-10m',
    component: LiveEasvSm10mComponent,
    resolve: {
      competition: CompetitionRoutingResolveService,
    },
  },
  {
    path: ':id/live-easv-nawu-gm',
    component: LiveEasvNawuGmComponent,
    resolve: {
      competition: CompetitionRoutingResolveService,
    },
  },
  {
    path: ':id/live-zsav-nawu-gm',
    component: LiveZsavNawuGmComponent,
    resolve: {
      competition: CompetitionRoutingResolveService,
    },
  },
  {
    path: ':id/live-easv-staendematch',
    component: LiveEasvStaendematchComponent,
    resolve: {
      competition: CompetitionRoutingResolveService,
    },
  },
  {
    path: ':id/live-easv-verbaendefinal',
    component: LiveEasvVerbaendefinalComponent,
    resolve: {
      competition: CompetitionRoutingResolveService,
    },
  },
];

@NgModule({
  imports: [RouterModule.forChild(athleteRoute)],
  exports: [RouterModule],
})
export class LiveRoutingModule {}
