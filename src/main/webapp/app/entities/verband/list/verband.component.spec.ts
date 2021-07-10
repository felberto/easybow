import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { VerbandService } from '../service/verband.service';

import { VerbandComponent } from './verband.component';

describe('Component Tests', () => {
  describe('Verband Management Component', () => {
    let comp: VerbandComponent;
    let fixture: ComponentFixture<VerbandComponent>;
    let service: VerbandService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [VerbandComponent],
      })
        .overrideTemplate(VerbandComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(VerbandComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(VerbandService);

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
      expect(comp.verbands?.[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
