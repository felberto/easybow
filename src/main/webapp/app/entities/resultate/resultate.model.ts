import { IPassen } from 'app/entities/passen/passen.model';
import { IGruppen } from 'app/entities/gruppen/gruppen.model';
import { ISchuetze } from 'app/entities/schuetze/schuetze.model';
import { IWettkampf } from 'app/entities/wettkampf/wettkampf.model';

export interface IResultate {
  id?: number;
  runde?: number | null;
  passe1?: IPassen | null;
  passe2?: IPassen | null;
  passe3?: IPassen | null;
  passe4?: IPassen | null;
  gruppe?: IGruppen | null;
  schuetze?: ISchuetze | null;
  wettkampf?: IWettkampf | null;
}

export class Resultate implements IResultate {
  constructor(
    public id?: number,
    public runde?: number | null,
    public passe1?: IPassen | null,
    public passe2?: IPassen | null,
    public passe3?: IPassen | null,
    public passe4?: IPassen | null,
    public gruppe?: IGruppen | null,
    public schuetze?: ISchuetze | null,
    public wettkampf?: IWettkampf | null
  ) {}
}

export function getResultateIdentifier(resultate: IResultate): number | undefined {
  return resultate.id;
}
