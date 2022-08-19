import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ViewComponent } from '../view/view.component';
import { CompetitionRoutingResolveService } from '../../competition/route/competition-routing-resolve.service';
import { LiveEasvWorldcupComponent } from '../live-easv-worldcup/live-easv-worldcup.component';
import { LiveZsavNawuGmComponent } from '../live-zsav-nawu-gm/live-zsav-nawu-gm.component';

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
    path: ':id/live-zsav-nawu-gm',
    component: LiveZsavNawuGmComponent,
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
