import { Adresa, Lice } from "../types";

export class Pronalazac extends Lice {
  constructor() {
    super();
  }
}

export class Podnosilac extends Lice {
  public pronalazac: boolean;
  constructor() {
    super();
    this.pronalazac = false;
  }
}

export class Punomocnik extends Lice {
  public zaPrijem: boolean;
  public zaZastupanje: boolean;
  public zajednickiPredstavnik: boolean;
  constructor() {
    super();
    this.zaZastupanje = false;
    this.zaPrijem = false;
    this.zajednickiPredstavnik = false;
  }
}

export class NazivPatent {
  public srpski: string;
  public engleski: string;
  constructor() {
    this.srpski = "";
    this.engleski = "";
  }
}

export enum TipPrvobitnePrijave {
  izdvojena = "izdvojena",
  dopunska = "dopunska",
}
export class PrvobitnaPrijava {
  public brojPrijave: string;
  public datum: string;
  public tip: TipPrvobitnePrijave;

  constructor() {
    this.brojPrijave = "";
    this.datum = "";
    this.tip = TipPrvobitnePrijave.izdvojena;
  }
}

export class RanijaPrijava {
  public brojPrijave: string;
  public datum: string;
  public oznaka: string;

  constructor() {
    this.brojPrijave = "";
    this.datum = "";
    this.oznaka = "";
  }
}

export class Dostavljanje extends Adresa {
  public pisano: boolean;
  public elektronski: boolean;
  constructor() {
    super();
    this.pisano = false;
    this.elektronski = false;
  }
}

export class PrilozeniDokumenti {
  public prvobitnaPrijava?: FileList;
  public ranijePrijave?: FileList;
  public osnovSticanja?: FileList;
  public nenavedenPronalazac?: FileList;
  public opisPronalaska?: FileList;
  public zastitaPronalaska?: FileList;
  public nacrt?: FileList;
  public apstrakt?: FileList;
  constructor() {}
}
