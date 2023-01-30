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

export class Kontakt {
  public telefon: string;
  public eposta: string;
  public faks: string;

  constructor() {
    this.telefon = "";
    this.eposta = "";
    this.faks = "";
  }
}

export class Adresa {
  public ulica: string;
  public broj: string;
  public postanskiBroj: string;
  public grad: string;
  public drzava: string;

  constructor() {
    this.ulica = "";
    this.broj = "";
    this.postanskiBroj = "";
    this.grad = "";
    this.drzava = "";
  }
}

export interface Info {
  ime: string;
  prezime?: string;
  drzavljanstvo?: string;
}
