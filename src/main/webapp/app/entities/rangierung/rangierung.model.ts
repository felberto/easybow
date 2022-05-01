import { IWettkampf } from 'app/entities/wettkampf/wettkampf.model';
import { Rangierungskriterien } from 'app/entities/enumerations/rangierungskriterien.model';

export interface IRangierung {
  id?: number;
  position?: number;
  rangierungskriterien?: Rangierungskriterien;
  wettkampf?: IWettkampf | null;
}

export class Rangierung implements IRangierung {
  constructor(
    public id?: number,
    public position?: number,
    public rangierungskriterien?: Rangierungskriterien,
    public wettkampf?: IWettkampf | null
  ) {}
}

export function getRangierungIdentifier(rangierung: IRangierung): number | undefined {
  return rangierung.id;
}

export function getRangierungKriterien(rangierung: IRangierung): string | undefined {
  return rangierung.rangierungskriterien;
}
