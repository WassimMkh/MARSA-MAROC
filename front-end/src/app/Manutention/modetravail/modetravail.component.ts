import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { HttpClient } from "@angular/common/http";
import { ModeTravailRequest } from "../../models/modetravail-request.model";

@Component({
  selector: 'app-modetravail',
  templateUrl: './modetravail.component.html',
  styleUrls: ['./modetravail.component.css']
})
export class ModetravailComponent implements OnInit {
  modeTravailForm: FormGroup;
  formSubmitted: boolean = false;

  constructor(private http: HttpClient) {
    this.modeTravailForm = new FormGroup({
      semaine: new FormControl("", [Validators.required]),
      jour: new FormControl("", [Validators.required]),
    });
  }

  onSubmit() {
    this.formSubmitted = true;

    if (this.modeTravailForm.valid) {
      const modeTravailRequest: ModeTravailRequest = {
        semaine: this.modeTravailForm.value.semaine,
        jour: this.modeTravailForm.value.jour
      };

      this.http.post('http://localhost:8088/increment-modetravail', modeTravailRequest).subscribe(
        response => {
          console.log('Form submitted successfully', response);
        },
        error => {
          console.error('Error submitting form', error);
        }
      );
    } else {
      console.log('Form is not valid');
    }
  }

  onCancel() {
    this.formSubmitted = false;
    this.modeTravailForm.reset();
  }

  ngOnInit() {
  }
}
