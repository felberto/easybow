import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AthleteNumberDialogComponent } from './athlete-number-dialog.component';

describe('ResultDialogComponent', () => {
  let component: AthleteNumberDialogComponent;
  let fixture: ComponentFixture<AthleteNumberDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AthleteNumberDialogComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AthleteNumberDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
