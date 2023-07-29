import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RankingListEasvWorldcup30mComponent } from './ranking-list-easv-worldcup-30m.component';

describe('RankingListEasvWorldupComponent', () => {
  let component: RankingListEasvWorldcup30mComponent;
  let fixture: ComponentFixture<RankingListEasvWorldcup30mComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [RankingListEasvWorldcup30mComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RankingListEasvWorldcup30mComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
