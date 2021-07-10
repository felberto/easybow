import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IVerband } from '../verband.model';

@Component({
  selector: 'jhi-verband-detail',
  templateUrl: './verband-detail.component.html',
})
export class VerbandDetailComponent implements OnInit {
  verband: IVerband | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ verband }) => {
      this.verband = verband;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
