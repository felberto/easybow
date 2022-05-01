import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ResultateDialogComponent } from './resultate-dialog.component';

describe('ResultateDialogComponent', () => {
  let component: ResultateDialogComponent;
  let fixture: ComponentFixture<ResultateDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ResultateDialogComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ResultateDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
