import { Component, OnInit } from '@angular/core';
import { ChartDataSets, ChartOptions, ChartType } from 'chart.js';
import {CategoryService} from '../../category-list/category.service';
import {Observable} from 'rxjs';


@Component({
  selector: 'app-barChart',
  templateUrl: './barChart.component.html',
})
export class BarChartComponent implements OnInit {
  public barChartOptions: ChartOptions = {
    responsive: true,
    // We use these empty structures as placeholders for dynamic theming.
    scales: { xAxes: [{}], yAxes: [{}] },
    plugins: {
      datalabels: {
        anchor: 'end',
        align: 'end',
      }
    }
  };

  public barChartData: ChartDataSets[];
  public barChartLabels = [];
  public barChartColors : string[];
  public barChartType: ChartType = 'bar';
  public barChartLegend = true;

  constructor(public categoryService: CategoryService) { }

  ngOnInit(): void {
    this.categoryService.getCategoryLabels().subscribe(
      () => this.categoryService.getCategoryColors().subscribe(
        () => {this.getData()}
      )
    )
  }

  getCategoryLabels(){
    this.categoryService.getCategoryLabels().subscribe(
      res => {console.log(res) ; this.barChartLabels = res}
    )
  }
  getCategoryColors(){
    this.categoryService.getCategoryColors().subscribe(
      res => {console.log(res) ; this.barChartColors = res}
    )
    return this.barChartColors;
  }


  public getData(){
    this.barChartData = [{ data: [65, 59, 80, 81, 56, 55, 40], backgroundColor: this.barChartColors}]
  }
  // events
  public chartClicked({ event, active }: { event: MouseEvent, active: {}[] }): void {
    console.log(event, active);
  }

  public chartHovered({ event, active }: { event: MouseEvent, active: {}[] }): void {
    console.log(event, active);
  }

  public randomize(): void {
    // Only Change 3 values
    this.barChartData[0].data = [
      Math.round(Math.random() * 100),
      59,
      80,
      (Math.random() * 100),
      56,
      (Math.random() * 100),
      40 ];
  }


}
