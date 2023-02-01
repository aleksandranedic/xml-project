import { Adresa, InformacijeOResenju, Lice } from "../types";

export class ZahtevZaPriznanjePatenta {
  public podnosilac: Podnosilac = new Podnosilac();
  public punomocnik: Punomocnik = new Punomocnik();
  public pronalazac: Pronalazac[] = [];
  public nazivPatent: NazivPatent = new NazivPatent();
  public prvobitnaPrijava: PrvobitnaPrijava = new PrvobitnaPrijava();
  public ranijePrijave: RanijaPrijava[] = [];
  public dostavljanje: Dostavljanje = new Dostavljanje();
  public prilozeniDokumenti: PrilozeniDokumenti = new PrilozeniDokumenti();

  public informacijeOResenju: InformacijeOResenju = new InformacijeOResenju();
  public datumPodnosenja: Date = new Date();
  public sifraZahteva: string = "";
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

  public static validate(punomocnik: Punomocnik) {
    if (
      !punomocnik.zaPrijem &&
      !punomocnik.zaZastupanje &&
      !punomocnik.zajednickiPredstavnik &&
      Lice.isEmpty(punomocnik)
    ) {
      return true;
    } else if (Lice.validate(punomocnik)) {
      if (
        (punomocnik.zaZastupanje && !punomocnik.zajednickiPredstavnik) ||
        (punomocnik.zajednickiPredstavnik && punomocnik.zaZastupanje)
      ) {
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
  public datum: string;
  public tip: TipPrvobitnePrijave;

  constructor() {
    this.brojPrijave = "";
    this.datum = "";
    this.tip = TipPrvobitnePrijave.izdvojena;
  }

  static validate(prijava: PrvobitnaPrijava) {
    if (this.isEmpty(prijava)) {
      return true;
    } else {
      return prijava.brojPrijave && prijava.datum;
    }
  }

  static isEmpty(prijava: PrvobitnaPrijava) {
    return !prijava.datum && !prijava.brojPrijave;
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
  static validate(prijava: RanijaPrijava) {
    if (this.isEmpty(prijava)) {
      return true;
    } else {
      return prijava.brojPrijave && prijava.datum && prijava.oznaka;
    }
  }

  static isEmpty(prijava: RanijaPrijava) {
    return !prijava.datum && !prijava.brojPrijave && !prijava.oznaka;
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

  public static validate(dostavljanje: Dostavljanje): boolean {
    if (
      (dostavljanje.pisano && !dostavljanje.elektronski) ||
      (dostavljanje.elektronski && !dostavljanje.pisano)
    ) {
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
