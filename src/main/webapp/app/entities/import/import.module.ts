import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ImportComponent } from './import.component';
import { ImportRoutingModule } from './import-routing.module';
import { TuiInputFileModule, TuiTextAreaModule } from '@taiga-ui/kit';
import { TuiDestroyService } from '@taiga-ui/cdk';

@NgModule({
  imports: [CommonModule, SharedModule, ImportRoutingModule, TuiInputFileModule, TuiTextAreaModule],
  declarations: [ImportComponent],
  providers: [TuiDestroyService],
})
export class ImportModule {}
