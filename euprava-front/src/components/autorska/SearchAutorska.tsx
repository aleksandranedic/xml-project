import Search from "../../page/Search";
import {useState} from "react";
import RequestTypeContext from "../../store/request-type-context";
import AutorskaContext from "../../store/autorska-zahtevi-context";
import Zahtevi from "../zahtevi/Zahtevi";
import {ZahtevData} from "../types";

const SearchAutorska: React.FunctionComponent = () => {
    const [type, setType] = useState<"patent" | "autor" | "zig" | null>("autor");
    const [autorskaZahtevi, setAutorskaZahtevi] = useState<ZahtevData[]>([]);

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