import { IWettkampf } from './wettkampf.model';
import { ISchuetzeResultat } from './schuetzeResultat.model';

export interface IRangliste {
  wettkampf?: IWettkampf;
  schuetzeResultatList?: ISchuetzeResultat[];
}

export class Rangliste implements IRangliste {
  constructor(public wettkampf?: IWettkampf, public schuetzeResultatList?: ISchuetzeResultat[]) {}
}
