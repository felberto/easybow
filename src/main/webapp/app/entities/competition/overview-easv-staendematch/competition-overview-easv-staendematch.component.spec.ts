import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CompetitionOverviewEasvStaendematchComponent } from './competition-overview-easv-staendematch.component';

describe('OverviewComponent', () => {
  let component: CompetitionOverviewEasvStaendematchComponent;
  let fixture: ComponentFixture<CompetitionOverviewEasvStaendematchComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CompetitionOverviewEasvStaendematchComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CompetitionOverviewEasvStaendematchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
