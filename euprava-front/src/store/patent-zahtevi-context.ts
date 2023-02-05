import { createContext } from "react";
import {ZahtevData} from "../components/types";

interface PatentZahtevContextType {
  patentZahtevi: ZahtevData[];
  setPatentZahtevi: React.Dispatch<
    React.SetStateAction<ZahtevData[]>
  >;
}

const PatentContext = createContext<PatentZahtevContextType>({
  patentZahtevi: [],
  setPatentZahtevi: () => {},
});

export default PatentContext;
