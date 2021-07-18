import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { ResultateService } from '../service/resultate.service';

import { ResultateComponent } from './resultate.component';

describe('Component Tests', () => {
  describe('Resultate Management Component', () => {
    let comp: ResultateComponent;
    let fixture: ComponentFixture<ResultateComponent>;
    let service: ResultateService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [ResultateComponent],
      })
        .overrideTemplate(ResultateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ResultateComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(ResultateService);

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
      expect(comp.resultates?.[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
