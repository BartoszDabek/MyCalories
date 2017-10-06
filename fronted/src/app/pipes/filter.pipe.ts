import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'filter'
})
export class FilterPipe implements PipeTransform {

  transform(values: any, args?: any): any {
    if (args === undefined) return values;
    return values.filter((value) => {
      return value.name.toLowerCase().includes(typeof args === 'string' ? args.toLowerCase() : args.name.toLowerCase());
    })
  }

}
