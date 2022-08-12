import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IResults, Results } from '../results.model';
import { ResultsService } from '../service/results.service';
import { ISeries } from 'app/entities/series/series.model';
import { SeriesService } from 'app/entities/series/service/series.service';
import { IGroup } from 'app/entities/group/group.model';
import { GroupService } from 'app/entities/group/service/group.service';
import { IAthlete } from 'app/entities/athlete/athlete.model';
import { ICompetition } from 'app/entities/competition/competition.model';
import { AthleteService } from '../../athlete/service/athlete.service';
import { CompetitionService } from '../../competition/service/competition.service';

@Component({
  selector: 'jhi-results-update',
  templateUrl: './results-update.component.html',
})
export class ResultsUpdateComponent implements OnInit {
  isSaving = false;

  serie1sCollection: ISeries[] = [];
  serie2sCollection: ISeries[] = [];
  serie3sCollection: ISeries[] = [];
  serie4sCollection: ISeries[] = [];
  groupsSharedCollection: IGroup[] = [];
  athletesSharedCollection: IAthlete[] = [];
  competitionsSharedCollection: ICompetition[] = [];

  editForm = this.fb.group({
    id: [],
    round: [null, [Validators.required]],
    result: [],
    athleteNumber: [],
    serie1: [],
    serie2: [],
    serie3: [],
    serie4: [],
    group: [],
    athlete: [],
    competition: [],
  });

  constructor(
    protected resultsService: ResultsService,
    protected seriesService: SeriesService,
    protected groupService: GroupService,
    protected athleteService: AthleteService,
    protected competitionService: CompetitionService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ results }) => {
      this.updateForm(results);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const results = this.createFromForm();
    if (results.id !== undefined) {
      this.subscribeToSaveResponse(this.resultsService.update(results));
    } else {
      this.subscribeToSaveResponse(this.resultsService.create(results));
    }
  }

  trackSeriesById(index: number, item: ISeries): number {
    return item.id!;
  }

  trackGroupById(index: number, item: IGroup): number {
    return item.id!;
  }

  trackAthleteById(index: number, item: IAthlete): number {
    return item.id!;
  }

  trackCompetitionById(index: number, item: ICompetition): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IResults>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(results: IResults): void {
    this.editForm.patchValue({
      id: results.id,
      round: results.round,
      result: results.result,
      athleteNumber: results.athleteNumber,
      serie1: results.serie1,
      serie2: results.serie2,
      serie3: results.serie3,
      serie4: results.serie4,
      group: results.group,
      athlete: results.athlete,
      competition: results.competition,
    });

    this.serie1sCollection = this.seriesService.addSeriesToCollectionIfMissing(this.serie1sCollection, results.serie1);
    this.serie2sCollection = this.seriesService.addSeriesToCollectionIfMissing(this.serie2sCollection, results.serie2);
    this.serie3sCollection = this.seriesService.addSeriesToCollectionIfMissing(this.serie3sCollection, results.serie3);
    this.serie4sCollection = this.seriesService.addSeriesToCollectionIfMissing(this.serie4sCollection, results.serie4);
    this.groupsSharedCollection = this.groupService.addGroupToCollectionIfMissing(this.groupsSharedCollection, results.group);
    this.athletesSharedCollection = this.athleteService.addAthleteToCollectionIfMissing(this.athletesSharedCollection, results.athlete);
    this.competitionsSharedCollection = this.competitionService.addCompetitionToCollectionIfMissing(
      this.competitionsSharedCollection,
      results.competition
    );
  }

  protected loadRelationshipsOptions(): void {
    this.seriesService
      .query({ 'resultsId.specified': 'false' })
      .pipe(map((res: HttpResponse<ISeries[]>) => res.body ?? []))
      .pipe(map((series: ISeries[]) => this.seriesService.addSeriesToCollectionIfMissing(series, this.editForm.get('serie1')!.value)))
      .subscribe((series: ISeries[]) => (this.serie1sCollection = series));

    this.seriesService
      .query({ 'resultsId.specified': 'false' })
      .pipe(map((res: HttpResponse<ISeries[]>) => res.body ?? []))
      .pipe(map((series: ISeries[]) => this.seriesService.addSeriesToCollectionIfMissing(series, this.editForm.get('serie2')!.value)))
      .subscribe((series: ISeries[]) => (this.serie2sCollection = series));

    this.seriesService
      .query({ 'resultsId.specified': 'false' })
      .pipe(map((res: HttpResponse<ISeries[]>) => res.body ?? []))
      .pipe(map((series: ISeries[]) => this.seriesService.addSeriesToCollectionIfMissing(series, this.editForm.get('serie3')!.value)))
      .subscribe((series: ISeries[]) => (this.serie3sCollection = series));

    this.seriesService
      .query({ 'resultsId.specified': 'false' })
      .pipe(map((res: HttpResponse<ISeries[]>) => res.body ?? []))
      .pipe(map((series: ISeries[]) => this.seriesService.addSeriesToCollectionIfMissing(series, this.editForm.get('serie4')!.value)))
      .subscribe((series: ISeries[]) => (this.serie4sCollection = series));

    this.groupService
      .query()
      .pipe(map((res: HttpResponse<IGroup[]>) => res.body ?? []))
      .pipe(map((groups: IGroup[]) => this.groupService.addGroupToCollectionIfMissing(groups, this.editForm.get('group')!.value)))
      .subscribe((groups: IGroup[]) => (this.groupsSharedCollection = groups));

    this.athleteService
      .query({
        page: 0,
        size: 1000,
      })
      .pipe(map((res: HttpResponse<IAthlete[]>) => res.body ?? []))
      .pipe(map((athlete: IAthlete[]) => this.athleteService.addAthleteToCollectionIfMissing(athlete, this.editForm.get('athlete')!.value)))
      .subscribe((athlete: IAthlete[]) => (this.athletesSharedCollection = athlete));

    this.competitionService
      .query()
      .pipe(map((res: HttpResponse<ICompetition[]>) => res.body ?? []))
      .pipe(
        map((competition: ICompetition[]) =>
          this.competitionService.addCompetitionToCollectionIfMissing(competition, this.editForm.get('competition')!.value)
        )
      )
      .subscribe((competition: ICompetition[]) => (this.competitionsSharedCollection = competition));
  }

  protected createFromForm(): IResults {
    return {
      ...new Results(),
      id: this.editForm.get(['id'])!.value,
      round: this.editForm.get(['round'])!.value,
      result: this.editForm.get(['result'])!.value,
      athleteNumber: this.editForm.get(['athlete_number'])!.value,
      serie1: this.editForm.get(['serie1'])!.value,
      serie2: this.editForm.get(['serie2'])!.value,
      serie3: this.editForm.get(['serie3'])!.value,
      serie4: this.editForm.get(['serie4'])!.value,
      group: this.editForm.get(['group'])!.value,
      athlete: this.editForm.get(['athlete'])!.value,
      competition: this.editForm.get(['competition'])!.value,
    };
  }
}
