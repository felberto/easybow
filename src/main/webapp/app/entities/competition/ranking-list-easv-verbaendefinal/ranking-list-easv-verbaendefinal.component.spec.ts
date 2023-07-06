import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RankingListEasvVerbaendefinalComponent } from './ranking-list-easv-verbaendefinal.component';

describe('RankingListEasvWorldupComponent', () => {
  let component: RankingListEasvVerbaendefinalComponent;
  let fixture: ComponentFixture<RankingListEasvVerbaendefinalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [RankingListEasvVerbaendefinalComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RankingListEasvVerbaendefinalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
