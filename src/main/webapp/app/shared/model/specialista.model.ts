export interface ISpecialista {
  id?: number;
  codFiscale?: string;
  cognome?: string;
  nome?: string;
  specializzazione?: string;
  numTelefono?: string;
  email?: string;
  indirizzoStudio?: string;
  paeseStudio?: string;
}

export class Specialista implements ISpecialista {
  constructor(
    public id?: number,
    public codFiscale?: string,
    public cognome?: string,
    public nome?: string,
    public specializzazione?: string,
    public numTelefono?: string,
    public email?: string,
    public indirizzoStudio?: string,
    public paeseStudio?: string
  ) {}
}
