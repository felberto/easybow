import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ImportComponent } from './import.component';

const importRoute: Routes = [
  {
    path: '',
    component: ImportComponent,
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(importRoute)],
  exports: [RouterModule],
})
export class ImportRoutingModule {}
