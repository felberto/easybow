import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ResultsComponent } from '../list/results.component';
import { ResultsDetailComponent } from '../detail/results-detail.component';
import { ResultsUpdateComponent } from '../update/results-update.component';
import { ResultsRoutingResolveService } from './results-routing-resolve.service';

const resultsRoute: Routes = [
  {
    path: '',
    component: ResultsComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ResultsDetailComponent,
    resolve: {
      results: ResultsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ResultsUpdateComponent,
    resolve: {
      results: ResultsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ResultsUpdateComponent,
    resolve: {
      results: ResultsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(resultsRoute)],
  exports: [RouterModule],
})
export class ResultsRoutingModule {}
