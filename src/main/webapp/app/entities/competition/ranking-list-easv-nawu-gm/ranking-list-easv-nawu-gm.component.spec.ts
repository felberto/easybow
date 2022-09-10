import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RankingListEasvNawuGmComponent } from './ranking-list-easv-nawu-gm.component';

describe('RankingListEasvWorldupComponent', () => {
  let component: RankingListEasvNawuGmComponent;
  let fixture: ComponentFixture<RankingListEasvNawuGmComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [RankingListEasvNawuGmComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RankingListEasvNawuGmComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
