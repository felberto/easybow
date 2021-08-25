import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PassenDialogComponent } from './passen-dialog.component';

describe('PassenDialogComponent', () => {
  let component: PassenDialogComponent;
  let fixture: ComponentFixture<PassenDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [PassenDialogComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PassenDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
