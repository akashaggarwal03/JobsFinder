import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class JobFinderService {

  constructor(private http: HttpClient) { }

  getJobs(range: String): Observable<any> {
    console.log("Got input for range:" + range);
    const url = `http://localhost:8080/api/findJobs/${range}`;
    return this.http.get(url);
  }
}
