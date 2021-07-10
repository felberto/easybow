import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { VereinDetailComponent } from './verein-detail.component';

describe('Component Tests', () => {
  describe('Verein Management Detail Component', () => {
    let comp: VereinDetailComponent;
    let fixture: ComponentFixture<VereinDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [VereinDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ verein: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(VereinDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(VereinDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load verein on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.verein).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
