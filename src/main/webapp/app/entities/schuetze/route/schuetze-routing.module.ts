import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { SchuetzeComponent } from '../list/schuetze.component';
import { SchuetzeDetailComponent } from '../detail/schuetze-detail.component';
import { SchuetzeUpdateComponent } from '../update/schuetze-update.component';
import { SchuetzeRoutingResolveService } from './schuetze-routing-resolve.service';

const schuetzeRoute: Routes = [
  {
    path: '',
    component: SchuetzeComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SchuetzeDetailComponent,
    resolve: {
      schuetze: SchuetzeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SchuetzeUpdateComponent,
    resolve: {
      schuetze: SchuetzeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SchuetzeUpdateComponent,
    resolve: {
      schuetze: SchuetzeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(schuetzeRoute)],
  exports: [RouterModule],
})
export class SchuetzeRoutingModule {}
