export interface IPassen {
  id?: number;
  p1?: number;
  p2?: number;
  p3?: number;
  p4?: number;
  p5?: number;
  p6?: number;
  p7?: number;
  p8?: number;
  p9?: number;
  p10?: number;
  resultat?: number | null;
}

export class Passen implements IPassen {
  constructor(
    public id?: number,
    public p1?: number,
    public p2?: number,
    public p3?: number,
    public p4?: number,
    public p5?: number,
    public p6?: number,
    public p7?: number,
    public p8?: number,
    public p9?: number,
    public p10?: number,
    public resultat?: number | null
  ) {}
}

export function getPassenIdentifier(passen: IPassen): number | undefined {
  return passen.id;
}
