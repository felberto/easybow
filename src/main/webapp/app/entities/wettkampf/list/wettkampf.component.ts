import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IWettkampf } from '../wettkampf.model';
import { WettkampfService } from '../service/wettkampf.service';
import { WettkampfDeleteDialogComponent } from '../delete/wettkampf-delete-dialog.component';

@Component({
  selector: 'jhi-wettkampf',
  templateUrl: './wettkampf.component.html',
})
export class WettkampfComponent implements OnInit {
  wettkampfs?: IWettkampf[];
  isLoading = false;

  constructor(protected wettkampfService: WettkampfService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.wettkampfService.query().subscribe(
      (res: HttpResponse<IWettkampf[]>) => {
        this.isLoading = false;
        this.wettkampfs = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IWettkampf): number {
    return item.id!;
  }

  delete(wettkampf: IWettkampf): void {
    const modalRef = this.modalService.open(WettkampfDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.wettkampf = wettkampf;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
