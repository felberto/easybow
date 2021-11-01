import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { Authority } from '../config/authority.constants';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'verband',
        data: { pageTitle: 'Verband', authorities: [Authority.ADMIN, Authority.ZSAV] },
        loadChildren: () => import('./verband/verband.module').then(m => m.VerbandModule),
      },
      {
        path: 'verein',
        data: { pageTitle: 'Verein', authorities: [Authority.ADMIN, Authority.ZSAV] },
        loadChildren: () => import('./verein/verein.module').then(m => m.VereinModule),
      },
      {
        path: 'schuetze',
        data: { pageTitle: 'Schuetze', authorities: [Authority.ADMIN, Authority.ZSAV] },
        loadChildren: () => import('./schuetze/schuetze.module').then(m => m.SchuetzeModule),
      },
      {
        path: 'wettkampf',
        data: { pageTitle: 'Wettkampf', authorities: [Authority.ADMIN, Authority.ZSAV, Authority.VEREIN] },
        loadChildren: () => import('./wettkampf/wettkampf.module').then(m => m.WettkampfModule),
      },
      {
        path: 'passen',
        data: { pageTitle: 'Passen', authorities: [Authority.ADMIN, Authority.ZSAV] },
        loadChildren: () => import('./passen/passen.module').then(m => m.PassenModule),
      },
      {
        path: 'runde',
        data: { pageTitle: 'jhipsterSampleApplicationApp.runde.home.title' },
        loadChildren: () => import('./runde/runde.module').then(m => m.RundeModule),
      },
      {
        path: 'gruppen',
        data: { pageTitle: 'Gruppen', authorities: [Authority.ADMIN, Authority.ZSAV] },
        loadChildren: () => import('./gruppen/gruppen.module').then(m => m.GruppenModule),
      },
      {
        path: 'resultate',
        data: { pageTitle: 'Resultate', authorities: [Authority.ADMIN, Authority.ZSAV] },
        loadChildren: () => import('./resultate/resultate.module').then(m => m.ResultateModule),
      },
      {
        path: 'rangierung',
        data: { pageTitle: 'Rangierung', authorities: [Authority.ADMIN, Authority.ZSAV] },
        loadChildren: () => import('./rangierung/rangierung.module').then(m => m.RangierungModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
