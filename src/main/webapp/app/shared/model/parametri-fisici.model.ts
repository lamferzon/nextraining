import { Moment } from 'moment';
import { ICalciatore } from 'app/shared/model/calciatore.model';

export const enum Stato {
  SOTTOPESO = 'SOTTOPESO',
  NORMOPESO = 'NORMOPESO',
  SOVRAPPESO = 'SOVRAPPESO'
}

export interface IParametriFisici {
  id?: number;
  dataRivelazione?: Moment;
  massaCorporea?: number;
  altezza?: number;
  bmi?: number;
  condizione?: Stato;
  fcRiposo?: number;
  calciatore?: ICalciatore;
}

export class ParametriFisici implements IParametriFisici {
  constructor(
    public id?: number,
    public dataRivelazione?: Moment,
    public massaCorporea?: number,
    public altezza?: number,
    public bmi?: number,
    public condizione?: Stato,
    public fcRiposo?: number,
    public calciatore?: ICalciatore
  ) {}
}
