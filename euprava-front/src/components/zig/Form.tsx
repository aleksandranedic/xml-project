import {useRef} from 'react';

interface ZigFormProps {
    
}
const nicanskaKlasifikacija = () => {
    const rows = [];
    for (let i = 1; i < 46; i++) {
        rows.push(
            <li key={i} className="w-fit">
                <div className="flex items-center">
                    <input type="checkbox" value="" className="w-4 h-4 text-blue-600 bg-gray-100 rounded border-gray-300 focus:ring-blue-500 focus:ring-2"/>
                    <label>{i}</label>
                </div>
            </li>
        );
    }
    return <ul className=" text-sm grid grid-cols-12 gap-1">{rows}</ul>;
}
const ZigForm: React.FunctionComponent<ZigFormProps> = () => {
    const namePodnosilac:React.RefObject<HTMLInputElement> = useRef<HTMLInputElement>(null);
    const surnamePodnosilac: React.RefObject<HTMLInputElement> = useRef<HTMLInputElement>(null);

    const namePunomocnik:React.RefObject<HTMLInputElement> = useRef<HTMLInputElement>(null);
    const surnamePunomocnik:React.RefObject<HTMLInputElement> = useRef<HTMLInputElement>(null);
    
    const showFizickoLice =(name:React.RefObject<HTMLInputElement>, surname:React.RefObject<HTMLInputElement>) => {
        name.current!.classList.remove("hidden");
        name.current!.classList.add("flex");
        name.current!.placeholder = "Ime";
        surname.current!.classList.remove("hidden");
        surname.current!.classList.add("flex");
    }

    const showPoslovnoLice = (name:React.RefObject<HTMLInputElement>, surname:React.RefObject<HTMLInputElement>) => {
        name.current!.classList.remove("hidden");
        name.current!.classList.add("flex");
        name.current!.placeholder = "Poslovno ime";
        surname.current!.classList.remove("flex");
        surname.current!.classList.add("hidden");
    }
    
    return ( 
        <div className="flex flex-col gap-10 justify-center items-center p-10 pl-0">
            <h1> Zahtev za priznanje žiga</h1>
            <form className="flex flex-col gap-5">
                <div id="podnosilac" className="form-block">
                    <h3>Podnosilac prijave</h3>
                    <div className="form-input-container">
                        <div id="licePodnosilac" className="grid grid-cols-2 gap-4"> 
                            <h2 className=" col-span-full"> Tip podnosilaca </h2>   
                            <div className="flex items-center mr-4">
                                <input type="radio" value="" name="podnosilac-radio" className="w-4 h-4" onClick={() => showFizickoLice(namePodnosilac, surnamePodnosilac)}/>
                                <label>Fizičko lice</label>
                            </div>
                            <div className="flex items-center mr-4 ml-9">
                                <input type="radio" value="" name="podnosilac-radio" className="w-4 h-4"  onClick={() => showPoslovnoLice(namePodnosilac, surnamePodnosilac)}/>
                                <label>Poslovno lice</label>
                            </div> 
                            <input ref={namePodnosilac} type="text" placeholder="Ime" name="ime" className='hidden'/>
                            <input ref={surnamePodnosilac} type="text" placeholder="Prezime" name="prezime" className='hidden'/>                       
                        </div>

                        <div id="adresaPodnosilac" className="grid grid-cols-3 gap-4">
                            <h2 className=" col-span-full"> Podaci o adresi</h2>
                            <input type="text" placeholder="Ulica" name="ulica"/>
                            <input type="text" placeholder="Broj" name="broj"/>
                            <input type="text" placeholder="Poštanski broj" name="postanskiBroj"/>
                            <input type="text" placeholder="Grad" name="grad"/>
                            <input type="text" placeholder="Država" name="drzava"/>
                        </div>

                        <div id="kontaktPodnosilac" className="grid grid-cols-3 gap-4">
                            <h2 className=" col-span-full"> Kontakt</h2>
                            <input type="text" placeholder="Telefon" name="telefon"/>
                            <input type="text" placeholder="Email" name="Email"/>
                            <input type="text" placeholder="Faks" name="faks"/>
                        </div>
                    </div>
                </div>

                <div id="punomocnik" className="form-block">
                    <h3>Punomoćnik</h3>
                    <div className="form-input-container">
                        <div id="licePunomocnik" className="grid grid-cols-2 gap-4"> 
                            <h2 className=" col-span-full"> Tip podnosilaca </h2>   
                            <div className="flex items-center mr-4">
                                <input type="radio" value="" name="punomocnik-radio" className="w-4 h-4" onClick={() => showFizickoLice(namePunomocnik, surnamePunomocnik)}/>
                                <label>Fizičko lice</label>
                            </div>
                            <div className="flex items-center mr-4 ml-9">
                                <input type="radio" value="" name="punomocnik-radio" className="w-4 h-4 " onClick={() => showPoslovnoLice(namePunomocnik, surnamePunomocnik)}/>
                                <label>Poslovno lice</label>
                            </div>                        
                            <input ref={namePunomocnik} type="text" placeholder="Ime" name="ime" className='hidden'/>
                            <input ref={surnamePunomocnik} type="text" placeholder="Prezime" name="prezime" className='hidden'/>                       
                        </div>
                        <div id="adresaPunomocnik" className="grid grid-cols-3 gap-4">
                            <h2 className=" col-span-full"> Podaci o adresi</h2>
                            <input type="text" placeholder="Ulica" name="ulica"/>
                            <input type="text" placeholder="Broj" name="broj"/>
                            <input type="text" placeholder="Poštanski broj" name="postanskiBroj"/>
                            <input type="text" placeholder="Grad" name="grad"/>
                            <input type="text" placeholder="Država" name="drzava"/>
                        </div>
                        <div id="kontaktPunomocnik" className="grid grid-cols-3 gap-4">
                            <h2 className=" col-span-full"> Kontakt</h2>
                            <input type="text" placeholder="Telefon" name="telefon"/>
                            <input type="text" placeholder="Email" name="Email"/>
                            <input type="text" placeholder="Faks" name="faks"/>
                        </div>
                    </div>
                </div>

                <div id="zig" className="form-block">
                    <h3>Informacije o žigu</h3>
                    <div className="form-input-container">
                        <div id="vrstaZiga" className="grid grid-cols-3 gap-6"> 
                            <h2 className=" col-span-full"> Vrsta žiga </h2>   
                            <div className="flex items-center mr-4">
                                <input type="radio" value="" name="tipa-radio" className="w-4 h-4"/>
                                <label >Individualni žig</label>
                            </div>
                            <div className="flex items-center mr-4">
                                <input type="radio" value="" name="tipa-radio" className="w-4 h-4 "/>
                                <label>Kolektivni žig</label>
                            </div> 
                            <div className="flex items-center mr-4">
                                <input type="radio" value="" name="tipa-radio" className="w-4 h-4 "/>
                                <label>Žig garancije</label>
                            </div> 

                            <hr className="col-span-full"/>   

                            <div className="flex items-center mr-4">
                                <input type="radio" value="" name="tipb-radio" className="w-4 h-4"/>
                                <label>Verbalni znak (znak u reči)</label>
                            </div>
                            <div className="flex items-center mr-4">
                                <input type="radio" value="" name="tipb-radio" className="w-4 h-4 "/>
                                <label> Grafički znak;<br/> boju, kombinaciju boja</label>
                            </div> 
                            <div className="flex items-center mr-4">
                                <input type="radio" value="" name="tipb-radio" className="w-4 h-4 "/>
                                <label>Kombinovani znak</label>
                            </div> 
                            <div className="flex items-center mr-4">
                                <input type="radio" value="" name="tipb-radio" className="w-4 h-4"/>
                                <label>Trodimenzionalni znak</label>
                            </div>
                            <input type="text" placeholder="Drugu vrstu znaka (navesti koju)" className="col-span-2"/>               
                        </div>
                        <div id="izgledZiga" className="flex flex-col gap-3">
                            <h2 className=" col-span-full"> Izgled žiga </h2>   
                            <div className="flex gap-5 w-full">
                                <div className="w-[25rem] flex flex-col gap-5">
                                    <input type="text" placeholder="Naznačenje boje, odnosno boja iz kojih se znak sastoji:" name="naznacenjeBoje"/>
                                    <input type="text" placeholder="Transliteracija znaka*:" name="transliteracija"/>
                                    <input type="text" placeholder="Prevod znaka*:" name="prevod"/>
                                    <textarea rows={4} placeholder="Opis znaka:" name="opisZnaka"/>
                                </div>
                                <div className="w-[20rem] flex flex-col gap-2">
                                    <p>Izgled znaka:</p>
                                    <hr/>
                                    <div className="bg-gray-light h-full">
                                        Upload pic
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div id="dodatneInformacije" className="flex flex-col gap-5">
                            <h2 className=" col-span-full"> Dodatne informacije </h2>
                            <div className="flex flex-col gap-3">
                                <p>Označite brojeve klase roba i usluga prema Ničanskoj klasifikaciji:</p>   
                                {nicanskaKlasifikacija()}                            
                            </div>
                            <input type="text" placeholder="Zatraženo pravo prvenstva i osnov:" name="pravoPrvenstva"/>
                        </div>

                    </div>
                    <footer className="self-start">*Popuniti samo ako je znak ili element znaka ispisan slovima koja nisu ćirilična ili latinična</footer>
                </div>

                <div id="takse" className="form-block">
                    <h3>Plaćene takse</h3>
                    <div className="form-input-container">
                        <div className="flex gap-3 w-full items-center">
                            <p>Osnovna taksa:</p>
                            <input type="text" placeholder="rsd" className="w-3/4"/>
                        </div>
                        <div className="flex gap-3 w-full items-center">
                            <p>Za</p> 
                            <input type="text"  className="w-1/6 !border-t-0 border-l-0 border-r-0 !border-b-2"/>
                            <p>klasa:</p>
                            <input type="text" placeholder="rsd" className="w-3/4"/>
                        </div>
                        <div className="flex gap-3 w-full items-center">
                            <p>Za grafičko rešenje:</p>
                            <input type="text" placeholder="rsd" className="w-3/4"/>
                        </div>
                        <div className="flex gap-3 w-full items-center">
                            <p>UKUPNO:</p>
                            <input type="text" placeholder="rsd" className="w-3/4"/>
                        </div>
                    </div>
                </div>

                <div id="takse" className="form-block">
                    <h3>Slanje</h3>
                    <div className="form-input-container">
                        <div className="flex justify-between w-full items-center">
                            <p>Unesite dodatne fajlove:</p>
                            <input type="file" multiple className="!border-none"/>
                        </div>
                        <button className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded w-fit self-center">Pošalji</button>
                    </div>
                </div>
                
            </form>
        </div>
     );
}
 
export default ZigForm;