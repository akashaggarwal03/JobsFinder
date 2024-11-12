import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CompanyWiseQuestionsComponent } from './company-wise-questions.component';

describe('CompanyWiseQuestionsComponent', () => {
  let component: CompanyWiseQuestionsComponent;
  let fixture: ComponentFixture<CompanyWiseQuestionsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CompanyWiseQuestionsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CompanyWiseQuestionsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
