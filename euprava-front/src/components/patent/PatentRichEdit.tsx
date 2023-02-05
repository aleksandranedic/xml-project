import * as React from "react";
import {useEffect, useState} from "react";
import {Builder, Util, XmlEditor} from "react-xml-editor";
import {Xml} from "react-xml-editor/lib/src/types";
import "../../css/xonomy.css"
import {useParams} from "react-router-dom";
import axios from "axios";

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
                const xml = this.ref.current.getXml();

                if (xml) {

                    this.setState({
                        xml: builder.buildObject(xml),
                    });

                    axios.put("http://localhost:8002/patent/rich/update", xml, {
                        headers: {
                            "Content-Type": "application/xml",
                            Accept: "application/xml",
                        }
                    }).then(result => {
                        console.log(result);
                    })
                }
            }
        }
    }

    return (<Editor/>);


}