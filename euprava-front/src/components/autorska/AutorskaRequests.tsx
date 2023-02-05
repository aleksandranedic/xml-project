import {useContext, useEffect, useState} from "react";
import Zahtevi from "../zahtevi/Zahtevi";
import RequestTypeContext from "../../store/request-type-context";
import axios from "axios";
import {Prilog, ZahtevData} from "../types";
import userContext from "../../store/user-context";

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


    return (
        <RequestTypeContext.Provider value={{type, setType, port: "8003"}}>
            <Zahtevi zahtevi={autorZahtevi}/>
        </RequestTypeContext.Provider>
    )
}