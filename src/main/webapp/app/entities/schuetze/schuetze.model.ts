import * as dayjs from 'dayjs';
import { IVerein } from 'app/entities/verein/verein.model';
import { Stellung } from 'app/entities/enumerations/stellung.model';

export interface ISchuetze {
  id?: number;
  name?: string;
  jahrgang?: dayjs.Dayjs;
  stellung?: Stellung;
  verein?: IVerein | null;
}

export class Schuetze implements ISchuetze {
  constructor(
    public id?: number,
    public name?: string,
    public jahrgang?: dayjs.Dayjs,
    public stellung?: Stellung,
    public verein?: IVerein | null
  ) {}
}

export function getSchuetzeIdentifier(schuetze: ISchuetze): number | undefined {
  return schuetze.id;
}
