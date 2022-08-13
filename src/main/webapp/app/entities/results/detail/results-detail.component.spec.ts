import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ResultsDetailComponent } from './results-detail.component';

describe('Component Tests', () => {
  describe('Results Management Detail Component', () => {
    let comp: ResultsDetailComponent;
    let fixture: ComponentFixture<ResultsDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [ResultsDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ results: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(ResultsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ResultsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load results on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.results).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
