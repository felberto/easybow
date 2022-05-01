import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { IWettkampf } from '../../wettkampf/wettkampf.model';
import { RanglisteService } from '../../wettkampf/service/rangliste.service';
import { IRangliste } from '../../wettkampf/rangliste.model';

@Component({
  selector: 'jhi-view',
  templateUrl: './view.component.html',
  styleUrls: ['./view.component.scss'],
})
export class ViewComponent implements OnInit {
  wettkampf?: IWettkampf | null;
  rangliste?: IRangliste | null;

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
        this.rangliste = res.body;
      });
    }
  }
}
