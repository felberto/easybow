import { TestBed } from '@angular/core/testing';

import { RankingListService } from './rankingList.service';

describe('RankingListService', () => {
  let service: RankingListService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RankingListService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
