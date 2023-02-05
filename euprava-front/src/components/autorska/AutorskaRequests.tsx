import {useContext, useEffect, useState} from "react";
import Zahtevi from "../zahtevi/Zahtevi";
import RequestTypeContext from "../../store/request-type-context";
import axios from "axios";
import {Prilog, ZahtevData} from "../types";
import userContext, { Role } from "../../store/user-context";
import { toast, ToastContainer } from "react-toastify";

export function AutorskaRequests() {

    const {user} = useContext(userContext)
    const [autorZahtevi, setAutorZahtevi] = useState<ZahtevData[]>([]);
    const [type, setType] = useState<"patent" | "autor" | "zig" | null>("autor");

    const path = user?.role === "Službenik" ? "" : "/resolved/" + user?.email;
    console.log(user?.role)
    useEffect(() => {
        axios.get('http://localhost:8003/autor' + path, {
            headers: {
                "Content-Type": "application/xml",
                Accept: "application/xml",
            }
        }).then(result => {
            const convert = require("xml-js");
            const jsonData = convert.xml2js(result.data, {
                compact: true,
                alwaysChildren: true,
            });
            let json = jsonData.List.item;
            let zahtevi: ZahtevData[] = [];
            for (let jsonData of json) {
                console.log(json)
                let zahtev: ZahtevData = new ZahtevData();
                zahtev.status = jsonData.status["_text"]
                zahtev.brojPrijave = jsonData.brojPrijave["_text"]
                zahtev.html = jsonData.html["_text"]
                zahtev.datum = jsonData.datum["_text"]

                let prilozi: Prilog[] = [];

                if (jsonData.prilozi.prilozi) {
                    for (let prilog of jsonData.prilozi.prilozi) {
                        prilozi.push({
                            putanja: prilog.putanja["_text"],
                            naslov: prilog.naslov["_text"]
                        })
                    }
                }
                zahtev.prilozi = prilozi;
                zahtevi.push(zahtev);
                console.log(zahtev);
            }
            setAutorZahtevi(zahtevi);
        })
    }, [])


    const [fromDate, setFromDate] = useState('');
    const [toDate, setToDate] = useState('');

    const onCreateReport = () => {
        if (fromDate === '') {
            toast.error('Unesite pocetni datum');
            return;
        }
        if (toDate === '') {
            toast.error('Unesite krajnji datum');
            return;
        } 
        //
    }

    return (
        <RequestTypeContext.Provider value={{type, setType, port: "8003"}}>
            {user!.role === Role.WORKER && 
            <>
            <div className="flex w-full gap-3 mt-10 items-center justify-around">
                <div className="flex gap-2">
                    <p className="font-light">Pocetni datum</p>
                    <input type="date" value={fromDate} onChange={e => setFromDate(e.target.value)}/>
                </div>
                <div className="flex gap-2">
                    <p className="font-light">Pocetni datum</p>
                    <input type="date" value={toDate} onChange={e => setToDate(e.target.value)}/>
                </div>
                <button className="bg-blue-500 hover:bg-blue-700 text-white font-bold px-6 py-2 rounded w-fit self-center" onClick = {e => onCreateReport()}>Kreiraj izvestaj</button>
                <ToastContainer position="top-center" draggable={false}/>
            </div>
            </>
            }
            <Zahtevi zahtevi={autorZahtevi}/>
        </RequestTypeContext.Provider>
    )
}