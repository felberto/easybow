import * as dayjs from 'dayjs';
import { IVerein } from 'app/entities/verein/verein.model';
import { Stellung } from 'app/entities/enumerations/stellung.model';

export interface ISchuetze {
  id?: number;
  name?: string | null;
  vorname?: string | null;
  jahrgang?: dayjs.Dayjs | null;
  stellung?: Stellung | null;
  verein?: IVerein | null;
}

export class Schuetze implements ISchuetze {
  constructor(
    public id?: number,
    public name?: string | null,
    public vorname?: string | null,
    public jahrgang?: dayjs.Dayjs | null,
    public stellung?: Stellung | null,
    public verein?: IVerein | null
  ) {}
}

export function getSchuetzeIdentifier(schuetze: ISchuetze): number | undefined {
  return schuetze.id;
}
