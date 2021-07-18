import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ResultateComponent } from '../list/resultate.component';
import { ResultateDetailComponent } from '../detail/resultate-detail.component';
import { ResultateUpdateComponent } from '../update/resultate-update.component';
import { ResultateRoutingResolveService } from './resultate-routing-resolve.service';

const resultateRoute: Routes = [
  {
    path: '',
    component: ResultateComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ResultateDetailComponent,
    resolve: {
      resultate: ResultateRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ResultateUpdateComponent,
    resolve: {
      resultate: ResultateRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ResultateUpdateComponent,
    resolve: {
      resultate: ResultateRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(resultateRoute)],
  exports: [RouterModule],
})
export class ResultateRoutingModule {}
