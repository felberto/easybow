import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SeriesDialog2Component } from './series-dialog-2.component';

describe('SeriesDialog2Component', () => {
  let component: SeriesDialog2Component;
  let fixture: ComponentFixture<SeriesDialog2Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [SeriesDialog2Component],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SeriesDialog2Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
