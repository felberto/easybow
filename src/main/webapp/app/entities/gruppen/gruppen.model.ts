export interface IGruppen {
  id?: number;
  name?: string;
}

export class Gruppen implements IGruppen {
  constructor(public id?: number, public name?: string) {}
}

export function getGruppenIdentifier(gruppen: IGruppen): number | undefined {
  return gruppen.id;
}
