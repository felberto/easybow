import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { WettkampfComponent } from './list/wettkampf.component';
import { WettkampfDetailComponent } from './detail/wettkampf-detail.component';
import { WettkampfUpdateComponent } from './update/wettkampf-update.component';
import { WettkampfDeleteDialogComponent } from './delete/wettkampf-delete-dialog.component';
import { WettkampfRoutingModule } from './route/wettkampf-routing.module';
import { WettkampfRangierungComponent } from './rangierung/wettkampf-rangierung.component';
import { DragDropModule } from '@angular/cdk/drag-drop';
import { WettkampfOverviewComponent } from './overview/wettkampf-overview.component';
import { CdkAccordionModule } from '@angular/cdk/accordion';
import { ResultateDialogComponent } from './resultate-dialog/resultate-dialog.component';
import { PassenDialog2Component } from './passen-dialog-2/passen-dialog-2.component';
import { FormsModule } from '@angular/forms';
import { RanglisteComponent } from './rangliste/rangliste.component';
import { MatTableModule } from '@angular/material/table';
import { MatRadioModule } from '@angular/material/radio';
import { RanglisteDialogComponent } from './rangliste-dialog/rangliste-dialog.component';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ConfirmDialogComponent } from './confirm-dialog/confirm-dialog.component';
import { TuiSvgModule } from '@taiga-ui/core';
import { TuiBadgeModule } from '@taiga-ui/kit';
import { PassenDialog1Component } from './passen-dialog-1/passen-dialog-1.component';

@NgModule({
  imports: [
    SharedModule,
    WettkampfRoutingModule,
    DragDropModule,
    CdkAccordionModule,
    FormsModule,
    MatTableModule,
    MatRadioModule,
    TuiSvgModule,
    TuiBadgeModule,
  ],
  declarations: [
    WettkampfComponent,
    WettkampfDetailComponent,
    WettkampfUpdateComponent,
    WettkampfDeleteDialogComponent,
    WettkampfRangierungComponent,
    WettkampfOverviewComponent,
    ResultateDialogComponent,
    PassenDialog1Component,
    PassenDialog2Component,
    RanglisteComponent,
    RanglisteDialogComponent,
    ConfirmDialogComponent,
  ],
  entryComponents: [
    WettkampfDeleteDialogComponent,
    ResultateDialogComponent,
    RanglisteComponent,
    PassenDialog1Component,
    PassenDialog2Component,
    ConfirmDialogComponent,
  ],
  providers: [NgbActiveModal],
  exports: [PassenDialog1Component, PassenDialog2Component],
})
export class WettkampfModule {}
