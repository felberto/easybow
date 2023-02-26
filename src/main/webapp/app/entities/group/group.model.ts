import { IClub } from '../club/club.model';
import { ICompetition } from '../competition/competition.model';

export interface IGroup {
  id?: number;
  name?: string;
  club?: IClub | null;
  round?: number;
  competition?: ICompetition | null;
}

export class Group implements IGroup {
  constructor(
    public id?: number,
    public name?: string,
    public club?: IClub | null,
    public round?: number,
    public competition?: ICompetition | null
  ) {}
}

export function getGroupIdentifier(group: IGroup): number | undefined {
  return group.id;
}
