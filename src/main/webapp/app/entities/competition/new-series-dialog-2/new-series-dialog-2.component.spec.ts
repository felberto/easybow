import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NewSeriesDialog2Component } from './new-series-dialog-2.component';

describe('SeriesDialog2Component', () => {
  let component: NewSeriesDialog2Component;
  let fixture: ComponentFixture<NewSeriesDialog2Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [NewSeriesDialog2Component],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NewSeriesDialog2Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
