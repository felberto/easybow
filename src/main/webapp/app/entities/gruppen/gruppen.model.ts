export interface IGruppen {
  id?: number;
  name?: string | null;
}

export class Gruppen implements IGruppen {
  constructor(public id?: number, public name?: string | null) {}
}

export function getGruppenIdentifier(gruppen: IGruppen): number | undefined {
  return gruppen.id;
}
