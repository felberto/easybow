import { Component, OnInit } from '@angular/core';
import { ICompetition } from '../competition.model';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { IAthlete } from '../../athlete/athlete.model';
import { AthleteService } from '../../athlete/service/athlete.service';
import { IResults } from 'app/entities/results/results.model';
import { ResultsService } from 'app/entities/results/service/results.service';
import { HttpResponse } from '@angular/common/http';

@Component({
  selector: 'jhi-resultate-dialog',
  templateUrl: './results-dialog.component.html',
})
export class ResultsDialogComponent implements OnInit {
  competition?: ICompetition;

  results: Array<IResults> | null = [];
  athletes?: Array<IAthlete> | null;
  addedAthletes?: Array<IAthlete> | null;

  constructor(protected activeModal: NgbActiveModal, protected athleteService: AthleteService, private resultsService: ResultsService) {}

  cancel(): void {
    this.activeModal.close('deleted');
  }

  ngOnInit(): void {
    this.athleteService.findAll().subscribe(result => {
      this.athletes = result.body;

      if (this.competition) {
        this.resultsService.findByCompetition(this.competition).subscribe((res: HttpResponse<Array<IResults>>) => {
          this.results = res.body;

          const tempAthlete: Array<IAthlete> = [];
          this.results?.forEach(value => {
            if (value.athlete) {
              tempAthlete.push(value.athlete);
            }
          });

          this.addedAthletes = tempAthlete.filter((s, i, arr) => arr.indexOf(<IAthlete>arr.find(t => t.id === s.id)) === i);
        });
      }
    });
  }

  addAthlete(athlete: IAthlete): void {
    if (this.competition?.numberOfRounds !== undefined) {
      for (let i = 0; i < this.competition.numberOfRounds; i++) {
        const result: IResults = {
          competition: this.competition,
          athlete,
          round: i + 1,
        };
        this.resultsService.create(result).subscribe();
      }
    }
    if (this.addedAthletes !== undefined && this.addedAthletes !== null) {
      this.addedAthletes.push(athlete);
    }
  }

  removeAthlete(athlete: IAthlete): void {
    if (this.addedAthletes !== null && this.addedAthletes !== undefined) {
      this.addedAthletes.forEach(s => {
        if (s.id === athlete.id) {
          this.addedAthletes?.splice(this.addedAthletes.indexOf(s), 1);
          this.resultsService.deleteByAthlete(athlete).subscribe();
        }
      });
    }
  }
}
