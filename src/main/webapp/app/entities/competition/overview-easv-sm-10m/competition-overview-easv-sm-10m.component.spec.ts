import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CompetitionOverviewEasvSm10mComponent } from './competition-overview-easv-sm-10m.component';

describe('OverviewComponent', () => {
  let component: CompetitionOverviewEasvSm10mComponent;
  let fixture: ComponentFixture<CompetitionOverviewEasvSm10mComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CompetitionOverviewEasvSm10mComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CompetitionOverviewEasvSm10mComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
