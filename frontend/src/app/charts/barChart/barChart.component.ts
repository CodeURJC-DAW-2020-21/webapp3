import { Component, OnInit } from '@angular/core';
import {ChartData} from "../../model/ChartData";
import { ChartDataSets, ChartOptions, ChartType } from 'chart.js';
import {CategoryService} from '../../category-list/category.service';
import {BarChartService} from "./bar-chart.service";
import {map} from "rxjs/operators";

@Component({
  selector: 'app-barChart',
  templateUrl: './barChart.component.html'
})

export class BarChartComponent implements OnInit {

  public barChartData = [];
  public barChartLabels = [];

  public barChartOptions: ChartOptions = {
    scales:{
      yAxes: [{
        ticks: {
          beginAtZero: true,
          min:0
        }
      }]
    },
    legend: {display: false},
    title: {
      display: true,
      text: "Tree height for each category"
    },
    responsive: true
  };

  constructor(private categoryService : CategoryService, private chartService: BarChartService) {
  }

  ngOnInit() {
    this.chartService.getData().subscribe(
      data => {
        this.barChartData = [
          { labels:this.barChartLabels,
            data : data.data,
            backgroundColor: data.color,
          }];
        this.barChartLabels = data.name;
        data.name = []
        data.data = []
        data.color = []
      }
    )
  }

  // events
  public chartClicked({ event, active }: { event: MouseEvent, active: {}[] }): void {
    console.log(event, active);
  }

  public chartHovered({ event, active }: { event: MouseEvent, active: {}[] }): void {
    console.log(event, active);
  }


}
