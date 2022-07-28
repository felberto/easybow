import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ClubDetailComponent } from './club-detail.component';

describe('Component Tests', () => {
  describe('Club Management Detail Component', () => {
    let comp: ClubDetailComponent;
    let fixture: ComponentFixture<ClubDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [ClubDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ club: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(ClubDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ClubDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load club on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.club).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
