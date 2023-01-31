import { Lice } from "../types";

export class Podnosilac extends Lice {
  public pseudonim: string;
  constructor() {
    super();
    this.pseudonim = "";
  }
}

export class Punomocnik extends Lice {}

export class Autor extends Podnosilac {
  public anoniman: boolean;
  public godinaSmrti: number;
  constructor() {
    super();
    this.anoniman = false;
    this.godinaSmrti = new Date().getFullYear();
  }
}

export enum VrstaDela {
  knjizevno = "knjizevno",
  muzicko = "muzicko",
  likovno = "likovno",
  racunarsko = "racunarsko",
}

export class AutorskoDelo {
  public radniOdnos: boolean;
  public naslov: string;
  public vrstaDela: VrstaDela;
  public drugaVrsta: string;
  public forma: string;
  public naslovPrerade: string;
  public imeAutoraPrerade: string;
  public nacinKoriscenja: string;

  constructor() {
    this.radniOdnos = false;
    this.naslov = "";
    this.vrstaDela = VrstaDela.knjizevno;
    this.drugaVrsta = "";
    this.forma = "";
    this.naslovPrerade = "";
    this.imeAutoraPrerade = "";
    this.nacinKoriscenja = "";
  }
}

export class PrilozeniDokumenti {
  public opisDela?: FileList;
  public primerDela?: FileList;
  public uplataTakse?: FileList;
  public pravniOsnov?: FileList;
  public zajednickiPredstavnik?: FileList;
  public punomocje?: FileList;
}
