import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NewGroupSeriesDialog2Component } from './new-group-series-dialog-2.component';

describe('SeriesDialog2Component', () => {
  let component: NewGroupSeriesDialog2Component;
  let fixture: ComponentFixture<NewGroupSeriesDialog2Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [NewGroupSeriesDialog2Component],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NewGroupSeriesDialog2Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
