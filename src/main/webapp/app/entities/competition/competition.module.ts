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
import { CompetitionOverviewEasvWorldcupComponent } from './overview-easv-worldcup/competition-overview-easv-worldcup.component';
import { SeriesDialog4Component } from './series-dialog-4/series-dialog-4.component';
import { RankingListEasvWorldcupComponent } from './ranking-list-easv-worldcup/ranking-list-easv-worldcup.component';
import { AthleteNumberDialogComponent } from './athlete-number-dialog/athlete-number-dialog.component';
import { GroupDialogComponent } from './group-dialog/group-dialog.component';
import { CompetitionOverviewZsavNawuGmComponent } from './overview-zsav-nawu-gm/competition-overview-zsav-nawu-gm.component';
import { RankingListZsavNawuGmComponent } from './ranking-list-zsav-nawu-gm/ranking-list-zsav-nawu-gm.component';
import { CompetitionOverviewEasvStaendematchComponent } from './overview-easv-staendematch/competition-overview-easv-staendematch.component';
import { AthleteNumberGroupDialogComponent } from './athlete-number-group-dialog/athlete-number-group-dialog.component';
import { SeriesDialog6Component } from './series-dialog-6/series-dialog-6.component';
import { RankingListEasvStaendematchComponent } from './ranking-list-easv-staendematch/ranking-list-easv-staendematch.component';
import { RankingListEasvNawuGmComponent } from './ranking-list-easv-nawu-gm/ranking-list-easv-nawu-gm.component';
import { CompetitionOverviewEasvNawuGmComponent } from './overview-easv-nawu-gm/competition-overview-easv-nawu-gm.component';
import { CompetitionOverviewEasvSm10mComponent } from './overview-easv-sm-10m/competition-overview-easv-sm-10m.component';
import { RankingListEasvSm10mComponent } from './ranking-list-easv-sm-10m/ranking-list-easv-sm-10m.component';
import { SeriesDialog2GroupComponent } from './series-dialog-2-group/series-dialog-2.component';
import { CreateAthleteDialogComponent } from './create-athete-dialog/create-athlete-dialog.component';
import { NewSeriesDialog1Component } from './new-series-dialog-1/new-series-dialog-1.component';
import { NewSeriesDialog2Component } from './new-series-dialog-2/new-series-dialog-2.component';
import { TuiTableModule } from '@taiga-ui/addon-table';
import { NewGroupSeriesDialog1Component } from './new-group-series-dialog-1/new-group-series-dialog-1.component';
import { CompetitionOverviewEasvVerbaendefinalComponent } from './overview-easv-verbaendefinal/competition-overview-easv-verbaendefinal.component';
import { RankingListEasvVerbaendefinalComponent } from './ranking-list-easv-verbaendefinal/ranking-list-easv-verbaendefinal.component';

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
    TuiTableModule,
  ],
  declarations: [
    CompetitionComponent,
    CompetitionDetailComponent,
    CompetitionUpdateComponent,
    CompetitionDeleteDialogComponent,
    CompetitionRankingComponent,
    CompetitionOverviewComponent,
    CompetitionOverviewEasvWorldcupComponent,
    CompetitionOverviewEasvSm10mComponent,
    CompetitionOverviewEasvNawuGmComponent,
    CompetitionOverviewZsavNawuGmComponent,
    CompetitionOverviewEasvStaendematchComponent,
    CompetitionOverviewEasvVerbaendefinalComponent,
    ResultsDialogComponent,
    NewSeriesDialog1Component,
    NewSeriesDialog2Component,
    NewGroupSeriesDialog1Component,
    SeriesDialog1Component,
    SeriesDialog2Component,
    SeriesDialog2GroupComponent,
    SeriesDialog4Component,
    SeriesDialog6Component,
    RankingListComponent,
    RankingListEasvWorldcupComponent,
    RankingListEasvSm10mComponent,
    RankingListEasvNawuGmComponent,
    RankingListZsavNawuGmComponent,
    RankingListEasvStaendematchComponent,
    RankingListEasvVerbaendefinalComponent,
    RankingListDialogComponent,
    ConfirmDialogComponent,
    CreateAthleteDialogComponent,
    AthleteNumberDialogComponent,
    AthleteNumberGroupDialogComponent,
    GroupDialogComponent,
  ],
  entryComponents: [
    CompetitionDeleteDialogComponent,
    ResultsDialogComponent,
    RankingListComponent,
    RankingListEasvWorldcupComponent,
    RankingListEasvSm10mComponent,
    RankingListEasvStaendematchComponent,
    RankingListEasvNawuGmComponent,
    RankingListEasvVerbaendefinalComponent,
    NewSeriesDialog1Component,
    NewSeriesDialog2Component,
    NewGroupSeriesDialog1Component,
    SeriesDialog1Component,
    SeriesDialog2Component,
    SeriesDialog2GroupComponent,
    SeriesDialog4Component,
    SeriesDialog6Component,
    ConfirmDialogComponent,
    CreateAthleteDialogComponent,
    AthleteNumberDialogComponent,
    GroupDialogComponent,
  ],
  providers: [NgbActiveModal],
  exports: [
    NewSeriesDialog1Component,
    NewSeriesDialog2Component,
    NewGroupSeriesDialog1Component,
    SeriesDialog1Component,
    SeriesDialog2Component,
    SeriesDialog2GroupComponent,
    SeriesDialog4Component,
    SeriesDialog6Component,
    CreateAthleteDialogComponent,
    AthleteNumberDialogComponent,
    GroupDialogComponent,
    AthleteNumberGroupDialogComponent,
  ],
})
export class CompetitionModule {}
