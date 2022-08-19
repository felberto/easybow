import { ICompetition } from './competition.model';
import { IGroupAthleteResult } from './groupAthleteResult.model';

export interface IGroupRankingList {
  competition?: ICompetition;
  groupAthleteResultList?: IGroupAthleteResult[];
  type?: number;
}

export class GroupRankingList implements IGroupRankingList {
  constructor(public competition?: ICompetition, public groupAthleteResultList?: IGroupAthleteResult[], public type?: number) {}
}
