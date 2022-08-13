import { IClub } from 'app/entities/club/club.model';
import { Position } from 'app/entities/enumerations/position.model';
import { Nationality } from '../enumerations/nationality.model';
import { Gender } from '../enumerations/gender.model';

export interface IAthlete {
  id?: number;
  name?: string;
  firstName?: string;
  yearOfBirth?: number;
  nationality?: Nationality;
  gender?: Gender;
  position?: Position;
  role?: string;
  club?: IClub | null;
}

export class Athlete implements IAthlete {
  constructor(
    public id?: number,
    public name?: string,
    public firstName?: string,
    public yearOfBirth?: number,
    public nationality?: Nationality,
    public gender?: Gender,
    public position?: Position,
    public role?: string,
    public club?: IClub | null
  ) {}
}

export function getAthleteIdentifier(athlete: IAthlete): number | undefined {
  return athlete.id;
}
