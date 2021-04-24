import { Component, OnInit } from '@angular/core';
import {CategoryService} from '../../category-list/category.service';

@Component({
  selector: 'app-doughnut-chart',
  templateUrl: './doughnut-chart.component.html',
  styleUrls: []
})
export class DoughnutChartComponent implements OnInit {

  constructor(private categoryService : CategoryService) {
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
