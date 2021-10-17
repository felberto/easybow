import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GenerateRanglisteDialogComponent } from './generate-rangliste-dialog.component';

describe('GenerateRanglisteDialogComponent', () => {
  let component: GenerateRanglisteDialogComponent;
  let fixture: ComponentFixture<GenerateRanglisteDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [GenerateRanglisteDialogComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(GenerateRanglisteDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
