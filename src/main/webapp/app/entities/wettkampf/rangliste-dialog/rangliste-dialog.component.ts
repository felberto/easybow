import { Component, OnInit } from '@angular/core';
import { IRangliste } from '../rangliste.model';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { RanglisteService } from '../service/rangliste.service';
import { FormBuilder, Validators } from '@angular/forms';

@Component({
  selector: 'jhi-rangliste-dialog',
  templateUrl: './rangliste-dialog.component.html',
  styleUrls: ['./rangliste-dialog.component.scss'],
})
export class RanglisteDialogComponent implements OnInit {
  isDataAvailable: boolean;
  rangliste?: IRangliste | null;

  printForm = this.fb.group({
    date: [null, [Validators.required]],
    time: [null, [Validators.required]],
    ort: [null, [Validators.required]],
    anzahl: [null, [Validators.required]],
  });

  constructor(protected activeModal: NgbActiveModal, private ranglisteService: RanglisteService, private fb: FormBuilder) {
    this.isDataAvailable = false;
  }

  cancel(): void {
    this.activeModal.close('');
  }

  ngOnInit(): void {
    console.log('this');
  }

  print(): void {
    this.rangliste!.vfDate = this.printForm.get('date')!.value;
    this.rangliste!.vfTime = this.printForm.get('time')!.value;
    this.rangliste!.vfOrt = this.printForm.get('ort')!.value;
    this.rangliste!.vfAnzahl = this.printForm.get('anzahl')!.value;
    if (this.rangliste !== null && this.rangliste !== undefined) {
      this.ranglisteService.printRangliste(this.rangliste).subscribe(data => {
        const blob = new Blob([data], {
          type: 'application/pdf', // must match the Accept type
        });
        const link = document.createElement('a');
        link.href = window.URL.createObjectURL(blob);
        link.target = '_blank';
        const runde = 'Qualifikation Verbändefinal';
        let title = '';
        if (
          this.rangliste?.wettkampf?.name !== undefined &&
          this.rangliste.wettkampf.jahr !== undefined &&
          this.rangliste.wettkampf.jahr !== null
        ) {
          title =
            'Rangliste ' + this.rangliste.wettkampf.name.toString() + ' ' + this.rangliste.wettkampf.jahr.toString() + ' ' + runde + '.pdf';
        }
        link.download = title;
        link.click();
        window.URL.revokeObjectURL(link.href);
      });
    }
  }
}
