import { IVerband } from 'app/entities/verband/verband.model';

export interface IVerein {
  id?: number;
  name?: string | null;
  verband?: IVerband | null;
}

export class Verein implements IVerein {
  constructor(public id?: number, public name?: string | null, public verband?: IVerband | null) {}
}

export function getVereinIdentifier(verein: IVerein): number | undefined {
  return verein.id;
}
