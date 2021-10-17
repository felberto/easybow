import { Component, OnInit } from '@angular/core';
import { IWettkampf } from '../wettkampf.model';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { RanglisteService } from '../../rangliste/service/rangliste.service';
import { HttpResponse } from '@angular/common/http';

@Component({
  selector: 'jhi-generate-rangliste-dialog',
  templateUrl: './generate-rangliste-dialog.component.html',
  styleUrls: ['./generate-rangliste-dialog.component.scss'],
})
export class GenerateRanglisteDialogComponent implements OnInit {
  wettkampf?: IWettkampf | null;
  checkboxArray: Array<any> = [];

  constructor(protected activeModal: NgbActiveModal, private ranglisteService: RanglisteService) {}

  cancel(): void {
    this.activeModal.close('');
  }

  ngOnInit(): void {
    console.log('test');
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
      this.ranglisteService.createRangliste(this.wettkampf, this.checkboxArray).subscribe((res: HttpResponse<any>) => {
        console.log(res.body);
      });
    }
  }
}
