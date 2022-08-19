import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LiveZsavNawuGmComponent } from './live-zsav-nawu-gm.component';

describe('ViewComponent', () => {
  let component: LiveZsavNawuGmComponent;
  let fixture: ComponentFixture<LiveZsavNawuGmComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [LiveZsavNawuGmComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(LiveZsavNawuGmComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
