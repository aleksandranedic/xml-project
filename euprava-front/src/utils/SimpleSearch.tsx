import {useContext, useState} from "react"
import RequestTypeContext from "../store/request-type-context";
import axios from "axios";
import {toast} from "react-toastify";
import PatentContext from "../store/patent-zahtevi-context";
import { ZahtevZaPriznanjePatenta } from "../components/patent/types";
import ZigContext from "../store/zig-zahtevi-context";
import { ZahtevZaPriznanjeZiga } from "../components/zig/types";
import AutorskaContext from "../store/autorska-zahtevi-context";
import { ZahtevZaPriznanjeAutorska } from "../components/autorska/types";
import convert from "xml-js";

interface SimpleSearchProps {
    
}
 
const SimpleSearch: React.FunctionComponent<SimpleSearchProps> = () => {
    const {type} = useContext(RequestTypeContext);
    const {setPatentZahtevi} = useContext(PatentContext);
    const {setZigZahtevi} = useContext(ZigContext);
    const {setAutorskaZahtevi} = useContext(AutorskaContext);

    const [keywords, setKeywords] = useState<string[]>([]);

    const handleBadgeRemove = (keyword: string) => {
        setKeywords(keywords.filter(kw => kw !== keyword));
    }

    function handleKeyDown(event: any) {
        if (event.keyCode === 13) {
          setKeywords([...keywords, event.target.value])
        }
    }

    function search() {
        let port;
        if (type === "patent") {
            port = "8002"
        }
        if (type === "zig") {
            port = "8000"
        }
        if (type === "autor") {
            port = "8003"
        }
        let terms:string = keywords.join(";");

        // const xml2js = require("xml2js");
        // const builder = new xml2js.Builder();
        // let xml_terms = builder.buildObject(terms);
        // Ovo nece hteti

        axios.get(`http://localhost:${port}/search/basic?terms=${terms}`,{
            headers:{
                "Content-Type":"application/xml",
                Accept: "*/*"
            }
        }).then(response => {
            const convert = require("xml-js");
            const jsonData = convert.xml2js(response.data, {
                compact: true,
                alwaysChildren: true,
            });
            switch (type) {
                case 'patent': setPatentZahtevi([new ZahtevZaPriznanjePatenta()]); break;
                case 'zig': setZigZahtevi([new ZahtevZaPriznanjeZiga()]); break;
                case 'autor': setAutorskaZahtevi([new ZahtevZaPriznanjeAutorska()]);
            }
            console.log(jsonData);
        }).catch(() => {
            switch (type) {
                case 'patent': setPatentZahtevi([new ZahtevZaPriznanjePatenta()]); break;
                case 'zig': setZigZahtevi([new ZahtevZaPriznanjeZiga()]); break;
                case 'autor': setAutorskaZahtevi([new ZahtevZaPriznanjeAutorska()]);
            }
            toast.error("Greška pri pretrazi.")
        })
    }

    return (
        <div className="overflow-auto flex justify-center w-1/2">
            <div className="overflow-auto flex gap-1 border border-gray-dark items-center w-10/12 justify-start rounded-l-lg">                  
                <div className="w-50vh flex items-center justify-end">
                    {keywords.map( keyword => 
                        <div className="flex items-center px-2 py-1 ml-1 text-sm text-gray-light bg-gray-800 rounded ">
                            {keyword}
                            <button type="button" className="flex items-center p-0.5 ml-2 text-sm text-gray-light border-gray-light bg-transparent rounded-sm hover:bg-gray-light hover:text-gray-dark" onClick={()=>handleBadgeRemove(keyword)}>
                                <svg aria-hidden="true" className="w-3.5 h-3.5" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z" clip-rule="evenodd"></path></svg>
                            </button>
                        </div>    
                    )}                  
                </div>
                <input onKeyDown={handleKeyDown} type='text' className={`w-[75vh] rounded-none py-2 bg-transparent border-0 appearance-none-full focus:outline-none focus:!ring-0 `}/>
            </div>
            <button className="text-white rounded-r-lg px-4 py-2 bg-green-700 w-2/12 " onClick={()=>search()}>Pretraga</button>
        </div>
     );
}
 
export default SimpleSearch;