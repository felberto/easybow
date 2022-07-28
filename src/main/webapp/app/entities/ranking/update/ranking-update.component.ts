import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IRanking, Ranking } from '../ranking.model';
import { RankingService } from '../service/ranking.service';
import { ICompetition } from '../../competition/competition.model';
import { CompetitionService } from '../../competition/service/competition.service';

@Component({
  selector: 'jhi-ranking-update',
  templateUrl: './ranking-update.component.html',
})
export class RankingUpdateComponent implements OnInit {
  isSaving = false;

  competitionsSharedCollection: ICompetition[] = [];

  editForm = this.fb.group({
    id: [],
    position: [null, [Validators.required]],
    rankingCriteria: [null, [Validators.required]],
    competition: [],
  });

  constructor(
    protected rankingService: RankingService,
    protected competitionService: CompetitionService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ranking }) => {
      this.updateForm(ranking);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const ranking = this.createFromForm();
    if (ranking.id !== undefined) {
      this.subscribeToSaveResponse(this.rankingService.update(ranking));
    } else {
      this.subscribeToSaveResponse(this.rankingService.create(ranking));
    }
  }

  trackCompetitionById(index: number, item: ICompetition): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRanking>>): void {
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

  protected updateForm(ranking: IRanking): void {
    this.editForm.patchValue({
      id: ranking.id,
      position: ranking.position,
      rankingCriteria: ranking.rankingCriteria,
      competition: ranking.competition,
    });

    this.competitionsSharedCollection = this.competitionService.addCompetitionToCollectionIfMissing(
      this.competitionsSharedCollection,
      ranking.competition
    );
  }

  protected loadRelationshipsOptions(): void {
    this.competitionService
      .query()
      .pipe(map((res: HttpResponse<ICompetition[]>) => res.body ?? []))
      .pipe(
        map((competitions: ICompetition[]) =>
          this.competitionService.addCompetitionToCollectionIfMissing(competitions, this.editForm.get('competition')!.value)
        )
      )
      .subscribe((competitions: ICompetition[]) => (this.competitionsSharedCollection = competitions));
  }

  protected createFromForm(): IRanking {
    return {
      ...new Ranking(),
      id: this.editForm.get(['id'])!.value,
      position: this.editForm.get(['position'])!.value,
      rankingCriteria: this.editForm.get(['rankingCriteria'])!.value,
      competition: this.editForm.get(['competition'])!.value,
    };
  }
}
