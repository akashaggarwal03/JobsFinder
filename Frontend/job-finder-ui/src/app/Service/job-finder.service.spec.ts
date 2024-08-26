import { TestBed } from '@angular/core/testing';

import { JobFinderService } from './job-finder.service';

describe('JobFinderService', () => {
  let service: JobFinderService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(JobFinderService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
