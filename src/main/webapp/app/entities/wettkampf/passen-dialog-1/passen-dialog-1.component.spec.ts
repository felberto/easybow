import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PassenDialog1Component } from './passen-dialog-1.component';

describe('PassenDialog1Component', () => {
  let component: PassenDialog1Component;
  let fixture: ComponentFixture<PassenDialog1Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [PassenDialog1Component],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PassenDialog1Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
