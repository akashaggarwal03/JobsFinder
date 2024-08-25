import { Component } from '@angular/core';
import {FormControl, FormsModule, ReactiveFormsModule} from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';
import {MatButtonModule} from '@angular/material/button';



@Component({
  selector: 'find-Companies',
  standalone: true,
  imports: [MatFormFieldModule, MatSelectModule, FormsModule, ReactiveFormsModule, MatButtonModule, ],
  templateUrl: './find-companies.component.html',
  styleUrl: './find-companies.component.scss'
})
export class FindCompaniesComponent {

  options = new FormControl('');
  optionList: string[] = ['Anytime', 'Last Week', 'Last Month', 'Last Year'];

  onSearch() {
    console.log('Selected option:', this.options.value);
    // Implement your search logic here
  }
}
