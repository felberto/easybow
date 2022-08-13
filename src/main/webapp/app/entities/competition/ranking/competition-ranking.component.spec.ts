import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CompetitionRankingComponent } from './competition-ranking.component';

describe('Component Tests', () => {
  describe('Competition Management Rangierung Component', () => {
    let comp: CompetitionRankingComponent;
    let fixture: ComponentFixture<CompetitionRankingComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [CompetitionRankingComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ competition: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(CompetitionRankingComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CompetitionRankingComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load competition on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.competition).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
