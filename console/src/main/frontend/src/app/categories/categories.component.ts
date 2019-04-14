import { Component, OnInit } from '@angular/core';
import {CategoryService} from "../services/category.service";
import {NzFormatEmitEvent} from "ng-zorro-antd";
import {ActivatedRoute, ParamMap} from "@angular/router";
import {switchMap} from "rxjs/operators";

class Category {
  id: number;
  name: string;
  inheritAttribute: boolean;
  children: Category[];
}

class TreeNode {
  title: string;
  key: string;
  expaned: boolean;
  children: TreeNode[];
  isLeaf: boolean;
}

@Component({
  selector: 'app-categories',
  templateUrl: './categories.component.html',
  styleUrls: ['./categories.component.css']
})
export class CategoriesComponent implements OnInit {

  listOfSelection = [
    {
      text: 'Select All Row',
      onSelect: () => {
        this.checkAll(true);
      }
    },
    {
      text: 'Select Odd Row',
      onSelect: () => {
        this.listOfDisplayData.forEach((data, index) => (this.mapOfCheckedId[data.id] = index % 2 !== 0));
        this.refreshStatus();
      }
    },
    {
      text: 'Select Even Row',
      onSelect: () => {
        this.listOfDisplayData.forEach((data, index) => (this.mapOfCheckedId[data.id] = index % 2 === 0));
        this.refreshStatus();
      }
    }
  ];

  isAllDisplayDataChecked = false;
  isIndeterminate = false;
  listOfDisplayData: any[] = [];
  listOfAllData: any[] = [];
  mapOfCheckedId: { [key: string]: boolean } = {};

  currentPageDataChange($event: Array<{ id: number; name: string; age: number; address: string }>): void {
    this.listOfDisplayData = $event;
    this.refreshStatus();
  }

  refreshStatus(): void {
    this.isAllDisplayDataChecked = this.listOfDisplayData.every(item => this.mapOfCheckedId[item.id]);
    this.isIndeterminate =
      this.listOfDisplayData.some(item => this.mapOfCheckedId[item.id]) && !this.isAllDisplayDataChecked;
  }

  checkAll(value: boolean): void {
    this.listOfDisplayData.forEach(item => (this.mapOfCheckedId[item.id] = value));
    this.refreshStatus();
  }

    nodes = new Array<TreeNode>();

    newCategoryName: string;

    isModalVisible = false;

    selectedId: number = 0;

  constructor(private categoryService: CategoryService, private activeRoute: ActivatedRoute) { }

  ngOnInit() {


    this.activeRoute.paramMap.pipe(
      switchMap((params: ParamMap) => {

        if (params.has('id')) {
          // (+) before `params.get()` turns the string into a number
          this.selectedId = +params.get('id');
          return this.categoryService.getCategory(this.selectedId);
        }
        else {
          return this.categoryService.getCategories();
        }
    })).subscribe((data: any) => {
      console.log(data);

      if (Array.isArray(data)) {
        this.listOfAllData = data;
      }
      else {
        this.listOfAllData = (data as Category).children;
      }

    });

    // for (let i = 0; i < 100; i++) {
    //   this.listOfAllData.push({
    //     id: i,
    //     name: `Edward King ${i}`,
    //     age: 32,
    //     address: `London, Park Lane no. ${i}`
    //   });
    // }

    // this.categoryService.getCategories().subscribe((data: CategoryView[])=> {
    //
    //   console.log(data);
    //
    //   this.listOfAllData = data;
    //
    //   console.log(this.listOfAllData);
    //
    //   //let treeNode = this.createTreeNode(data);
    //   //this.nodes = treeNode.children;
    //
    //   //console.log(this.nodes)
    //
    // })

  }

  createTreeNode(category: Category) : TreeNode {

    let treeNode = new TreeNode();

    treeNode.key = category.id.toString();
    treeNode.title = category.name;

    if (category.children.length > 0) {
      treeNode.isLeaf = false;
      treeNode.children = new Array<TreeNode>();

      for (let child of category.children) {
        let node = this.createTreeNode(child)
        treeNode.children.push(node);
      }
    }
    else {
      treeNode.isLeaf = true;
    }

    return treeNode;


  }

  showModal() {
    this.isModalVisible = true;
  }

  handleOk() {

    this.categoryService.createNewCategory(this.selectedId, this.newCategoryName).subscribe(
      (data) => {
        console.log(data);
        if (Array.isArray(data) && data.length > 0) {
          this.listOfAllData.push(data[0]);
        }
        else {
          this.listOfAllData.push(data);
        }

        this.newCategoryName = "";
        this.isModalVisible = false;
      },
      (error) => {
        console.log(error);
        this.isModalVisible = false;
      }
    )


  }

  handleCancel() {
    this.isModalVisible = false;
  }

  nzClickEvent(event: NzFormatEmitEvent): void {
    console.log(event);
  }

}
