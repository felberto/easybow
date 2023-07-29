import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LiveEasvWorldcup30mComponent } from './live-easv-worldcup-30m.component';

describe('ViewComponent', () => {
  let component: LiveEasvWorldcup30mComponent;
  let fixture: ComponentFixture<LiveEasvWorldcup30mComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [LiveEasvWorldcup30mComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(LiveEasvWorldcup30mComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
