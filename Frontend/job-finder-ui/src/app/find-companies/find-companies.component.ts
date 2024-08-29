import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { JobFinderService } from '../Service/job-finder.service';
import { CompanyTableComponent } from '../company-table/company-table.component';
import { TIME_RANGES } from '../Utils/DateRanges';
import { CommonModule } from '@angular/common';
import { OverlayLoadingComponent } from '../Utils/overlay-loading/overlay-loading.component';
import { DropdownModule } from 'primeng/dropdown';
import { ButtonModule } from 'primeng/button';
import { CardModule } from 'primeng/card';

@Component({
  selector: 'find-companies',
  standalone: true,
  imports: [
    ReactiveFormsModule, CommonModule, CompanyTableComponent, OverlayLoadingComponent,
    DropdownModule, ButtonModule, CardModule
  ],
  templateUrl: './find-companies.component.html',
  styleUrls: ['./find-companies.component.scss']
})
export class FindCompaniesComponent implements OnInit {
  formGroup!: FormGroup;
  timeRanges = TIME_RANGES;
  companiesData: any[] = [];
  isLoading: boolean = false;

  constructor(private jobFinderService: JobFinderService) {}

  ngOnInit() {
    this.formGroup = new FormGroup({
      selectedTimeRange: new FormControl(null)
    });
  }

  onSearch() {
    const selectedRange = this.formGroup?.get('selectedTimeRange')?.value;
    console.log('Selected option:', selectedRange);
    this.isLoading = true;
    this.companiesData = [];

    this.jobFinderService.getJobs(selectedRange?.value || "").subscribe(response => {
      this.companiesData = response;
      console.log('Job listings:', response);
      this.isLoading = false;
    });
  }
}

