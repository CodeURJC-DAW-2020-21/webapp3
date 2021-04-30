import { Component, OnInit } from '@angular/core';
import { CategoryListService } from '../../category-list/category-list.service';
import { ChartOptions, ChartType } from "chart.js";
import { DoughnutChartService } from "./doughnut-chart.service";

@Component({
  selector: 'app-doughnut-chart',
  templateUrl: './doughnut-chart.component.html',
  styleUrls: []
})

export class DoughnutChartComponent implements OnInit {

  constructor(private categoryService : CategoryListService, private doughnutChartService: DoughnutChartService) {
  }

  public doughnutChartData = [];
  public doughnutChartLabels = [];
  public legend: boolean = true;
  public chartType: ChartType = "doughnut";

  public doughnutChartOptions: ChartOptions = {
    responsive: true,
    title: {
      display: true,
      text: 'Number of likes given by the user for categories'
    }
  }

  ngOnInit(): void {
    this.doughnutChartService.getData().subscribe(
      data => {
        this.doughnutChartData = [
          {
            labels:this.doughnutChartLabels,
            data: data.data,
            backgroundColor: data.color,
          }
        ];
        this.doughnutChartLabels = data.name;
        data.data = []
        data.name = []
        data.color = []
      }
    )
  }

}
