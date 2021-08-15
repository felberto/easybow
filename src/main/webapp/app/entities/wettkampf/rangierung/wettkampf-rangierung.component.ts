import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IWettkampf } from '../wettkampf.model';
import { RangierungService } from '../../rangierung/service/rangierung.service';
import { HttpResponse } from '@angular/common/http';
import { getRangierungKriterien, IRangierung } from '../../rangierung/rangierung.model';
import { CdkDragDrop, moveItemInArray, transferArrayItem } from '@angular/cdk/drag-drop';
import { Rangierungskriterien } from '../../enumerations/rangierungskriterien.model';

@Component({
  selector: 'jhi-wettkampf-rangierung',
  templateUrl: './wettkampf-rangierung.component.html',
  styleUrls: ['./wettkampf-rangierung.component.css'],
})
export class WettkampfRangierungComponent implements OnInit {
  wettkampf!: IWettkampf;

  available = ['SERIE', 'ALTER', 'RESULTAT', 'TIEFSCHUESSE', 'MOUCHEN'];

  used = [''];

  constructor(protected activatedRoute: ActivatedRoute, protected rangierungService: RangierungService) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ wettkampf }) => {
      this.wettkampf = wettkampf;
      this.used = [];
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
            this.used.push(getRangierungKriterien(value) ?? '');
          });
        }

        this.available = this.available.filter(item => this.used.indexOf(item) < 0);
      });
    });
    console.log(this.used);
  }

  save(): void {
    this.rangierungService.deleteAllByWettkampf(this.wettkampf).subscribe(value => {
      this.used.forEach(item => {
        switch (item) {
          case Rangierungskriterien.RESULTAT: {
            const rangierung: IRangierung = {
              wettkampf: this.wettkampf,
              position: this.used.indexOf(item) + 1,
              rangierungskriterien: Rangierungskriterien.RESULTAT,
            };
            this.rangierungService.create(rangierung).subscribe();
            break;
          }
          case Rangierungskriterien.MOUCHEN: {
            const rangierung: IRangierung = {
              wettkampf: this.wettkampf,
              position: this.used.indexOf(item) + 1,
              rangierungskriterien: Rangierungskriterien.MOUCHEN,
            };
            this.rangierungService.create(rangierung).subscribe();
            break;
          }
          case Rangierungskriterien.ALTER: {
            const rangierung: IRangierung = {
              wettkampf: this.wettkampf,
              position: this.used.indexOf(item) + 1,
              rangierungskriterien: Rangierungskriterien.ALTER,
            };
            this.rangierungService.create(rangierung).subscribe();
            break;
          }
          case Rangierungskriterien.SERIE: {
            const rangierung: IRangierung = {
              wettkampf: this.wettkampf,
              position: this.used.indexOf(item) + 1,
              rangierungskriterien: Rangierungskriterien.SERIE,
            };
            this.rangierungService.create(rangierung).subscribe();
            break;
          }
          case Rangierungskriterien.TIEFSCHUESSE: {
            const rangierung: IRangierung = {
              wettkampf: this.wettkampf,
              position: this.used.indexOf(item) + 1,
              rangierungskriterien: Rangierungskriterien.TIEFSCHUESSE,
            };
            this.rangierungService.create(rangierung).subscribe();
            break;
          }
          default: {
            //statements;
            break;
          }
        }
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
