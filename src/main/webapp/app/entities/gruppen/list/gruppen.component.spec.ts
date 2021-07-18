import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { GruppenService } from '../service/gruppen.service';

import { GruppenComponent } from './gruppen.component';

describe('Component Tests', () => {
  describe('Gruppen Management Component', () => {
    let comp: GruppenComponent;
    let fixture: ComponentFixture<GruppenComponent>;
    let service: GruppenService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [GruppenComponent],
      })
        .overrideTemplate(GruppenComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GruppenComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(GruppenService);

      const headers = new HttpHeaders().append('link', 'link;link');
      jest.spyOn(service, 'query').mockReturnValue(
        of(
          new HttpResponse({
            body: [{ id: 123 }],
            headers,
          })
        )
      );
    });

    it('Should call load all on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.gruppens?.[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
