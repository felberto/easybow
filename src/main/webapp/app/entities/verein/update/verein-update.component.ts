import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IVerein, Verein } from '../verein.model';
import { VereinService } from '../service/verein.service';
import { IVerband } from 'app/entities/verband/verband.model';
import { VerbandService } from 'app/entities/verband/service/verband.service';

@Component({
  selector: 'jhi-verein-update',
  templateUrl: './verein-update.component.html',
})
export class VereinUpdateComponent implements OnInit {
  isSaving = false;

  verbandsSharedCollection: IVerband[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    verband: [],
  });

  constructor(
    protected vereinService: VereinService,
    protected verbandService: VerbandService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ verein }) => {
      this.updateForm(verein);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const verein = this.createFromForm();
    if (verein.id !== undefined) {
      this.subscribeToSaveResponse(this.vereinService.update(verein));
    } else {
      this.subscribeToSaveResponse(this.vereinService.create(verein));
    }
  }

  trackVerbandById(index: number, item: IVerband): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVerein>>): void {
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

  protected updateForm(verein: IVerein): void {
    this.editForm.patchValue({
      id: verein.id,
      name: verein.name,
      verband: verein.verband,
    });

    this.verbandsSharedCollection = this.verbandService.addVerbandToCollectionIfMissing(this.verbandsSharedCollection, verein.verband);
  }

  protected loadRelationshipsOptions(): void {
    this.verbandService
      .query()
      .pipe(map((res: HttpResponse<IVerband[]>) => res.body ?? []))
      .pipe(
        map((verbands: IVerband[]) => this.verbandService.addVerbandToCollectionIfMissing(verbands, this.editForm.get('verband')!.value))
      )
      .subscribe((verbands: IVerband[]) => (this.verbandsSharedCollection = verbands));
  }

  protected createFromForm(): IVerein {
    return {
      ...new Verein(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      verband: this.editForm.get(['verband'])!.value,
    };
  }
}
