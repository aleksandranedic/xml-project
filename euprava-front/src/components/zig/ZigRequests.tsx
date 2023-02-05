import {useContext, useEffect, useState} from "react";
import Zahtevi from "../zahtevi/Zahtevi";
import RequestTypeContext from "../../store/request-type-context";
import axios from "axios";
import {Prilog, ZahtevData} from "../types";
import userContext, { Role } from "../../store/user-context";

export function ZigRequests() {

    const {user} = useContext(userContext);
    const [zigZahtevi, setZigZahtevi] = useState<ZahtevData[]>([]);
    const [type, setType] = useState<"patent" | "autor" | "zig" | null>("zig");

    const path = user?.role === Role.WORKER? "" : "/resolved/" + user?.email;
    useEffect(() => {
        axios.get('http://localhost:8000/zig' + path, {
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
            console.log(jsonData)
            let json = jsonData.List.item;
            let zahtevi: ZahtevData[] = [];
            if (json.status) {
                let zahtev: ZahtevData = new ZahtevData();
                zahtev.status = json.status["_text"]
                zahtev.brojPrijave = json.brojPrijave["_text"]
                zahtev.html = json.html["_text"]
                zahtev.datum = json.datum["_text"]

                let prilozi: Prilog[] = [];

                if (json.prilozi.prilozi) {
                    for (let prilog of json.prilozi.prilozi) {
                        prilozi.push({
                            putanja: prilog.putanja["_text"],
                            naslov: prilog.naslov["_text"]
                        })
                    }
                }
                zahtev.prilozi = prilozi;
                zahtevi.push(zahtev);
            } else {

                for (let jsonData of json) {
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
            }
           
            setZigZahtevi(zahtevi);
        })
    }, [])


    return (
        <RequestTypeContext.Provider value={{type, setType, port: "8000"}}>
            {zigZahtevi.length === 0 && <p className="flex w-full justify-center text-xl mt-5">Korisnik nema nijedan razrešen zahtev.</p>}
            <Zahtevi zahtevi={zigZahtevi}/>
        </RequestTypeContext.Provider>
    )
}