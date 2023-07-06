import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CompetitionOverviewEasvVerbaendefinalComponent } from './competition-overview-easv-verbaendefinal.component';

describe('OverviewComponent', () => {
  let component: CompetitionOverviewEasvVerbaendefinalComponent;
  let fixture: ComponentFixture<CompetitionOverviewEasvVerbaendefinalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CompetitionOverviewEasvVerbaendefinalComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CompetitionOverviewEasvVerbaendefinalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
