import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IWettkampf } from '../wettkampf.model';
import { RangierungService } from '../../rangierung/service/rangierung.service';
import { HttpResponse } from '@angular/common/http';
import { getRangierungKriterien, IRangierung } from '../../rangierung/rangierung.model';
import { CdkDragDrop, moveItemInArray, transferArrayItem } from '@angular/cdk/drag-drop';

@Component({
  selector: 'jhi-wettkampf-rangierung',
  templateUrl: './wettkampf-rangierung.component.html',
  styleUrls: ['./wettkampf-rangierung.component.css'],
})
export class WettkampfRangierungComponent implements OnInit {
  wettkampf: IWettkampf | null = null;

  items = ['SERIE', 'ALTER', 'RESULTAT', 'TIEFSCHUESSE', 'MOUCHEN'];

  basket = [''];

  constructor(protected activatedRoute: ActivatedRoute, protected rangierungService: RangierungService) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ wettkampf }) => {
      this.wettkampf = wettkampf;
      this.basket = [];
      this.rangierungService.findByWettkampf(wettkampf).subscribe((res: HttpResponse<Array<IRangierung>>) => {
        const array = res.body;

        if (array !== null) {
          array.sort((n1, n2) => {
            if ((n1.position || 0) > (n2.position || 0)) {
              return 1;
            }

            if ((n1.position || 0) < (n2.position || 0)) {
              return -1;
            }

            return 0;
          });
          array.forEach(value => {
            this.basket.push(getRangierungKriterien(value) ?? '');
          });
        }

        const missing = this.items.filter(item => this.basket.indexOf(item) < 0);
        console.log(missing);
      });
    });
  }

  drop(event: CdkDragDrop<string[]>): void {
    if (event.previousContainer === event.container) {
      moveItemInArray(event.container.data, event.previousIndex, event.currentIndex);
    } else {
      transferArrayItem(event.previousContainer.data, event.container.data, event.previousIndex, event.currentIndex);
    }
  }

  previousState(): void {
    window.history.back();
  }
}
