import { IVerein } from 'app/entities/verein/verein.model';
import { Stellung } from 'app/entities/enumerations/stellung.model';

export interface ISchuetze {
  id?: number;
  name?: string;
  jahrgang?: number;
  stellung?: Stellung;
  rolle?: string;
  verein?: IVerein | null;
}

export class Schuetze implements ISchuetze {
  constructor(
    public id?: number,
    public name?: string,
    public jahrgang?: number,
    public stellung?: Stellung,
    public rolle?: string,
    public verein?: IVerein | null
  ) {}
}

export function getSchuetzeIdentifier(schuetze: ISchuetze): number | undefined {
  return schuetze.id;
}
