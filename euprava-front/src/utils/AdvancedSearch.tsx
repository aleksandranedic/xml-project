import { useState } from "react";
import { IoIosAddCircle, IoIosCloseCircle } from 'react-icons/io'

enum RelationType {
    NAZIV = 'Naziv',
    DATUM_PODNOSENJA = 'Datum podnošenja',
    BROJ_PRIJAVE = 'Broj prijave'
}

enum Operator {
    EQUALS = '=',
    LESS = '<',
    MORE = '>'
}

enum LogicalOperator {
    I = 'I',
    ILI = 'ILI',
    NE = 'NE'
}

interface Params {
    logicalOperator: LogicalOperator,
    relationType: RelationType,
    operator: Operator,
    value: string
}

const AdvancedSearch: React.FunctionComponent = () => {
    const initialSearchParam = {logicalOperator: LogicalOperator.I ,relationType: RelationType.NAZIV, operator: Operator.EQUALS, value:''};
    const [searchParams, setSearchParams] = useState<Params[]>([initialSearchParam]);
    
    const setLogicalOperator = (value: LogicalOperator, index: number) => {
        let newParams = [...searchParams];
        newParams = newParams.map((param, ind) => {
            if (ind === index) return {...param, logicalOperator: value}
            return {...param}
        })
        setSearchParams(newParams)
    }

    const setRelationType = (value: RelationType, index: number) => {
        let newParams = [...searchParams];
        newParams = newParams.map((param, ind) => {
            if (ind === index) return {...param, relationType: value}
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

    return ( 
        <table className="w-1/2 flex flex-col gap-3 justify-center shadow-xl rounded p-5">
            {searchParams.map((searchParam, index) => 
                <tr className="flex gap-5 justify-around">
                    <td className="w-1/12">
                    {
                        index > 0 &&
                        <>
                            <select value={searchParam.logicalOperator} onChange={e => setLogicalOperator(e.target.value as LogicalOperator, index)} className="w-full py-2.5 px-0 bg-transparent border-0 border-b-2 appearance-none focus:!outline-none focus:ring-0 border-gray-dark">
                            <option value="I">I</option>
                            <option value="ILI">ILI</option>
                            <option value="NE">NE</option>
                        </select>
                        </>
                    }                  
                    </td>
                    <td className="w-3/12">
                    <select value={searchParam.relationType} onChange={e => setRelationType(e.target.value as RelationType, index)} className="w-full py-2.5 px-0 bg-transparent border-0 border-b-2 appearance-none focus:!outline-none focus:ring-0 border-gray-dark">
                        <option value="Naziv">Naziv</option>
                        <option value="Datum podnošenja">Datum podnošenja</option>
                        <option value="Broj prijave">Broj prijave</option>
                    </select>
                    </td>
                    <td className="w-2/12">
                    <select value={searchParam.operator} onChange={e => setOperator(e.target.value as Operator, index)} className="w-full py-2.5 px-0 bg-transparent border-0 border-b-2 appearance-none focus:!outline-none focus:ring-0 border-gray-dark">
                        <option value="=">=</option>
                        <option value=">">&gt; </option>
                        <option value="<">&lt;</option>
                    </select>
                    </td>
                    <td className="w-5/12">
                    <input value={searchParam.value} onChange={e => setValue(e.target.value, index)} type="text" className="w-full py-2.5 px-0 bg-transparent border-0 border-b-2 appearance-none focus:!outline-none focus:ring-0 border-gray-dark"/>
                    </td>
                    <td className="w-1/12">
                    <button className="w-full flex pt-4">
                        <IoIosAddCircle fill='#14843c' className="w-7 h-7 cursor-pointer" onClick={addSearchParams}/>
                    </button>
                    </td>
                    <td className="w-1/12">
                    { index > 0 &&
                        <button className="w-full flex pt-4">
                            <IoIosCloseCircle fill='#c53030' className="w-7 h-7 cursor-pointer" onClick={e => removeSearchParam(index)}/>
                        </button>   
                    }
                    </td>
                </tr>
            )}
        </table>
     );
}
 
export default AdvancedSearch;