import { Component, OnInit } from '@angular/core';
import { IRangliste } from '../rangliste.model';
import { CdkDragDrop, moveItemInArray, transferArrayItem } from '@angular/cdk/drag-drop';
import { ISchuetzeResultat } from '../schuetzeResultat.model';
import { IWettkampf } from '../wettkampf.model';
import { RanglisteService } from '../service/rangliste.service';
import { ActivatedRoute } from '@angular/router';
import { IResultate } from '../../resultate/resultate.model';

@Component({
  selector: 'jhi-rangliste',
  templateUrl: './rangliste.component.html',
  styleUrls: ['./rangliste.component.scss'],
})
export class RanglisteComponent implements OnInit {
  wettkampf?: IWettkampf | null;
  checkboxArray: Array<any> = [];
  rangliste?: IRangliste | null;

  constructor(private ranglisteService: RanglisteService, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ wettkampf }) => {
      this.wettkampf = wettkampf;
    });
  }

  onCheckboxChange(e: any): void {
    if (e.target.checked) {
      this.checkboxArray.push(e.target.value);
    } else {
      const index = this.checkboxArray.indexOf(e.target.value);
      this.checkboxArray.splice(index, 1);
    }
  }

  generate(): void {
    console.log(this.checkboxArray);
    if (this.wettkampf != null) {
      this.ranglisteService.getRangliste(this.wettkampf, this.checkboxArray).subscribe(res => {
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
    console.log('print');
    console.log(this.rangliste);
  }
}
