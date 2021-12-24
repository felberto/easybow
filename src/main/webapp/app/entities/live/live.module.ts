import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ViewComponent } from './view/view.component';
import { SharedModule } from '../../shared/shared.module';
import { LiveRoutingModule } from './route/live-routing.module';

@NgModule({
  declarations: [ViewComponent],
  imports: [CommonModule, SharedModule, LiveRoutingModule],
})
export class LiveModule {}
