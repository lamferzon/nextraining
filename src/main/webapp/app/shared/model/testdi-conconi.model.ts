import { ICalciatore } from 'app/shared/model/calciatore.model';

export const enum Feedback {
  SCARSO = 'SCARSO',
  DISCRETO = 'DISCRETO',
  BUONO = 'BUONO',
  OTTIMO = 'OTTIMO',
  ECCELLENTE = 'ECCELLENTE'
}

export interface ITestdiConconi {
  id?: number;
  fcMax?: number;
  sogliaAnaerobica?: number;
  velSoglia?: number;
  durata?: number;
  commento?: Feedback;
  condClimatiche?: string;
  calciatore?: ICalciatore;
}

export class TestdiConconi implements ITestdiConconi {
  constructor(
    public id?: number,
    public fcMax?: number,
    public sogliaAnaerobica?: number,
    public velSoglia?: number,
    public durata?: number,
    public commento?: Feedback,
    public condClimatiche?: string,
    public calciatore?: ICalciatore
  ) {}
}
