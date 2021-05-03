import { Component, OnInit, ViewChild } from '@angular/core';
import { UserService } from "../../service/user.service";
import { Router } from "@angular/router";
import { Title } from '@angular/platform-browser';
import { ImageService } from "../../service/image.service";
import { BarChartComponent } from "../charts/barChart/barChart.component";
import { RadarChartComponent } from "../charts/radar-chart/radar-chart.component";
import * as html2pdf from 'html2pdf.js';


@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['../../../assets/css/profileStylesheet.css', "../../../assets/vendor/font-awesome/css/all.css"]
})
export class ProfileComponent implements OnInit {

  cardHeader;
  item;
  administratorColor;
  formName;
  formEmail;
  email: string
  username: string
  name: string
  surname: string
  admin: boolean
  img;

  constructor(public authorization: UserService, private router: Router, private titleService: Title, private imgService: ImageService) { }

  @ViewChild(BarChartComponent) barChart
  @ViewChild(RadarChartComponent) radarChart

  public setTitle(newTitle: string) {
    this.titleService.setTitle(newTitle);
  }

  ngOnInit(): void {
    this.setTitle("Growing - Profile")
    this.authorization.getUserInfo().subscribe(
      user => {
        this.email = user.email;
        this.username = user.username;
        this.name = user.name;
        this.surname = user.surname;
        this.admin = this.authorization.isAdmin()
        this.getProfileImage()
        this.cardHeader = {
          "card-header": true,
          "admin": this.admin
        };
          this.item = {
            'item' : true,
            'admin' : this.admin
          };
          this.administratorColor = {
            'fas fa-edit-user': true,
            'administrator': this.admin
          }
          this.formName = {
            name: true,
            ad: this.admin
          }
          this.formEmail = {
            email: true,
            ad: this.admin
          }
      }
    )
  }

  getProfileImage(){
    this.imgService.getProfileImage().subscribe(
      image => {this.createImageFromBlob(image);}
    )
  }

  createImageFromBlob(image: Blob) {
    let reader = new FileReader();
    reader.addEventListener("load", () => {
      this.img = reader.result;
    }, false);

    if (image) {
      reader.readAsDataURL(image);
    }
  }

  goToEditProfile(){
    this.router.navigate(['editProfile']);
  }

  rechargeCharts(){
    this.barChart.generateChart();
    this.radarChart.generateRadar();

  }

  downloadPDF(){
    var day = new Date();
    const progress: Element = document.getElementById('chartsPDF');
    html2pdf()
      .set({
        filename: 'progress' + day.getMonth() + '-' + day.getDate() + '-' + day.getFullYear() + '.pdf',
        image: {type: 'png', quality: 0.98},
        html2canvas: {scale: 3, letterRendering: true,},
        jsPDF:{orientation: 'portrait', unit: "in", format: "a4",}
      })
      .from(progress)
      .save()
      .catch(err => console.log(err));
  }

}
