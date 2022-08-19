import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CompetitionOverviewZsavNawuGmComponent } from './competition-overview-zsav-nawu-gm.component';

describe('OverviewComponent', () => {
  let component: CompetitionOverviewZsavNawuGmComponent;
  let fixture: ComponentFixture<CompetitionOverviewZsavNawuGmComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CompetitionOverviewZsavNawuGmComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CompetitionOverviewZsavNawuGmComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
