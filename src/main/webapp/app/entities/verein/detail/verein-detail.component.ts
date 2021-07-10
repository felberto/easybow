import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IVerein } from '../verein.model';

@Component({
  selector: 'jhi-verein-detail',
  templateUrl: './verein-detail.component.html',
})
export class VereinDetailComponent implements OnInit {
  verein: IVerein | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ verein }) => {
      this.verein = verein;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
