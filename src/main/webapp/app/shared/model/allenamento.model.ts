import { Moment } from 'moment';
import { ICalciatore } from 'app/shared/model/calciatore.model';

export const enum Natura {
  AEROBICO = 'AEROBICO',
  ANAEROBICO = 'ANAEROBICO',
  POTENZIAMENTO = 'POTENZIAMENTO',
  VELOCITA = 'VELOCITA'
}

export interface IAllenamento {
  id?: number;
  dataSvolgimento?: Moment;
  natura?: Natura;
  lavoro?: string;
  calciatores?: ICalciatore[];
}

export class Allenamento implements IAllenamento {
  constructor(
    public id?: number,
    public dataSvolgimento?: Moment,
    public natura?: Natura,
    public lavoro?: string,
    public calciatores?: ICalciatore[]
  ) {}
}
