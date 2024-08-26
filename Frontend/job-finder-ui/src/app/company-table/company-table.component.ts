import { Component, Input } from '@angular/core';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'company-table',
  standalone: true,
  templateUrl: './company-table.component.html',
  styleUrls: ['./company-table.component.scss'],
  imports: [ MatTableModule ,CommonModule ],
})
export class CompanyTableComponent  
 {
  @Input() companyData: any[] = [];
 // displayedColumns: string[] = ['numberOfPosts'];
  dataSource = new MatTableDataSource<any>([]);

  ngOnInit() {
    this.dataSource.data = this.companyData;
  }
}
