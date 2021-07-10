import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { WettkampfDetailComponent } from './wettkampf-detail.component';

describe('Component Tests', () => {
  describe('Wettkampf Management Detail Component', () => {
    let comp: WettkampfDetailComponent;
    let fixture: ComponentFixture<WettkampfDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [WettkampfDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ wettkampf: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(WettkampfDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(WettkampfDetailComponent);
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
