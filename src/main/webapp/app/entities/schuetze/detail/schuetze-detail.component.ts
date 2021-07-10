import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISchuetze } from '../schuetze.model';

@Component({
  selector: 'jhi-schuetze-detail',
  templateUrl: './schuetze-detail.component.html',
})
export class SchuetzeDetailComponent implements OnInit {
  schuetze: ISchuetze | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ schuetze }) => {
      this.schuetze = schuetze;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
