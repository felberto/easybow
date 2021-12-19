import { ISchuetze } from '../schuetze/schuetze.model';
import { IResultate } from '../resultate/resultate.model';

export interface ISchuetzeResultat {
  schuetze: ISchuetze;
  resultatList: IResultate[];
  resultat: number;
  resultatRunde1: number;
  resultatRunde2: number;
  resultatFinal: number;
  resultatRunde1Passe1: number;
  resultatRunde1Passe2: number;
  resultatRunde2Passe1: number;
  resultatRunde2Passe2: number;
  resultatFinalPasse1: number;
  resultatFinalPasse2: number;
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

export class SchuetzeResultat implements ISchuetzeResultat {
  constructor(
    public schuetze: ISchuetze,
    public resultatList: IResultate[],
    public resultat: number,
    public resultatRunde1: number,
    public resultatRunde2: number,
    public resultatFinal: number,
    public resultatRunde1Passe1: number,
    public resultatRunde1Passe2: number,
    public resultatRunde2Passe1: number,
    public resultatRunde2Passe2: number,
    public resultatFinalPasse1: number,
    public resultatFinalPasse2: number,
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
