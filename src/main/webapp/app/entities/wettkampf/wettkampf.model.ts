import * as dayjs from 'dayjs';

export interface IWettkampf {
  id?: number;
  name?: string;
  jahr?: dayjs.Dayjs;
  anzahlRunden?: number;
  finalRunde?: boolean | null;
  anzahlPassen?: number;
  anzahlPassenFinal?: number | null;
  anzahlTeam?: number | null;
}

export class Wettkampf implements IWettkampf {
  constructor(
    public id?: number,
    public name?: string,
    public jahr?: dayjs.Dayjs,
    public anzahlRunden?: number,
    public finalRunde?: boolean | null,
    public anzahlPassen?: number,
    public anzahlPassenFinal?: number | null,
    public anzahlTeam?: number | null
  ) {
    this.finalRunde = this.finalRunde ?? false;
  }
}

export function getWettkampfIdentifier(wettkampf: IWettkampf): number | undefined {
  return wettkampf.id;
}
