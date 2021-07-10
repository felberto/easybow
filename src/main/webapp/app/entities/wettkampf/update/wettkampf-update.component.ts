import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
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
    name: [],
    jahr: [],
    anzahlPassen: [],
    team: [],
    template: [],
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
      anzahlPassen: wettkampf.anzahlPassen,
      team: wettkampf.team,
      template: wettkampf.template,
    });
  }

  protected createFromForm(): IWettkampf {
    return {
      ...new Wettkampf(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      jahr: this.editForm.get(['jahr'])!.value,
      anzahlPassen: this.editForm.get(['anzahlPassen'])!.value,
      team: this.editForm.get(['team'])!.value,
      template: this.editForm.get(['template'])!.value,
    };
  }
}
