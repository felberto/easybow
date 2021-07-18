import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { RangierungComponent } from '../list/rangierung.component';
import { RangierungDetailComponent } from '../detail/rangierung-detail.component';
import { RangierungUpdateComponent } from '../update/rangierung-update.component';
import { RangierungRoutingResolveService } from './rangierung-routing-resolve.service';

const rangierungRoute: Routes = [
  {
    path: '',
    component: RangierungComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: RangierungDetailComponent,
    resolve: {
      rangierung: RangierungRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: RangierungUpdateComponent,
    resolve: {
      rangierung: RangierungRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: RangierungUpdateComponent,
    resolve: {
      rangierung: RangierungRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(rangierungRoute)],
  exports: [RouterModule],
})
export class RangierungRoutingModule {}
