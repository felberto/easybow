import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PassenComponent } from '../list/passen.component';
import { PassenDetailComponent } from '../detail/passen-detail.component';
import { PassenUpdateComponent } from '../update/passen-update.component';
import { PassenRoutingResolveService } from './passen-routing-resolve.service';

const passenRoute: Routes = [
  {
    path: '',
    component: PassenComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PassenDetailComponent,
    resolve: {
      passen: PassenRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PassenUpdateComponent,
    resolve: {
      passen: PassenRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PassenUpdateComponent,
    resolve: {
      passen: PassenRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(passenRoute)],
  exports: [RouterModule],
})
export class PassenRoutingModule {}
