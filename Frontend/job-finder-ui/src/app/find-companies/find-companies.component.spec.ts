import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FindCompaniesComponent } from './find-companies.component';

describe('FindCompaniesComponent', () => {
  let component: FindCompaniesComponent;
  let fixture: ComponentFixture<FindCompaniesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FindCompaniesComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FindCompaniesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
