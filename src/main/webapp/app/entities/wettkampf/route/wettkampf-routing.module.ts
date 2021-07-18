import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { WettkampfComponent } from '../list/wettkampf.component';
import { WettkampfDetailComponent } from '../detail/wettkampf-detail.component';
import { WettkampfUpdateComponent } from '../update/wettkampf-update.component';
import { WettkampfRoutingResolveService } from './wettkampf-routing-resolve.service';

const wettkampfRoute: Routes = [
  {
    path: '',
    component: WettkampfComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: WettkampfDetailComponent,
    resolve: {
      wettkampf: WettkampfRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: WettkampfUpdateComponent,
    resolve: {
      wettkampf: WettkampfRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: WettkampfUpdateComponent,
    resolve: {
      wettkampf: WettkampfRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(wettkampfRoute)],
  exports: [RouterModule],
})
export class WettkampfRoutingModule {}
