import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'verband',
        data: { pageTitle: 'Verband' },
        loadChildren: () => import('./verband/verband.module').then(m => m.VerbandModule),
      },
      {
        path: 'verein',
        data: { pageTitle: 'Verein' },
        loadChildren: () => import('./verein/verein.module').then(m => m.VereinModule),
      },
      {
        path: 'schuetze',
        data: { pageTitle: 'Schuetze' },
        loadChildren: () => import('./schuetze/schuetze.module').then(m => m.SchuetzeModule),
      },
      {
        path: 'wettkampf',
        data: { pageTitle: 'Wettkampf' },
        loadChildren: () => import('./wettkampf/wettkampf.module').then(m => m.WettkampfModule),
      },
      {
        path: 'passen',
        data: { pageTitle: 'Passen' },
        loadChildren: () => import('./passen/passen.module').then(m => m.PassenModule),
      },
      {
        path: 'gruppen',
        data: { pageTitle: 'Gruppen' },
        loadChildren: () => import('./gruppen/gruppen.module').then(m => m.GruppenModule),
      },
      {
        path: 'resultate',
        data: { pageTitle: 'Resultate' },
        loadChildren: () => import('./resultate/resultate.module').then(m => m.ResultateModule),
      },
      {
        path: 'rangierung',
        data: { pageTitle: 'Rangierung' },
        loadChildren: () => import('./rangierung/rangierung.module').then(m => m.RangierungModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
