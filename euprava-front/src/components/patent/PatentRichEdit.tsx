import * as React from "react";
import {useEffect, useState} from "react";
import {Builder, Util, XmlEditor} from "react-xml-editor";
import {Xml} from "react-xml-editor/lib/src/types";
import "../../css/xonomy.css"

import axios from "axios";
import {toast, ToastContainer} from "react-toastify";

const docSpec = {
    elements: {
        Zahtev: {
          menu: [
              {
                  action: Util.newElementChild('<podnosilac />'),
                  caption: 'Dodaj podnosilac',
              },
              {
                  action: Util.newElementChild('<pronalazaci />'),
                  caption: 'Dodaj pronalazace',
              },
              {
                  action: Util.newElementChild('<podonosilacJePronalazac />'),
                  caption: 'Obelezi da je podnosilac pronalazac',
              },
              {
                  action: Util.newElementChild('<nazivNaSrpskom />'),
                  caption: 'Dodaj naziv na srpskom',
              },
              {
                  action: Util.newElementChild('<nazivNaEngleskom />'),
                  caption: 'Dodaj naziv na engleskom',
              },
              {
                  action: Util.newElementChild('<adresaZaDostavljanje />'),
                  caption: 'Dodaj adresu za dostavljanje',
              },
              {
                  action: Util.newElementChild('<nacinDostavljanja />'),
                  caption: 'Dodaj nacin dostavljanja',
              },
              {
                  action: Util.newElementChild('<prvobitnaPrijava />'),
                  caption: 'Dodaj prvobitnu prijavu',
              },

              {
                  action: Util.newElementChild('<ranijePrijave />'),
                  caption: 'Dodaj ranije prijave',
              }

          ]

        },
        podnosilac: {
            menu:[
                {
                    action: Util.newElementChild('<adresa />'),
                    caption: 'Dodaj adresu',
                },
                {
                    action: Util.newElementChild('<info />'),
                    caption: 'Dodaj informacije',
                },
                {
                    action: Util.newElementChild('<kontakt />'),
                    caption: 'Dodaj kontakt',
                },
                {
                    action: Util.deleteElement,
                    caption: 'Obrisi',
                }
            ]
        },
        pronalazaci: {
            menu: [
                {
                    action: Util.newElementChild('<pronalazaci />'),
                    caption: 'Dodaj pronalazaca',
                },
                {
                    action: Util.newElementChild('<adresa />'),
                    caption: 'Dodaj adresu',
                },
                {
                    action: Util.newElementChild('<info />'),
                    caption: 'Dodaj informacije',
                },
                {
                    action: Util.newElementChild('<kontakt />'),
                    caption: 'Dodaj kontakt',
                },
                {
                    action: Util.deleteElement,
                    caption: 'Obrisi',
                }
            ]
        },
        podnosilacJePronalazac: {
            menu: [
                {
                    action: Util.newTextChild("true"),
                    caption: 'Dodaj sadržaj'
                },
                {
                    action: Util.deleteElement,
                    caption: 'Obrisi',
                }
            ]
        },
        punomocnik: {
            menu: [
                {
                    action: Util.newElementChild('<adresa />'),
                    caption: 'Dodaj adresu',
                },
                {
                    action: Util.newElementChild('<info />'),
                    caption: 'Dodaj informacije',
                },
                {
                    action: Util.newElementChild('<kontakt />'),
                    caption: 'Dodaj kontakt',
                },
                {
                    action: Util.newElementChild('<zaPrijem />'),
                    caption: 'Dodaj opciju da je punomocnik za prijem',
                },
                {
                    action: Util.newElementChild('<zaZastupanje />'),
                    caption: 'Dodaj opciju da je punomocnik za zastupanje',
                },
                {
                    action: Util.newElementChild('<zajednickiPredstavnik />'),
                    caption: 'Dodaj opciju da je punomocnik zajednicki predstavnik',
                },
                {
                    action: Util.deleteElement,
                    caption: 'Obrisi',
                }
            ]
        },
        nazivNaSrpskom: {
            menu: [
                {
                    action: Util.newTextChild("Tekst"),
                    caption: 'Dodaj sadržaj'
                },
                {
                    action: Util.deleteElement,
                    caption: 'Obrisi',
                }
            ]
        },
        nazivNaEngleskom: {
            menu: [
                {
                    action: Util.newTextChild("Tekst"),
                    caption: 'Dodaj sadržaj'
                },
                {
                    action: Util.deleteElement,
                    caption: 'Obrisi',
                }
            ]
        },
        adresaZaDostavljanje: {
            menu: [
                {
                    action: Util.newElementChild('<adresa />'),
                    caption: 'Dodaj adresu',
                },
                {
                    action: Util.deleteElement,
                    caption: 'Obrisi',
                }
            ]
        },
        nacinDostavljanja:{
            menu: [
                {
                    action: Util.newTextChild("Tekst"),
                    caption: 'Dodaj sadržaj'
                },
                {
                    action: Util.deleteElement,
                    caption: 'Obrisi',
                }
            ]
        },
        prvobitnaPrijava: {
            menu: [
                {
                    action: Util.newElementChild('<brojPrijave />'),
                    caption: 'Dodaj broj prijave',
                },

                {
                    action: Util.newElementChild('<datumPodnosenja />'),
                    caption: 'Dodaj datum podnosenja',
                },

                {
                    action: Util.newElementChild('<tipPrijave />'),
                    caption: 'Dodaj tip prijave',
                },
                {
                    action: Util.deleteElement,
                    caption: 'Obrisi',
                }
            ]
        },
        ranijePrijave:{
            menu: [
                {
                    action: Util.newElementChild('<ranijePrijave />'),
                    caption: 'Dodaj raniju prijavu',
                },
                {
                    action: Util.newElementChild('<brojPrijave />'),
                    caption: 'Dodaj prijave',
                },
                {
                    action: Util.newElementChild('<datumPodnosenja />'),
                    caption: 'Dodaj datum podnosenja',
                },
                {
                    action: Util.newElementChild('<tipPrijave />'),
                    caption: 'Dodaj tip prijave',
                },
                {
                    action: Util.deleteElement,
                    caption: 'Obrisi',
                }
            ]
        },
        brojPrijave:{
            menu: [
                {
                    action: Util.newTextChild("Tekst"),
                    caption: 'Dodaj sadržaj'
                },
                {
                    action: Util.deleteElement,
                    caption: 'Obrisi',
                }
            ]
        },
        datumPodnosenja:{
            menu: [
                {
                    action: Util.newTextChild("Tekst"),
                    caption: 'Dodaj sadržaj'
                },
                {
                    action: Util.deleteElement,
                    caption: 'Obrisi',
                }
            ]
        },
        dvoslovnaOznaka:{
            menu: [
                {
                    action: Util.newTextChild("Tekst"),
                    caption: 'Dodaj sadržaj'
                },
                {
                    action: Util.deleteElement,
                    caption: 'Obrisi',
                }
            ]
        },
        tipPrijave:{
            menu: [
                {
                    action: Util.newTextChild("Tekst"),
                    caption: 'Dodaj sadržaj'
                },
                {
                    action: Util.deleteElement,
                    caption: 'Obrisi',
                }
            ]
        },
        info:{
            menu: [
                {
                    action: Util.newElementChild('<ime />'),
                    caption: 'Dodaj ime',
                },
                {
                    action: Util.newElementChild('<prezime />'),
                    caption: 'Dodaj prezime',
                },
                {
                    action: Util.newElementChild('<drzavljanstvo />'),
                    caption: 'Dodaj drzavljanstvo',
                },
                {
                    action: Util.deleteElement,
                    caption: 'Obrisi',
                }
            ]
        },
        ime:{
            menu: [
                {
                    action: Util.newTextChild("Tekst"),
                    caption: 'Dodaj sadržaj'
                },
                {
                    action: Util.deleteElement,
                    caption: 'Obrisi',
                }
            ]
        },
        prezime:{
            menu: [
                {
                    action: Util.newTextChild("Tekst"),
                    caption: 'Dodaj sadržaj'
                },
                {
                    action: Util.deleteElement,
                    caption: 'Obrisi',
                }
            ]
        },
        drzavljanstvo: {
            menu: [
                {
                    action: Util.newTextChild("Tekst"),
                    caption: 'Dodaj sadržaj'
                },
                {
                    action: Util.deleteElement,
                    caption: 'Obrisi',
                }
            ]
        },
        adresa: {
            menu: [
                {
                    action: Util.newElementChild('<ulica />'),
                    caption: 'Dodaj ulicu',
                },
                {
                    action: Util.newElementChild('<broj />'),
                    caption: 'Dodaj broj',
                },
                {
                    action: Util.newElementChild('<grad />'),
                    caption: 'Dodaj grad',
                },
                {
                    action: Util.newElementChild('<drzava />'),
                    caption: 'Dodaj drzavu',
                },
                {
                    action: Util.newElementChild('<postanskiBroj />'),
                    caption: 'Dodaj postanski broj',
                },
                {
                    action: Util.deleteElement,
                    caption: 'Obrisi',
                }
            ]
        },
        ulica: {
            menu: [
                {
                    action: Util.newTextChild("Tekst"),
                    caption: 'Dodaj sadržaj'
                },
                {
                    action: Util.deleteElement,
                    caption: 'Obrisi',
                }
            ]
        },
        broj: {
            menu: [
                {
                    action: Util.newTextChild("Tekst"),
                    caption: 'Dodaj sadržaj'
                },
                {
                    action: Util.deleteElement,
                    caption: 'Obrisi',
                }
            ]
        },
        drzava: {
            menu: [
                {
                    action: Util.newTextChild("Tekst"),
                    caption: 'Dodaj sadržaj'
                },
                {
                    action: Util.deleteElement,
                    caption: 'Obrisi',
                }
            ]
        },
        postanskiBroj: {
            menu: [
                {
                    action: Util.newTextChild("Tekst"),
                    caption: 'Dodaj sadržaj'
                },
                {
                    action: Util.deleteElement,
                    caption: 'Obrisi',
                }
            ]
        },
        grad: {
            menu: [
                {
                    action: Util.newTextChild("Tekst"),
                    caption: 'Dodaj sadržaj'
                },
                {
                    action: Util.deleteElement,
                    caption: 'Obrisi',
                }
            ]
        },
        kontakt: {
            menu: [
                {
                    action: Util.newElementChild('<telefon />'),
                    caption: 'Dodaj telefon',
                },
                {
                    action: Util.newElementChild('<faks />'),
                    caption: 'Dodaj faks',
                },
                {
                    action: Util.newElementChild('<eposta />'),
                    caption: 'Dodaj e-postu',
                },
                {
                    action: Util.deleteElement,
                    caption: 'Obrisi',
                }
            ]
        },
        telefon: {
            menu: [
                {
                    action: Util.newTextChild("Tekst"),
                    caption: 'Dodaj sadržaj'
                },
                {
                    action: Util.deleteElement,
                    caption: 'Obrisi',
                }
            ]
        },
        eposta: {
            menu: [
                {
                    action: Util.newTextChild("Tekst"),
                    caption: 'Dodaj sadržaj'
                },
                {
                    action: Util.deleteElement,
                    caption: 'Obrisi',
                }
            ]
        },
        faks: {
            menu: [
                {
                    action: Util.newTextChild("Tekst"),
                    caption: 'Dodaj sadržaj'
                },
                {
                    action: Util.deleteElement,
                    caption: 'Obrisi',
                }
            ]
        },
        zaPrijem: {
            menu: [
                {
                    action: Util.newTextChild("Tekst"),
                    caption: 'Dodaj sadržaj'
                }
                ,
                {
                    action: Util.deleteElement,
                    caption: 'Obrisi',
                }
            ]
        },
        zaZastupanje: {
            menu: [
                {
                    action: Util.newTextChild("Tekst"),
                    caption: 'Dodaj sadržaj'
                },
                {
                    action: Util.deleteElement,
                    caption: 'Obrisi',
                }
            ]
        },
        zajednickiPredstavnik: {
            menu: [
                {
                    action: Util.newTextChild("Tekst"),
                    caption: 'Dodaj sadržaj'
                },
                {
                    action: Util.deleteElement,
                    caption: 'Obrisi',
                }
            ]
        }
    }
};


