import { Component } from '@angular/core';
import { ICompetition } from '../competition.model';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { IAthlete } from '../../athlete/athlete.model';
import { IResults } from 'app/entities/results/results.model';
import { ResultsService } from 'app/entities/results/service/results.service';
import { FormControl, FormGroup } from '@angular/forms';

@Component({
  selector: 'jhi-athlete-number-dialog',
  templateUrl: './athlete-number-dialog.component.html',
})
export class AthleteNumberDialogComponent {
  competition?: ICompetition;
  athlete?: IAthlete;

  results: Array<IResults> | null = [];

  testForm = new FormGroup({
    number: new FormControl(''),
  });

  constructor(protected activeModal: NgbActiveModal, private resultsService: ResultsService, protected modalService: NgbModal) {}

  cancel(): void {
    this.activeModal.close('');
  }

  addAthlete(): void {
    const number = this.testForm.get('number')!.value;
    if (this.competition?.numberOfRounds !== undefined) {
      for (let i = 0; i < this.competition.numberOfRounds; i++) {
        const result: IResults = {
          competition: this.competition,
          athlete: this.athlete,
          round: i + 1,
          athleteNumber: number,
        };
        this.resultsService.create(result).subscribe();
        this.cancel();
      }
    }
  }
}
