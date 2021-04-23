import { Component, OnInit } from '@angular/core';
import {CategoryService} from '../../category-list/category.service';

@Component({
  selector: 'app-barChart',
  templateUrl: './barChart.component.html',
  styleUrls: []
})
export class BarChartComponent implements OnInit {

  public categoryLabels: string[] = this.getCategoryLabels();

  ngOnInit(): void {
    this.getCategoryLabels();
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

  public barChartOptions = {
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
  }

  public barChartData = [
    {data: [65,59,80,81,56,10]}
  ];


}
