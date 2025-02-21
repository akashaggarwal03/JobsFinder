import { Component , OnInit} from '@angular/core';
import { ImportsModule } from '../Utils/import';
import { CommonModule } from '@angular/common';
import { MenuItem } from 'primeng/api';

@Component({
  selector: 'app-menu',
  standalone: true,
  imports: [ImportsModule, CommonModule],
  templateUrl: './menu.component.html',
  styleUrl: './menu.component.scss'
})
export class MenuComponent implements OnInit {

  items: MenuItem[] | undefined;

    ngOnInit() {
        this.items = [
            {
                label: 'Home',
                icon: 'pi pi-home',
                routerLink: ['/menu']
            },
            {
                label: 'Features',
                icon: 'pi pi-search',
                items: [
                    {
                        label: 'Find Companies Hiring',
                        icon: 'pi pi-bolt',
                        routerLink: ['/find-companies']
                    },
                    {
                        label: 'Compnay Wise Questions',
                        icon: 'pi pi-server',
                        // routerLink: ['/app-company-wise-questions']
                    },
                    {
                        label: 'System Design',
                        icon: 'pi pi-pencil'
                    },
                    {
                        separator: true
                    }
                ]
            },
            {
                label: 'Contact',
                icon: 'pi pi-envelope',
                routerLink: '/contact'
            }
        ];

      }


}
