import { Component, EnvironmentInjector } from '@angular/core';
import {FormControl, FormsModule, ReactiveFormsModule} from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';
import {MatButtonModule} from '@angular/material/button';
import { JobFinderService } from '../Service/job-finder.service';
import {MatTableModule} from '@angular/material/table';
import { CompanyTableComponent } from '../company-table/company-table.component';
import { TIME_RANGES } from '../Utils/DateRanges';
import { CommonModule } from '@angular/common';
import { OverlayLoadingComponent } from '../Utils/overlay-loading/overlay-loading.component';

@Component({
  selector: 'find-Companies',
  standalone: true,
  imports: [MatFormFieldModule,
    MatSelectModule, FormsModule, ReactiveFormsModule, MatButtonModule, MatTableModule,
    CompanyTableComponent, CommonModule, OverlayLoadingComponent],
  templateUrl: './find-companies.component.html',
  styleUrl: './find-companies.component.scss'
})
export class FindCompaniesComponent {

  constructor(private jobFinderService: JobFinderService) {}

  options = new FormControl('');
  optionList = TIME_RANGES;
  companiesData: any[] = [];
  isLoading: boolean = false;



  onSearch() {
    console.log('Selected option:', this.options.value);
    this.isLoading = true;
    this.companiesData = [];
    // Implement your search logic here
    this.jobFinderService.getJobs(this.options.value || "").subscribe(response => {
      this.companiesData = response;
      console.log('Job listings:', response);
      this.isLoading =false;
    });
  }
}
