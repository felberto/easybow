import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PassenDialog2Component } from './passen-dialog-2.component';

describe('PassenDialog2Component', () => {
  let component: PassenDialog2Component;
  let fixture: ComponentFixture<PassenDialog2Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [PassenDialog2Component],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PassenDialog2Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
