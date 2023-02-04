import { useState, useContext} from "react"
import AutorskaContext from "../store/autorska-zahtevi-context";
import PatentContext from "../store/patent-zahtevi-context";
import RequestTypeContext from "../store/request-type-context";
import ZigContext from "../store/zig-zahtevi-context";
import AdvancedSearch from "../utils/AdvancedSearch";
import SimpleSearch from "../utils/SimpleSearch";

const Search: React.FunctionComponent = () => {
    const [searchType, setSearchType] = useState<number>(1);
    const {type} = useContext(RequestTypeContext);
    const {setPatentZahtevi} = useContext(PatentContext); 
    const {setZigZahtevi} = useContext(ZigContext);
    const {setAutorskaZahtevi} = useContext(AutorskaContext);

    const switchSearch = (searchType: number) => {
        setSearchType(searchType);
        switch (type) {
            case 'patent': setPatentZahtevi([]); break;
            case 'zig': setZigZahtevi([]); break;
            case 'autor': setAutorskaZahtevi([]); break;
        }
    }
    return ( 
        <div className="px-12">
        <div className="shadow-xl rounded border-t">
            <div className="w-full flex justify-center items-center">
                <div onClick={e => switchSearch(1)} className={`w-1/2 flex justify-center items-center py-3 border-r-2 cursor-pointer ${searchType === 1 ? 'shadow-big bg-gray-100' : ''}`}>
                    <p className="font-semibold text-lg">Osnovna pretraga</p>
                </div>
                <div onClick={e => switchSearch(2)} className={`w-1/2 flex justify-center items-center py-3 border-r-2 cursor-pointer ${searchType === 2 ? 'shadow-big bg-gray-100' : ''}`}>
                    <p className="font-semibold text-lg">Napredna pretraga</p>
                </div>
            </div>

        </div>
        <div className="flex justify-center pt-10">
            { searchType === 1 && <SimpleSearch /> }
            { searchType === 2 && <AdvancedSearch /> }
            </div>
        </div>
     );
}
 
export default Search;