import {Dispatch, SetStateAction, useContext} from "react";
import UserContext, {Role} from "../../store/user-context";
import {Prilog, ZahtevData} from "../types";
import RequestTypeContext from "../../store/request-type-context";
import axios from "axios";
import {toast} from "react-toastify";

interface ZahtevModalProps {
    showModal: boolean,
    setShowModal: Dispatch<SetStateAction<boolean>>
    zahtev: ZahtevData | null;
}

const ZahtevBody: React.FunctionComponent<{ zahtev: ZahtevData }> = ({zahtev}) => {
    return (<div dangerouslySetInnerHTML={{__html: zahtev.html}}/>)
}
const ZahtevModal: React.FunctionComponent<ZahtevModalProps> = ({zahtev, showModal, setShowModal}) => {
    const {user} = useContext(UserContext);

    const {type} = useContext(RequestTypeContext);

    const setType = () => {

        switch (type) {
            case 'patent':
                return 'patenta';
            case 'zig':
                return 'žiga';
            case 'autor':
                return 'autorskog dela';
        }
    }

    const leaveReview = (status: string) => {
        let port;
        let path;

        switch (type) {
            case 'patent': {
                port = "8002";
                path = "patent";
                break;
            }

            case 'zig': {
                port = "8000"
                path = "zig"
                break;
            }
            case 'autor': {
                port = "8003"
                path = "autor"
                break;
            }
        }


        let ResenjeDto = {
            ime: user?.name,
            prezime: user?.surname,
            obrazlozenje: "Obrazlozenje", //TODO treba ubaciti da dopuni
            status: status,
            brojPrijave: "A-38169" //TODO treba izmeniti
        }
        const xml2js = require("xml2js");
        const builder = new xml2js.Builder();
        ResenjeDto = builder.buildObject({resenje: ResenjeDto});
        console.log(ResenjeDto)
        console.log(`http://localhost:${port}/${path}`)

        axios.put(`http://localhost:${port}/${path}`, ResenjeDto, {
            headers: {
                "Content-Type": "application/xml",
                Accept: "*/*"
            }
        }).then(response => {
            console.log(response.data)
            const convert = require("xml-js");
            const jsonData = convert.xml2js(response.data, {
                compact: true,
                alwaysChildren: true,
            });
            console.log(jsonData);
            toast.success(response.data)
        }).catch(() => {
            toast.error("Greška pri pretrazi.")
        })

    }

    return (
        <>
            {
                showModal && zahtev &&
                <>
                    <div
                        className="justify-center items-center pt-10 max-h-[40rem] flex overflow-x-hidden overflow-y-auto fixed inset-0 z-50 outline-none focus:outline-none">
                        <div className="relative  w-auto mb-20 max-h-[35rem] mx-auto max-w-3xl">
                            {/*content*/}
                            <div
                                className="border-0 rounded-lg shadow-lg relative flex flex-col w-full bg-white outline-none focus:outline-none">
                                {/*header*/}
                                <div
                                    className="flex w-full items-start justify-between px-5 py-0 border-solid border-slate-200 rounded-t">
                                    <button
                                        className="p-1 ml-auto flex items-center bg-transparent border-0 text-black float-right text-3xl leading-none font-semibold outline-none focus:outline-none"
                                        onClick={() => setShowModal(false)}>
                                        <span
                                            className="bg-transparent text-black h-6 w-6 block outline-none focus:outline-none">×</span>
                                    </button>
                                </div>
                                {/*body*/}
                                <div className="relative p-6 flex-auto">
                                    <ZahtevBody zahtev={zahtev}/>
                                </div>

                                <div className="grid grid-cols-3 gap-4 p-4">
                                    {zahtev.prilozi.map((prilog, index) => {
                                        return (
                                            <PrilogCard prilog={prilog}></PrilogCard>
                                        )

                                    })}

                                </div>

                                {user?.role === Role.WORKER &&
                                    <div
                                        className="flex w-full items-center justify-between p-6 border-t border-solid border-slate-200 rounded-b">
                                        <div className="flex gap-5">
                                            <select className="w-full p-1">
                                                <option value="xhtml">XHTML</option>
                                                <option value="pdf">PDF</option>
                                                <option value="pdf">RDF</option>
                                                <option value="pdf">JSON</option>
                                            </select>
                                            <button
                                                className="bg-blue-500 hover:bg-blue-700 text-white font-bold px-6 py-2 rounded w-fit"
                                                type="button">
                                                Preuzmi
                                            </button>
                                        </div>
                                        <div className="flex gap-3">
                                            <button
                                                onClick={() => {
                                                    leaveReview("Odbijen")
                                                }}
                                                className="bg-red-500 hover:bg-red-700 text-white font-semibold px-4 py-1 rounded w-fit text-sm"
                                                type="button">
                                                Odbij zahtev
                                            </button>
                                            <button onClick={() => {
                                                leaveReview("Odobren")
                                            }}
                                                    className="bg-green-500 hover:bg-green-700 text-white font-semibold px-4 py-1 rounded w-fit text-sm"
                                                    type="button">
                                                Odobri zahtev
                                            </button>
                                        </div>
                                    </div>
                                }
                            </div>
                        </div>
                    </div>
                    <div className="opacity-25 fixed inset-0 z-40 bg-black"></div>
                </>
            }
        </>);
}

interface PrilogCardProps {
    prilog: Prilog;
}

function PrilogCard(props: PrilogCardProps) {
    const {prilog} = props;
    return (

        <div
            className="w-full max-w-sm bg-white border border-gray-200 rounded-lg shadow">
            <div className="flex flex-col items-center py-6">
                <h5 className="mb-1 text-sm font-medium text-gray-900 text-center ">{prilog.naslov}</h5>
                <div className="flex space-x-3">
                    <a href={prilog.putanja} target="_blank"
                       className="inline-flex items-center px-4 py-2  text-xs font-medium text-center text-gray-900 bg-white border border-gray-300 rounded-lg hover:bg-gray-100 focus:ring-4 focus:outline-none focus:ring-gray-200"
                    >Pregledaj
                    </a>
                    <a href={prilog.putanja} download={prilog.naslov.replace(" ", "_") + ".pdf"}
                       className="inline-flex items-center px-4 py-2  text-xs font-medium text-center text-gray-900 bg-white border border-gray-300 rounded-lg hover:bg-gray-100 focus:ring-4 focus:outline-none focus:ring-gray-200"
                    >Preuzmi</a>
                </div>
            </div>
        </div>

    )
}

export default ZahtevModal;