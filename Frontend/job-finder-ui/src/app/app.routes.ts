import { Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { FindCompaniesComponent } from './find-companies/find-companies.component';
import { CompanyWiseQuestionsComponent } from './company-wise-questions/company-wise-questions.component';

export const routes: Routes = [
  { path: '', redirectTo: '/menu', pathMatch: 'full' },
  { path: 'menu', component: HomeComponent},
  { path: 'find-companies', component: FindCompaniesComponent },
  {path:'app-company-wise-questions', component: CompanyWiseQuestionsComponent}
];

