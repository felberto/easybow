import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NewGroupSeriesDialog1Component } from './new-group-series-dialog-1.component';

describe('SeriesDialog2Component', () => {
  let component: NewGroupSeriesDialog1Component;
  let fixture: ComponentFixture<NewGroupSeriesDialog1Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [NewGroupSeriesDialog1Component],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NewGroupSeriesDialog1Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
