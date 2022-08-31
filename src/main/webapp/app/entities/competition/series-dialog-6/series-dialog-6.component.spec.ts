import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SeriesDialog6Component } from './series-dialog-6.component';

describe('SeriesDialog2Component', () => {
  let component: SeriesDialog6Component;
  let fixture: ComponentFixture<SeriesDialog6Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [SeriesDialog6Component],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SeriesDialog6Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
