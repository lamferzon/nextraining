import { Moment } from 'moment';
import { ISpecialista } from 'app/shared/model/specialista.model';
import { ICalciatore } from 'app/shared/model/calciatore.model';

export const enum Gravita {
  UNO = 'UNO',
  DUE = 'DUE',
  TRE = 'TRE',
  QUATTRO = 'QUATTRO',
  CINQUE = 'CINQUE'
}

export interface IInfortunio {
  id?: number;
  dataInizio?: Moment;
  dataFine?: Moment;
  gravita?: Gravita;
  descrizione?: string;
  specialista?: ISpecialista;
  calciatore?: ICalciatore;
}

export class Infortunio implements IInfortunio {
  constructor(
    public id?: number,
    public dataInizio?: Moment,
    public dataFine?: Moment,
    public gravita?: Gravita,
    public descrizione?: string,
    public specialista?: ISpecialista,
    public calciatore?: ICalciatore
  ) {}
}
