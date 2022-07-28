import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { CompetitionComponent } from './list/competition.component';
import { CompetitionDetailComponent } from './detail/competition-detail.component';
import { CompetitionUpdateComponent } from './update/competition-update.component';
import { CompetitionDeleteDialogComponent } from './delete/competition-delete-dialog.component';
import { CompetitionRoutingModule } from './route/competition-routing.module';
import { CompetitionRankingComponent } from './ranking/competition-ranking.component';
import { DragDropModule } from '@angular/cdk/drag-drop';
import { CompetitionOverviewComponent } from './overview/competition-overview.component';
import { CdkAccordionModule } from '@angular/cdk/accordion';
import { ResultsDialogComponent } from './results-dialog/results-dialog.component';
import { SeriesDialog2Component } from './series-dialog-2/series-dialog-2.component';
import { FormsModule } from '@angular/forms';
import { RankingListComponent } from './rankingList/rankingList.component';
import { MatTableModule } from '@angular/material/table';
import { MatRadioModule } from '@angular/material/radio';
import { RankingListDialogComponent } from './rankingList-dialog/rankingList-dialog.component';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ConfirmDialogComponent } from './confirm-dialog/confirm-dialog.component';
import { TuiSvgModule } from '@taiga-ui/core';
import { TuiBadgeModule } from '@taiga-ui/kit';
import { SeriesDialog1Component } from './series-dialog-1/series-dialog-1.component';

@NgModule({
  imports: [
    SharedModule,
    CompetitionRoutingModule,
    DragDropModule,
    CdkAccordionModule,
    FormsModule,
    MatTableModule,
    MatRadioModule,
    TuiSvgModule,
    TuiBadgeModule,
  ],
  declarations: [
    CompetitionComponent,
    CompetitionDetailComponent,
    CompetitionUpdateComponent,
    CompetitionDeleteDialogComponent,
    CompetitionRankingComponent,
    CompetitionOverviewComponent,
    ResultsDialogComponent,
    SeriesDialog1Component,
    SeriesDialog2Component,
    RankingListComponent,
    RankingListDialogComponent,
    ConfirmDialogComponent,
  ],
  entryComponents: [
    CompetitionDeleteDialogComponent,
    ResultsDialogComponent,
    RankingListComponent,
    SeriesDialog1Component,
    SeriesDialog2Component,
    ConfirmDialogComponent,
  ],
  providers: [NgbActiveModal],
  exports: [SeriesDialog1Component, SeriesDialog2Component],
})
export class CompetitionModule {}
