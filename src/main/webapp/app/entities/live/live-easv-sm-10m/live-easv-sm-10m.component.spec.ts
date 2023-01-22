import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LiveEasvSm10mComponent } from './live-easv-sm-10m.component';

describe('ViewComponent', () => {
  let component: LiveEasvSm10mComponent;
  let fixture: ComponentFixture<LiveEasvSm10mComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [LiveEasvSm10mComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(LiveEasvSm10mComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
