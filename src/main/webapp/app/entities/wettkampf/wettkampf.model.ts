import * as dayjs from 'dayjs';

export interface IWettkampf {
  id?: number;
  name?: string | null;
  jahr?: dayjs.Dayjs | null;
  anzahlPassen?: number | null;
  team?: number | null;
  template?: boolean | null;
}

export class Wettkampf implements IWettkampf {
  constructor(
    public id?: number,
    public name?: string | null,
    public jahr?: dayjs.Dayjs | null,
    public anzahlPassen?: number | null,
    public team?: number | null,
    public template?: boolean | null
  ) {
    this.template = this.template ?? false;
  }
}

export function getWettkampfIdentifier(wettkampf: IWettkampf): number | undefined {
  return wettkampf.id;
}
