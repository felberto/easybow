import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IWettkampf } from '../wettkampf.model';

@Component({
  selector: 'jhi-wettkampf-detail',
  templateUrl: './wettkampf-detail.component.html',
})
export class WettkampfDetailComponent implements OnInit {
  wettkampf: IWettkampf | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ wettkampf }) => {
      this.wettkampf = wettkampf;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
