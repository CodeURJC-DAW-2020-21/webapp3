import { Component, OnInit } from '@angular/core';
import {CategoryService} from '../../category-list/category.service';

@Component({
  selector: 'app-doughnut-chart',
  templateUrl: './doughnut-chart.component.html',
  styleUrls: []
})
export class DoughnutChartComponent implements OnInit {

  public categoryLabels: string[] = this.getCategoryLabels();

  ngOnInit(): void {
    this.getCategoryLabels();
    this.getCategoryColors();
  }

  getCategoryLabels() : string[]{
    this.categoryService.getCategoryLabels().subscribe(
      label => this.categoryLabels = label,
      error => console.log(error)
    );
    return this.categoryLabels;
  }

  getCategoryColors() : string[]{
    this.categoryService.getCategoryColors().subscribe(
      color => this.categoryLabels = color,
      error => console.log(error)
    );
    return this.categoryLabels;
  }

  constructor(private categoryService : CategoryService) {
  }

  public doughnutChartOptions = {
    bezierCurve: false,
    responsive: true,
    backgroundColor: this.getCategoryColors(),
    title: {
      display: true,
      text: 'Number of likes given by the user for categories'
    }
  }


  public doughnutChartData = [
    {data: [65,59,80,81,56, 20], label : 'Series A'},
  ];

}
