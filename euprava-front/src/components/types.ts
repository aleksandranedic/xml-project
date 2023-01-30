export class Lice {
  public info: Info;
  public adresa: Adresa;
  public kontakt: Kontakt;
  constructor() {
    this.info = { ime: "", prezime: "", drzavljanstvo: "" };
    this.adresa = {
      ulica: "",
      broj: "",
      postanskiBroj: "",
      grad: "",
      drzava: "",
    };
    this.kontakt = { telefon: "", eposta: "", faks: "" };
  }
}

export interface Kontakt {
  telefon: string;
  eposta: string;
  faks: string;
}

export interface Adresa {
  ulica: string;
  broj: string;
  postanskiBroj: string;
  grad: string;
  drzava: string;
}

export interface Info {
  ime: string;
  prezime?: string;
  drzavljanstvo?: string;
}

export class Pronalazac extends Lice {
  public pronalazacNaveden: boolean;
  constructor() {
    super();
    this.pronalazacNaveden = false;
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
