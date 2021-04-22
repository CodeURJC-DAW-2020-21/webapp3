import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ExplorePlanPaginationComponent } from './explore-plan-pagination.component';

describe('ExplorePlanPaginationComponent', () => {
  let component: ExplorePlanPaginationComponent;
  let fixture: ComponentFixture<ExplorePlanPaginationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ExplorePlanPaginationComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ExplorePlanPaginationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
