import {useState} from 'react'
import { ZahtevZaPriznanjeAutorska } from "../autorska/types";
import { ZahtevZaPriznanjePatenta } from "../patent/types";
import {  Status, Zahtev } from "../types";
import { ZahtevZaPriznanjeZiga } from "../zig/types";
import ZahtevModal from './ZahtevModal';

interface ZahteviProps {
    zahtevi: ZahtevZaPriznanjeZiga[] | ZahtevZaPriznanjeAutorska[] | ZahtevZaPriznanjePatenta[];
}
 
const Zahtevi: React.FunctionComponent<ZahteviProps> = ({zahtevi}) => {
    const [showModal, setShowModal] = useState<boolean>(false);
    const [zahtev, setZahtev] = useState<Zahtev | null>(null);

    const getColor = (status: string) => {
        if (status === Status.NA_CEKANJU) return 'text-blue-700';
        else if (status === Status.ODOBRENO) return 'text-green-700';
        else return 'text-red-600';
    }
    
    const setType = (zahtev: Zahtev) => {
        if (zahtev instanceof ZahtevZaPriznanjeAutorska) return 'autorskog dela';
        else if (zahtev instanceof ZahtevZaPriznanjeZiga) return 'žiga';
        return 'patenta';
    }

    const onShowModal = (zahtev:Zahtev) => {
        setZahtev(zahtev);
        setShowModal(true);
    }

    return ( 
        <div className="w-1/2">
        {zahtevi.map(zahtev => 
            <div onClick={e => onShowModal(zahtev)} key={zahtev.sifraZahteva} className="shadow-aroundLess bg-white p-7 flex flex-col gap-4 items-start hover:scale-105 hover:shadow-around cursor-pointer">
                <div className="w-full justify-between flex">
                    <p className="font-semibold text-lg"> Zahtev za priznanje {setType(zahtev)}</p>
                    <p className={`font-semibold italic ${getColor(zahtev.informacijeOResenju.status)}`}> {zahtev.informacijeOResenju.status} </p>
                </div>
                <div className="w-full justify-around gap-5 flex">
                    <span className="flex gap-3">
                        <p className="font-light">Šifra prijave:</p>
                        <p> {zahtev.sifraZahteva} </p>
                    </span>
                    <span className="flex gap-3">
                        <p className="font-light">Datum podnošenja:</p>
                        <p> {zahtev.datumPodnosenja.toDateString()} </p>
                    </span>
                </div>
            </div>
        )}
        <ZahtevModal zahtev={zahtev} showModal={showModal} setShowModal={setShowModal} />
        </div>
    );
}
 
export default Zahtevi;