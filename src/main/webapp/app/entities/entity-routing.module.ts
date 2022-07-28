import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { Authority } from '../config/authority.constants';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'association',
        data: { pageTitle: 'Association', authorities: [Authority.ADMIN, Authority.ZSAV] },
        loadChildren: () => import('./association/association.module').then(m => m.AssociationModule),
      },
      {
        path: 'club',
        data: { pageTitle: 'Club', authorities: [Authority.ADMIN, Authority.ZSAV] },
        loadChildren: () => import('./club/club.module').then(m => m.ClubModule),
      },
      {
        path: 'athlete',
        data: { pageTitle: 'Athlete', authorities: [Authority.ADMIN, Authority.ZSAV] },
        loadChildren: () => import('./athlete/athlete.module').then(m => m.AthleteModule),
      },
      {
        path: 'competition',
        data: { pageTitle: 'Competition', authorities: [Authority.ADMIN, Authority.ZSAV, Authority.VEREIN] },
        loadChildren: () => import('./competition/competition.module').then(m => m.CompetitionModule),
      },
      {
        path: 'series',
        data: { pageTitle: 'Series', authorities: [Authority.ADMIN, Authority.ZSAV] },
        loadChildren: () => import('./series/series.module').then(m => m.SeriesModule),
      },
      {
        path: 'round',
        data: { pageTitle: 'Round' },
        loadChildren: () => import('./round/round.module').then(m => m.RoundModule),
      },
      {
        path: 'group',
        data: { pageTitle: 'Group', authorities: [Authority.ADMIN, Authority.ZSAV] },
        loadChildren: () => import('./group/group.module').then(m => m.GroupModule),
      },
      {
        path: 'results',
        data: { pageTitle: 'Results', authorities: [Authority.ADMIN, Authority.ZSAV] },
        loadChildren: () => import('./results/results.module').then(m => m.ResultsModule),
      },
      {
        path: 'ranking',
        data: { pageTitle: 'Ranking', authorities: [Authority.ADMIN, Authority.ZSAV] },
        loadChildren: () => import('./ranking/ranking.module').then(m => m.RankingModule),
      },
      {
        path: 'live',
        data: { pageTitle: 'LiveView', authorities: [Authority.ADMIN, Authority.ZSAV, Authority.LIVE] },
        loadChildren: () => import('./live/live.module').then(m => m.LiveModule),
      },
      {
        path: 'import',
        data: { pageTitle: 'Import', authorities: [Authority.ADMIN] },
        loadChildren: () => import('./import/import.module').then(m => m.ImportModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
