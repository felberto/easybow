import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RankingListEasvWorldcupComponent } from './ranking-list-easv-worldcup.component';

describe('RankingListEasvWorldupComponent', () => {
  let component: RankingListEasvWorldcupComponent;
  let fixture: ComponentFixture<RankingListEasvWorldcupComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [RankingListEasvWorldcupComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RankingListEasvWorldcupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
