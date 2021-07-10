import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IPassen, Passen } from '../passen.model';
import { PassenService } from '../service/passen.service';

@Component({
  selector: 'jhi-passen-update',
  templateUrl: './passen-update.component.html',
})
export class PassenUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    p1: [],
    p2: [],
    p3: [],
    p4: [],
    p5: [],
    p6: [],
    p7: [],
    p8: [],
    p9: [],
    p10: [],
    resultat: [],
  });

  constructor(protected passenService: PassenService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ passen }) => {
      this.updateForm(passen);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const passen = this.createFromForm();
    if (passen.id !== undefined) {
      this.subscribeToSaveResponse(this.passenService.update(passen));
    } else {
      this.subscribeToSaveResponse(this.passenService.create(passen));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPassen>>): void {
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

  protected updateForm(passen: IPassen): void {
    this.editForm.patchValue({
      id: passen.id,
      p1: passen.p1,
      p2: passen.p2,
      p3: passen.p3,
      p4: passen.p4,
      p5: passen.p5,
      p6: passen.p6,
      p7: passen.p7,
      p8: passen.p8,
      p9: passen.p9,
      p10: passen.p10,
      resultat: passen.resultat,
    });
  }

  protected createFromForm(): IPassen {
    return {
      ...new Passen(),
      id: this.editForm.get(['id'])!.value,
      p1: this.editForm.get(['p1'])!.value,
      p2: this.editForm.get(['p2'])!.value,
      p3: this.editForm.get(['p3'])!.value,
      p4: this.editForm.get(['p4'])!.value,
      p5: this.editForm.get(['p5'])!.value,
      p6: this.editForm.get(['p6'])!.value,
      p7: this.editForm.get(['p7'])!.value,
      p8: this.editForm.get(['p8'])!.value,
      p9: this.editForm.get(['p9'])!.value,
      p10: this.editForm.get(['p10'])!.value,
      resultat: this.editForm.get(['resultat'])!.value,
    };
  }
}
