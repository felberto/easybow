import { CompetitionType } from '../enumerations/competitionType.model';

export interface ICompetition {
  id?: number;
  name?: string;
  year?: number | null;
  competitionType?: CompetitionType;
  numberOfRounds?: number;
  numberOfSeries?: number;
  finalRound?: boolean | null;
  finalPreparation?: boolean | null;
  numberOfFinalAthletes?: number | null;
  numberOfFinalSeries?: number | null;
  teamSize?: number | null;
  template?: boolean | null;
}

export class Competition implements ICompetition {
  constructor(
    public id?: number,
    public name?: string,
    public year?: number | null,
    public competitionType?: CompetitionType,
    public numberOfRounds?: number,
    public numberOfSeries?: number,
    public finalRound?: boolean | null,
    public finalPreparation?: boolean | null,
    public numberOfFinalAthletes?: number | null,
    public numberOfFinalSeries?: number | null,
    public teamSize?: number | null,
    public template?: boolean | null
  ) {
    this.finalRound = this.finalRound ?? false;
    this.finalPreparation = this.finalPreparation ?? false;
    this.template = this.template ?? false;
  }
}

export function getCompetitionIdentifier(competition: ICompetition): number | undefined {
  return competition.id;
}
