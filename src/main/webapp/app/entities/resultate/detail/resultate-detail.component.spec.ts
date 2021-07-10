import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ResultateDetailComponent } from './resultate-detail.component';

describe('Component Tests', () => {
  describe('Resultate Management Detail Component', () => {
    let comp: ResultateDetailComponent;
    let fixture: ComponentFixture<ResultateDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [ResultateDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ resultate: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(ResultateDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ResultateDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load resultate on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.resultate).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
