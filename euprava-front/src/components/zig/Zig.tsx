import axios from 'axios';
import { useEffect, useState } from 'react'
import { useParams } from 'react-router-dom'
import { createZahtev } from '../../utils/search.service';
import { ZahtevData } from '../types';
import { PrilogCard, ZahtevBody } from '../zahtevi/ZahtevModal';
 
const Zig: React.FunctionComponent = () => {
    const [zah, setZah] = useState<ZahtevData>();
    const {broj} = useParams();

    useEffect(() => {
        axios.get(`http://localhost:8000/zig/${broj}`)
        .then(result => {
            const convert = require("xml-js");
            const jsonDataRes = convert.xml2js(result.data, {
                compact: true,
                alwaysChildren: true,
            });
            const zahtevData = createZahtev(jsonDataRes.ZahtevData);
            setZah(zahtevData)
        })
        .catch(err => console.log(err))
    }, [])
    return (
        <>
        { zah &&
        <div className='py-10'>
        <ZahtevBody zahtev={zah}/>
        <div className='w-full flex justify-center'>
            <div className="grid grid-cols-3 gap-4 p-4 max-w-2xl">
                {zah!.prilozi.map(prilog => <PrilogCard prilog={prilog}></PrilogCard>)}
            </div>
        </div>
        </div>}
        </>
      );
}
 
export default Zig;