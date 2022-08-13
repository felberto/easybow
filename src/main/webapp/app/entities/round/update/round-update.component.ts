import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IRound, Round } from '../round.model';
import { RoundService } from '../service/round.service';
import { ICompetition } from 'app/entities/competition/competition.model';
import { CompetitionService } from '../../competition/service/competition.service';

@Component({
  selector: 'jhi-round-update',
  templateUrl: './round-update.component.html',
})
export class RoundUpdateComponent implements OnInit {
  isSaving = false;

  competitionsSharedCollection: ICompetition[] = [];

  editForm = this.fb.group({
    id: [],
    round: [null, [Validators.required]],
    date: [],
    competition: [],
  });

  constructor(
    protected roundService: RoundService,
    protected competitionService: CompetitionService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ round }) => {
      this.updateForm(round);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const round = this.createFromForm();
    if (round.id !== undefined) {
      this.subscribeToSaveResponse(this.roundService.update(round));
    } else {
      this.subscribeToSaveResponse(this.roundService.create(round));
    }
  }

  trackCompetitionById(index: number, item: ICompetition): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRound>>): void {
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

  protected updateForm(round: IRound): void {
    this.editForm.patchValue({
      id: round.id,
      round: round.round,
      date: round.date,
      competition: round.competition,
    });

    this.competitionsSharedCollection = this.competitionService.addCompetitionToCollectionIfMissing(
      this.competitionsSharedCollection,
      round.competition
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

  protected createFromForm(): IRound {
    return {
      ...new Round(),
      id: this.editForm.get(['id'])!.value,
      round: this.editForm.get(['round'])!.value,
      date: this.editForm.get(['date'])!.value,
      competition: this.editForm.get(['competition'])!.value,
    };
  }
}
