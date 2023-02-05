import { createContext } from "react";
import {ZahtevData} from "../components/types";

interface AutorskaZahtevContextType {
  autorskaZahtevi: ZahtevData[];
  setAutorskaZahtevi: React.Dispatch<
    React.SetStateAction<ZahtevData[]>
  >;
}

const AutorskaContext = createContext<AutorskaZahtevContextType>({
  autorskaZahtevi: [],
  setAutorskaZahtevi: () => {},
});

export default AutorskaContext;
