import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRunde } from '../runde.model';

@Component({
  selector: 'jhi-runde-detail',
  templateUrl: './runde-detail.component.html',
})
export class RundeDetailComponent implements OnInit {
  runde: IRunde | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ runde }) => {
      this.runde = runde;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
