import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CompanyRequestComponent } from './company-request.component';

describe('CompanyRequestComponent', () => {
  let component: CompanyRequestComponent;
  let fixture: ComponentFixture<CompanyRequestComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CompanyRequestComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CompanyRequestComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
