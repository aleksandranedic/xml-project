import { createContext } from "react";
import {ZahtevData} from "../components/types";

interface ZigZahtevContextType {
  zigZahtevi: ZahtevData[];
  setZigZahtevi: React.Dispatch<React.SetStateAction<ZahtevData[]>>;
}

const ZigContext = createContext<ZigZahtevContextType>({
  zigZahtevi: [],
  setZigZahtevi: () => {},
});

export default ZigContext;
