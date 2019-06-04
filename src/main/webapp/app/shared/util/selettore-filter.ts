import { Pipe, PipeTransform } from '@angular/core';
import { ICalciatore } from 'app/shared/model/calciatore.model';

@Pipe({ name: 'selettoreFilter' })
export class SelettoreFilterPipe implements PipeTransform {
  transform(allCalciatores: ICalciatore[], selettore: string) {
    if (!allCalciatores || !selettore) {
      return allCalciatores;
    }
    return allCalciatores.filter(calciatore => calciatore.selettore === selettore);
  }
}
