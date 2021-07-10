import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IResultate } from '../resultate.model';

@Component({
  selector: 'jhi-resultate-detail',
  templateUrl: './resultate-detail.component.html',
})
export class ResultateDetailComponent implements OnInit {
  resultate: IResultate | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ resultate }) => {
      this.resultate = resultate;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
