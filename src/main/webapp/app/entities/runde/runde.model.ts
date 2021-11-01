import * as dayjs from 'dayjs';
import { IWettkampf } from 'app/entities/wettkampf/wettkampf.model';

export interface IRunde {
  id?: number;
  runde?: number;
  datum?: dayjs.Dayjs | null;
  wettkampf?: IWettkampf | null;
}

export class Runde implements IRunde {
  constructor(public id?: number, public runde?: number, public datum?: dayjs.Dayjs | null, public wettkampf?: IWettkampf | null) {}
}

export function getRundeIdentifier(runde: IRunde): number | undefined {
  return runde.id;
}
