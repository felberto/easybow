import * as dayjs from 'dayjs';
import { ICompetition } from 'app/entities/competition/competition.model';

export interface IRound {
  id?: number;
  round?: number;
  date?: dayjs.Dayjs | null;
  competition?: ICompetition | null;
}

export class Round implements IRound {
  constructor(public id?: number, public round?: number, public date?: dayjs.Dayjs | null, public competition?: ICompetition | null) {}
}

export function getRoundIdentifier(round: IRound): number | undefined {
  return round.id;
}
