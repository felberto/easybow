import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RundeDetailComponent } from './runde-detail.component';

describe('Component Tests', () => {
  describe('Runde Management Detail Component', () => {
    let comp: RundeDetailComponent;
    let fixture: ComponentFixture<RundeDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [RundeDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ runde: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(RundeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RundeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load runde on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.runde).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
