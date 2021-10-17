import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from 'app/shared/shared.module';
import { HOME_ROUTE } from './home.route';
import { HomeComponent } from './home.component';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatCardModule } from '@angular/material/card';

@NgModule({
  imports: [SharedModule, RouterModule.forChild([HOME_ROUTE]), MatGridListModule, MatCardModule],
  declarations: [HomeComponent],
})
export class HomeModule {}
