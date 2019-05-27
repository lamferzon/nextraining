import { Moment } from 'moment';
import { IAllenamento } from 'app/shared/model/allenamento.model';
import { IAllenamentoAggiuntivo } from 'app/shared/model/allenamento-aggiuntivo.model';

export const enum Reparto {
  DIFESA = 'DIFESA',
  CENTROCAMPO = 'CENTROCAMPO',
  ATTACCO = 'ATTACCO'
}

export const enum Ruolo {
  POR = 'POR',
  LIB = 'LIB',
  ADA = 'ADA',
  TD = 'TD',
  CD = 'CD',
  DC = 'DC',
  CS = 'CS',
  TS = 'TS',
  ASA = 'ASA',
  CCD = 'CCD',
  MDD = 'MDD',
  CDC = 'CDC',
  MDS = 'MDS',
  CDS = 'CDS',
  AD = 'AD',
  ED = 'ED',
  CC = 'CC',
  CCS = 'CCS',
  ES = 'ES',
  AS = 'AS',
  COD = 'COD',
  CAD = 'CAD',
  COC = 'COC',
  CAS = 'CAS',
  COS = 'COS',
  CA = 'CA',
  PD = 'PD',
  ATT = 'ATT',
  PS = 'PS',
  AT = 'AT'
}

export const enum Selettore {
  DIFENSORE = 'DIFENSORE',
  ATTACCANTE = 'ATTACCANTE'
}

export interface ICalciatore {
  id?: number;
  codFiscale?: string;
  cognome?: string;
  nome?: string;
  dataNascita?: Moment;
  numTelefono?: string;
  email?: string;
  reparto?: Reparto;
  ruolo?: Ruolo;
  selettore?: Selettore;
  allenamentos?: IAllenamento[];
  allenamentoExtras?: IAllenamentoAggiuntivo[];
}

export class Calciatore implements ICalciatore {
  constructor(
    public id?: number,
    public codFiscale?: string,
    public cognome?: string,
    public nome?: string,
    public dataNascita?: Moment,
    public numTelefono?: string,
    public email?: string,
    public reparto?: Reparto,
    public ruolo?: Ruolo,
    public selettore?: Selettore,
    public allenamentos?: IAllenamento[],
    public allenamentoExtras?: IAllenamentoAggiuntivo[]
  ) {}
}
