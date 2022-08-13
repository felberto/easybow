import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { Competition, ICompetition } from '../competition.model';
import { CompetitionService } from '../service/competition.service';

@Component({
  selector: 'jhi-competition-update',
  templateUrl: './competition-update.component.html',
})
export class CompetitionUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    year: [],
    competitionType: [],
    numberOfRounds: [null, [Validators.required]],
    numberOfSeries: [null, [Validators.required, Validators.min(1), Validators.max(4)]],
    finalRound: [],
    finalPreparation: [],
    numberOfFinalAthletes: [],
    numberOfFinalSeries: [null, [Validators.min(1), Validators.max(4)]],
    teamSize: [],
    template: [],
  });

  constructor(protected competitionService: CompetitionService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ competition }) => {
      this.updateForm(competition);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const competition = this.createFromForm();
    if (competition.id !== undefined) {
      this.subscribeToSaveResponse(this.competitionService.update(competition));
    } else {
      this.subscribeToSaveResponse(this.competitionService.create(competition));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICompetition>>): void {
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

  protected updateForm(competition: ICompetition): void {
    this.editForm.patchValue({
      id: competition.id,
      name: competition.name,
      year: competition.year,
      competitionType: competition.competitionType,
      numberOfRounds: competition.numberOfRounds,
      numberOfSeries: competition.numberOfSeries,
      finalRound: competition.finalRound,
      finalPreparation: competition.finalPreparation,
      numberOfFinalAthletes: competition.numberOfFinalAthletes,
      numberOfFinalSeries: competition.numberOfFinalSeries,
      teamSize: competition.teamSize,
      template: competition.template,
    });
  }

  protected createFromForm(): ICompetition {
    return {
      ...new Competition(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      year: this.editForm.get(['year'])!.value,
      competitionType: this.editForm.get(['competitionType'])!.value,
      numberOfRounds: this.editForm.get(['numberOfRounds'])!.value,
      numberOfSeries: this.editForm.get(['numberOfSeries'])!.value,
      finalRound: this.editForm.get(['finalRound'])!.value,
      finalPreparation: this.editForm.get(['finalPreparation'])!.value,
      numberOfFinalAthletes: this.editForm.get(['numberOfFinalAthletes'])!.value,
      numberOfFinalSeries: this.editForm.get(['numberOfFinalSeries'])!.value,
      teamSize: this.editForm.get(['teamSize'])!.value,
      template: this.editForm.get(['template'])!.value,
    };
  }
}
