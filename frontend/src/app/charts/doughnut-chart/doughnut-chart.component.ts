import { Component, OnInit } from '@angular/core';
import {CategoryService} from '../../category-list/category.service';
import {ChartType} from "chart.js";

@Component({
  selector: 'app-doughnut-chart',
  templateUrl: './doughnut-chart.component.html',
  styleUrls: []
})
export class DoughnutChartComponent implements OnInit {

  constructor(private categoryService : CategoryService) {
  }

  public categoryLabels: string[] = this.getCategoryLabels();
  public categoryColors: string[] = this.getCategoryColors();
  public legend: boolean = true;
  public chartType: ChartType = "doughnut";

  getCategoryLabels() : string[]{
    this.categoryService.getCategoryLabels().subscribe(
      label => this.categoryLabels = label,
      error => console.log(error)
    );
    return this.categoryLabels;
  }

  getCategoryColors() : string[]{
    this.categoryService.getCategoryColors().subscribe(
      color => this.categoryColors = color,
      error => console.log(error)
    );
    return this.categoryColors
  }

  public doughnutChartOptions = {
    bezierCurve: false,
    responsive: true,
    title: {
      display: true,
      text: 'Number of likes given by the user for categories'
    }
  }

  public doughnutChartData = [
    {data: [65,59,80,81,56, 20]},
  ];

  ngOnInit(): void {
  }

}
