import {Lice} from "../types";

export class Podnosilac extends Lice {}

export class Punomocnik extends Lice {}

export enum VrstaA {
  individualni = "individualni",
  kolektivni = "kolektivni",
  garancije = "garancije",
}

export enum VrstaB {
  verbalni = "verbalni",
  graficki = "graficki",
  kombinovani = "kombinovani",
  trodimenzionalni = "trodimenzionalni",
}
export class InformacijeOZigu {
  public vrstaA: VrstaA;
  public vrstaB: VrstaB | null;
  public drugaVrstaB: string;
  public boje: string;
  public transliteracija: string;
  public prevod: string;
  public opis: string;
  public klasifikacija: number[];
  public pravo: string;

  constructor() {
    this.vrstaA = VrstaA.garancije;
    this.vrstaB = null;
    this.drugaVrstaB = "";
    this.boje = "";
    this.transliteracija = "";
    this.prevod = "";
    this.opis = "";
    this.klasifikacija = [];
    this.pravo = "";
  }
}

export class PlaceneTakse {
  public osnovna: number;
  public naziv: string;
  public zaKlasa: number;
  public grafickoResenje: number;
  public ukupno: number;

  constructor() {
    this.osnovna = 0;
    this.naziv = "";
    this.zaKlasa = 0;
    this.grafickoResenje = 0;
    this.ukupno = 0;
  }

  static validate(placeneTakse: PlaceneTakse): boolean {
    return (
      placeneTakse.naziv !== "" &&
      placeneTakse.osnovna !== 0 &&
      placeneTakse.zaKlasa !== 0 &&
      placeneTakse.grafickoResenje !== 0 &&
      placeneTakse.ukupno !== 0
    );
  }
}

export class PrilozeniDokumenti {
  public izgledZnaka?: FileList;
  public spisakRobeIUsluga?: FileList;
  public punomocje?: FileList;
  public opstiAkt?: FileList;
  public pravoPrvenstva?: FileList;
  public uplataTakse?: FileList;
}
