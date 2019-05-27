import { Moment } from 'moment';
import { ICalciatore } from 'app/shared/model/calciatore.model';

export const enum Feedback {
  SCARSO = 'SCARSO',
  DISCRETO = 'DISCRETO',
  BUONO = 'BUONO',
  OTTIMO = 'OTTIMO',
  ECCELLENTE = 'ECCELLENTE'
}

export interface IProva1500m {
  id?: number;
  dataProva?: Moment;
  tempo?: number;
  tempoKm?: number;
  commento?: Feedback;
  condClimatiche?: string;
  calciatore?: ICalciatore;
}

export class Prova1500m implements IProva1500m {
  constructor(
    public id?: number,
    public dataProva?: Moment,
    public tempo?: number,
    public tempoKm?: number,
    public commento?: Feedback,
    public condClimatiche?: string,
    public calciatore?: ICalciatore
  ) {}
}
