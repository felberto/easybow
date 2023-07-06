import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LiveEasvVerbaendefinalComponent } from './live-easv-verbaendefinal.component';

describe('ViewComponent', () => {
  let component: LiveEasvVerbaendefinalComponent;
  let fixture: ComponentFixture<LiveEasvVerbaendefinalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [LiveEasvVerbaendefinalComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(LiveEasvVerbaendefinalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
