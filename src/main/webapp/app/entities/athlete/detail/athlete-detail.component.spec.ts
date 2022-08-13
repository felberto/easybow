import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AthleteDetailComponent } from './athlete-detail.component';

describe('Component Tests', () => {
  describe('Athlete Management Detail Component', () => {
    let comp: AthleteDetailComponent;
    let fixture: ComponentFixture<AthleteDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [AthleteDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ athlete: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(AthleteDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AthleteDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load athlete on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.athlete).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
