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
    const url = `https://sincere-contentment-production.up.railway.app/api/findJobs/${range}`;
    return this.http.get(url);
  }

  getQuestions(companyName: String,range:String, questionType: String): Observable<any> {
    const url = `https://sincere-contentment-production.up.railway.app/api/questions/${companyName}/${range}/${questionType}`;
    return this.http.get(url);
  }
}
