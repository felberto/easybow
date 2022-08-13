import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ISeries, Series } from '../series.model';
import { SeriesService } from '../service/series.service';

@Component({
  selector: 'jhi-series-update',
  templateUrl: './series-update.component.html',
})
export class SeriesUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    p1: [null, [Validators.required, Validators.min(0), Validators.max(11)]],
    p2: [null, [Validators.required, Validators.min(0), Validators.max(11)]],
    p3: [null, [Validators.required, Validators.min(0), Validators.max(11)]],
    p4: [null, [Validators.required, Validators.min(0), Validators.max(11)]],
    p5: [null, [Validators.required, Validators.min(0), Validators.max(11)]],
    p6: [null, [Validators.required, Validators.min(0), Validators.max(11)]],
    p7: [null, [Validators.required, Validators.min(0), Validators.max(11)]],
    p8: [null, [Validators.required, Validators.min(0), Validators.max(11)]],
    p9: [null, [Validators.required, Validators.min(0), Validators.max(11)]],
    p10: [null, [Validators.required, Validators.min(0), Validators.max(11)]],
    result: [],
  });

  constructor(protected seriesService: SeriesService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ series }) => {
      this.updateForm(series);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const series = this.createFromForm();
    if (series.id !== undefined) {
      this.subscribeToSaveResponse(this.seriesService.update(series));
    } else {
      this.subscribeToSaveResponse(this.seriesService.create(series));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISeries>>): void {
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

  protected updateForm(series: ISeries): void {
    this.editForm.patchValue({
      id: series.id,
      p1: series.p1,
      p2: series.p2,
      p3: series.p3,
      p4: series.p4,
      p5: series.p5,
      p6: series.p6,
      p7: series.p7,
      p8: series.p8,
      p9: series.p9,
      p10: series.p10,
      result: series.result,
    });
  }

  protected createFromForm(): ISeries {
    return {
      ...new Series(),
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
      result: this.editForm.get(['result'])!.value,
    };
  }
}
