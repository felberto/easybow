import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LiveEasvWorldcupComponent } from './live-easv-worldcup.component';

describe('ViewComponent', () => {
  let component: LiveEasvWorldcupComponent;
  let fixture: ComponentFixture<LiveEasvWorldcupComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [LiveEasvWorldcupComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(LiveEasvWorldcupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
