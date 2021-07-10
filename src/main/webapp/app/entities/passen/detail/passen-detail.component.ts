import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPassen } from '../passen.model';

@Component({
  selector: 'jhi-passen-detail',
  templateUrl: './passen-detail.component.html',
})
export class PassenDetailComponent implements OnInit {
  passen: IPassen | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ passen }) => {
      this.passen = passen;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
