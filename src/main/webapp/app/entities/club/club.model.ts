import { IAssociation } from 'app/entities/association/association.model';

export interface IClub {
  id?: number;
  name?: string;
  association?: IAssociation | null;
}

export class Club implements IClub {
  constructor(public id?: number, public name?: string, public association?: IAssociation | null) {}
}

export function getClubIdentifier(club: IClub): number | undefined {
  return club.id;
}
