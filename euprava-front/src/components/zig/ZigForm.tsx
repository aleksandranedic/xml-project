import {useRef, useState} from 'react';
import { InformacijeOZigu, PlaceneTakse, Podnosilac, PrilozeniDokumenti, Punomocnik, VrstaA, VrstaB } from './types';

interface ZigFormProps {
    
}
const nicanskaKlasifikacija = (informacijeOZigu: InformacijeOZigu, setInformacijeOZigu: React.Dispatch<React.SetStateAction<InformacijeOZigu>>) => {
    const updateKlasifikacija = (i: number, add:boolean) => {
        if (add) {
            setInformacijeOZigu({...informacijeOZigu, klasifikacija:[...informacijeOZigu.klasifikacija,i]})
        } else {
            const filtered = informacijeOZigu.klasifikacija.filter(klas => klas !== i)
            setInformacijeOZigu({...informacijeOZigu, klasifikacija: filtered})
        }
    }
    
    const rows = [];
    for (let i = 1; i < 46; i++) {
        rows.push(
            <li key={i} className="w-fit">
                <div className="flex items-center">
                    <input type="checkbox" checked={informacijeOZigu.klasifikacija.includes(i)} onChange={e => updateKlasifikacija(i, e.target.checked)} className="w-4 h-4 text-blue-600 bg-gray-100 rounded border-gray-300 focus:ring-blue-500 focus:ring-2"/>
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

    const [punomocnik, setPunomocnik] = useState<Punomocnik>(new Punomocnik());
    const [podnosilac, setPodnosilac] = useState<Podnosilac>(new Podnosilac());
    const [informacijeOZigu, setInformacijeOZigu] = useState<InformacijeOZigu>(new InformacijeOZigu());
    const [placeneTakse, setPlaceneTakse] = useState<PlaceneTakse>(new PlaceneTakse());
    const [prilozeniDokumenti, setPrilozeniDokumenti] = useState<PrilozeniDokumenti>(new PrilozeniDokumenti());
    
    const showFizickoLice =(name:React.RefObject<HTMLInputElement>, surname:React.RefObject<HTMLInputElement>) => {
        name.current!.classList.remove("hidden");
        name.current!.classList.add("flex");
        surname.current!.classList.remove("hidden");
        surname.current!.classList.add("flex");      
    }

    const showPoslovnoLice = (name:React.RefObject<HTMLInputElement>, surname:React.RefObject<HTMLInputElement>) => {
        name.current!.classList.remove("hidden");
        name.current!.classList.add("flex");
        surname.current!.classList.remove("flex");
        surname.current!.classList.add("hidden");
    }
    
    const onSubmit = (e: any) => {
        e.preventDefault()
        //validacija
        //cuvanje
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
                            <div className='flex-col gap-1 items-start hidden'  ref={namePodnosilac}>
                                <p className='font-light text-sm'>Ime</p>
                                <input type="text" name="ime" className='w-full' value={podnosilac.info.ime} onChange = {e => setPodnosilac({...podnosilac, info:{...podnosilac.info, ime:e.target.value}})} />
                            </div>

                            <div className='flex-col gap-1 items-start hidden' ref={surnamePodnosilac}>
                                <p className='font-light text-sm'>Prezime</p>
                                <input type="text"  name="prezime" className='w-full' value={podnosilac.info.prezime} onChange = {e => setPodnosilac({...podnosilac, info:{...podnosilac.info, prezime:e.target.value}})}/>                                        
                            </div>

                        </div>

                        <div id="adresaPodnosilac" className="grid grid-cols-3 gap-4">
                            <h2 className=" col-span-full"> Podaci o adresi</h2>
                            <div className='flex-col gap-1 items-start'>
                                <p className='font-light text-sm'>Ulica</p>
                                <input type="text" name="ulica" className='w-full' value={podnosilac.adresa.ulica} onChange = {e => setPodnosilac({...podnosilac, adresa:{...podnosilac.adresa, ulica:e.target.value}})}/>
                            </div>
                            <div className='flex-col gap-1 items-start'>
                                <p className='font-light text-sm'>Broj</p>
                                <input type="text"  name="broj" className='w-full' value={podnosilac.adresa.broj} onChange = {e => setPodnosilac({...podnosilac, adresa:{...podnosilac.adresa, broj:e.target.value}})}/>
                            </div>
                            <div className='flex-col gap-1 items-start'>
                                <p className='font-light text-sm'>Poštanski broj</p>
                                <input type="text"  name="postanskiBroj" className='w-full' value={podnosilac.adresa.postanskiBroj} onChange = {e => setPodnosilac({...podnosilac, adresa:{...podnosilac.adresa, postanskiBroj:e.target.value}})}/>
                            </div>
                            <div className='flex-col gap-1 items-start'>
                                <p className='font-light text-sm'>Grad</p>
                                <input type="text"  name="grad" className='w-full' value={podnosilac.adresa.grad} onChange = {e => setPodnosilac({...podnosilac, adresa:{...podnosilac.adresa, grad:e.target.value}})}/>
                            </div>
                            <div className='flex-col gap-1 items-start'>
                                <p className='font-light text-sm'>Država</p>
                                <input type="text"  name="drzava" className='w-full' value={podnosilac.adresa.drzava} onChange = {e => setPodnosilac({...podnosilac, adresa:{...podnosilac.adresa, drzava:e.target.value}})}/>
                            </div>
                        </div>

                        <div id="kontaktPodnosilac" className="grid grid-cols-3 gap-4">
                            <h2 className=" col-span-full"> Kontakt</h2>
                            <div className='flex-col gap-1 items-start'>
                                <p className='font-light text-sm'>Telefon</p>
                                <input type="text" name="telefon" className='w-full' value={podnosilac.kontakt.telefon} onChange = {e => setPodnosilac({...podnosilac, kontakt:{...podnosilac.kontakt, telefon:e.target.value}})}/>
                            </div>
                            <div className='flex-col gap-1 items-start'>
                                <p className='font-light text-sm'>Email</p>
                                <input type="text" name="email" className='w-full' value={podnosilac.kontakt.eposta} onChange = {e => setPodnosilac({...podnosilac, kontakt:{...podnosilac.kontakt, eposta:e.target.value}})}/>
                            </div>
                            <div className='flex-col gap-1 items-start'>
                                <p className='font-light text-sm'>Faks</p>
                                <input type="text"  name="faks" className='w-full' value={podnosilac.kontakt.faks} onChange = {e => setPodnosilac({...podnosilac, kontakt:{...podnosilac.kontakt, faks:e.target.value}})}/>
                            </div>
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
                            <div className='flex-col gap-1 items-start hidden' ref={namePunomocnik}>
                                <p className='font-light text-sm'>Ime</p>                      
                                <input type="text" name="ime-punomocnik" className='w-full'  value={punomocnik.info.ime} onChange = {e => setPunomocnik({...punomocnik, info:{...punomocnik.info, ime:e.target.value}})}/>
                            </div>
                            <div className='flex-col gap-1 items-start hidden' ref={surnamePunomocnik}>
                                <p className='font-light text-sm'>Prezime</p>
                                <input type="text" name="prezime-punomocnik" className='w-full' value={punomocnik.info.prezime} onChange = {e => setPunomocnik({...punomocnik, info:{...punomocnik.info, prezime:e.target.value}})}/>                       
                            </div>
                        </div>
                        <div id="adresaPunomocnik" className="grid grid-cols-3 gap-4">
                            <h2 className=" col-span-full"> Podaci o adresi</h2>
                            <div className='flex-col gap-1 items-start'>
                                <p className='font-light text-sm'>Ulica</p>
                                <input type="text" name="ulica-punomocnik" className='w-full' value={punomocnik.adresa.ulica} onChange = {e => setPunomocnik({...punomocnik, adresa:{...punomocnik.adresa, ulica:e.target.value}})}/>
                            </div>
                            <div className='flex-col gap-1 items-start'>
                                <p className='font-light text-sm'>Broj</p>
                                <input type="text" name="broj-punomocnik" className='w-full' value={punomocnik.adresa.broj} onChange = {e => setPunomocnik({...punomocnik, adresa:{...punomocnik.adresa, broj:e.target.value}})}/>
                            </div>
                            <div className='flex-col gap-1 items-start'>
                                <p className='font-light text-sm'>Poštanski broj</p>
                                <input type="text" name="postanskiBroj-punomocnik" className='w-full' value={punomocnik.adresa.postanskiBroj} onChange = {e => setPunomocnik({...punomocnik, adresa:{...punomocnik.adresa, postanskiBroj:e.target.value}})}/>
                            </div>
                            <div className='flex-col gap-1 items-start'>
                                <p className='font-light text-sm'>Grad</p>
                                <input type="text" name="grad-punomocnik" className='w-full' value={punomocnik.adresa.grad} onChange = {e => setPunomocnik({...punomocnik, adresa:{...punomocnik.adresa, grad:e.target.value}})}/>
                            </div>
                            <div className='flex-col gap-1 items-start'>
                                <p className='font-light text-sm'>Država</p>
                                <input type="text" name="drzava-punomocnik" className='w-full' value={punomocnik.adresa.drzava} onChange = {e => setPunomocnik({...punomocnik, adresa:{...punomocnik.adresa, drzava:e.target.value}})}/>
                            </div>
                        </div>
                        <div id="kontaktPunomocnik" className="grid grid-cols-3 gap-4">
                            <h2 className=" col-span-full"> Kontakt</h2>
                            <div className='flex-col gap-1 items-start'>
                                <p className='font-light text-sm'>Telefon</p>
                                <input type="text" name="telefon-punomocnik" className='w-full' value={punomocnik.kontakt.telefon} onChange = {e => setPunomocnik({...punomocnik, kontakt:{...punomocnik.kontakt, telefon:e.target.value}})}/>
                            </div>
                            <div className='flex-col gap-1 items-start'>
                                <p className='font-light text-sm'>Email</p>
                                <input type="text" name="email-punomocnik" className='w-full' value={punomocnik.kontakt.eposta} onChange = {e => setPunomocnik({...punomocnik, kontakt:{...punomocnik.kontakt, eposta:e.target.value}})}/>
                            </div>
                            <div className='flex-col gap-1 items-start'>
                                <p className='font-light text-sm'>Faks</p>
                                <input type="text" name="faks-punomocnik" className='w-full' value={punomocnik.kontakt.faks} onChange = {e => setPunomocnik({...punomocnik, kontakt:{...punomocnik.kontakt, faks:e.target.value}})}/>
                            </div>
                        </div>
                    </div>
                </div>

                <div id="zig" className="form-block">
                    <h3>Informacije o žigu</h3>
                    <div className="form-input-container">
                        <div id="vrstaZiga" className="grid grid-cols-3 gap-6"> 
                            <h2 className=" col-span-full"> Vrsta žiga </h2>   
                            <div className="flex items-center mr-4">
                                <input type="radio" checked={informacijeOZigu.vrstaA === VrstaA.individualni} onChange ={e => setInformacijeOZigu({...informacijeOZigu, vrstaA: VrstaA.individualni})} name="tipa-radio" className="w-4 h-4"/>
                                <label >Individualni žig</label>
                            </div>
                            <div className="flex items-center mr-4">
                                <input type="radio" checked={informacijeOZigu.vrstaA === VrstaA.kolektivni} onChange ={e => setInformacijeOZigu({...informacijeOZigu, vrstaA: VrstaA.kolektivni})} name="tipa-radio" className="w-4 h-4 "/>
                                <label>Kolektivni žig</label>
                            </div> 
                            <div className="flex items-center mr-4">
                                <input type="radio" checked={informacijeOZigu.vrstaA === VrstaA.garancije} onChange ={e => setInformacijeOZigu({...informacijeOZigu, vrstaA: VrstaA.garancije})} name="tipa-radio" className="w-4 h-4 "/>
                                <label>Žig garancije</label>
                            </div> 

                            <hr className="col-span-full"/>   

                            <div className="flex items-center mr-4">
                                <input type="radio" checked={informacijeOZigu.vrstaB === VrstaB.verbalni} onChange ={e => setInformacijeOZigu({...informacijeOZigu, vrstaB: VrstaB.verbalni, drugaVrstaB:''})} name="tipb-radio" className="w-4 h-4"/>
                                <label>Verbalni znak (znak u reči)</label>
                            </div>
                            <div className="flex items-center mr-4">
                                <input type="radio" checked={informacijeOZigu.vrstaB === VrstaB.graficki} onChange ={e => setInformacijeOZigu({...informacijeOZigu, vrstaB: VrstaB.graficki, drugaVrstaB:''})} name="tipb-radio" className="w-4 h-4 "/>
                                <label> Grafički znak;<br/> boju, kombinaciju boja</label>
                            </div> 
                            <div className="flex items-center mr-4">
                                <input type="radio" checked={informacijeOZigu.vrstaB === VrstaB.kombinovani} onChange ={e => setInformacijeOZigu({...informacijeOZigu, vrstaB: VrstaB.kombinovani, drugaVrstaB:''})} name="tipb-radio" className="w-4 h-4 "/>
                                <label>Kombinovani znak</label>
                            </div> 
                            <div className="flex items-center mr-4">
                                <input type="radio" checked={informacijeOZigu.vrstaB === VrstaB.trodimenzionalni} onChange ={e => setInformacijeOZigu({...informacijeOZigu, vrstaB: VrstaB.trodimenzionalni, drugaVrstaB:''})} name="tipb-radio" className="w-4 h-4"/>
                                <label>Trodimenzionalni znak</label>
                            </div>
                            <div className='flex-col gap-1 items-start col-span-2'>
                                <p className='font-light text-sm'>Druga vrsta znaka (navesti koja)</p>
                                <input type="text" name='druga-vrsta-znaka' className="w-full" value={informacijeOZigu.drugaVrstaB} onChange={e => setInformacijeOZigu({...informacijeOZigu, drugaVrstaB:e.target.value, vrstaB:null})}/>               
                            </div>
                        </div>
                        <div id="izgledZiga" className="flex flex-col gap-3">
                            <h2 className=" col-span-full"> Izgled žiga </h2>   
                            <div className="flex gap-5 w-full">
                                <div className="w-[25rem] flex flex-col gap-5">
                                    <div className='flex-col gap-1 items-start'>
                                        <p className='font-light text-sm'>Naznačenje boje, odnosno boja iz kojih se znak sastoji:</p>                                                                      
                                        <input type="text" name="naznacenjeBoje" className='w-full' value={informacijeOZigu.boje} onChange={e => setInformacijeOZigu({...informacijeOZigu, boje:e.target.value})}/>
                                    </div>
                                    <div className='flex-col gap-1 items-start'>
                                        <p className='font-light text-sm'>Transliteracija znaka*:</p>                                                                      
                                        <input type="text" name="transliteracija" className='w-full' value={informacijeOZigu.transliteracija} onChange={e => setInformacijeOZigu({...informacijeOZigu, transliteracija:e.target.value})}/>                
                                    </div>
                                    <div className='flex-col gap-1 items-start'>
                                        <p className='font-light text-sm'>Prevod znaka*:</p>                                                                      
                                        <input type="text"  name="prevod" className='w-full' value={informacijeOZigu.prevod} onChange={e => setInformacijeOZigu({...informacijeOZigu, prevod:e.target.value})}/>                
                                    </div>
                                    <div className='flex-col gap-1 items-start'>
                                        <p className='font-light text-sm'>Opis znaka:</p>                                                                      
                                        <textarea rows={2} name="opisZnaka" className='w-full' value={informacijeOZigu.opis} onChange={e => setInformacijeOZigu({...informacijeOZigu, opis:e.target.value})}/>                                                     
                                    </div>
                                </div>
                                <div className="w-[20rem] flex flex-col gap-2">
                                    <p>Izgled znaka:</p>
                                    <hr/>
                                    <div className="flex justify-center h-full">
                                        <div className="flex items-center justify-center w-full">
                                            <label htmlFor="dropzone-file" className="flex flex-col items-center justify-center w-full h-64 border-2 border-gray-300 border-dashed rounded-lg cursor-pointer bg-gray-50  hover:bg-gray-100 ">
                                                <div className="flex flex-col items-center justify-center pt-5 pb-6">
                                                    {informacijeOZigu.izgledZnaka && informacijeOZigu.izgledZnaka.length > 0 && (
                                                        <img src={URL.createObjectURL(informacijeOZigu.izgledZnaka[0])} alt="img" className="w-28 h-28 mb-3" />
                                                    )}
                                                    <svg aria-hidden="true" className="w-10 h-10 mb-3 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M7 16a4 4 0 01-.88-7.903A5 5 0 1115.9 6L16 6a5 5 0 011 9.9M15 13l-3-3m0 0l-3 3m3-3v12"></path></svg>
                                                    <p className="mb-2 text-sm text-gray-500 "><span className="font-semibold">Click to upload</span> or drag and drop</p>
                                                    <p className="text-xs text-gray-500 ">SVG, PNG, JPG or GIF (MAX. 800x400px)</p>
                                                </div>
                                                <input id="dropzone-file" type="file" className="hidden" onChange={e => setInformacijeOZigu({...informacijeOZigu, izgledZnaka:e.target.files as FileList})} />
                                            </label>
                                        </div> 
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div id="dodatneInformacije" className="flex flex-col gap-5">
                            <h2 className=" col-span-full"> Dodatne informacije </h2>
                            <div className="flex flex-col gap-3">
                                <p>Označite brojeve klase roba i usluga prema Ničanskoj klasifikaciji:</p>   
                                {nicanskaKlasifikacija(informacijeOZigu, setInformacijeOZigu)}                            
                            </div>
                            <div className='flex-col gap-1 items-start'>
                                <p className='font-light text-sm'>Zatraženo pravo prvenstva i osnov:</p>                                                                      
                                <input type="text" name="pravoPrvenstva" className='w-full' value={informacijeOZigu.pravo} onChange={e => setInformacijeOZigu({...informacijeOZigu, pravo:e.target.value})}/>                                            
                            </div>
                        </div>

                    </div>
                    <footer className="self-start">*Popuniti samo ako je znak ili element znaka ispisan slovima koja nisu ćirilična ili latinična</footer>
                </div>

                <div id="takse" className="form-block">
                    <h3>Plaćene takse</h3>
                    <table className="w-[90%] h-48">
                        <tbody>
                            <tr>
                                <td className='items-end'>
                                    Osnovna taksa:
                                </td>
                                <td className='w-3/5'>
                                    <input type="number" placeholder="rsd" name='osnovna-taksa' className='w-full' value={placeneTakse.osnovna} onChange={e => setPlaceneTakse({...placeneTakse, osnovna:+e.target.value})}/>
                                </td>
                            </tr>
                            <tr>
                                <td className="flex gap-4 justify-start mr-4 items-end">
                                    <p className=''>Za</p> 
                                    <input type="text" name='naziv-klase'  className="w-20 !border-t-0 border-l-0 border-r-0 !border-b-2" value={placeneTakse.naziv} onChange={e => setPlaceneTakse({...placeneTakse, naziv:e.target.value})}/>
                                    <p className=''>klasa:</p>
                                </td>
                                <td className="w-3/5">
                                    <input type="number" placeholder="rsd" className="w-full" name='za-klasa' value={placeneTakse.zaKlasa} onChange={e => setPlaceneTakse({...placeneTakse, zaKlasa:+e.target.value})}/>
                                </td>
                            </tr>
                            <tr>
                                <td className='flex items-end'>Za grafičko rešenje:</td>
                                <td className='w-3/5'>
                                    <input type="number" placeholder="rsd" className="w-full" name='graficko-resenje' value={placeneTakse.grafickoResenje} onChange={e => setPlaceneTakse({...placeneTakse, grafickoResenje:+e.target.value})}/>
                                </td>
                            </tr>
                            <tr>
                                <td className='flex items-end'>UKUPNO:</td>
                                <td className='w-3/5'>
                                    <input type="number" placeholder="rsd" className="w-full" name='ukupno' value={placeneTakse.ukupno} onChange={e => setPlaceneTakse({...placeneTakse, ukupno:+e.target.value})}/>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>

                <div id="prilozi" className="form-block">
                    <h3>Prilozi uz zahtev:</h3>
                    <div className="form-input-container">
                        <div className="flex justify-between w-full items-center">
                            <p>Spisak robe i usluga:</p>
                            <input type="file" name='spisak-robe'  className="!border-none" onChange={e => setPrilozeniDokumenti({...prilozeniDokumenti, spisakRobeIUsluga:e.target.files as FileList})}/>
                        </div>
                        <div className="flex justify-between w-full items-center">
                            <p>Punomocje:</p>
                            <input type="file" name='punomocje-fajl'  className="!border-none" onChange={e => setPrilozeniDokumenti({...prilozeniDokumenti, punomocje:e.target.files as FileList})}/>
                        </div>
                        <div className="flex justify-between w-full items-center">
                            <p>Generalno punomocje:</p>
                            <input type="file" name='generalno-punomocje-fajl' className="!border-none" onChange={e => setPrilozeniDokumenti({...prilozeniDokumenti, generalnoPunomocje:e.target.files as FileList})}/>
                        </div>
                        <div className="flex justify-between w-full items-center">
                            <p>Punomocje naknadno dostavljeno:</p>
                            <input type="file" name='naknadno-punomocje-fajl' className="!border-none" onChange={e => setPrilozeniDokumenti({...prilozeniDokumenti, punomocjeNaknadnoDostavljeno:e.target.files as FileList})}/>
                        </div>
                        <div className="flex justify-between w-full items-center">
                            <p>Opsti akt:</p>
                            <input type="file" name='opsti-akt-fajl' className="!border-none" onChange={e => setPrilozeniDokumenti({...prilozeniDokumenti, opstiAkt:e.target.files as FileList})}/>
                        </div>
                        <div className="flex justify-between w-full items-center">
                            <p>Dokaz o pravu prvenstva:</p>
                            <input type="file" name='dokaz-o-pravu-prvenstva-fajl' className="!border-none" onChange={e => setPrilozeniDokumenti({...prilozeniDokumenti, pravoPrvenstva:e.target.files as FileList})}/>
                        </div>
                        <div className="flex justify-between w-full items-center">
                            <p>Dokaz o uplati takse:</p>
                            <input type="file" name='dokaz-o-uplati-takse-fajl' className="!border-none" onChange={e => setPrilozeniDokumenti({...prilozeniDokumenti, uplataTakse:e.target.files as FileList})}/>
                        </div>
                    </div>
                </div>

                <button onClick={e => onSubmit(e)} className="bg-blue-500 hover:bg-blue-700 text-white font-bold px-6 py-2 rounded w-fit self-center mt-10 text-lg">Pošalji</button>
                
            </form>
        </div>
     );
}
 
export default ZigForm;