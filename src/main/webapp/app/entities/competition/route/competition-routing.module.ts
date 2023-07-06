import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CompetitionComponent } from '../list/competition.component';
import { CompetitionDetailComponent } from '../detail/competition-detail.component';
import { CompetitionUpdateComponent } from '../update/competition-update.component';
import { CompetitionRoutingResolveService } from './competition-routing-resolve.service';
import { CompetitionRankingComponent } from '../ranking/competition-ranking.component';
import { CompetitionOverviewComponent } from '../overview/competition-overview.component';
import { RankingListComponent } from '../rankingList/rankingList.component';
import { CompetitionOverviewEasvWorldcupComponent } from '../overview-easv-worldcup/competition-overview-easv-worldcup.component';
import { RankingListEasvWorldcupComponent } from '../ranking-list-easv-worldcup/ranking-list-easv-worldcup.component';
import { CompetitionOverviewZsavNawuGmComponent } from '../overview-zsav-nawu-gm/competition-overview-zsav-nawu-gm.component';
import { RankingListZsavNawuGmComponent } from '../ranking-list-zsav-nawu-gm/ranking-list-zsav-nawu-gm.component';
import { CompetitionOverviewEasvStaendematchComponent } from '../overview-easv-staendematch/competition-overview-easv-staendematch.component';
import { RankingListEasvStaendematchComponent } from '../ranking-list-easv-staendematch/ranking-list-easv-staendematch.component';
import { CompetitionOverviewEasvNawuGmComponent } from '../overview-easv-nawu-gm/competition-overview-easv-nawu-gm.component';
import { RankingListEasvNawuGmComponent } from '../ranking-list-easv-nawu-gm/ranking-list-easv-nawu-gm.component';
import { CompetitionOverviewEasvSm10mComponent } from '../overview-easv-sm-10m/competition-overview-easv-sm-10m.component';
import { RankingListEasvSm10mComponent } from '../ranking-list-easv-sm-10m/ranking-list-easv-sm-10m.component';
import { CompetitionOverviewEasvVerbaendefinalComponent } from '../overview-easv-verbaendefinal/competition-overview-easv-verbaendefinal.component';
import { RankingListEasvVerbaendefinalComponent } from '../ranking-list-easv-verbaendefinal/ranking-list-easv-verbaendefinal.component';

const competitionRoute: Routes = [
  {
    path: '',
    component: CompetitionComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CompetitionDetailComponent,
    resolve: {
      competition: CompetitionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/ranking',
    component: CompetitionRankingComponent,
    resolve: {
      competition: CompetitionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/overview',
    component: CompetitionOverviewComponent,
    resolve: {
      competition: CompetitionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/overview-easv-worldcup',
    component: CompetitionOverviewEasvWorldcupComponent,
    resolve: {
      competition: CompetitionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/overview-easv-sm-10m',
    component: CompetitionOverviewEasvSm10mComponent,
    resolve: {
      competition: CompetitionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/overview-easv-nawu-gm',
    component: CompetitionOverviewEasvNawuGmComponent,
    resolve: {
      competition: CompetitionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/overview-zsav-nawu-gm',
    component: CompetitionOverviewZsavNawuGmComponent,
    resolve: {
      competition: CompetitionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/overview-easv-staendematch',
    component: CompetitionOverviewEasvStaendematchComponent,
    resolve: {
      competition: CompetitionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/overview-easv-verbaendefinal',
    component: CompetitionOverviewEasvVerbaendefinalComponent,
    resolve: {
      competition: CompetitionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CompetitionUpdateComponent,
    resolve: {
      competition: CompetitionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CompetitionUpdateComponent,
    resolve: {
      competition: CompetitionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/rankingList',
    component: RankingListComponent,
    resolve: {
      competition: CompetitionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/ranking-list-easv-worldcup',
    component: RankingListEasvWorldcupComponent,
    resolve: {
      competition: CompetitionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/ranking-list-easv-sm-10m',
    component: RankingListEasvSm10mComponent,
    resolve: {
      competition: CompetitionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/ranking-list-easv-nawu-gm',
    component: RankingListEasvNawuGmComponent,
    resolve: {
      competition: CompetitionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/ranking-list-zsav-nawu-gm',
    component: RankingListZsavNawuGmComponent,
    resolve: {
      competition: CompetitionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/ranking-list-easv-staendematch',
    component: RankingListEasvStaendematchComponent,
    resolve: {
      competition: CompetitionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/ranking-list-easv-verbaendefinal',
    component: RankingListEasvVerbaendefinalComponent,
    resolve: {
      competition: CompetitionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(competitionRoute)],
  exports: [RouterModule],
})
export class CompetitionRoutingModule {}
