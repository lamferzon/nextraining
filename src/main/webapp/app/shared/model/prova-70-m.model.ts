import { Moment } from 'moment';
import { ICalciatore } from 'app/shared/model/calciatore.model';

export const enum Feedback {
  SCARSO = 'SCARSO',
  DISCRETO = 'DISCRETO',
  BUONO = 'BUONO',
  OTTIMO = 'OTTIMO',
  ECCELLENTE = 'ECCELLENTE'
}

export interface IProva70m {
  id?: number;
  dataProva?: Moment;
  tempo?: number;
  partenzaBlocchi?: boolean;
  velMax?: number;
  commento?: Feedback;
  condClimatiche?: string;
  calciatore?: ICalciatore;
}

export class Prova70m implements IProva70m {
  constructor(
    public id?: number,
    public dataProva?: Moment,
    public tempo?: number,
    public partenzaBlocchi?: boolean,
    public velMax?: number,
    public commento?: Feedback,
    public condClimatiche?: string,
    public calciatore?: ICalciatore
  ) {
    this.partenzaBlocchi = this.partenzaBlocchi || false;
  }
}
