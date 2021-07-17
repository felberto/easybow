import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IRangierung, Rangierung } from '../rangierung.model';
import { RangierungService } from '../service/rangierung.service';
import { IWettkampf } from 'app/entities/wettkampf/wettkampf.model';
import { WettkampfService } from 'app/entities/wettkampf/service/wettkampf.service';

@Component({
  selector: 'jhi-rangierung-update',
  templateUrl: './rangierung-update.component.html',
})
export class RangierungUpdateComponent implements OnInit {
  isSaving = false;

  wettkampfsSharedCollection: IWettkampf[] = [];

  editForm = this.fb.group({
    id: [],
    position: [null, [Validators.required]],
    rangierungskriterien: [null, [Validators.required]],
    wettkampf: [],
  });

  constructor(
    protected rangierungService: RangierungService,
    protected wettkampfService: WettkampfService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ rangierung }) => {
      this.updateForm(rangierung);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const rangierung = this.createFromForm();
    if (rangierung.id !== undefined) {
      this.subscribeToSaveResponse(this.rangierungService.update(rangierung));
    } else {
      this.subscribeToSaveResponse(this.rangierungService.create(rangierung));
    }
  }

  trackWettkampfById(index: number, item: IWettkampf): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRangierung>>): void {
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

  protected updateForm(rangierung: IRangierung): void {
    this.editForm.patchValue({
      id: rangierung.id,
      position: rangierung.position,
      rangierungskriterien: rangierung.rangierungskriterien,
      wettkampf: rangierung.wettkampf,
    });

    this.wettkampfsSharedCollection = this.wettkampfService.addWettkampfToCollectionIfMissing(
      this.wettkampfsSharedCollection,
      rangierung.wettkampf
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

  protected createFromForm(): IRangierung {
    return {
      ...new Rangierung(),
      id: this.editForm.get(['id'])!.value,
      position: this.editForm.get(['position'])!.value,
      rangierungskriterien: this.editForm.get(['rangierungskriterien'])!.value,
      wettkampf: this.editForm.get(['wettkampf'])!.value,
    };
  }
}
