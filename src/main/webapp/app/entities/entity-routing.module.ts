import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'verband',
        data: { pageTitle: 'Verbands' },
        loadChildren: () => import('./verband/verband.module').then(m => m.VerbandModule),
      },
      {
        path: 'verein',
        data: { pageTitle: 'Vereins' },
        loadChildren: () => import('./verein/verein.module').then(m => m.VereinModule),
      },
      {
        path: 'schuetze',
        data: { pageTitle: 'Schuetzes' },
        loadChildren: () => import('./schuetze/schuetze.module').then(m => m.SchuetzeModule),
      },
      {
        path: 'wettkampf',
        data: { pageTitle: 'Wettkampfs' },
        loadChildren: () => import('./wettkampf/wettkampf.module').then(m => m.WettkampfModule),
      },
      {
        path: 'passen',
        data: { pageTitle: 'Passens' },
        loadChildren: () => import('./passen/passen.module').then(m => m.PassenModule),
      },
      {
        path: 'gruppen',
        data: { pageTitle: 'Gruppens' },
        loadChildren: () => import('./gruppen/gruppen.module').then(m => m.GruppenModule),
      },
      {
        path: 'resultate',
        data: { pageTitle: 'Resultates' },
        loadChildren: () => import('./resultate/resultate.module').then(m => m.ResultateModule),
      },
      {
        path: 'rangierung',
        data: { pageTitle: 'Rangierungs' },
        loadChildren: () => import('./rangierung/rangierung.module').then(m => m.RangierungModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
