import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CompetitionOverviewEasvWorldcupComponent } from './competition-overview-easv-worldcup.component';

describe('OverviewComponent', () => {
  let component: CompetitionOverviewEasvWorldcupComponent;
  let fixture: ComponentFixture<CompetitionOverviewEasvWorldcupComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CompetitionOverviewEasvWorldcupComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CompetitionOverviewEasvWorldcupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
