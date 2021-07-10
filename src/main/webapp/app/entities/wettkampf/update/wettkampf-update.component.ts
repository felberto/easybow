import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IWettkampf, Wettkampf } from '../wettkampf.model';
import { WettkampfService } from '../service/wettkampf.service';

@Component({
  selector: 'jhi-wettkampf-update',
  templateUrl: './wettkampf-update.component.html',
})
export class WettkampfUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    jahr: [null, [Validators.required]],
    anzahlRunden: [null, [Validators.required]],
    finalRunde: [],
    anzahlPassen: [null, [Validators.required, Validators.min(1), Validators.max(4)]],
    anzahlPassenFinal: [null, [Validators.min(1), Validators.max(4)]],
    anzahlTeam: [],
  });

  constructor(protected wettkampfService: WettkampfService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ wettkampf }) => {
      this.updateForm(wettkampf);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const wettkampf = this.createFromForm();
    if (wettkampf.id !== undefined) {
      this.subscribeToSaveResponse(this.wettkampfService.update(wettkampf));
    } else {
      this.subscribeToSaveResponse(this.wettkampfService.create(wettkampf));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IWettkampf>>): void {
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

  protected updateForm(wettkampf: IWettkampf): void {
    this.editForm.patchValue({
      id: wettkampf.id,
      name: wettkampf.name,
      jahr: wettkampf.jahr,
      anzahlRunden: wettkampf.anzahlRunden,
      finalRunde: wettkampf.finalRunde,
      anzahlPassen: wettkampf.anzahlPassen,
      anzahlPassenFinal: wettkampf.anzahlPassenFinal,
      anzahlTeam: wettkampf.anzahlTeam,
    });
  }

  protected createFromForm(): IWettkampf {
    return {
      ...new Wettkampf(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      jahr: this.editForm.get(['jahr'])!.value,
      anzahlRunden: this.editForm.get(['anzahlRunden'])!.value,
      finalRunde: this.editForm.get(['finalRunde'])!.value,
      anzahlPassen: this.editForm.get(['anzahlPassen'])!.value,
      anzahlPassenFinal: this.editForm.get(['anzahlPassenFinal'])!.value,
      anzahlTeam: this.editForm.get(['anzahlTeam'])!.value,
    };
  }
}
