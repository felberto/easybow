import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { SchuetzeService } from '../service/schuetze.service';

import { SchuetzeComponent } from './schuetze.component';

describe('Component Tests', () => {
  describe('Schuetze Management Component', () => {
    let comp: SchuetzeComponent;
    let fixture: ComponentFixture<SchuetzeComponent>;
    let service: SchuetzeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [SchuetzeComponent],
      })
        .overrideTemplate(SchuetzeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SchuetzeComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(SchuetzeService);

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
      expect(comp.schuetzes?.[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
