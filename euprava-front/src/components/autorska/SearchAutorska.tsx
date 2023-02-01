import Search from "../../page/Search";
import {useState} from "react";
import RequestTypeContext from "../../store/request-type-context";
import { ZahtevZaPriznanjeAutorska } from "./types";
import AutorskaContext from "../../store/autorska-zahtevi-context";
import Zahtevi from "../zahtevi/Zahtevi";

const SearchAutorska: React.FunctionComponent = () => {
    const [type, setType] = useState<"patent" | "autor" | "zig" | null>("autor");
    const [autorskaZahtevi, setAutorskaZahtevi] = useState<ZahtevZaPriznanjeAutorska[]>([]);

    return (
        <RequestTypeContext.Provider value={{type, setType}}>
            <AutorskaContext.Provider value={{autorskaZahtevi, setAutorskaZahtevi}}>
                <div className="p-8 h-fit">
                    <Search/>
                </div>
                <div className="py-10 px-36 flex flex-wrap gap-5 h-full">
                    <Zahtevi zahtevi={autorskaZahtevi} />
                </div>

            </AutorskaContext.Provider>
        </RequestTypeContext.Provider>

    );
}
 
export default SearchAutorska;