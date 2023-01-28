import {useState} from "react"

interface SimpleSearchProps {
    
}
 
const SimpleSearch: React.FunctionComponent<SimpleSearchProps> = () => {
    const [keywords, setKeywords] = useState<string[]>([]);

    const handleBadgeRemove = (keyword: string) => {
        setKeywords(keywords.filter(kw => kw !== keyword));
    }

    function handleKeyDown(event: any) {
        if (event.keyCode === 13) {
          setKeywords([...keywords, event.target.value])
        }
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
            <button className="text-white rounded-r-lg px-4 py-2 bg-green-700 w-2/12 ">Pretraga</button>
        </div>
     );
}
 
export default SimpleSearch;