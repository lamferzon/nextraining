import { Moment } from 'moment';
import { ICalciatore } from 'app/shared/model/calciatore.model';

export const enum Feedback {
  SCARSO = 'SCARSO',
  DISCRETO = 'DISCRETO',
  BUONO = 'BUONO',
  OTTIMO = 'OTTIMO',
  ECCELLENTE = 'ECCELLENTE'
}

export interface ITestdiCooper {
  id?: number;
  dataTest?: Moment;
  distanza?: number;
  v02Max?: number;
  commento?: Feedback;
  condClimatiche?: string;
  calciatore?: ICalciatore;
}

export class TestdiCooper implements ITestdiCooper {
  constructor(
    public id?: number,
    public dataTest?: Moment,
    public distanza?: number,
    public v02Max?: number,
    public commento?: Feedback,
    public condClimatiche?: string,
    public calciatore?: ICalciatore
  ) {}
}
