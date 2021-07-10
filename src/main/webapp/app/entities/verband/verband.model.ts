export interface IVerband {
  id?: number;
  name?: string | null;
}

export class Verband implements IVerband {
  constructor(public id?: number, public name?: string | null) {}
}

export function getVerbandIdentifier(verband: IVerband): number | undefined {
  return verband.id;
}
