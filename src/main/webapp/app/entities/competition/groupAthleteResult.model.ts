import { IGroup } from '../group/group.model';
import { IAthleteResult } from './athleteResult.model';

export interface IGroupAthleteResult {
  group: IGroup;
  athleteResultList: IAthleteResult[];
  result: number;
}

export class GroupAthleteResult implements IGroupAthleteResult {
  constructor(public group: IGroup, public athleteResultList: IAthleteResult[], public result: number) {}
}
