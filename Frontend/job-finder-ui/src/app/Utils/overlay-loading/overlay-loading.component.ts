import { Component, Input } from '@angular/core';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-overlay-loading',
  standalone: true,
  imports: [ MatProgressSpinnerModule, CommonModule],
  templateUrl: './overlay-loading.component.html',
  styleUrl: './overlay-loading.component.scss'
})
export class OverlayLoadingComponent {

  @Input() show: boolean = false;
}
