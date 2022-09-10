import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LiveEasvNawuGmComponent } from './live-easv-nawu-gm.component';

describe('ViewComponent', () => {
  let component: LiveEasvNawuGmComponent;
  let fixture: ComponentFixture<LiveEasvNawuGmComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [LiveEasvNawuGmComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(LiveEasvNawuGmComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
