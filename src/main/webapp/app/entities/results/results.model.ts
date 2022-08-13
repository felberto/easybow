import { ISeries } from 'app/entities/series/series.model';
import { IGroup } from 'app/entities/group/group.model';
import { IAthlete } from 'app/entities/athlete/athlete.model';
import { ICompetition } from '../competition/competition.model';

export interface IResults {
  id?: number;
  round?: number;
  result?: number | null;
  athleteNumber?: string | null;
  serie1?: ISeries | null;
  serie2?: ISeries | null;
  serie3?: ISeries | null;
  serie4?: ISeries | null;
  group?: IGroup | null;
  athlete?: IAthlete | null;
  competition?: ICompetition | null;
}

export class Results implements IResults {
  constructor(
    public id?: number,
    public round?: number,
    public result?: number | null,
    public athleteNumber?: string | null,
    public serie1?: ISeries | null,
    public serie2?: ISeries | null,
    public serie3?: ISeries | null,
    public serie4?: ISeries | null,
    public group?: IGroup | null,
    public athlete?: IAthlete | null,
    public competition?: ICompetition | null
  ) {}
}

export function getResultsIdentifier(results: IResults): number | undefined {
  return results.id;
}
