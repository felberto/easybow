import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WettkampfOverviewComponent } from './wettkampf-overview.component';

describe('OverviewComponent', () => {
  let component: WettkampfOverviewComponent;
  let fixture: ComponentFixture<WettkampfOverviewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [WettkampfOverviewComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(WettkampfOverviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
