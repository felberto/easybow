import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { WettkampfRangierungComponent } from './wettkampf-rangierung.component';

describe('Component Tests', () => {
  describe('Wettkampf Management Rangierung Component', () => {
    let comp: WettkampfRangierungComponent;
    let fixture: ComponentFixture<WettkampfRangierungComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [WettkampfRangierungComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ wettkampf: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(WettkampfRangierungComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(WettkampfRangierungComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load wettkampf on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.wettkampf).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
