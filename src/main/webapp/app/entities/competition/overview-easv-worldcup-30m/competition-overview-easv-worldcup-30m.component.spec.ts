import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CompetitionOverviewEasvWorldcup30mComponent } from './competition-overview-easv-worldcup-30m.component';

describe('OverviewComponent', () => {
  let component: CompetitionOverviewEasvWorldcup30mComponent;
  let fixture: ComponentFixture<CompetitionOverviewEasvWorldcup30mComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CompetitionOverviewEasvWorldcup30mComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CompetitionOverviewEasvWorldcup30mComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
