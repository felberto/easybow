import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IResults } from '../results.model';

@Component({
  selector: 'jhi-results-detail',
  templateUrl: './results-detail.component.html',
})
export class ResultsDetailComponent implements OnInit {
  results: IResults | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ results }) => {
      this.results = results;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
