export interface IVerband {
  id?: number;
  name?: string;
}

export class Verband implements IVerband {
  constructor(public id?: number, public name?: string) {}
}

export function getVerbandIdentifier(verband: IVerband): number | undefined {
  return verband.id;
}
