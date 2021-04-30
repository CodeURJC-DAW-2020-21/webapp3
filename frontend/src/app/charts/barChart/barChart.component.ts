import { Component, OnInit } from '@angular/core';
import { ChartOptions, ChartType } from 'chart.js';
import { CategoryListService } from '../../category-list/category-list.service';
import { BarChartService } from "./bar-chart.service";
import {generate} from "rxjs";

@Component({
  selector: 'app-barChart',
  templateUrl: './barChart.component.html',
  styleUrls: []
})

export class BarChartComponent implements OnInit {

  public barChartData = [];
  public barChartLabels = [];
  public chartType: ChartType = "bar";

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

  constructor(private categoryService : CategoryListService, private barChartService: BarChartService) {
  }

  ngOnInit() {
    this.generateChart()
  }

  public generateChart(){
    this.barChartService.getData().subscribe(
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
