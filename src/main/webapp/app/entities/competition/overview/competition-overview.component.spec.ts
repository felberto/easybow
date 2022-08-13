import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CompetitionOverviewComponent } from './competition-overview.component';

describe('OverviewComponent', () => {
  let component: CompetitionOverviewComponent;
  let fixture: ComponentFixture<CompetitionOverviewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CompetitionOverviewComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CompetitionOverviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
