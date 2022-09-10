import { Component, OnInit } from '@angular/core';
import { ICompetition } from '../competition.model';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { IAthlete } from '../../athlete/athlete.model';
import { AthleteService } from '../../athlete/service/athlete.service';
import { IResults } from 'app/entities/results/results.model';
import { ResultsService } from 'app/entities/results/service/results.service';
import { HttpResponse } from '@angular/common/http';
import { AthleteNumberDialogComponent } from '../athlete-number-dialog/athlete-number-dialog.component';
import { GroupDialogComponent } from '../group-dialog/group-dialog.component';
import { AthleteNumberGroupDialogComponent } from '../athlete-number-group-dialog/athlete-number-group-dialog.component';

@Component({
  selector: 'jhi-resultate-dialog',
  templateUrl: './results-dialog.component.html',
})
export class ResultsDialogComponent implements OnInit {
  competition?: ICompetition;

  results: Array<IResults> | null = [];
  athletes?: Array<IAthlete> | null;
  addedAthletes?: Array<IAthlete> | null;

  constructor(
    protected activeModal: NgbActiveModal,
    protected athleteService: AthleteService,
    private resultsService: ResultsService,
    protected modalService: NgbModal
  ) {}

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

  addAthlete(athlete: IAthlete, competition: ICompetition): void {
    let modalRef;
    if (competition.competitionType === 'EASV_WORLDCUP') {
      modalRef = this.modalService.open(AthleteNumberDialogComponent, { size: 'xl', backdrop: 'static' });
      modalRef.componentInstance.athlete = athlete;
      modalRef.componentInstance.competition = competition;
    } else if (competition.competitionType === 'ZSAV_NAWU_GM') {
      modalRef = this.modalService.open(GroupDialogComponent, { size: 'xl', backdrop: 'static' });
      modalRef.componentInstance.athlete = athlete;
      modalRef.componentInstance.competition = competition;
    } else if (competition.competitionType === 'EASV_NAWU_GM') {
      modalRef = this.modalService.open(AthleteNumberGroupDialogComponent, { size: 'xl', backdrop: 'static' });
      modalRef.componentInstance.athlete = athlete;
      modalRef.componentInstance.competition = competition;
    } else if (competition.competitionType === 'EASV_STAENDEMATCH') {
      modalRef = this.modalService.open(AthleteNumberGroupDialogComponent, { size: 'xl', backdrop: 'static' });
      modalRef.componentInstance.athlete = athlete;
      modalRef.componentInstance.competition = competition;
    }
  }

  removeAthlete(athlete: IAthlete, competition: ICompetition): void {
    this.resultsService.deleteByAthleteAndCompetition(athlete, competition).subscribe();
  }
}
