import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SeriesDialog4Component } from './series-dialog-4.component';

describe('SeriesDialog2Component', () => {
  let component: SeriesDialog4Component;
  let fixture: ComponentFixture<SeriesDialog4Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [SeriesDialog4Component],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SeriesDialog4Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
