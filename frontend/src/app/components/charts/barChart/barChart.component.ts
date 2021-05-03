import { Component, OnInit } from '@angular/core';
import { ChartOptions, ChartType } from 'chart.js';
import { CategoryService } from '../../../service/category.service';
import { BarChartService } from "../../../service/charts/bar-chart.service";

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

  constructor(private categoryService : CategoryService, private barChartService: BarChartService) {
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
