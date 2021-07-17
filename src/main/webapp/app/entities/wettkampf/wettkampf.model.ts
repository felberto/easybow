import * as dayjs from 'dayjs';

export interface IWettkampf {
  id?: number;
  name?: string;
  jahr?: dayjs.Dayjs | null;
  anzahlRunden?: number;
  anzahlPassen?: number;
  finalRunde?: boolean | null;
  finalVorbereitung?: boolean | null;
  anzahlFinalteilnehmer?: number | null;
  anzahlPassenFinal?: number | null;
  anzahlTeam?: number | null;
  template?: boolean | null;
}

export class Wettkampf implements IWettkampf {
  constructor(
    public id?: number,
    public name?: string,
    public jahr?: dayjs.Dayjs | null,
    public anzahlRunden?: number,
    public anzahlPassen?: number,
    public finalRunde?: boolean | null,
    public finalVorbereitung?: boolean | null,
    public anzahlFinalteilnehmer?: number | null,
    public anzahlPassenFinal?: number | null,
    public anzahlTeam?: number | null,
    public template?: boolean | null
  ) {
    this.finalRunde = this.finalRunde ?? false;
    this.finalVorbereitung = this.finalVorbereitung ?? false;
    this.template = this.template ?? false;
  }
}

export function getWettkampfIdentifier(wettkampf: IWettkampf): number | undefined {
  return wettkampf.id;
}
