import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GroupDialogComponent } from './group-dialog.component';

describe('ResultDialogComponent', () => {
  let component: GroupDialogComponent;
  let fixture: ComponentFixture<GroupDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [GroupDialogComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(GroupDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
