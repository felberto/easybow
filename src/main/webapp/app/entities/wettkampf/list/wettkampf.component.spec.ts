import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { WettkampfService } from '../service/wettkampf.service';

import { WettkampfComponent } from './wettkampf.component';

describe('Component Tests', () => {
  describe('Wettkampf Management Component', () => {
    let comp: WettkampfComponent;
    let fixture: ComponentFixture<WettkampfComponent>;
    let service: WettkampfService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [WettkampfComponent],
      })
        .overrideTemplate(WettkampfComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(WettkampfComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(WettkampfService);

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
      expect(comp.wettkampfs?.[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
