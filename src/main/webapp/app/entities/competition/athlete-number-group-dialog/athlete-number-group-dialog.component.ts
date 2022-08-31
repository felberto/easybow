import { Component, OnInit } from '@angular/core';
import { ICompetition } from '../competition.model';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { IAthlete } from '../../athlete/athlete.model';
import { IResults } from 'app/entities/results/results.model';
import { ResultsService } from 'app/entities/results/service/results.service';
import { FormControl, FormGroup } from '@angular/forms';
import { IGroup } from '../../group/group.model';
import { map } from 'rxjs/operators';
import { HttpResponse } from '@angular/common/http';
import { GroupService } from '../../group/service/group.service';

@Component({
  selector: 'jhi-athlete-number-group-dialog',
  templateUrl: './athlete-number-group-dialog.component.html',
})
export class AthleteNumberGroupDialogComponent implements OnInit {
  competition?: ICompetition;
  athlete?: IAthlete;

  results: Array<IResults> | null = [];

  groupsSharedCollection: IGroup[] = [];

  testForm = new FormGroup({
    number: new FormControl(''),
    group: new FormControl(''),
  });

  constructor(
    protected activeModal: NgbActiveModal,
    private resultsService: ResultsService,
    protected modalService: NgbModal,
    private groupService: GroupService
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
    const number = this.testForm.get('number')!.value;
    const groupField = this.testForm.get('group')!.value;
    if (this.competition?.numberOfRounds !== undefined) {
      for (let i = 0; i < this.competition.numberOfRounds; i++) {
        const result: IResults = {
          competition: this.competition,
          athlete: this.athlete,
          round: i + 1,
          athleteNumber: number,
          group: groupField,
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
