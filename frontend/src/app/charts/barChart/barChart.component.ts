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

 data: ChartData[];


  public barChartType: ChartType = 'bar';
  public barChartLegend = true;
  /*public barChartData:any = [
    {data : [10,15,12,3],
      label : this.getCategoryLabels(),
      backgroundColor: this.barChartColors,
      borderColor: this.barChartColors,
      pointBackgroundColor: "rgb(18, 162, 141)"
    }
  ];*/
  public barChartOptions: ChartOptions = {
    responsive: true,
    title: {
      display: true,
      text: 'Tree height for each category'
    },
    // We use these empty structures as placeholders for dynamic theming.
    scales: { xAxes: [{}], yAxes: [{}] },
    plugins: {
      datalabels: {
        anchor: 'end',
        align: 'end',
      }
    }
  };

  constructor(private categoryService : CategoryService, private chartService: BarChartService) {
  }

  ngOnInit() {
      this.chartService.getData().subscribe(
        data => this.data.
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