export default function PatentRichEdit() {
    const [xml, setXml] = useState<string>('  <Zahtev>\n' +
        '    <podnosilac>\n' +
        '      <adresa>\n' +
        '        <ulica></ulica>\n' +
        '        <broj></broj>\n' +
        '        <grad></grad>\n' +
        '        <drzava></drzava>\n' +
        '        <postanskiBroj></postanskiBroj>\n' +
        '      </adresa>\n' +
        '      <info>\n' +
        '        <ime></ime>\n' +
        '        <prezime></prezime>\n' +
        '        <drzavljanstvo></drzavljanstvo>\n' +
        '      </info>\n' +
        '      <kontakt>\n' +
        '        <telefon></telefon>\n' +
        '        <faks></faks>\n' +
        '        <eposta></eposta>\n' +
        '      </kontakt>\n' +
        '    </podnosilac>\n' +
        '    <nazivNaSrpskom></nazivNaSrpskom>\n' +
        '    <nazivNaEngleskom></nazivNaEngleskom>\n' +
        '    <nacinDostavljanja></nacinDostavljanja>\n' +
        '  </Zahtev>');



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

                    axios.post("http://localhost:8002/patent/rich", builder.buildObject(xml), {
                        headers: {
                            "Content-Type": "application/xml",
                            Accept: "application/xml",
                        }
                    }).then((response) => {
                        toast.success(response.data)
                    }).catch(error=> {
                        toast.error("Greška pri čuvanju zahteva.")
                    })
                }
            }
        }
    }

    return (<><Editor/><ToastContainer position="top-center" draggable={false}/></>);


}