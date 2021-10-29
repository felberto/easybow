import { ISchuetze } from '../schuetze/schuetze.model';
import { IResultate } from '../resultate/resultate.model';

export interface ISchuetzeResultat {
  schuetze: ISchuetze;
  resultatList: IResultate[];
  resultat: number;
}

export class SchuetzeResultat implements ISchuetzeResultat {
  constructor(public schuetze: ISchuetze, public resultatList: IResultate[], public resultat: number) {}
}
