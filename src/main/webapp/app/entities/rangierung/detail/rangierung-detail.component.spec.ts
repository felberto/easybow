import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RangierungDetailComponent } from './rangierung-detail.component';

describe('Component Tests', () => {
  describe('Rangierung Management Detail Component', () => {
    let comp: RangierungDetailComponent;
    let fixture: ComponentFixture<RangierungDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [RangierungDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ rangierung: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(RangierungDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RangierungDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load rangierung on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.rangierung).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
