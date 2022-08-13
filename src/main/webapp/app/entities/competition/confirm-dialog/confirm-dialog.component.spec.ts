import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RankingListDialogComponent } from '../rankingList-dialog/rankingList-dialog.component';

describe('RankingListDialogComponent', () => {
  let component: RankingListDialogComponent;
  let fixture: ComponentFixture<RankingListDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [RankingListDialogComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RankingListDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
