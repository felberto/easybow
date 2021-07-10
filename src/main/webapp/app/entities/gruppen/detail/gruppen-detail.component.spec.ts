import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GruppenDetailComponent } from './gruppen-detail.component';

describe('Component Tests', () => {
  describe('Gruppen Management Detail Component', () => {
    let comp: GruppenDetailComponent;
    let fixture: ComponentFixture<GruppenDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [GruppenDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ gruppen: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(GruppenDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(GruppenDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load gruppen on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.gruppen).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
