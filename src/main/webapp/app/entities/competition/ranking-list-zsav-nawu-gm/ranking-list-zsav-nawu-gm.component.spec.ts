import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RankingListZsavNawuGmComponent } from './ranking-list-zsav-nawu-gm.component';

describe('RankingListEasvWorldupComponent', () => {
  let component: RankingListZsavNawuGmComponent;
  let fixture: ComponentFixture<RankingListZsavNawuGmComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [RankingListZsavNawuGmComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RankingListZsavNawuGmComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
