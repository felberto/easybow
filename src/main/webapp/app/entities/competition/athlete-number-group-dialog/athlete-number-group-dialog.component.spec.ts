import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AthleteNumberGroupDialogComponent } from './athlete-number-group-dialog.component';

describe('ResultDialogComponent', () => {
  let component: AthleteNumberGroupDialogComponent;
  let fixture: ComponentFixture<AthleteNumberGroupDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AthleteNumberGroupDialogComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AthleteNumberGroupDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
