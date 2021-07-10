import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SchuetzeDetailComponent } from './schuetze-detail.component';

describe('Component Tests', () => {
  describe('Schuetze Management Detail Component', () => {
    let comp: SchuetzeDetailComponent;
    let fixture: ComponentFixture<SchuetzeDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [SchuetzeDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ schuetze: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(SchuetzeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SchuetzeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load schuetze on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.schuetze).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
