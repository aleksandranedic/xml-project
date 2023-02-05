import {Dispatch, SetStateAction, useContext} from "react";
import UserContext, {Role} from "../../store/user-context";
import {ZahtevZaPriznanjeAutorska} from "../autorska/types";
import {Zahtev} from "../types";
import {ZahtevZaPriznanjeZiga} from "../zig/types";
import ZahtevAutorska from "./ZahtevAutorska";
import ZahtevPatent from "./ZahtevPatent";
import ZahtevZig from "./ZahtevZig";
import axios from "axios";
import {toast} from "react-toastify";

interface ZahtevModalProps {
    showModal: boolean,
    setShowModal: Dispatch<SetStateAction<boolean>>
    zahtev: Zahtev | null;
}

const ZahtevBody: React.FunctionComponent<{ zahtev: Zahtev }> = ({zahtev}) => {
    if (zahtev instanceof ZahtevZaPriznanjeAutorska) return <ZahtevAutorska zahtev={zahtev}/>;
    else if (zahtev instanceof ZahtevZaPriznanjeZiga) return <ZahtevZig zahtev={zahtev}/>;
    return <ZahtevPatent zahtev={zahtev}/>;
}
const ZahtevModal: React.FunctionComponent<ZahtevModalProps> = ({zahtev, showModal, setShowModal}) => {
    const {user} = useContext(UserContext);
    const setType = (zahtev: Zahtev) => {
        if (zahtev instanceof ZahtevZaPriznanjeAutorska) return 'autorskog dela';
        else if (zahtev instanceof ZahtevZaPriznanjeZiga) return 'žiga';
        return 'patenta';
    }

    const leaveReview = (status: string) => {
        let port;
        let path;
        let type = setType(zahtev!);
        if (type === "patenta") {
            port = "8002"
            path = " patent";
        }
        if (type === "žiga") {
            port = "8000"
            path = "zig"
        }
        if (type === "autorskog dela") {
            port = "8003"
            path = "autor"
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
        ResenjeDto = builder.buildObject({resenje:ResenjeDto});
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
                        className="justify-center items-center flex overflow-x-hidden overflow-y-auto fixed inset-0 z-50 outline-none focus:outline-none">
                        <div className="relative w-auto my-6 mx-auto max-w-3xl">
                            {/*content*/}
                            <div
                                className="border-0 rounded-lg shadow-lg relative flex flex-col w-full bg-white outline-none focus:outline-none">
                                {/*header*/}
                                <div
                                    className="flex items-start justify-between p-5 border-b border-solid border-slate-200 rounded-t">
                                    <h3 className="text-3xl font-semibold flex justify-center w-full">
                                        Zahtev za priznanje {setType(zahtev)}
                                    </h3>
                                    <button
                                        className="p-1 ml-auto bg-transparent border-0 text-black float-right text-3xl leading-none font-semibold outline-none focus:outline-none"
                                        onClick={() => setShowModal(false)}>
                                        <span
                                            className="bg-transparent text-black h-6 w-6 block outline-none focus:outline-none">×</span>
                                    </button>
                                </div>
                                {/*body*/}
                                <div className="relative p-6 flex-auto">
                                    <ZahtevBody zahtev={zahtev}/>
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
                                                className="bg-blue-500 hover:bg-blue-700 text-white font-semibold px-4 py-1 rounded w-fit text-sm"
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

export default ZahtevModal;