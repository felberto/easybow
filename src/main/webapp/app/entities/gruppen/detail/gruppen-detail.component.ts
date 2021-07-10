import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGruppen } from '../gruppen.model';

@Component({
  selector: 'jhi-gruppen-detail',
  templateUrl: './gruppen-detail.component.html',
})
export class GruppenDetailComponent implements OnInit {
  gruppen: IGruppen | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ gruppen }) => {
      this.gruppen = gruppen;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
