import {useRef, useState} from 'react'
import { IoIosAddCircle, IoIosCloseCircle } from 'react-icons/io'
import { AiOutlineClose } from 'react-icons/ai'
import { Pronalazac, Punomocnik } from '../types';
import { toast, ToastContainer } from 'react-toastify';
 
interface previousApplication {
    applicationNumber:string,
    applicationDate:string,
    applicationLabel:string
}

const PatentForm: React.FunctionComponent = () => {
    const namePodnosilac:React.RefObject<HTMLInputElement> = useRef<HTMLInputElement>(null);
    const surnamePodnosilac: React.RefObject<HTMLInputElement> = useRef<HTMLInputElement>(null);
    const citizenshipPodnosilac: React.RefObject<HTMLInputElement> = useRef<HTMLInputElement>(null);

    const namePronalazac:React.RefObject<HTMLInputElement> = useRef<HTMLInputElement>(null);
    const surnamePronalazac:React.RefObject<HTMLInputElement> = useRef<HTMLInputElement>(null);
    const citizenshipPronalazac: React.RefObject<HTMLInputElement> = useRef<HTMLInputElement>(null);

    const namePunomocnik:React.RefObject<HTMLInputElement> = useRef<HTMLInputElement>(null);
    const surnamePunomocnik:React.RefObject<HTMLInputElement> = useRef<HTMLInputElement>(null);
    const citizenshipPunomocnik: React.RefObject<HTMLInputElement> = useRef<HTMLInputElement>(null);

    const initialPeviousApplication = {applicationNumber:'', applicationDate:'', applicationLabel:''};
    const [previousApplications, setPreviousApplications] = useState<previousApplication[]>([initialPeviousApplication]);
    const [pronalazaci, setPronalazaci] = useState<Pronalazac[]>([new Pronalazac()]);
    const [punomocnik, setPunomocnik] = useState<Punomocnik>(new Punomocnik());

    const addPreviousApplication = () => {
        setPreviousApplications([...previousApplications, initialPeviousApplication])
    }

    const removePreviousApplication = (ind: number) => {
        const filtered = previousApplications.filter((application, index) => index !== ind)
        setPreviousApplications(filtered)
    }

    const removePronalazac = (ind: number) => {
        const filtered = pronalazaci.filter((pronalazac, index) => index !== ind)
        setPronalazaci(filtered)
    }

    const setPronalazacType = (type:string, value:boolean) => {
        if (type==='prijem' && value && !punomocnik.zaZastupanje && !punomocnik.zajednickiPredstavnik) {
            toast.warning('Morate označiti i jednu od preostale dve opcije.')
            return;
        }
        if (type==='zastupanje' && value && punomocnik.zajednickiPredstavnik){
            toast.error('Punomoćnik za zastupanje ne može biti u isto vreme i zajednički predstavnik')
            return;
        }
        else if (type==='predstavnik' && value && punomocnik.zaZastupanje){
            toast.error('Punomoćnik za zastupanje ne može biti u isto vreme i zajednički predstavnik')
            return;
        }
        if (type==='prijem') {
            setPunomocnik({...punomocnik, zaPrijem:value});
        }
        else if (type==='zastupanje') {
            setPunomocnik({...punomocnik, zaZastupanje:value});
        }
        else {
            setPunomocnik({...punomocnik, zajednickiPredstavnik:value});
        }
    }

    const showFizickoLice =(name:React.RefObject<HTMLInputElement>, surname:React.RefObject<HTMLInputElement>, citizenship:React.RefObject<HTMLInputElement>) => {
        name.current!.classList.remove("hidden");
        name.current!.classList.add("flex");
        surname.current!.classList.remove("hidden");
        surname.current!.classList.add("flex");
        citizenship.current!.classList.remove("hidden");
        citizenship.current!.classList.add("flex");
    }

    const showPoslovnoLice = (name:React.RefObject<HTMLInputElement>, surname:React.RefObject<HTMLInputElement>, citizenship:React.RefObject<HTMLInputElement>) => {
        name.current!.classList.remove("hidden");
        name.current!.classList.add("flex");
        surname.current!.classList.remove("flex");
        surname.current!.classList.add("hidden");
        citizenship.current!.classList.remove("flex");
        citizenship.current!.classList.add("hidden");
    }

    return ( 
        <div className="flex flex-col gap-10 justify-center items-center p-10 pl-0">
            <h1> Zahtev za priznanje patenta </h1>
            <form className="flex flex-col gap-5">
                <div id="naziv-patenta" className="form-block">
                    <h2 className=" col-span-full">Naziv patenta</h2>
                    <div className="flex justify-between items-center w-full">
                        <p>Naziv na srpskom:</p>
                        <input type="text" name="naziv-srpski" className='w-3/4'/>
                    </div>
                    <div className="flex justify-between items-center w-full">
                        <p>Naziv na engleskom:</p>
                        <input type="text" name="naziv-engleski" className='w-3/4'/>
                    </div>
                </div>

                <div id="podnosilac" className="form-block">
                    <h3>Podnosilac prijave</h3>
                    <div className="form-input-container">
                        <div id="licePodnosilac" className="grid grid-cols-2 gap-4"> 
                            <h2 className=" col-span-full"> Tip podnosilaca </h2>        
                            <div className="flex items-center mb-4 col-span-2 self-start">
                                <input id="disabled-checkbox" type="checkbox" value="" className=" w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 rounded focus:ring-blue-500 focus:ring-2"/>
                                <label htmlFor="disabled-checkbox" className="ml-2 text-base">Podnosilac prijave je i pronalazač</label>
                            </div>
                            <div className="flex items-center mr-4">
                                <input type="radio" value="" name="podnosilac-radio" className="w-4 h-4" onClick={() => showFizickoLice(namePodnosilac, surnamePodnosilac, citizenshipPodnosilac)}/>
                                <label>Fizičko lice</label>
                            </div>
                            <div className="flex items-center mr-4 ml-9">
                                <input type="radio" value="" name="podnosilac-radio" className="w-4 h-4"  onClick={() => showPoslovnoLice(namePodnosilac, surnamePodnosilac, citizenshipPodnosilac)}/>
                                <label>Poslovno lice</label>
                            </div> 
                            <div className='flex-col gap-1 items-start hidden'  ref={namePodnosilac}>
                                <p className='font-light text-sm'>Ime</p>
                                <input type="text" name="ime" className='w-full'/>
                            </div>

                            <div className='flex-col gap-1 items-start hidden' ref={surnamePodnosilac}>
                                <p className='font-light text-sm'>Prezime</p>
                                <input type="text"  name="prezime" className='w-full'/>                                        
                            </div>

                            <div className='flex-col gap-1 items-start hidden' ref={citizenshipPodnosilac}>
                                <p className='font-light text-sm'>Državljanstvo</p>
                                <input type="text"  name="drzavljanstvo" className='w-full'/>                                        
                            </div>

                        </div>

                        <div id="adresaPodnosilac" className="grid grid-cols-3 gap-4">
                            <h2 className=" col-span-full"> Podaci o adresi</h2>
                            <div className='flex-col gap-1 items-start'>
                                <p className='font-light text-sm'>Ulica</p>
                                <input type="text" name="ulica" className='w-full'/>
                            </div>
                            <div className='flex-col gap-1 items-start'>
                                <p className='font-light text-sm'>Broj</p>
                                <input type="text"  name="broj" className='w-full'/>
                            </div>
                            <div className='flex-col gap-1 items-start'>
                                <p className='font-light text-sm'>Poštanski broj</p>
                                <input type="text"  name="postanskiBroj" className='w-full'/>
                            </div>
                            <div className='flex-col gap-1 items-start'>
                                <p className='font-light text-sm'>Grad</p>
                                <input type="text"  name="grad" className='w-full'/>
                            </div>
                            <div className='flex-col gap-1 items-start'>
                                <p className='font-light text-sm'>Država</p>
                                <input type="text"  name="drzava" className='w-full'/>
                            </div>
                        </div>

                        <div id="kontaktPodnosilac" className="grid grid-cols-3 gap-4">
                            <h2 className=" col-span-full"> Kontakt</h2>
                            <div className='flex-col gap-1 items-start'>
                                <p className='font-light text-sm'>Telefon</p>
                                <input type="text" name="telefon" className='w-full'/>
                            </div>
                            <div className='flex-col gap-1 items-start'>
                                <p className='font-light text-sm'>E-pošta</p>
                                <input type="text" name="eposta" className='w-full'/>
                            </div>
                            <div className='flex-col gap-1 items-start'>
                                <p className='font-light text-sm'>Faks</p>
                                <input type="text"  name="faks" className='w-full'/>
                            </div>
                        </div>
                    </div>
                </div>

                { pronalazaci.map((pronalazac, index) =>
                    <div id="pronalazac" className="form-block relative" key={index}>
                        <h3>Pronalazač</h3>
                        {
                            index > 0 && <AiOutlineClose className='absolute top-7 right-7 hover:scale-125 cursor-pointer' onClick={e => removePronalazac(index)}/>
                        }
                        <div className="form-input-container">
                            <div id="licePronalazac" className="grid grid-cols-2 gap-4"> 
                                <h2 className=" col-span-full"> Tip pronalazača </h2>        
                                <div className="flex items-center mb-4 col-span-2 self-start">
                                    <input id="disabled-checkbox-pronalazac" type="checkbox" value="" className=" w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 rounded focus:ring-blue-500 focus:ring-2"/>
                                    <label htmlFor="disabled-checkbox-pronalazac" className="ml-2 text-base">Pronalazač ne želi da bude navedem</label>
                                </div>
                                <div className="flex items-center mr-4">
                                    <input type="radio" value="" name="pronalazac-radio" className="w-4 h-4" onClick={() => showFizickoLice(namePronalazac, surnamePronalazac, citizenshipPronalazac)}/>
                                    <label>Fizičko lice</label>
                                </div>
                                <div className="flex items-center mr-4 ml-9">
                                    <input type="radio" value="" name="pronalazac-radio" className="w-4 h-4"  onClick={() => showPoslovnoLice(namePronalazac, surnamePronalazac, citizenshipPronalazac)}/>
                                    <label>Poslovno lice</label>
                                </div> 
                                <div className='flex-col gap-1 items-start hidden'  ref={namePronalazac}>
                                    <p className='font-light text-sm'>Ime</p>
                                    <input type="text" name="ime-pronalazac" className='w-full'/>
                                </div>

                                <div className='flex-col gap-1 items-start hidden' ref={surnamePronalazac}>
                                    <p className='font-light text-sm'>Prezime</p>
                                    <input type="text"  name="prezime-pronalazac" className='w-full'/>                                        
                                </div>

                                <div className='flex-col gap-1 items-start hidden' ref={citizenshipPronalazac}>
                                    <p className='font-light text-sm'>Državljanstvo</p>
                                    <input type="text"  name="drzavljanstvo-pronalac" className='w-full'/>                                        
                                </div>

                            </div>

                            <div id="adresaPronalazac" className="grid grid-cols-3 gap-4">
                                <h2 className=" col-span-full"> Podaci o adresi</h2>
                                <div className='flex-col gap-1 items-start'>
                                    <p className='font-light text-sm'>Ulica</p>
                                    <input type="text" name="ulica-pronalazac" className='w-full'/>
                                </div>
                                <div className='flex-col gap-1 items-start'>
                                    <p className='font-light text-sm'>Broj</p>
                                    <input type="text"  name="broj-pronalazac" className='w-full'/>
                                </div>
                                <div className='flex-col gap-1 items-start'>
                                    <p className='font-light text-sm'>Poštanski broj</p>
                                    <input type="text"  name="postanskiBroj-pronalazac" className='w-full'/>
                                </div>
                                <div className='flex-col gap-1 items-start'>
                                    <p className='font-light text-sm'>Grad</p>
                                    <input type="text"  name="grad-pronalazac" className='w-full'/>
                                </div>
                                <div className='flex-col gap-1 items-start'>
                                    <p className='font-light text-sm'>Država</p>
                                    <input type="text"  name="drzava-pronalazac" className='w-full'/>
                                </div>
                            </div>

                            <div id="kontaktPronalazac" className="grid grid-cols-3 gap-4">
                                <h2 className=" col-span-full"> Kontakt</h2>
                                <div className='flex-col gap-1 items-start'>
                                    <p className='font-light text-sm'>Telefon</p>
                                    <input type="text" name="telefon-pronalazac" className='w-full'/>
                                </div>
                                <div className='flex-col gap-1 items-start'>
                                    <p className='font-light text-sm'>E-pošta</p>
                                    <input type="text" name="eposta-pronalazac" className='w-full'/>
                                </div>
                                <div className='flex-col gap-1 items-start'>
                                    <p className='font-light text-sm'>Faks</p>
                                    <input type="text"  name="faks-pronalazac" className='w-full'/>
                                </div>
                            </div>
                        </div>
                    </div>
                )}
                <div className="border border-gray-dark cursor-pointer hover:bg-gray-dark hover:text-white px-4 py-2 rounded w-fit self-center mb-10 text-normal" onClick={e => setPronalazaci([...pronalazaci, new Pronalazac()])}>Dodaj pronalazača </div>

                <div id="punomocnik" className="form-block">
                    <h3>Punomoćnik</h3>
                    <div className="form-input-container">
                        <div id="licePunomocnik" className="grid grid-cols-2 gap-4"> 
                                <div id="adresaPunomocnik" className="grid grid-cols-3 gap-4 col-span-2">
                                    <h2 className=" col-span-full"> Vrsta punomocnika </h2>      
                                <div className="flex items-center mb-4 self-start">
                                    <input id="disabled-checkbox-punomocnik" type="checkbox" value="" className=" w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 rounded focus:ring-blue-500 focus:ring-2" onChange={e => setPronalazacType('prijem', e.target.checked)}/>
                                    <label htmlFor="disabled-checkbox-punomocnik" className="ml-2 text-base">Punomocnik za prijem pismena</label>
                                </div>
                                <div className="flex items-center mb-4 self-start">
                                    <input id="disabled-checkbox-punomocnik" type="checkbox" value="" className=" w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 rounded focus:ring-blue-500 focus:ring-2" onChange={e => setPronalazacType('zastupanje', e.target.checked)}/>
                                    <label htmlFor="disabled-checkbox-punomocnik" className="ml-2 text-base">Punomocnik za zastupanje</label>
                                </div>
                                <div className="flex items-center mb-4 self-start">
                                    <input id="disabled-checkbox-punomocnik" type="checkbox" value="" className=" w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 rounded focus:ring-blue-500 focus:ring-2" onChange={e => setPronalazacType('predstavnik', e.target.checked)}/>
                                    <label htmlFor="disabled-checkbox-punomocnik" className="ml-2 text-base">Zajednicki predstavnik</label>
                                </div>
                            </div>
                            <h2 className=" col-span-full"> Tip punomoćnika </h2>  
                            <div className="flex items-center mr-4">
                                <input type="radio" value="" name="punomocnik-radio" className="w-4 h-4" onClick={() => showFizickoLice(namePunomocnik, surnamePunomocnik, citizenshipPunomocnik)}/>
                                <label>Fizičko lice</label>
                            </div>
                            <div className="flex items-center mr-4 ml-9">
                                <input type="radio" value="" name="punomocnik-radio" className="w-4 h-4"  onClick={() => showPoslovnoLice(namePunomocnik, surnamePunomocnik, citizenshipPunomocnik)}/>
                                <label>Poslovno lice</label>
                            </div> 
                            <div className='flex-col gap-1 items-start hidden'  ref={namePunomocnik}>
                                <p className='font-light text-sm'>Ime</p>
                                <input type="text" name="ime-punomocnik" className='w-full'/>
                            </div>

                            <div className='flex-col gap-1 items-start hidden' ref={surnamePunomocnik}>
                                <p className='font-light text-sm'>Prezime</p>
                                <input type="text"  name="prezime-punomocnik" className='w-full'/>                                        
                            </div>

                            <div className='flex-col gap-1 items-start hidden' ref={citizenshipPunomocnik}>
                                <p className='font-light text-sm'>Državljanstvo</p>
                                <input type="text"  name="drzavljanstvo-punomocnik" className='w-full'/>                                        
                            </div>

                        </div>

                        <div id="adresaPunomocnik" className="grid grid-cols-3 gap-4">
                            <h2 className=" col-span-full"> Podaci o adresi</h2>
                            <div className='flex-col gap-1 items-start'>
                                <p className='font-light text-sm'>Ulica</p>
                                <input type="text" name="ulica-punomocnik" className='w-full'/>
                            </div>
                            <div className='flex-col gap-1 items-start'>
                                <p className='font-light text-sm'>Broj</p>
                                <input type="text"  name="broj-punomocnik" className='w-full'/>
                            </div>
                            <div className='flex-col gap-1 items-start'>
                                <p className='font-light text-sm'>Poštanski broj</p>
                                <input type="text"  name="postanskiBroj-punomocnik" className='w-full'/>
                            </div>
                            <div className='flex-col gap-1 items-start'>
                                <p className='font-light text-sm'>Grad</p>
                                <input type="text"  name="grad-punomocnik" className='w-full'/>
                            </div>
                            <div className='flex-col gap-1 items-start'>
                                <p className='font-light text-sm'>Država</p>
                                <input type="text"  name="drzava-punomocnik" className='w-full'/>
                            </div>
                        </div>

                        <div id="kontaktPunomocnik" className="grid grid-cols-3 gap-4">
                            <h2 className=" col-span-full"> Kontakt</h2>
                            <div className='flex-col gap-1 items-start'>
                                <p className='font-light text-sm'>Telefon</p>
                                <input type="text" name="telefon-punomocnik" className='w-full'/>
                            </div>
                            <div className='flex-col gap-1 items-start'>
                                <p className='font-light text-sm'>E-pošta</p>
                                <input type="text" name="eposta-punomocnik" className='w-full'/>
                            </div>
                            <div className='flex-col gap-1 items-start'>
                                <p className='font-light text-sm'>Faks</p>
                                <input type="text"  name="faks-punomocnik" className='w-full'/>
                            </div>
                        </div>
                    </div>
                </div>


                <div id="punomocnik" className="form-block">
                    <h3>Dostavljanje</h3>
                    <div className="form-input-container">
                        <div id="adresaDostavljanje" className="grid grid-cols-3 gap-4">
                            <h2 className=" col-span-full"> Adresa za dostavljanje</h2>
                            <div className='flex-col gap-1 items-start'>
                                <p className='font-light text-sm'>Ulica</p>
                                <input type="text" name="ulica-dostavljanje" className='w-full'/>
                            </div>
                            <div className='flex-col gap-1 items-start'>
                                <p className='font-light text-sm'>Broj</p>
                                <input type="text"  name="broj-dostavljanje" className='w-full'/>
                            </div>
                            <div className='flex-col gap-1 items-start'>
                                <p className='font-light text-sm'>Poštanski broj</p>
                                <input type="text"  name="postanskiBroj-dostavljanje" className='w-full'/>
                            </div>
                            <div className='flex-col gap-1 items-start'>
                                <p className='font-light text-sm'>Grad</p>
                                <input type="text"  name="grad-dostavljanje" className='w-full'/>
                            </div>
                            <div className='flex-col gap-1 items-start'>
                                <p className='font-light text-sm'>Država</p>
                                <input type="text"  name="drzava-dostavljanje" className='w-full'/>
                            </div>
                        </div>

                        <div id="licePodnosilac" className="grid grid-cols-2 gap-4"> 
                            <h2 className=" col-span-full"> Način dostavljanja </h2>                          
                            <div className="flex items-center mr-4">
                                <input type="radio" value="" name="podnosilac-radio" className="w-4 h-4"/>
                                <label>Pisano</label>
                            </div>
                            <div className="flex items-center mr-4 ml-9">
                                <input type="radio" value="" name="podnosilac-radio" className="w-4 h-4"/>
                                <label>Elektronski</label>
                            </div> 
                        </div>
                    </div>
                </div>

                <div id="prvobitna-prijava" className="form-block">
                    <h3>Prvobitna prijava</h3>
                    <table className="w-[90%] h-48">
                        <tbody>
                        <tr>
                            <td className='items-end'>Broj prijave: </td>
                            <td className='w-3/5'>
                                <input type="text" placeholder="P-" name='prvobitna-prijava-broj' className='w-full'/>
                            </td>
                        </tr>                      
                        <tr className='align-baseline'>
                            <td className='flex items-end'>Datum podnošenja:</td>
                            <td className='w-3/5'>
                                <input type="date" className="w-full" name='prvobitna-prijava-datum'/>
                            </td>
                        </tr>
                        <tr>
                            <td className='flex items-end'>Tip prijave koja se podnosi:</td>
                            <td className='w-3/5 align-baseline'>
                                <select className="w-full p-1" name='tip-prvobitne-prijave'>
                                    <option value="izdvojena">Izdvojena</option>
                                    <option value="dopunska">Dopunska</option>
                                </select>
                            </td>
                        </tr>
                        </tbody>

                    </table>
                </div>

                <div id="ranije-prijave" className="form-block">
                    <h3>Ranije prijave</h3>
                    <table className="w-[90%] border-separate border-spacing-3">
                        <thead>
                        <tr>
                            <th></th>
                            <th>Broj prijave</th>
                            <th>Tip prijave</th>
                            <th>Dvoslovna oznaka</th>
                            <th></th>
                            <th></th>
                        </tr> 
                        </thead>
                        <tbody>
                        {  previousApplications.map((application, index) =>                   
                            <tr key={index}>
                                <th>
                                    {index+1}.
                                </th>
                                <td>
                                    <input type="text" placeholder="P-" name={`ranija-prijava-broj-${index}`} className='w-full' value={application.applicationNumber}/>
                                </td>
                                <td>
                                    <input type="date" className="w-full" name={`ranija-prijava-datum-${index}`} value={application.applicationDate}/>
                                </td>
                                <td>
                                    <input type="input" className="w-full" name={`ranija-prijava-oznaka-${index}`} value={application.applicationDate}/>
                                </td>
                                <td>
                                    <IoIosAddCircle fill='rgb(14 165 233)' className="w-7 h-7 cursor-pointer" onClick={addPreviousApplication}/>
                                </td>                              
                                <td>
                                    { index > 0 &&
                                    <IoIosCloseCircle fill='#c53030' className="w-7 h-7 cursor-pointer" onClick={e => removePreviousApplication(index)}/>
                                    }
                                </td>
                            </tr> 
                        )} 
                        </tbody>                   
                    </table>
                </div>

                <div id="prilozi" className="form-block">
                    <h3>Prilozi uz zahtev:</h3>
                    <div className="form-input-container">
                        <div className="flex justify-between w-full items-center">
                            <p>Prvobitna prijava:</p>
                            <input type="file" name='prvobitna-prijava-fajl' className="!border-none"/>
                        </div>
                        <div className="flex justify-between w-full items-center">
                            <p>Ranije prijave:</p>
                            <input type="file" name='ranije-prijave-fajl' multiple className="!border-none"/>
                        </div>
                        <div className="flex justify-between w-full items-center">
                            <p>Izjava o osnovu sticanja prava na podnosenje prijave:</p>
                            <input type="file" name='osnova-sticanja-prava-fajl'  className="!border-none"/>
                        </div>
                        <div className="flex justify-between w-full items-center">
                            <p>Izjava pronalazaca da ne zeli da bude naveden:</p>
                            <input type="file" name='izjava-pronalazaca-fajl'  className="!border-none"/>
                        </div>
                        <div className="flex justify-between w-full items-center">
                            <p>Opis pronalaska:</p>
                            <input type="file" name='opis-pronalaska-fajl' className="!border-none"/>
                        </div>
                        <div className="flex justify-between w-full items-center">
                            <p>Patent zahtevi za zastitu pronalaska:</p>
                            <input type="file" name='patent-zahtevi-fajl' className="!border-none"/>
                        </div>
                        <div className="flex justify-between w-full items-center">
                            <p>Nacrt na koji se poziva opis:</p>
                            <input type="file" name='nacrt-fajl' className="!border-none"/>
                        </div>
                        <div className="flex justify-between w-full items-center">
                            <p>Apstrakt:</p>
                            <input type="file" name='apstrakt-fajl' className="!border-none"/>
                        </div>                       
                    </div>
                </div>

                <button className="bg-blue-500 hover:bg-blue-700 text-white font-bold px-6 py-2 rounded w-fit self-center mt-10 text-lg">Pošalji</button>
                
            </form>
            <ToastContainer position="top-center" draggable={false}/>
        </div>
    );
}
 
export default PatentForm;