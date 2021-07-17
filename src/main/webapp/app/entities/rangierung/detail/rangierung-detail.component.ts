import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRangierung } from '../rangierung.model';

@Component({
  selector: 'jhi-rangierung-detail',
  templateUrl: './rangierung-detail.component.html',
})
export class RangierungDetailComponent implements OnInit {
  rangierung: IRangierung | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ rangierung }) => {
      this.rangierung = rangierung;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
