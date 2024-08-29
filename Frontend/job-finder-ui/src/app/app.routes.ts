import { Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { FindCompaniesComponent } from './find-companies/find-companies.component';

export const routes: Routes = [
  { path: '', redirectTo: '/menu', pathMatch: 'full' },
  { path: 'menu', component: HomeComponent},
  { path: 'find-companies', component: FindCompaniesComponent }
];

