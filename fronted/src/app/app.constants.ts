import { Injectable } from '@angular/core';

@Injectable()
export class Configuration {

    public static get DATE_NOW() {
        return new Date();
    }
}
