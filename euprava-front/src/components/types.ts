

export class Prilog {
  public putanja: string;
  public naslov: string;

  constructor() {
    this.putanja = "";
    this.naslov = "";
  }
}
export class ZahtevData {
  public status: string;
  public datum: string;
  public brojPrijave: string;
  public html: string;
  public prilozi:Prilog[];

  constructor() {
    this.status = "";
    this.datum = "";
    this.brojPrijave = "";
    this.html = "";
    this.prilozi = [];
  }


}
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
  public static validate(lice: Lice): boolean {
    return (
      Kontakt.validate(lice.kontakt) &&
      Adresa.validate(lice.adresa) &&
      Info.validate(lice.info)
    );
  }

  public static isEmpty(lice: Lice): boolean {
    return (
      Kontakt.isEmpty(lice.kontakt) &&
      Adresa.isEmpty(lice.adresa) &&
      Info.isEmpty(lice.info)
    );
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

  public static validate(kontakt: Kontakt) {
    return (
      kontakt.eposta !== "" && kontakt.faks !== "" && kontakt.telefon !== ""
    );
  }

  public static isEmpty(kontakt: Kontakt) {
    return (
      kontakt.eposta === "" && kontakt.faks === "" && kontakt.telefon === ""
    );
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

  public static validate(adresa: Adresa): boolean {
    return (
      adresa.ulica !== "" &&
      adresa.broj !== "" &&
      adresa.postanskiBroj !== "" &&
      adresa.grad !== "" &&
      adresa.drzava !== ""
    );
  }

  public static isEmpty(adresa: Adresa): boolean {
    return (
      adresa.ulica === "" &&
      adresa.broj === "" &&
      adresa.postanskiBroj === "" &&
      adresa.grad === "" &&
      adresa.drzava === ""
    );
  }
}

export class Info {
  ime: string;
  prezime?: string;
  drzavljanstvo?: string;

  constructor() {
    this.ime = "";
  }
  static validate(info: Info): boolean {
    if (info.drzavljanstvo) {
      return (
        info.ime !== "" &&
        typeof info.prezime !== undefined &&
        info.prezime !== ""
      );
    } else return info.ime !== "";
  }

  static isEmpty(info: Info): boolean {
    return info.ime === "" && !info.prezime && !info.drzavljanstvo;
  }
}

export enum Status {
  NA_CEKANJU = "NA ČEKANJU",
  ODOBRENO = "ODOBRENO",
  ODBIJENO = "ODBIJENO",
}

export class InformacijeOResenju {
  public status = Status.NA_CEKANJU;
  public datumKreiranjaResenja?: Date;
  public zavedenPodSifrom?: string;
  public sluzbenik?: Info;
  public referenca?: string;
  public obrazlozenje?: string;
}
