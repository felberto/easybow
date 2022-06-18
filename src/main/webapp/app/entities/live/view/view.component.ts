import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { IWettkampf } from '../../wettkampf/wettkampf.model';
import { RanglisteService } from '../../wettkampf/service/rangliste.service';
import { IRangliste } from '../../wettkampf/rangliste.model';
import { ISchuetzeResultat } from '../../wettkampf/schuetzeResultat.model';

@Component({
  selector: 'jhi-view',
  templateUrl: './view.component.html',
  styleUrls: ['./view.component.scss'],
})
export class ViewComponent implements OnInit {
  wettkampf?: IWettkampf | null;
  rangliste?: IRangliste | null;

  ranglisteView: ISchuetzeResultat[] = [];
  rang = 0;

  variable = 1;

  constructor(protected activatedRoute: ActivatedRoute, private ranglisteService: RanglisteService) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ wettkampf }) => {
      this.wettkampf = wettkampf;
      if (this.wettkampf != null) {
        this.refreshData();
        setInterval(() => {
          this.refreshData();
        }, 10000);
      }
    });
  }

  refreshData(): void {
    if (this.wettkampf != null) {
      this.ranglisteService.getRangliste(this.wettkampf, 99).subscribe(res => {
        console.log(res.body);
        this.rangliste = res.body;
        if (this.rangliste!.schuetzeResultatList!.length > 10) {
          if (this.variable === 1) {
            this.ranglisteView = this.rangliste!.schuetzeResultatList!.slice(0, 10);
            this.variable = 2;
            this.rang = 0;
          } else {
            this.ranglisteView = this.rangliste!.schuetzeResultatList!.slice(10, 21);
            this.variable = 1;
            this.rang = 1;
          }
        }
      });
    }
  }
}
