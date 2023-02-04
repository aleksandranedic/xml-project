import { createContext } from "react";
import { ZahtevZaPriznanjeAutorska } from "../components/autorska/types";

interface AutorskaZahtevContextType {
  autorskaZahtevi: ZahtevZaPriznanjeAutorska[];
  setAutorskaZahtevi: React.Dispatch<
    React.SetStateAction<ZahtevZaPriznanjeAutorska[]>
  >;
}

const AutorskaContext = createContext<AutorskaZahtevContextType>({
  autorskaZahtevi: [],
  setAutorskaZahtevi: () => {},
});

export default AutorskaContext;
