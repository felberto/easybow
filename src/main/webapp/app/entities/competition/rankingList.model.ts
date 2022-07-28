import { ICompetition } from './competition.model';
import { IAthleteResult } from './athleteResult.model';

export interface IRankingList {
  competition?: ICompetition;
  athleteResultList?: IAthleteResult[];
  type?: number;
  vfDate?: Date;
  vfTime?: number;
  vfOrt?: string;
  vfAnzahl?: number;
}

export class RankingList implements IRankingList {
  constructor(
    public competition?: ICompetition,
    public athleteResultList?: IAthleteResult[],
    public type?: number,
    public vfDate?: Date,
    public vfTime?: number,
    public vfOrt?: string,
    public vfAnzahl?: number
  ) {}
}
