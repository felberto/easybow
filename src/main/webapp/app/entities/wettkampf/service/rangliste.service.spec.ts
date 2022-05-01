import { TestBed } from '@angular/core/testing';

import { RanglisteService } from './rangliste.service';

describe('RanglisteService', () => {
  let service: RanglisteService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RanglisteService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
