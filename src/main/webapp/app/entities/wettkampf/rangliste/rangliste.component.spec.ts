import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RanglisteComponent } from './rangliste.component';

describe('RanglisteComponent', () => {
  let component: RanglisteComponent;
  let fixture: ComponentFixture<RanglisteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [RanglisteComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RanglisteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
