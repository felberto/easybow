import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RankingListEasvStaendematchComponent } from './ranking-list-easv-staendematch.component';

describe('RankingListEasvWorldupComponent', () => {
  let component: RankingListEasvStaendematchComponent;
  let fixture: ComponentFixture<RankingListEasvStaendematchComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [RankingListEasvStaendematchComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RankingListEasvStaendematchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
