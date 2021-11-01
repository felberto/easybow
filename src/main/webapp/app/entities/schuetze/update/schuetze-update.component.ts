import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ISchuetze, Schuetze } from '../schuetze.model';
import { SchuetzeService } from '../service/schuetze.service';
import { IVerein } from 'app/entities/verein/verein.model';
import { VereinService } from 'app/entities/verein/service/verein.service';

@Component({
  selector: 'jhi-schuetze-update',
  templateUrl: './schuetze-update.component.html',
})
export class SchuetzeUpdateComponent implements OnInit {
  isSaving = false;

  vereinsSharedCollection: IVerein[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    jahrgang: [null, [Validators.required]],
    stellung: [null, [Validators.required]],
    rolle: [null, [Validators.required]],
    verein: [],
  });

  constructor(
    protected schuetzeService: SchuetzeService,
    protected vereinService: VereinService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ schuetze }) => {
      this.updateForm(schuetze);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const schuetze = this.createFromForm();
    if (schuetze.id !== undefined) {
      this.subscribeToSaveResponse(this.schuetzeService.update(schuetze));
    } else {
      this.subscribeToSaveResponse(this.schuetzeService.create(schuetze));
    }
  }

  trackVereinById(index: number, item: IVerein): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISchuetze>>): void {
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

  protected updateForm(schuetze: ISchuetze): void {
    this.editForm.patchValue({
      id: schuetze.id,
      name: schuetze.name,
      jahrgang: schuetze.jahrgang,
      stellung: schuetze.stellung,
      rolle: schuetze.rolle,
      verein: schuetze.verein,
    });

    this.vereinsSharedCollection = this.vereinService.addVereinToCollectionIfMissing(this.vereinsSharedCollection, schuetze.verein);
  }

  protected loadRelationshipsOptions(): void {
    this.vereinService
      .query()
      .pipe(map((res: HttpResponse<IVerein[]>) => res.body ?? []))
      .pipe(map((vereins: IVerein[]) => this.vereinService.addVereinToCollectionIfMissing(vereins, this.editForm.get('verein')!.value)))
      .subscribe((vereins: IVerein[]) => (this.vereinsSharedCollection = vereins));
  }

  protected createFromForm(): ISchuetze {
    return {
      ...new Schuetze(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      jahrgang: this.editForm.get(['jahrgang'])!.value,
      stellung: this.editForm.get(['stellung'])!.value,
      rolle: this.editForm.get(['rolle'])!.value,
      verein: this.editForm.get(['verein'])!.value,
    };
  }
}
