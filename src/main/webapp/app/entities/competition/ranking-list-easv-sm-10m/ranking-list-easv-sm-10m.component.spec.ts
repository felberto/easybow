import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RankingListEasvSm10mComponent } from './ranking-list-easv-sm-10m.component';

describe('RankingListEasvSm10mComponent', () => {
  let component: RankingListEasvSm10mComponent;
  let fixture: ComponentFixture<RankingListEasvSm10mComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [RankingListEasvSm10mComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RankingListEasvSm10mComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
