import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { VerbandComponent } from '../list/verband.component';
import { VerbandDetailComponent } from '../detail/verband-detail.component';
import { VerbandUpdateComponent } from '../update/verband-update.component';
import { VerbandRoutingResolveService } from './verband-routing-resolve.service';

const verbandRoute: Routes = [
  {
    path: '',
    component: VerbandComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: VerbandDetailComponent,
    resolve: {
      verband: VerbandRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: VerbandUpdateComponent,
    resolve: {
      verband: VerbandRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: VerbandUpdateComponent,
    resolve: {
      verband: VerbandRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(verbandRoute)],
  exports: [RouterModule],
})
export class VerbandRoutingModule {}
