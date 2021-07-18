import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { GruppenComponent } from '../list/gruppen.component';
import { GruppenDetailComponent } from '../detail/gruppen-detail.component';
import { GruppenUpdateComponent } from '../update/gruppen-update.component';
import { GruppenRoutingResolveService } from './gruppen-routing-resolve.service';

const gruppenRoute: Routes = [
  {
    path: '',
    component: GruppenComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: GruppenDetailComponent,
    resolve: {
      gruppen: GruppenRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: GruppenUpdateComponent,
    resolve: {
      gruppen: GruppenRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: GruppenUpdateComponent,
    resolve: {
      gruppen: GruppenRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(gruppenRoute)],
  exports: [RouterModule],
})
export class GruppenRoutingModule {}
