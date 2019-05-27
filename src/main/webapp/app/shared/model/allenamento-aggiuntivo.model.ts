import { ICalciatore } from 'app/shared/model/calciatore.model';

export const enum Natura {
  AEROBICO = 'AEROBICO',
  ANAEROBICO = 'ANAEROBICO',
  POTENZIAMENTO = 'POTENZIAMENTO',
  VELOCITA = 'VELOCITA'
}

export interface IAllenamentoAggiuntivo {
  id?: number;
  sport?: string;
  natura?: Natura;
  lavoro?: string;
  durata?: number;
  calciatores?: ICalciatore[];
}

export class AllenamentoAggiuntivo implements IAllenamentoAggiuntivo {
  constructor(
    public id?: number,
    public sport?: string,
    public natura?: Natura,
    public lavoro?: string,
    public durata?: number,
    public calciatores?: ICalciatore[]
  ) {}
}
