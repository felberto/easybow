import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CompetitionOverviewEasvNawuGmComponent } from './competition-overview-easv-nawu-gm.component';

describe('OverviewComponent', () => {
  let component: CompetitionOverviewEasvNawuGmComponent;
  let fixture: ComponentFixture<CompetitionOverviewEasvNawuGmComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CompetitionOverviewEasvNawuGmComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CompetitionOverviewEasvNawuGmComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
