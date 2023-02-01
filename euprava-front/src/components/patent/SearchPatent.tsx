import Search from "../../page/Search";
import RequestTypeContext from "../../store/request-type-context";
import {useState} from "react";

const SearchPatent: React.FunctionComponent = () => {
    const [type, setType] = useState<"patent" | "autor" | "zig" | null>("patent");

    return (
        <RequestTypeContext.Provider value={{type, setType}}>
            <div className="p-8 h-full">
                <Search/>
            </div>
        </RequestTypeContext.Provider>

    );
}

export default SearchPatent;