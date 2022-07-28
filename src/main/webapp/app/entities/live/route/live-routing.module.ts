import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ViewComponent } from '../view/view.component';
import { CompetitionRoutingResolveService } from '../../competition/route/competition-routing-resolve.service';

const schuetzeRoute: Routes = [
  {
    path: ':id',
    component: ViewComponent,
    resolve: {
      wettkampf: CompetitionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(schuetzeRoute)],
  exports: [RouterModule],
})
export class LiveRoutingModule {}
