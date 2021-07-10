import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IVerband, Verband } from '../verband.model';
import { VerbandService } from '../service/verband.service';

@Component({
  selector: 'jhi-verband-update',
  templateUrl: './verband-update.component.html',
})
export class VerbandUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
  });

  constructor(protected verbandService: VerbandService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ verband }) => {
      this.updateForm(verband);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const verband = this.createFromForm();
    if (verband.id !== undefined) {
      this.subscribeToSaveResponse(this.verbandService.update(verband));
    } else {
      this.subscribeToSaveResponse(this.verbandService.create(verband));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVerband>>): void {
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

  protected updateForm(verband: IVerband): void {
    this.editForm.patchValue({
      id: verband.id,
      name: verband.name,
    });
  }

  protected createFromForm(): IVerband {
    return {
      ...new Verband(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
    };
  }
}
