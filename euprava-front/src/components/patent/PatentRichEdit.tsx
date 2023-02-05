import * as React from "react";
import {useEffect, useState} from "react";
import {Builder, Util, XmlEditor} from "react-xml-editor";
import {Xml} from "react-xml-editor/lib/src/types";
import "../../css/xonomy.css"
import {useParams} from "react-router-dom";
import axios from "axios";
import convert from "xml-js";
import {Pronalazac, PrvobitnaPrijava, Punomocnik, RanijaPrijava} from "./types";
import {Adresa} from "../types";

const docSpec = {
    elements: {
        item: {
            attributes: {
                label: {
                    asker: Util.askString,
                    menu: [{
                        action: Util.deleteAttribute,
                        caption: 'Delete attribute',
                    }],
                },
                type: {
                    asker: Util.askPicklist([{
                        value: 'short', caption: 'short'
                    }, {
                        value: 'medium', caption: 'medium',
                    }, 'long']),
                },
            },
            menu: [{
                action: Util.newElementChild('<child />'),
                caption: 'Append child <child />',
            }, {
                action: Util.newAttribute({
                    name: 'label',
                    value: 'default value',
                }),
                caption: 'Add attribute @label',
                hideIf: (xml: any, id: string[]) => {
                    const element = Util.getXmlNode(xml, id);
                    return element && element.$ && typeof element.$.label !== 'undefined';
                },
            }, {
                action: Util.deleteElement,
                caption: 'Delete this <item />',
                icon: 'exclamation.png',
            }, {
                action: Util.newElementBefore('<item />'),
                caption: 'New <item /> before this',
            }, {
                action: Util.newElementAfter('<item />'),
                caption: 'New <item /> after this',
            }, {
                action: Util.duplicateElement,
                caption: 'Copy <item />',
            }, {
                action: Util.moveElementUp,
                caption: 'Move <item /> up',
                hideIf: (xml: Xml, id: string[]) => !Util.canMoveElementUp(xml, id),
            }, {
                action: Util.moveElementDown,
                caption: 'Move <item /> down',
                hideIf: (xml: Xml, id: string[]) => !Util.canMoveElementDown(xml, id),
            }]
        },
    }
};


export default function PatentRichEdit() {

    const [xml, setXml] = useState<string>('<ZahtevZaPriznanjePatenta></ZahtevZaPriznanjePatenta>');

    const {brojPrijave} = useParams();

    useEffect(() => {
        let url = "http://localhost:8002/patent/" + brojPrijave;
        axios.get(url, {
            headers: {
                "Content-Type": "application/xml",
                Accept: "application/xml",
            }
        }).then(response => {
                setXml(response.data)
            }
        ).catch(error => {
            console.error(error.message)
        })

    }, [])

    class Editor extends React.Component<{}, { xml: string }> {
        private ref: React.RefObject<XmlEditor>;

        public constructor(props: { xml: string }) {
            super(props);
            this.ref = React.createRef();
            this.onClickHarvest = this.onClickHarvest.bind(this);
            this.state = {
                xml: xml,
            };
        }

        public render(): React.ReactNode {
            return (
                <>
                    <div className="p-6">
                        <div className="flex justify-center w-full m-6">
                            <button onClick={this.onClickHarvest}
                                    className="inline-flex items-center px-4 py-2  text-lg font-medium text-center text-gray-900 bg-white border border-gray-300 rounded-lg hover:bg-gray-100 focus:ring-4 focus:outline-none focus:ring-gray-200"
                            >
                                Sačuvaj izmene
                            </button>
                        </div>

                        <div className="h-[75vh] overflow-y-scroll">
                            <XmlEditor
                                docSpec={docSpec}
                                ref={this.ref}
                                xml={xml}
                            />
                        </div>


                    </div>
                </>
            );
        }

        private onClickHarvest(): void {
            if (this.ref.current) {

                const builder = new Builder({});
                const xml:Xml|undefined = this.ref.current.getXml();

                if (xml) {

                    this.setState({
                        xml: builder.buildObject(xml),
                    });

                    const convert = require("xml-js");
                    const jsonData = convert.xml2js(builder.buildObject(xml), {
                        compact: true,
                        alwaysChildren: true,
                    });
                    let json = jsonData.ZahtevZaPriznanjePatenta;


                    let dto: any = {};

                    // dto.nacinDostavljanja = dostavljanje.pisano ? "pisano" : "elektornski";
                    dto.nazivNaSrpskom = json.popunjavaPodnosioc.nazivNaSrpskom;
                    dto.nazivNaEngleskom = json.popunjavaPodnosioc.nazivNaEngleskom;
                    //
                    // dto.podnosilac = {
                    //     info: {...podnosilac.info},
                    //     adresa: {...podnosilac.adresa},
                    //     kontakt: {eposta: user?.email, telefon: podnosilac.kontakt.telefon, faks: podnosilac.kontakt.faks}
                    // }
                    //
                    // dto.prilozi = await getPrilozi();
                    //
                    // if (pronalazaciNavedeni) {
                    //     let dodatiPronalazaci = []
                    //     for (let pronalazac of pronalazaci) {
                    //         if (Pronalazac.validate(pronalazac)) {
                    //             dodatiPronalazaci.push({"Lice": pronalazac})
                    //         }
                    //     }
                    //     if (dodatiPronalazaci.length > 0) {
                    //         dto.pronalazaci = dodatiPronalazaci;
                    //     }
                    // }
                    //
                    // if (PrvobitnaPrijava.isFull(prvobitnaPrijava)) {
                    //     dto.prvobitnaPrijava = prvobitnaPrijava;
                    // }
                    //
                    // let dodateRanijePrijave = []
                    //
                    // for (let ranijaPrijava of ranijePrijave) {
                    //     if (RanijaPrijava.isFull(ranijaPrijava)) {
                    //         dodateRanijePrijave.push({"RanijaPrijava": ranijaPrijava})
                    //     }
                    // }
                    // if (dodateRanijePrijave.length > 0) {
                    //     dto.ranijePrijave = dodateRanijePrijave;
                    // }
                    //
                    // if (Adresa.validate(dostavljanje) && dostavljanje.pisano) {
                    //     dto.adresaZaDostavljanje = {
                    //         ulica: dostavljanje.ulica,
                    //         grad: dostavljanje.grad,
                    //         broj: dostavljanje.broj,
                    //         drzava: dostavljanje.drzava,
                    //         postanskiBroj: dostavljanje.postanskiBroj
                    //     }
                    // }
                    //
                    // if (Punomocnik.isFull(punomocnik)) {
                    //     dto.punomocnik = punomocnik;
                    // }



                    console.log(json);

                    axios.put("http://localhost:8002/patent/rich/update", json, {

                    }).then(result => {
                        console.log(result);
                    })
                }
            }
        }
    }

    return (<Editor/>);


}