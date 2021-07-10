import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PassenDetailComponent } from './passen-detail.component';

describe('Component Tests', () => {
  describe('Passen Management Detail Component', () => {
    let comp: PassenDetailComponent;
    let fixture: ComponentFixture<PassenDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [PassenDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ passen: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(PassenDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PassenDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load passen on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.passen).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
