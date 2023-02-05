import { Adresa, Lice } from "../types";

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

  public static validate(punomocnik:Punomocnik) {
    if (!punomocnik.zaPrijem && !punomocnik.zaZastupanje && !punomocnik.zajednickiPredstavnik && Lice.isEmpty(punomocnik)) {
      return true;
    }
    else if (Lice.validate(punomocnik)) {
      if ((punomocnik.zaZastupanje && !punomocnik.zajednickiPredstavnik) || (punomocnik.zajednickiPredstavnik && punomocnik.zaZastupanje)) {
        return true;
      }
    }
    return false;
  }

  public static isFull(punomocnik:Punomocnik):boolean {
    if (Lice.validate(punomocnik)) {
      if ((punomocnik.zaZastupanje && !punomocnik.zajednickiPredstavnik) || (punomocnik.zajednickiPredstavnik && punomocnik.zaZastupanje)) {
        return true;
      }
    }
    return false;
  }
}

export class Pronalazac extends Lice {}

export class NazivPatent {
  public srpski: string;
  public engleski: string;
  constructor() {
    this.srpski = "";
    this.engleski = "";
  }

  public static validate(naziv: NazivPatent) {
    return naziv.srpski && naziv.engleski;
  }
}

export enum TipPrvobitnePrijave {
  izdvojena = "izdvojena",
  dopunska = "dopunska",
}
export class PrvobitnaPrijava {
  public brojPrijave: string;
  public datumPodnosenja: string;
  public tipPrijave: TipPrvobitnePrijave;

  constructor() {
    this.brojPrijave = "";
    this.datumPodnosenja = "";
    this.tipPrijave = TipPrvobitnePrijave.izdvojena;
  }

  static validate(prijava: PrvobitnaPrijava) {
    if (this.isEmpty(prijava)) {
      return true;
    }
    else {
      return this.isFull(prijava);
    }
  }

  public static isFull(prijava: PrvobitnaPrijava) {
    return prijava.brojPrijave && prijava.datumPodnosenja;
  }

  public static isEmpty(prijava:PrvobitnaPrijava) {
    return !prijava.datumPodnosenja && !prijava.brojPrijave;
  }
}

export class RanijaPrijava {
  public brojPrijave: string;
  public datumPodnosenja: string;
  public dvoslovnaOznaka: string;

  constructor() {
    this.brojPrijave = "";
    this.datumPodnosenja = "";
    this.dvoslovnaOznaka = "";
  }
  static validate(prijava: RanijaPrijava) {

    if (this.isEmpty(prijava)) {
      return true;
    }
    else {
      return this.isFull(prijava);
    }
  }

  public static isFull(prijava: RanijaPrijava) {
    return prijava.brojPrijave && prijava.datumPodnosenja && prijava.dvoslovnaOznaka;
  }

  static isEmpty(prijava:RanijaPrijava) {
    return !prijava.datumPodnosenja && !prijava.brojPrijave && !prijava.dvoslovnaOznaka;
  }
}

export class Dostavljanje extends Adresa {
  public pisano: boolean;
  public elektronski: boolean;
  constructor() {
    super();
    this.pisano = false;
    this.elektronski = true;
  }

  public static validate(dostavljanje:Dostavljanje):boolean {
    if ((dostavljanje.pisano && !dostavljanje.elektronski) || (dostavljanje.elektronski && !dostavljanje.pisano)) {
      return Adresa.isEmpty(dostavljanje) || Adresa.validate(dostavljanje);
    }
    return false;
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
}
