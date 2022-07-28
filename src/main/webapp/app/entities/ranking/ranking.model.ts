import { ICompetition } from 'app/entities/competition/competition.model';
import { RankingCriteria } from 'app/entities/enumerations/rankingCriteria.model';

export interface IRanking {
  id?: number;
  position?: number;
  rankingCriteria?: RankingCriteria;
  competition?: ICompetition | null;
}

export class Ranking implements IRanking {
  constructor(
    public id?: number,
    public position?: number,
    public rankingCriteria?: RankingCriteria,
    public competition?: ICompetition | null
  ) {}
}

export function getRankingIdentifier(ranking: IRanking): number | undefined {
  return ranking.id;
}

export function getRankingCriteria(ranking: IRanking): string | undefined {
  return ranking.rankingCriteria;
}
