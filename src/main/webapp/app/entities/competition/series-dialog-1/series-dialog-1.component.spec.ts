import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SeriesDialog1Component } from './series-dialog-1.component';

describe('SeriesDialog1Component', () => {
  let component: SeriesDialog1Component;
  let fixture: ComponentFixture<SeriesDialog1Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [SeriesDialog1Component],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SeriesDialog1Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
