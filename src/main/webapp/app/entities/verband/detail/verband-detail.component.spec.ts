import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { VerbandDetailComponent } from './verband-detail.component';

describe('Component Tests', () => {
  describe('Verband Management Detail Component', () => {
    let comp: VerbandDetailComponent;
    let fixture: ComponentFixture<VerbandDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [VerbandDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ verband: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(VerbandDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(VerbandDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load verband on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.verband).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
