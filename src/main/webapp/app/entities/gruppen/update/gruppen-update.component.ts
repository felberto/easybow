import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IGruppen, Gruppen } from '../gruppen.model';
import { GruppenService } from '../service/gruppen.service';

@Component({
  selector: 'jhi-gruppen-update',
  templateUrl: './gruppen-update.component.html',
})
export class GruppenUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [],
  });

  constructor(protected gruppenService: GruppenService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ gruppen }) => {
      this.updateForm(gruppen);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const gruppen = this.createFromForm();
    if (gruppen.id !== undefined) {
      this.subscribeToSaveResponse(this.gruppenService.update(gruppen));
    } else {
      this.subscribeToSaveResponse(this.gruppenService.create(gruppen));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGruppen>>): void {
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

  protected updateForm(gruppen: IGruppen): void {
    this.editForm.patchValue({
      id: gruppen.id,
      name: gruppen.name,
    });
  }

  protected createFromForm(): IGruppen {
    return {
      ...new Gruppen(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
    };
  }
}
