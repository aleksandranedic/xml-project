import {useContext, useState} from "react"
import RequestTypeContext from "../store/request-type-context";
import axios from "axios";
import {toast} from "react-toastify";
import PatentContext from "../store/patent-zahtevi-context";
import ZigContext from "../store/zig-zahtevi-context";
import AutorskaContext from "../store/autorska-zahtevi-context";
import {Prilog, ZahtevData} from "../components/types";
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
          event.target.value = '';
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

        axios.get(`http://localhost:${port}/search/basic?terms=${terms}`,{
            headers:{
                "Content-Type":"application/xml",
                Accept: "*/*"
            }
        }).then(response => {
            const convert = require("xml-js");
            const jsonDataRes = convert.xml2js(response.data, {
                compact: true,
                alwaysChildren: true,
            });
            
            let zahtevi:ZahtevData[] = [];
            let json = jsonDataRes.ListN.item;
            if (json.status) {
                console.log("uso")
                const zahtev = createZahtev(json)
                zahtevi.push(zahtev);
            } else {
                for (let jsonData of json) {
                    let zahtev = createZahtev(jsonData);
                    zahtevi.push(zahtev);
                }
            }
            console.log(zahtevi)
            switch (type) {
                case 'patent': setPatentZahtevi(zahtevi); break;
                case 'zig': setZigZahtevi(zahtevi); break;
                case 'autor': setAutorskaZahtevi(zahtevi);
            }
        }).catch(() => {
            toast.error("Greška pri pretrazi.")
        })
    }

    const createZahtev = (jsonData: any):ZahtevData => {
        let zahtev:ZahtevData = new ZahtevData();
        zahtev.status = jsonData.status["_text"]
        zahtev.brojPrijave = jsonData.brojPrijave["_text"]
        zahtev.html = jsonData.html["_text"]
        zahtev.datum = jsonData.datum["_text"]          
        let prilozi:Prilog[] = [];
        
        if (jsonData.prilozi) {
            for (let prilog of jsonData.prilozi.prilozi) {
                prilozi.push({
                    putanja: prilog.putanja["_text"],
                    naslov: prilog.naslov["_text"]
                })
            }
        }
        zahtev.prilozi = prilozi;
        return zahtev;
    } 

    return (
        <div className="overflow-auto flex justify-center w-1/2">
            <div className="overflow-auto flex gap-1 border border-gray-dark items-center w-10/12 justify-start rounded-l-lg">                  
                <div className="w-50vh flex items-center justify-end">
                    {keywords.map( keyword => 
                        <div className="flex items-center px-2 py-1 ml-1 text-sm text-gray-light bg-gray-800 rounded ">
                            {keyword}
                            <button type="button" className="flex items-center p-0.5 ml-2 text-sm text-gray-light border-gray-light bg-transparent rounded-sm hover:bg-gray-light hover:text-gray-dark" onClick={()=>handleBadgeRemove(keyword)}>
                                <svg aria-hidden="true" className="w-3.5 h-3.5" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fillRule="evenodd" d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z" clipRule="evenodd"></path></svg>
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