import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { VereinService } from '../service/verein.service';

import { VereinComponent } from './verein.component';

describe('Component Tests', () => {
  describe('Verein Management Component', () => {
    let comp: VereinComponent;
    let fixture: ComponentFixture<VereinComponent>;
    let service: VereinService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [VereinComponent],
      })
        .overrideTemplate(VereinComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(VereinComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(VereinService);

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
      expect(comp.vereins?.[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
