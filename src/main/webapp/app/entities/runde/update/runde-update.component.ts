import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IRunde, Runde } from '../runde.model';
import { RundeService } from '../service/runde.service';
import { IWettkampf } from 'app/entities/wettkampf/wettkampf.model';
import { WettkampfService } from 'app/entities/wettkampf/service/wettkampf.service';

@Component({
  selector: 'jhi-runde-update',
  templateUrl: './runde-update.component.html',
})
export class RundeUpdateComponent implements OnInit {
  isSaving = false;

  wettkampfsSharedCollection: IWettkampf[] = [];

  editForm = this.fb.group({
    id: [],
    runde: [null, [Validators.required]],
    datum: [],
    wettkampf: [],
  });

  constructor(
    protected rundeService: RundeService,
    protected wettkampfService: WettkampfService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ runde }) => {
      this.updateForm(runde);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const runde = this.createFromForm();
    if (runde.id !== undefined) {
      this.subscribeToSaveResponse(this.rundeService.update(runde));
    } else {
      this.subscribeToSaveResponse(this.rundeService.create(runde));
    }
  }

  trackWettkampfById(index: number, item: IWettkampf): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRunde>>): void {
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

  protected updateForm(runde: IRunde): void {
    this.editForm.patchValue({
      id: runde.id,
      runde: runde.runde,
      datum: runde.datum,
      wettkampf: runde.wettkampf,
    });

    this.wettkampfsSharedCollection = this.wettkampfService.addWettkampfToCollectionIfMissing(
      this.wettkampfsSharedCollection,
      runde.wettkampf
    );
  }

  protected loadRelationshipsOptions(): void {
    this.wettkampfService
      .query()
      .pipe(map((res: HttpResponse<IWettkampf[]>) => res.body ?? []))
      .pipe(
        map((wettkampfs: IWettkampf[]) =>
          this.wettkampfService.addWettkampfToCollectionIfMissing(wettkampfs, this.editForm.get('wettkampf')!.value)
        )
      )
      .subscribe((wettkampfs: IWettkampf[]) => (this.wettkampfsSharedCollection = wettkampfs));
  }

  protected createFromForm(): IRunde {
    return {
      ...new Runde(),
      id: this.editForm.get(['id'])!.value,
      runde: this.editForm.get(['runde'])!.value,
      datum: this.editForm.get(['datum'])!.value,
      wettkampf: this.editForm.get(['wettkampf'])!.value,
    };
  }
}
