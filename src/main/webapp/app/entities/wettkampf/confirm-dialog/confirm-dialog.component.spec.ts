import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RanglisteDialogComponent } from './confirm-dialog.component';

describe('RanglisteDialogComponent', () => {
  let component: RanglisteDialogComponent;
  let fixture: ComponentFixture<RanglisteDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [RanglisteDialogComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RanglisteDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
