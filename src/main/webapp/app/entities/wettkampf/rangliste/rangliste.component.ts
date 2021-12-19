import { Component, OnInit } from '@angular/core';
import { IRangliste } from '../rangliste.model';
import { CdkDragDrop, moveItemInArray, transferArrayItem } from '@angular/cdk/drag-drop';
import { ISchuetzeResultat } from '../schuetzeResultat.model';
import { IWettkampf } from '../wettkampf.model';
import { RanglisteService } from '../service/rangliste.service';
import { ActivatedRoute } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { RanglisteDialogComponent } from '../rangliste-dialog/rangliste-dialog.component';

@Component({
  selector: 'jhi-rangliste',
  templateUrl: './rangliste.component.html',
  styleUrls: ['./rangliste.component.scss'],
})
export class RanglisteComponent implements OnInit {
  wettkampf?: IWettkampf | null;
  rangliste?: IRangliste | null;

  constructor(private ranglisteService: RanglisteService, protected activatedRoute: ActivatedRoute, protected modalService: NgbModal) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ wettkampf }) => {
      this.wettkampf = wettkampf;
    });
  }

  generate(type: number): void {
    if (this.wettkampf != null) {
      this.ranglisteService.getRangliste(this.wettkampf, type).subscribe(res => {
        this.rangliste = res.body;
        console.log(this.rangliste);
      });
    }
  }

  drop(event: CdkDragDrop<Array<ISchuetzeResultat>, any>): void {
    if (event.previousContainer === event.container) {
      moveItemInArray(event.container.data, event.previousIndex, event.currentIndex);
    } else {
      transferArrayItem(event.previousContainer.data, event.container.data, event.previousIndex, event.currentIndex);
    }
  }

  print(): void {
    if (this.rangliste !== null && this.rangliste !== undefined) {
      //TODO if verbändefinal 101 open modal and date and anzahl for wettkampf, more things needed probably
      if (this.rangliste.type === 101) {
        const modalRef = this.modalService.open(RanglisteDialogComponent, { size: 'xl', backdrop: 'static' });
        modalRef.componentInstance.rangliste = this.rangliste;
      } else {
        this.ranglisteService.printRangliste(this.rangliste).subscribe(data => {
          const blob = new Blob([data], {
            type: 'application/pdf', // must match the Accept type
          });
          const link = document.createElement('a');
          link.href = window.URL.createObjectURL(blob);
          link.target = '_blank';
          let runde = '';
          if (this.rangliste!.type === 1) {
            runde = '1. Runde';
          }
          if (this.rangliste!.type === 2) {
            runde = '2. Runde';
          }
          if (this.rangliste!.type === 100) {
            runde = 'Qualifikation Final';
          }
          if (this.rangliste!.type === 99) {
            runde = 'Final';
          }
          if (this.rangliste!.type === 101) {
            runde = 'Qualifikation Verbändefinal';
          }
          runde = runde.concat(' Runde');
          let title = '';
          if (
            this.rangliste?.wettkampf?.name !== undefined &&
            this.rangliste.wettkampf.jahr !== undefined &&
            this.rangliste.wettkampf.jahr !== null
          ) {
            title =
              'Rangliste ' +
              this.rangliste.wettkampf.name.toString() +
              ' ' +
              this.rangliste.wettkampf.jahr.toString() +
              ' ' +
              runde +
              '.pdf';
          }
          link.download = title;
          link.click();
          window.URL.revokeObjectURL(link.href);
        });
      }
    }
  }
}
