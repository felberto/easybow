import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { RangierungService } from '../service/rangierung.service';

import { RangierungComponent } from './rangierung.component';

describe('Component Tests', () => {
  describe('Rangierung Management Component', () => {
    let comp: RangierungComponent;
    let fixture: ComponentFixture<RangierungComponent>;
    let service: RangierungService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [RangierungComponent],
      })
        .overrideTemplate(RangierungComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RangierungComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(RangierungService);

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
      expect(comp.rangierungs?.[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
