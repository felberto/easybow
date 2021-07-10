import * as dayjs from 'dayjs';

export interface IWettkampf {
  id?: number;
  name?: string | null;
  jahr?: dayjs.Dayjs | null;
  anzahlRunden?: number | null;
  finalRunde?: boolean | null;
  anzahlPassen?: number | null;
  anzahlPassenFinal?: number | null;
  team?: number | null;
  template?: boolean | null;
}

export class Wettkampf implements IWettkampf {
  constructor(
    public id?: number,
    public name?: string | null,
    public jahr?: dayjs.Dayjs | null,
    public anzahlRunden?: number | null,
    public finalRunde?: boolean | null,
    public anzahlPassen?: number | null,
    public anzahlPassenFinal?: number | null,
    public team?: number | null,
    public template?: boolean | null
  ) {
    this.finalRunde = this.finalRunde ?? false;
    this.template = this.template ?? false;
  }
}

export function getWettkampfIdentifier(wettkampf: IWettkampf): number | undefined {
  return wettkampf.id;
}
