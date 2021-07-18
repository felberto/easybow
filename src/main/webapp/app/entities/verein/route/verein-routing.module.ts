import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { VereinComponent } from '../list/verein.component';
import { VereinDetailComponent } from '../detail/verein-detail.component';
import { VereinUpdateComponent } from '../update/verein-update.component';
import { VereinRoutingResolveService } from './verein-routing-resolve.service';

const vereinRoute: Routes = [
  {
    path: '',
    component: VereinComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: VereinDetailComponent,
    resolve: {
      verein: VereinRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: VereinUpdateComponent,
    resolve: {
      verein: VereinRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: VereinUpdateComponent,
    resolve: {
      verein: VereinRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(vereinRoute)],
  exports: [RouterModule],
})
export class VereinRoutingModule {}
