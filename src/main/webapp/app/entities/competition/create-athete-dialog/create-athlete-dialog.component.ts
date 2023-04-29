import { Component } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IClub } from 'app/entities/club/club.model';
import { ICompetition } from '../competition.model';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { AthleteService } from '../../athlete/service/athlete.service';
import { Athlete, IAthlete } from '../../athlete/athlete.model';
import { Nationality } from '../../enumerations/nationality.model';

@Component({
  selector: 'jhi-create-athlete-dialog',
  templateUrl: './create-athlete-dialog.component.html',
})
export class CreateAthleteDialogComponent {
  competition?: ICompetition;
  accountClub?: IClub;
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    firstName: [null, [Validators.required]],
    yearOfBirth: [null, [Validators.required]],
    nationality: [],
    gender: [null, [Validators.required]],
    position: [null, [Validators.required]],
    role: [],
    club: [],
  });

  constructor(
    protected activeModal: NgbActiveModal,
    protected athleteService: AthleteService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  cancel(): void {
    this.activeModal.close('');
  }

  save(): void {
    this.isSaving = true;
    const athlete = this.createFromForm();
    console.log(athlete);
    this.subscribeToSaveResponse(this.athleteService.create(athlete));
  }

  trackClubById(index: number, item: IClub): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAthlete>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.cancel();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected createFromForm(): IAthlete {
    return {
      ...new Athlete(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      firstName: this.editForm.get(['firstName'])!.value,
      yearOfBirth: this.editForm.get(['yearOfBirth'])!.value,
      nationality: Nationality.SUI,
      gender: this.editForm.get(['gender'])!.value,
      position: this.editForm.get(['position'])!.value,
      role: 'ROLE_VEREIN',
      club: this.accountClub,
    };
  }
}
