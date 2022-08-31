import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LiveEasvStaendematchComponent } from './live-easv-staendematch.component';

describe('ViewComponent', () => {
  let component: LiveEasvStaendematchComponent;
  let fixture: ComponentFixture<LiveEasvStaendematchComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [LiveEasvStaendematchComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(LiveEasvStaendematchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
