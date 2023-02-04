import {useContext, useState} from "react";
import {IoIosAddCircle, IoIosCloseCircle} from 'react-icons/io'
import RequestTypeContext from "../store/request-type-context";
import axios from "axios";
import {toast} from "react-toastify";
import PatentContext from "../store/patent-zahtevi-context";
import {ZahtevZaPriznanjePatenta} from "../components/patent/types";
import ZigContext from "../store/zig-zahtevi-context";
import {ZahtevZaPriznanjeZiga} from "../components/zig/types";
import AutorskaContext from "../store/autorska-zahtevi-context";
import {ZahtevZaPriznanjeAutorska} from "../components/autorska/types";


enum Operator {
    EQUALS = '=',
    NON_EQULAS = '!='
}

enum LogicalOperator {
    I = 'I',
    ILI = 'ILI',
}

interface Params {
    logicalOperator: LogicalOperator,
    meta: string,
    operator: Operator,
    value: string
}

const AdvancedSearch: React.FunctionComponent = () => {

    const initialSearchParam = {
        logicalOperator: LogicalOperator.I,
        meta: "Broj_prijave",
        operator: Operator.EQUALS,
        value: ''
    };
    const [searchParams, setSearchParams] = useState<Params[]>([initialSearchParam]);
    const {type} = useContext(RequestTypeContext);
    const {setPatentZahtevi} = useContext(PatentContext);
    const {setZigZahtevi} = useContext(ZigContext);
    const {setAutorskaZahtevi} = useContext(AutorskaContext);

    const allParams = getAllParams()

    function getAllParams() {
        if (type === "patent") {
            return [
                {value: "Broj_prijave", name: "Broj prijave"},
                {value: "Podnosioc", name: "Ime i prezime/poslovno ime podnosioca"},
                {value: "Email", name: "E-pošta podnosioca"},
                {value: "Naziv_na_srpskom", name: "Naziv na srpskom jeziku"},
                {value: "Naziv_na_engleskom", name: "Naziv na engleskom jeziku"},
                {value: "Datum_podnosenja", name: "Priznati datum podnošenja"},
                {value: "Datum_prijema", name: "Datum prijema"}
            ]
        }
        if (type === "autor") {
            return [
                {value: "Broj_prijave", name: "Broj prijave"},
                {value: "Podnosioc", name: "Ime i prezime/poslovno ime podnosioca"},
                {value: "Email", name: "E-pošta podnosioca"},
                {value: "Naslov", name: "Naslov"},
                {value: "Vrsta", name: "Vrsta"},
                {value: "Forma", name: "Naziv na srpskom jeziku"},
                {value: "Datum_podnosenja", name: "Priznati datum podnošenja"},
            ]
        }
        return []
    }


    const setLogicalOperator = (value: LogicalOperator, index: number) => {
        let newParams = [...searchParams];
        newParams = newParams.map((param, ind) => {
            if (ind === index) return {...param, logicalOperator: value}
            return {...param}
        })
        setSearchParams(newParams)
    }

    const setRelationType = (value: string, index: number) => {
        let newParams = [...searchParams];
        newParams = newParams.map((param, ind) => {
            if (ind === index) return {...param, meta: value}
            return {...param}
        })
        setSearchParams(newParams)
    }

    const setOperator = (value: Operator, index: number) => {
        let newParams = [...searchParams];
        newParams = newParams.map((param, ind) => {
            if (ind === index) return {...param, operator: value}
            return {...param}
        })
        setSearchParams(newParams)
    }

    const setValue = (value: string, index: number) => {
        let newParams = [...searchParams];
        newParams = newParams.map((param, ind) => {
            if (ind === index) return {...param, value: value}
            return {...param}
        })
        setSearchParams(newParams)
    }

    const addSearchParams = () => {
        setSearchParams([...searchParams, initialSearchParam])
    }

    const removeSearchParam = (ind: number) => {
        const filtered = searchParams.filter((param, index) => index !== ind)
        setSearchParams(filtered)
    }

    function search(): void {
        let port;
        if (type === "patent") {
            port = "8002"
        }
        if (type === "autor") {
            port = "8003"
        }
        axios.post(`http://localhost:${port}/search/advanced`, {metadata: searchParams}, {
            headers: {
                "Content-Type": "application/xml",
                Accept: "*/*"
            }
        }).then(response => {
            console.log(response.data);
            switch (type) {
                case 'patent':
                    setPatentZahtevi([new ZahtevZaPriznanjePatenta()]);
                    break;
                case 'zig':
                    setZigZahtevi([new ZahtevZaPriznanjeZiga()]);
                    break;
                case 'autor':
                    setAutorskaZahtevi([new ZahtevZaPriznanjeAutorska()]);
            }
        }).catch(() => {
            toast.error("Greška pri pretrazi.")
        })

    }

    return <div className="w-2/3 ">
        <table className="w-full flex flex-col gap-3 justify-center shadow-xl rounded p-5">
            <tbody>


            {searchParams.map((searchParam, index) =>
                <tr key={index} className="flex gap-5 justify-around">
                    <td className="w-1/12">
                        {
                            index > 0 &&
                            <>
                                <select value={searchParam.logicalOperator}
                                        onChange={e => setLogicalOperator(e.target.value as LogicalOperator, index)}
                                        className="w-full py-2.5 px-0 bg-transparent border-0 border-b-2 appearance-none focus:!outline-none focus:ring-0 border-gray-dark">
                                    <option value="I">I</option>
                                    <option value="ILI">ILI</option>
                                </select>
                            </>
                        }
                    </td>
                    <td className="w-5/12">
                        <select value={searchParam.meta}
                                onChange={e => setRelationType(e.target.value as string, index)}
                                className="w-full py-2.5 px-0 bg-transparent border-0 border-b-2 appearance-none focus:!outline-none focus:ring-0 border-gray-dark">
                            {allParams.map((param, index) => {
                                return (<option value={param.value} key={index}>{param.name}</option>)
                            })
                            }
                        </select>
                    </td>
                    <td className="w-1/12">
                        <select value={searchParam.operator}
                                onChange={e => setOperator(e.target.value as Operator, index)}
                                className="w-full py-2.5 px-0 bg-transparent border-0 border-b-2 appearance-none focus:!outline-none focus:ring-0 border-gray-dark">
                            <option value="=">=</option>
                            <option value="!=">!=</option>
                        </select>
                    </td>
                    <td className="w-4/12">
                        <input value={searchParam.value} onChange={e => setValue(e.target.value, index)} type="text"
                               className="w-full py-2.5 px-0 bg-transparent border-0 border-b-2 appearance-none focus:!outline-none focus:ring-0 border-gray-dark"/>
                    </td>
                    <td className="w-1/12">
                        <button className="w-full flex pt-4">
                            <IoIosAddCircle fill='#14843c' className="w-7 h-7 cursor-pointer"
                                            onClick={addSearchParams}/>
                        </button>
                    </td>
                    <td className="w-1/12">
                        {index > 0 &&
                            <button className="w-full flex pt-4">
                                <IoIosCloseCircle fill='#c53030' className="w-7 h-7 cursor-pointer"
                                                  onClick={e => removeSearchParam(index)}/>
                            </button>
                        }
                    </td>
                </tr>
            )}
            </tbody>
        </table>

        <div className="flex items-center justify-center mt-6">
            <button className="text-white rounded-lg px-4 py-2 bg-green-700 w-2/12 " onClick={() => search()}>Pretraga
            </button>
        </div>
    </div>;
}

export default AdvancedSearch;