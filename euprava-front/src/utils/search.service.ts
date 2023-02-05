import { Prilog, ZahtevData } from "../components/types";

export const ConvertToZahtevi = (jsonDataRes: any): ZahtevData[] => {
  let zahtevi: ZahtevData[] = [];
  if (!Object.keys(jsonDataRes.ListN).includes("item")) {
    return zahtevi;
  }
  let json = jsonDataRes.ListN.item;
  if (json.status) {
    const zahtev = createZahtev(json);
    zahtevi.push(zahtev);
  } else {
    for (let jsonData of json) {
      let zahtev = createZahtev(jsonData);
      zahtevi.push(zahtev);
    }
  }
  return zahtevi;
};

export const createZahtev = (jsonData: any): ZahtevData => {
  let zahtev: ZahtevData = new ZahtevData();
  zahtev.status = jsonData.status["_text"];
  zahtev.brojPrijave = jsonData.brojPrijave["_text"];
  zahtev.html = jsonData.html["_text"];
  zahtev.datum = jsonData.datum["_text"];
  let prilozi: Prilog[] = [];

  if (jsonData.prilozi) {
    for (let prilog of jsonData.prilozi.prilozi) {
      prilozi.push({
        putanja: prilog.putanja["_text"],
        naslov: prilog.naslov["_text"],
      });
    }
  }
  zahtev.prilozi = prilozi;
  return zahtev;
};
