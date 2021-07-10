export interface IPassen {
  id?: number;
  p1?: number | null;
  p2?: number | null;
  p3?: number | null;
  p4?: number | null;
  p5?: number | null;
  p6?: number | null;
  p7?: number | null;
  p8?: number | null;
  p9?: number | null;
  p10?: number | null;
  resultat?: number | null;
}

export class Passen implements IPassen {
  constructor(
    public id?: number,
    public p1?: number | null,
    public p2?: number | null,
    public p3?: number | null,
    public p4?: number | null,
    public p5?: number | null,
    public p6?: number | null,
    public p7?: number | null,
    public p8?: number | null,
    public p9?: number | null,
    public p10?: number | null,
    public resultat?: number | null
  ) {}
}

export function getPassenIdentifier(passen: IPassen): number | undefined {
  return passen.id;
}
