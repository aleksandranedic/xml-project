import Search from "../../page/Search";
import {useState} from "react";
import RequestTypeContext from "../../store/request-type-context";
import ZigContext from "../../store/zig-zahtevi-context";
import Zahtevi from "../zahtevi/Zahtevi";
import {ZahtevData} from "../types";

const SearchZig: React.FunctionComponent = () => {
    const [type, setType] = useState<"patent" | "autor" | "zig" | null>("zig");
    const [zigZahtevi, setZigZahtevi] = useState<ZahtevData[]>([]);
    const port = "8000";

    return (
        <RequestTypeContext.Provider value={{type, setType, port}}>
            <ZigContext.Provider value={{zigZahtevi, setZigZahtevi}}>
                <div className="p-8 h-full">
                    <Search/>
                </div>
                <div className="py-10 px-36 flex flex-wrap gap-5 h-full">
                    <Zahtevi zahtevi={zigZahtevi}/>                    
                </div>
            </ZigContext.Provider>
        </RequestTypeContext.Provider>

    );
}
 
export default SearchZig;