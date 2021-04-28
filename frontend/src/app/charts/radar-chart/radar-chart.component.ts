import { Component, OnInit } from '@angular/core';
import {CategoryService} from '../../service/category.service';
import {Category} from "../../model/Category";
import {map} from 'rxjs/operators';

@Component({
  selector: 'app-radar-chart',
  templateUrl: './radar-chart.component.html',
  styleUrls: []
})
export class RadarChartComponent implements OnInit {

  public categoryLabels: string[] = this.getCategoryLabels();

  ngOnInit(): void {
  }

  getCategoryLabels() : string[]{
    this.categoryService.getCategoryLabels().subscribe(
      label => this.categoryLabels = label,
      error => console.log(error)
    );
    return this.categoryLabels;
  }
  constructor(private categoryService : CategoryService) {
  }

  public radarChartOptions = {
    bezierCurve: false,
    responsive: true,
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

  public radarChartLabels = ['Q1', 'Q2', 'Q3', 'Q4']

  public radarChartData = [
    {data : [10,15,12,3],
      label : this.getCategoryLabels(),
      backgroundColor: 'rgba(18, 162, 148, 0.2)',
      borderColor: "rgba(18, 162, 148, 0.8)",
      pointBackgroundColor: "rgba(18, 162, 148, 1)"
    }
  ]
}
