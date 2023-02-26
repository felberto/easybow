import { ComponentFixture, TestBed } from '@angular/core/testing';
import { SeriesDialog2GroupComponent } from './series-dialog-2.component';

describe('SeriesDialog2GroupComponent', () => {
  let component: SeriesDialog2GroupComponent;
  let fixture: ComponentFixture<SeriesDialog2GroupComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [SeriesDialog2GroupComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SeriesDialog2GroupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
