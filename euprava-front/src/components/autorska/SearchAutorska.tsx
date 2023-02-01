import Search from "../../page/Search";
import {useState} from "react";
import RequestTypeContext from "../../store/request-type-context";

const SearchAutorska: React.FunctionComponent = () => {
    const [type, setType] = useState<"patent" | "autor" | "zig" | null>("autor");

    return (
        <RequestTypeContext.Provider value={{type, setType}}>
            <div className="p-8 h-full">
                <Search/>
            </div>
        </RequestTypeContext.Provider>

    );
}
 
export default SearchAutorska;