import {useContext, useEffect, useState} from "react";
import Zahtevi from "../zahtevi/Zahtevi";
import RequestTypeContext from "../../store/request-type-context";
import axios from "axios";
import {Prilog, ZahtevData} from "../types";
import UserContext from "../../store/user-context";

export function PatentRequests() {

    const {user} = useContext(UserContext)
    const [patentZahtevi, setPatentZahtevi] = useState<ZahtevData[]>([]);
    const [type, setType] = useState<"patent" | "autor" | "zig" | null>("patent");

    const path = user?.role === "Sluzbenik" ? "" : "/resolved/" + user?.email;
    useEffect(() => {
        axios.get('http://localhost:8002/patent'+path, {
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
            }
            setPatentZahtevi(zahtevi);
        })
    }, [])


    return (
        <RequestTypeContext.Provider value={{type, setType, port: "8002"}}>
            <Zahtevi zahtevi={patentZahtevi}/>
        </RequestTypeContext.Provider>
    )
}