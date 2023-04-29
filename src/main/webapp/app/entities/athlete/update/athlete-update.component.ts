import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { Athlete, IAthlete } from '../athlete.model';
import { AthleteService } from '../service/athlete.service';
import { IClub } from 'app/entities/club/club.model';
import { ClubService } from 'app/entities/club/service/club.service';

@Component({
  selector: 'jhi-athlete-update',
  templateUrl: './athlete-update.component.html',
})
export class AthleteUpdateComponent implements OnInit {
  isSaving = false;

  clubsSharedCollection: IClub[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    firstName: [null, [Validators.required]],
    yearOfBirth: [null, [Validators.required]],
    nationality: [null, [Validators.required]],
    gender: [null, [Validators.required]],
    position: [null, [Validators.required]],
    role: [],
    club: [],
  });

  constructor(
    protected athleteService: AthleteService,
    protected clubService: ClubService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ athlete }) => {
      this.updateForm(athlete);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const athlete = this.createFromForm();
    console.log(athlete);
    if (athlete.id !== undefined) {
      this.subscribeToSaveResponse(this.athleteService.update(athlete));
    } else {
      this.subscribeToSaveResponse(this.athleteService.create(athlete));
    }
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
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(athlete: IAthlete): void {
    this.editForm.patchValue({
      id: athlete.id,
      name: athlete.name,
      firstName: athlete.firstName,
      yearOfBirth: athlete.yearOfBirth,
      nationality: athlete.nationality,
      gender: athlete.gender,
      position: athlete.position,
      role: athlete.role,
      club: athlete.club,
    });

    this.clubsSharedCollection = this.clubService.addClubToCollectionIfMissing(this.clubsSharedCollection, athlete.club);
  }

  protected loadRelationshipsOptions(): void {
    this.clubService
      .query({
        page: 0,
        size: 100,
      })
      .pipe(map((res: HttpResponse<IClub[]>) => res.body ?? []))
      .pipe(map((clubs: IClub[]) => this.clubService.addClubToCollectionIfMissing(clubs, this.editForm.get('club')!.value)))
      .subscribe((clubs: IClub[]) => (this.clubsSharedCollection = clubs));
  }

  protected createFromForm(): IAthlete {
    return {
      ...new Athlete(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      firstName: this.editForm.get(['firstName'])!.value,
      yearOfBirth: this.editForm.get(['yearOfBirth'])!.value,
      nationality: this.editForm.get(['nationality'])!.value,
      gender: this.editForm.get(['gender'])!.value,
      position: this.editForm.get(['position'])!.value,
      role: this.editForm.get(['role'])!.value,
      club: this.editForm.get(['club'])!.value,
    };
  }
}
