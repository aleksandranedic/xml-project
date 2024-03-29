import Search from "../../page/Search";
import RequestTypeContext from "../../store/request-type-context";
import {useState} from "react";
import PatentContext from "../../store/patent-zahtevi-context";
import Zahtevi from "../zahtevi/Zahtevi";
import {ZahtevData} from "../types";

const SearchPatent: React.FunctionComponent = () => {
    const [type, setType] = useState<"patent" | "autor" | "zig" | null>("patent");
    const [patentZahtevi, setPatentZahtevi] = useState<ZahtevData[]>([]);
    const port = "8002";

    return (
        <RequestTypeContext.Provider value={{type, setType, port}}>
            <PatentContext.Provider value={{patentZahtevi, setPatentZahtevi}}>
                <div className="p-8 h-full">
                    <Search/>
                </div>
                <div className="py-10 px-36 flex flex-wrap gap-5 h-full">
                    <Zahtevi zahtevi={patentZahtevi} />                
                </div>
            </PatentContext.Provider>
        </RequestTypeContext.Provider>

    );
}

export default SearchPatent;