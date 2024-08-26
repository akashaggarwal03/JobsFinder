import { Component, EnvironmentInjector } from '@angular/core';
import {FormControl, FormsModule, ReactiveFormsModule} from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';
import {MatButtonModule} from '@angular/material/button';
import { JobFinderService } from '../Service/job-finder.service';
import {MatTableModule} from '@angular/material/table';
import { CompanyTableComponent } from '../company-table/company-table.component';

@Component({
  selector: 'find-Companies',
  standalone: true,
  imports: [MatFormFieldModule, MatSelectModule, FormsModule, ReactiveFormsModule, MatButtonModule, MatTableModule, CompanyTableComponent],
  templateUrl: './find-companies.component.html',
  styleUrl: './find-companies.component.scss'
})
export class FindCompaniesComponent {

  constructor(private jobFinderService: JobFinderService) {}

  options = new FormControl('');
  optionList: string[] = ['Anytime', 'Last Week', 'Last Month', 'Last Year'];
  companiesData: any[] = [];



  onSearch() {
    console.log('Selected option:', this.options.value);
    // Implement your search logic here
    this.jobFinderService.getJobs(this.options.value || "").subscribe(response => {
      this.companiesData = response;
      console.log('Job listings:', response);
    });
  }
}
