import { createContext } from "react";
import { ZahtevZaPriznanjeZiga } from "../components/zig/types";

interface ZigZahtevContextType {
  zigZahtevi: ZahtevZaPriznanjeZiga[];
  setZigZahtevi: React.Dispatch<React.SetStateAction<ZahtevZaPriznanjeZiga[]>>;
}

const ZigContext = createContext<ZigZahtevContextType>({
  zigZahtevi: [],
  setZigZahtevi: () => {},
});

export default ZigContext;
