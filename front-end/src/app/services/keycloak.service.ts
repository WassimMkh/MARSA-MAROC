import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class KeycloakService {

  constructor() { }

  async init() {
    console.log("Init keycloak")
  }
}
