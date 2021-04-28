import { Component, OnInit } from '@angular/core';
import {CategoryService} from '../../category-list/category.service';
import {Category} from "../../model/Category";
import {map} from 'rxjs/operators';
import {ChartOptions, ChartType} from "chart.js";
import {RadarChartService} from "./radar-chart.service";

@Component({
  selector: 'app-radar-chart',
  templateUrl: './radar-chart.component.html',
  styleUrls: []
})

export class RadarChartComponent implements OnInit {

  constructor(private categoryService : CategoryService, private radarChartService: RadarChartService) {
  }

  public radarChartData = [];
  public radarChartLabels = [];
  public chartType: ChartType = "radar";

  public radarChartOptions: ChartOptions = {
    responsive: true,
    legend: {display: false},
    title: {
      display: true,
      text: 'Number of tasks done by category'
    },
    scale: {
      ticks: {
        beginAtZero: true,
        min:0
      }
    }
  }

  ngOnInit() {
    this.radarChartService.getData().subscribe(
      data => {
        this.radarChartData = [
          {
            labels: this.radarChartLabels,
            data: data.data,
            backgroundColor: 'rgba(18, 162, 148, 0.2)',
            borderColor: "rgba(18, 162, 148, 0.8)",
            pointBackgroundColor: "rgba(18, 162, 148, 1)"
          }
        ];
        this.radarChartLabels = data.name;
        data.name = []
        data.data = []
      }
    )
  }


}
