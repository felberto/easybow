import { IAthlete } from '../athlete/athlete.model';
import { IResults } from '../results/results.model';

export interface IAthleteResult {
  athlete: IAthlete;
  resultsList: IResults[];
  result: number;
  resultRound1: number;
  resultRound2: number;
  resultFinal: number;
  resultRound1Serie1: number;
  resultRound1Serie2: number;
  resultRound1Serie3: number;
  resultRound1Serie4: number;
  resultRound2Serie1: number;
  resultRound2Serie2: number;
  resultRound2Serie3: number;
  resultRound2Serie4: number;
  resultFinalSerie1: number;
  resultFinalSerie2: number;
  t0: number;
  t1: number;
  t2: number;
  t3: number;
  t4: number;
  t5: number;
  t6: number;
  t7: number;
  t8: number;
  t9: number;
  t10: number;
  t11: number;
}

export class AthleteResult implements IAthleteResult {
  constructor(
    public athlete: IAthlete,
    public resultsList: IResults[],
    public result: number,
    public resultRound1: number,
    public resultRound2: number,
    public resultFinal: number,
    public resultRound1Serie1: number,
    public resultRound1Serie2: number,
    public resultRound1Serie3: number,
    public resultRound1Serie4: number,
    public resultRound2Serie1: number,
    public resultRound2Serie2: number,
    public resultRound2Serie3: number,
    public resultRound2Serie4: number,
    public resultFinalSerie1: number,
    public resultFinalSerie2: number,
    public t0: number,
    public t1: number,
    public t10: number,
    public t11: number,
    public t2: number,
    public t3: number,
    public t4: number,
    public t5: number,
    public t6: number,
    public t7: number,
    public t8: number,
    public t9: number
  ) {}
}
