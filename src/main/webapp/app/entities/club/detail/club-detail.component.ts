import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IClub } from '../club.model';

@Component({
  selector: 'jhi-club-detail',
  templateUrl: './club-detail.component.html',
})
export class ClubDetailComponent implements OnInit {
  club: IClub | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ club }) => {
      this.club = club;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
