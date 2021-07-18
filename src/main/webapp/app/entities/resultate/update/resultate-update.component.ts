import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IResultate, Resultate } from '../resultate.model';
import { ResultateService } from '../service/resultate.service';
import { IPassen } from 'app/entities/passen/passen.model';
import { PassenService } from 'app/entities/passen/service/passen.service';
import { IGruppen } from 'app/entities/gruppen/gruppen.model';
import { GruppenService } from 'app/entities/gruppen/service/gruppen.service';
import { ISchuetze } from 'app/entities/schuetze/schuetze.model';
import { SchuetzeService } from 'app/entities/schuetze/service/schuetze.service';
import { IWettkampf } from 'app/entities/wettkampf/wettkampf.model';
import { WettkampfService } from 'app/entities/wettkampf/service/wettkampf.service';

@Component({
  selector: 'jhi-resultate-update',
  templateUrl: './resultate-update.component.html',
})
export class ResultateUpdateComponent implements OnInit {
  isSaving = false;

  passe1sCollection: IPassen[] = [];
  passe2sCollection: IPassen[] = [];
  passe3sCollection: IPassen[] = [];
  passe4sCollection: IPassen[] = [];
  gruppesCollection: IGruppen[] = [];
  schuetzesSharedCollection: ISchuetze[] = [];
  wettkampfsSharedCollection: IWettkampf[] = [];

  editForm = this.fb.group({
    id: [],
    runde: [null, [Validators.required]],
    passe1: [],
    passe2: [],
    passe3: [],
    passe4: [],
    gruppe: [],
    schuetze: [],
    wettkampf: [],
  });

  constructor(
    protected resultateService: ResultateService,
    protected passenService: PassenService,
    protected gruppenService: GruppenService,
    protected schuetzeService: SchuetzeService,
    protected wettkampfService: WettkampfService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ resultate }) => {
      this.updateForm(resultate);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const resultate = this.createFromForm();
    if (resultate.id !== undefined) {
      this.subscribeToSaveResponse(this.resultateService.update(resultate));
    } else {
      this.subscribeToSaveResponse(this.resultateService.create(resultate));
    }
  }

  trackPassenById(index: number, item: IPassen): number {
    return item.id!;
  }

  trackGruppenById(index: number, item: IGruppen): number {
    return item.id!;
  }

  trackSchuetzeById(index: number, item: ISchuetze): number {
    return item.id!;
  }

  trackWettkampfById(index: number, item: IWettkampf): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IResultate>>): void {
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

  protected updateForm(resultate: IResultate): void {
    this.editForm.patchValue({
      id: resultate.id,
      runde: resultate.runde,
      passe1: resultate.passe1,
      passe2: resultate.passe2,
      passe3: resultate.passe3,
      passe4: resultate.passe4,
      gruppe: resultate.gruppe,
      schuetze: resultate.schuetze,
      wettkampf: resultate.wettkampf,
    });

    this.passe1sCollection = this.passenService.addPassenToCollectionIfMissing(this.passe1sCollection, resultate.passe1);
    this.passe2sCollection = this.passenService.addPassenToCollectionIfMissing(this.passe2sCollection, resultate.passe2);
    this.passe3sCollection = this.passenService.addPassenToCollectionIfMissing(this.passe3sCollection, resultate.passe3);
    this.passe4sCollection = this.passenService.addPassenToCollectionIfMissing(this.passe4sCollection, resultate.passe4);
    this.gruppesCollection = this.gruppenService.addGruppenToCollectionIfMissing(this.gruppesCollection, resultate.gruppe);
    this.schuetzesSharedCollection = this.schuetzeService.addSchuetzeToCollectionIfMissing(
      this.schuetzesSharedCollection,
      resultate.schuetze
    );
    this.wettkampfsSharedCollection = this.wettkampfService.addWettkampfToCollectionIfMissing(
      this.wettkampfsSharedCollection,
      resultate.wettkampf
    );
  }

  protected loadRelationshipsOptions(): void {
    this.passenService
      .query({ 'resultateId.specified': 'false' })
      .pipe(map((res: HttpResponse<IPassen[]>) => res.body ?? []))
      .pipe(map((passens: IPassen[]) => this.passenService.addPassenToCollectionIfMissing(passens, this.editForm.get('passe1')!.value)))
      .subscribe((passens: IPassen[]) => (this.passe1sCollection = passens));

    this.passenService
      .query({ 'resultateId.specified': 'false' })
      .pipe(map((res: HttpResponse<IPassen[]>) => res.body ?? []))
      .pipe(map((passens: IPassen[]) => this.passenService.addPassenToCollectionIfMissing(passens, this.editForm.get('passe2')!.value)))
      .subscribe((passens: IPassen[]) => (this.passe2sCollection = passens));

    this.passenService
      .query({ 'resultateId.specified': 'false' })
      .pipe(map((res: HttpResponse<IPassen[]>) => res.body ?? []))
      .pipe(map((passens: IPassen[]) => this.passenService.addPassenToCollectionIfMissing(passens, this.editForm.get('passe3')!.value)))
      .subscribe((passens: IPassen[]) => (this.passe3sCollection = passens));

    this.passenService
      .query({ 'resultateId.specified': 'false' })
      .pipe(map((res: HttpResponse<IPassen[]>) => res.body ?? []))
      .pipe(map((passens: IPassen[]) => this.passenService.addPassenToCollectionIfMissing(passens, this.editForm.get('passe4')!.value)))
      .subscribe((passens: IPassen[]) => (this.passe4sCollection = passens));

    this.gruppenService
      .query({ 'resultateId.specified': 'false' })
      .pipe(map((res: HttpResponse<IGruppen[]>) => res.body ?? []))
      .pipe(
        map((gruppens: IGruppen[]) => this.gruppenService.addGruppenToCollectionIfMissing(gruppens, this.editForm.get('gruppe')!.value))
      )
      .subscribe((gruppens: IGruppen[]) => (this.gruppesCollection = gruppens));

    this.schuetzeService
      .query()
      .pipe(map((res: HttpResponse<ISchuetze[]>) => res.body ?? []))
      .pipe(
        map((schuetzes: ISchuetze[]) =>
          this.schuetzeService.addSchuetzeToCollectionIfMissing(schuetzes, this.editForm.get('schuetze')!.value)
        )
      )
      .subscribe((schuetzes: ISchuetze[]) => (this.schuetzesSharedCollection = schuetzes));

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

  protected createFromForm(): IResultate {
    return {
      ...new Resultate(),
      id: this.editForm.get(['id'])!.value,
      runde: this.editForm.get(['runde'])!.value,
      passe1: this.editForm.get(['passe1'])!.value,
      passe2: this.editForm.get(['passe2'])!.value,
      passe3: this.editForm.get(['passe3'])!.value,
      passe4: this.editForm.get(['passe4'])!.value,
      gruppe: this.editForm.get(['gruppe'])!.value,
      schuetze: this.editForm.get(['schuetze'])!.value,
      wettkampf: this.editForm.get(['wettkampf'])!.value,
    };
  }
}
