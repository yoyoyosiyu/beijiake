import { Component, OnInit } from '@angular/core';
import {CategoryService} from "../services/category.service";
import {HttpErrorResponse} from "@angular/common/http";

@Component({
  selector: 'app-about',
  templateUrl: './about.component.html',
  styleUrls: ['./about.component.css']
})
export class AboutComponent implements OnInit {

  constructor(private categoryService: CategoryService) { }

  ngOnInit() {
    this.categoryService.getCategory(1).subscribe((data) => {
      console.log(data);
    }, (error: HttpErrorResponse) => {
      console.log(error)
    })
  }

}
