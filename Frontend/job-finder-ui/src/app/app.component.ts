import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { FindCompaniesComponent } from './find-companies/find-companies.component';
import { MenuComponent } from './menu/menu.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, FindCompaniesComponent, MenuComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'job-finder-ui';
}
