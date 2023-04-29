import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NewSeriesDialog1Component } from './new-series-dialog-1.component';

describe('SeriesDialog2Component', () => {
  let component: NewSeriesDialog1Component;
  let fixture: ComponentFixture<NewSeriesDialog1Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [NewSeriesDialog1Component],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NewSeriesDialog1Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
