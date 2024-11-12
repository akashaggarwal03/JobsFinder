import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { OverlayLoadingComponent } from '../Utils/overlay-loading/overlay-loading.component';
import { DropdownModule } from 'primeng/dropdown';
import { ButtonModule } from 'primeng/button';
import { CardModule } from 'primeng/card';
import { TIME_RANGES } from '../Utils/DateRanges';
import { TYPE_OF_QUESTION } from '../Utils/TypeOfQuestion';
import { COMPANIES } from '../Utils/companies';
import { JobFinderService } from '../Service/job-finder.service';
import { TableModule } from 'primeng/table';
import { MatTableModule } from '@angular/material/table';
import { PaginatorModule } from 'primeng/paginator';

interface PageEvent {
  first: number;
  rows: number;
  page: number;
  pageCount: number;
}

@Component({
  selector: 'app-company-wise-questions',
  standalone: true,
  imports: [
    ReactiveFormsModule, CommonModule, OverlayLoadingComponent,
    DropdownModule, ButtonModule, CardModule, MatTableModule ,CommonModule, TableModule, PaginatorModule
  ],
  templateUrl: './company-wise-questions.component.html',
  styleUrl: './company-wise-questions.component.scss'
})
export class CompanyWiseQuestionsComponent {

  isLoading: boolean = false;
  formGroup!: FormGroup;
  timeRanges = TIME_RANGES;
  typeofQuestion = TYPE_OF_QUESTION;
  companies= COMPANIES;

  questions: any[] = [];         // Holds all questions fetched from the API
  paginatedQuestions: any[] = []; // Holds questions for the current page
  totalRecords: number = 0;
  rowsPerPage: number = 10;
  first: number = 0;

  constructor(private jobFinderService: JobFinderService) {}

  ngOnInit() {
    this.formGroup = new FormGroup({
      selectedTimeRange: new FormControl(null),
      selectedTypeOfQuestion: new FormControl(null),
      selectedCompanyName: new FormControl(null)
    });
  }

  onSearch() {
    const selectedRange = this.formGroup?.get('selectedTimeRange')?.value;
    const selectedQuestionType = this.formGroup?.get('selectedTypeOfQuestion')?.value;
    const selectedCompanyName = this.formGroup?.get('selectedCompanyName')?.value;

    console.log('Selected time range:', selectedRange);
    console.log('Selected type of question:', selectedQuestionType);
    console.log('Selected Company Name:',selectedCompanyName);
    this.isLoading = true;


    this.jobFinderService.getQuestions(selectedCompanyName,"ANYTIME",selectedQuestionType?.value).subscribe(response => {
      this.questions = response;
      this.isLoading = false;
      this.totalRecords = this.questions.length;
      // this.paginate({ first: 0, rows: this.rowsPerPage }); // Initial pagination
    });


  }

  paginate(event: { first: number; rows: number }) {
    this.paginatedQuestions = this.questions.slice(event.first, event.first + event.rows);
  }

  onPageChange(event: PageEvent) {
    this.first = event.first;
    this.rowsPerPage = event.rows;
}

}
