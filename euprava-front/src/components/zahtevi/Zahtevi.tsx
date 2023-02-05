import {useContext, useState} from 'react'
import {Status, ZahtevData} from "../types";
import ZahtevModal from './ZahtevModal';
import RequestTypeContext from "../../store/request-type-context";

interface ZahteviProps {
    zahtevi: ZahtevData[];
}

const Zahtevi: React.FunctionComponent<ZahteviProps> = ({zahtevi}) => {
    const [showModal, setShowModal] = useState<boolean>(false);
    const [zahtev, setZahtev] = useState<ZahtevData | null>(null);

    const {type} = useContext(RequestTypeContext);

    const getColor = (status: string) => {
        //alert(status + " " + Status.Prilozen)
        if (status === Status.Prilozen) return 'text-blue-700';
        else if (status === Status.Odobreno) return 'text-green-700';
        else return 'text-red-600';
    }

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

    const onShowModal = (zahtev: ZahtevData) => {
        setZahtev(zahtev);
        setShowModal(true);
    }

    return (
        <div className="p-8 grid grid-cols-2 gap-8">
            {zahtevi.map(zahtev =>
                <div onClick={e => onShowModal(zahtev)} key={zahtev.brojPrijave}
                     className="w-[100%] shadow-aroundLess bg-white p-7 flex flex-col gap-4 items-start hover:scale-105 hover:shadow-around cursor-pointer">
                    <div className="w-full justify-between flex">
                        <p className="font-semibold text-lg"> Zahtev za priznanje {setType()}</p>
                        <p className={`font-semibold italic ${getColor(zahtev.status)}`}> {zahtev.status} </p>
                    </div>
                    <div className="w-full justify-around gap-5 flex">
                    <span className="flex gap-3">
                        <p className="font-light">Šifra prijave:</p>
                        <p> {zahtev.brojPrijave} </p>
                    </span>
                        <span className="flex gap-3">
                        <p className="font-light">Datum podnošenja:</p>
                        <p> {zahtev.datum} </p>
                    </span>
                    </div>
                </div>
            )}
            <ZahtevModal zahtev={zahtev} showModal={showModal} setShowModal={setShowModal}/>
        </div>
    );
}

export default Zahtevi;