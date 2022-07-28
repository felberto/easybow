import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ViewComponent } from '../view/view.component';
import { CompetitionRoutingResolveService } from '../../competition/route/competition-routing-resolve.service';

const athleteRoute: Routes = [
  {
    path: ':id',
    component: ViewComponent,
    resolve: {
      competition: CompetitionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(athleteRoute)],
  exports: [RouterModule],
})
export class LiveRoutingModule {}
