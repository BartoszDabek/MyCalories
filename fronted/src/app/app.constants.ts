import { Injectable } from '@angular/core';

@Injectable()
export class Configuration {
    private static Server = 'http://localhost:8080/';
    // private static ApiUrl = 'api/';

    public static get HOME_URL(): string { 
        return this.Server; 
    };
}