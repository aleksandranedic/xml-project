import {Prilog} from "../types";
import {useContext, useRef} from "react";
import RequestTypeContext from "../../store/request-type-context";
import axios from "axios";

interface ZahtevButtonData {
    prilog: Prilog;


}

function ZahtevButton(props: ZahtevButtonData) {
    const {prilog} = props;
    const {type, port} = useContext(RequestTypeContext);

    const linkRef = useRef<HTMLAnchorElement>(null);
    const downloadFile = async () => {
        try {
            let strings = prilog.putanja.split("/");
            let fileName: string | undefined = strings.at(strings.length - 1);

            const response = await axios.get(`http://localhost:${port}/download/${fileName}.${prilog.naslov}`, {
                responseType: 'blob',
            });

            let fileType = 'application/' + prilog.naslov
            switch (prilog.naslov){
                case "html":
                    fileType="text/html";
                    break;
                case "json":
                    fileType="application/json";
                    break;
                case "pdf":
                    fileType="application/pdf";
                    break;
                case "rdf":
                    fileType="application/rdf+xml";
                    break;
            }
            const file = new Blob([response.data], {type: fileType});
            const fileUrl = URL.createObjectURL(file);

            if (linkRef.current) {
                linkRef.current!.href = fileUrl;
                if (fileName) {
                    linkRef.current.setAttribute('download', fileName);
                }
                linkRef.current!.click();
            }

            URL.revokeObjectURL(fileUrl);
        } catch (error) {
            console.error(error);
        }
    };
    return (
        <>
            <div>
                <button onClick={downloadFile}
                        className="inline-flex items-center px-4 py-2  text-xs font-medium text-center text-gray-900 bg-white border border-gray-300 rounded-lg hover:bg-gray-100 focus:ring-4 focus:outline-none focus:ring-gray-200">
                    {prilog.naslov.toUpperCase()}
                </button>
                < a ref={linkRef} style={{display: 'none'}}/>
            </div>
        </>
    )
}

export default ZahtevButton;