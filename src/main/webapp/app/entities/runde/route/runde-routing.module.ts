import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { RundeComponent } from '../list/runde.component';
import { RundeDetailComponent } from '../detail/runde-detail.component';
import { RundeUpdateComponent } from '../update/runde-update.component';
import { RundeRoutingResolveService } from './runde-routing-resolve.service';

const rundeRoute: Routes = [
  {
    path: '',
    component: RundeComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: RundeDetailComponent,
    resolve: {
      runde: RundeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: RundeUpdateComponent,
    resolve: {
      runde: RundeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: RundeUpdateComponent,
    resolve: {
      runde: RundeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(rundeRoute)],
  exports: [RouterModule],
})
export class RundeRoutingModule {}
