import { InformacijeOResenju, Lice } from "../types";

export class ZahtevZaPriznanjeAutorska {
  public podnosilac: Podnosilac = new Podnosilac();
  public punomocnik: Punomocnik = new Punomocnik();
  public autor: Autor[] = [];
  public autorskoDelo: AutorskoDelo = new AutorskoDelo();
  public prilozeniDokumenti: PrilozeniDokumenti = new PrilozeniDokumenti();

  public informacijeOResenju: InformacijeOResenju = new InformacijeOResenju();
  public datumPodnosenja: Date = new Date();
  public sifraZahteva: string = "";
}

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
  public vrstaDela: VrstaDela | null;
  public drugaVrsta: string;
  public forma: string;
  public naslovPrerade: string;
  public imeAutoraPrerade: string;
  public nacinKoriscenja: string;

  constructor() {
    this.radniOdnos = false;
    this.naslov = "";
    this.vrstaDela = null;
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
