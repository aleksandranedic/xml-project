import { createContext } from "react";
import { ZahtevZaPriznanjePatenta } from "../components/patent/types";

interface PatentZahtevContextType {
  patentZahtevi: ZahtevZaPriznanjePatenta[];
  setPatentZahtevi: React.Dispatch<
    React.SetStateAction<ZahtevZaPriznanjePatenta[]>
  >;
}

const PatentContext = createContext<PatentZahtevContextType>({
  patentZahtevi: [],
  setPatentZahtevi: () => {},
});

export default PatentContext;
