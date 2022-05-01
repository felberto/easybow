import { IWettkampf } from './wettkampf.model';
import { ISchuetzeResultat } from './schuetzeResultat.model';

export interface IRangliste {
  wettkampf?: IWettkampf;
  schuetzeResultatList?: ISchuetzeResultat[];
  type?: number;
  vfDate?: Date;
  vfTime?: number;
  vfOrt?: string;
  vfAnzahl?: number;
}

export class Rangliste implements IRangliste {
  constructor(
    public wettkampf?: IWettkampf,
    public schuetzeResultatList?: ISchuetzeResultat[],
    public type?: number,
    public vfDate?: Date,
    public vfTime?: number,
    public vfOrt?: string,
    public vfAnzahl?: number
  ) {}
}
