import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  constructor(private http: HttpClient) { }

  public getCategories() {
    return this.http.get("/api/categories")
  }

  public getCategory(id) {
    return this.http.get(`/api/categories/${id}`);
  }

  public createNewCategory(parentId, name) {
    if (parentId == 0) {
      return this.http.post('/api/categories', {name: name});
    }
    else {
      return this.http.post(`/api/categories/${parentId}`, [{name: name}]);
    }
  }
}
