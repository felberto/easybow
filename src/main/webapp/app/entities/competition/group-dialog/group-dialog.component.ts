import { Component, OnInit } from '@angular/core';
import { ICompetition } from '../competition.model';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { IAthlete } from '../../athlete/athlete.model';
import { IResults } from 'app/entities/results/results.model';
import { ResultsService } from 'app/entities/results/service/results.service';
import { FormBuilder } from '@angular/forms';
import { map } from 'rxjs/operators';
import { HttpResponse } from '@angular/common/http';
import { IGroup } from '../../group/group.model';
import { GroupService } from '../../group/service/group.service';

@Component({
  selector: 'jhi-athlete-number-dialog',
  templateUrl: './group-dialog.component.html',
})
export class GroupDialogComponent implements OnInit {
  competition?: ICompetition;
  athlete?: IAthlete;

  results: Array<IResults> | null = [];

  groupsSharedCollection: IGroup[] = [];

  testForm = this.fb.group({
    group: [],
  });

  constructor(
    protected activeModal: NgbActiveModal,
    private resultsService: ResultsService,
    private groupService: GroupService,
    protected modalService: NgbModal,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.loadRelationshipsOptions();
  }

  trackGroupById(index: number, item: IGroup): number {
    return item.id!;
  }

  cancel(): void {
    this.activeModal.close('');
  }

  addAthlete(): void {
    if (this.competition?.numberOfRounds !== undefined) {
      for (let i = 0; i < this.competition.numberOfRounds; i++) {
        const result: IResults = {
          competition: this.competition,
          athlete: this.athlete,
          round: i + 1,
          group: this.testForm.get('group')!.value,
        };
        this.resultsService.create(result).subscribe();
        this.cancel();
      }
    }
  }

  protected loadRelationshipsOptions(): void {
    this.groupService
      .query()
      .pipe(map((res: HttpResponse<IGroup[]>) => res.body ?? []))
      .pipe(map((groups: IGroup[]) => this.groupService.addGroupToCollectionIfMissing(groups, this.testForm.get('group')!.value)))
      .subscribe((groups: IGroup[]) => (this.groupsSharedCollection = groups));
  }
}
