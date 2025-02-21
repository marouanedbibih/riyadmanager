import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RoomMainComponent } from './room-main.component';

describe('RoomMainComponent', () => {
  let component: RoomMainComponent;
  let fixture: ComponentFixture<RoomMainComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [RoomMainComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RoomMainComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
