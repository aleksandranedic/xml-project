import {useRef, useState} from 'react'
import { AiOutlineClose } from 'react-icons/ai';
import { Autor, AutorskoDelo, Podnosilac, PrilozeniDokumenti, Punomocnik } from './types';
 
const AutorskaForm: React.FunctionComponent = () => {
    const namePodnosilac:React.RefObject<HTMLInputElement> = useRef<HTMLInputElement>(null);
    const surnamePodnosilac: React.RefObject<HTMLInputElement> = useRef<HTMLInputElement>(null);
    const citizenshipPodnosilac: React.RefObject<HTMLInputElement> = useRef<HTMLInputElement>(null);
    const pseudonimPodnosilac: React.RefObject<HTMLInputElement> = useRef<HTMLInputElement>(null);

    const namePunomocnik:React.RefObject<HTMLInputElement> = useRef<HTMLInputElement>(null);
    const surnamePunomocnik:React.RefObject<HTMLInputElement> = useRef<HTMLInputElement>(null);
    const citizenshipPunomocnik: React.RefObject<HTMLInputElement> = useRef<HTMLInputElement>(null);

    const [podnosilac, setPodnosilac] = useState<Podnosilac>(new Podnosilac());
    const [punomocnik, setPunomocnik] = useState<Punomocnik>(new Punomocnik());
    const [autori, setAutori] = useState<Autor[]>([new Autor()]);
    const [autorskoDelo, setAutorskoDelo] = useState<AutorskoDelo>(new AutorskoDelo());
    const [prilozeniDokumenti, setPrilozeniDokumenti] = useState<PrilozeniDokumenti>(new PrilozeniDokumenti());

    const showFizickoLice =(name:React.RefObject<HTMLInputElement>, surname:React.RefObject<HTMLInputElement>, citizenship:React.RefObject<HTMLInputElement>, pseudonim?:React.RefObject<HTMLInputElement>) => {
        name.current!.classList.remove("hidden");
        name.current!.classList.add("flex");
        surname.current!.classList.remove("hidden");
        surname.current!.classList.add("flex");
        citizenship.current!.classList.remove("hidden");
        citizenship.current!.classList.add("flex");
        pseudonim?.current!.classList.remove('flex')
        pseudonim?.current!.classList.add("hidden");
    }

    const showPoslovnoLice = (name:React.RefObject<HTMLInputElement>, surname:React.RefObject<HTMLInputElement>, citizenship:React.RefObject<HTMLInputElement>, pseudonim?:React.RefObject<HTMLInputElement>) => {
        name.current!.classList.remove("hidden");
        name.current!.classList.add("flex");
        surname.current!.classList.remove("flex");
        surname.current!.classList.add("hidden");
        citizenship.current!.classList.remove("flex");
        citizenship.current!.classList.add("hidden");
        pseudonim?.current!.classList.remove('flex')
        pseudonim?.current!.classList.add("hidden");
    }

    const showAutor = (name:React.RefObject<HTMLInputElement>, surname:React.RefObject<HTMLInputElement>, citizenship:React.RefObject<HTMLInputElement>, pseudonim:React.RefObject<HTMLInputElement>) => {
        name.current!.classList.remove("hidden");
        name.current!.classList.add("flex");
        surname.current!.classList.remove("hidden");
        surname.current!.classList.add("flex");
        citizenship.current!.classList.remove("hidden");
        citizenship.current!.classList.add("flex");
        pseudonim.current!.classList.remove('hidden')
        pseudonim.current!.classList.add("flex");
    }

    const removeAutor = (ind: number) => {
        const filtered = autori.filter((autor, index) => index !== ind)
        setAutori(filtered)
    }

    const changeAnonymity = (ind: number, value:boolean) => {
        const filtered = autori.map((autor, index) =>  (index === ind ? {...autor, anoniman:value} : {...autor}));
        setAutori(filtered)
    }

    const setAutor = (ind: number, updatedAutor: Autor) => {
        const updated = autori.map((autor, index) => index === ind ? updatedAutor : autor);
        setAutori(updated);
    }


    return ( 
    <div className="flex flex-col gap-10 justify-center items-center p-10 pl-0">
        <h1> Zahtev za priznanje autorskog dela </h1>
        <form className="flex flex-col gap-5">
            <div id="podnosilac" className="form-block">
                <h3>Podnosilac prijave</h3>
                <div className="form-input-container">
                    <div id="licePodnosilac" className="grid grid-cols-2 gap-4"> 
                        <h2 className="col-span-full"> Tip podnosilaca </h2>                           
                        <div className='col-span-full flex gap-2'>
                            <div className="flex items-center w-1/3">
                                <input type="radio" value="" name="podnosilac-radio" className="w-4 h-4" onClick={() => showFizickoLice(namePodnosilac, surnamePodnosilac, citizenshipPodnosilac, pseudonimPodnosilac)}/>
                                <label>Fizičko lice</label>
                            </div>
                            <div className="flex items-center w-1/3">
                                <input type="radio" value="" name="podnosilac-radio" className="w-4 h-4"  onClick={() => showPoslovnoLice(namePodnosilac, surnamePodnosilac, citizenshipPodnosilac, pseudonimPodnosilac)}/>
                                <label>Poslovno lice</label>
                            </div> 
                            <div className="flex items-center w-1/3">
                                <input type="radio" value="" name="podnosilac-radio" className="w-4 h-4"  onClick={() => showAutor(namePodnosilac, surnamePodnosilac, citizenshipPodnosilac, pseudonimPodnosilac)}/>
                                <label>Autor</label>
                            </div> 
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

                        <div className='flex-col gap-1 items-start hidden' ref={pseudonimPodnosilac}>
                            <p className='font-light text-sm'>Pseudonim</p>
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
                            <p className='font-light text-sm'>Email</p>
                            <input type="text" name="email" className='w-full'/>
                        </div>
                        <div className='flex-col gap-1 items-start'>
                            <p className='font-light text-sm'>Faks</p>
                            <input type="text"  name="faks" className='w-full'/>
                        </div>
                    </div>
                </div>
            </div>

            <div id="punomocnik" className="form-block">
                <h3>Punomoćnik</h3>
                <div className="form-input-container">
                    <div id="licePunomocnik" className="grid grid-cols-2 gap-4"> 
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
                            <input type="text" name="broj-punomocnik" className='w-full'/>
                        </div>
                        <div className='flex-col gap-1 items-start'>
                            <p className='font-light text-sm'>Poštanski broj</p>
                            <input type="text" name="postanskiBroj-punomocnik" className='w-full'/>
                        </div>
                        <div className='flex-col gap-1 items-start'>
                            <p className='font-light text-sm'>Grad</p>
                            <input type="text" name="grad-punomocnik" className='w-full'/>
                        </div>
                        <div className='flex-col gap-1 items-start'>
                            <p className='font-light text-sm'>Država</p>
                            <input type="text" name="drzava-punomocnik" className='w-full'/>
                        </div>
                    </div>
                    <div id="kontaktPunomocnik" className="grid grid-cols-3 gap-4">
                        <h2 className=" col-span-full"> Kontakt</h2>
                        <div className='flex-col gap-1 items-start'>
                            <p className='font-light text-sm'>Telefon</p>
                            <input type="text" name="telefon-punomocnik" className='w-full'/>
                        </div>
                        <div className='flex-col gap-1 items-start'>
                            <p className='font-light text-sm'>Email</p>
                            <input type="text" name="email-punomocnik" className='w-full'/>
                        </div>
                        <div className='flex-col gap-1 items-start'>
                            <p className='font-light text-sm'>Faks</p>
                            <input type="text" name="faks-punomocnik" className='w-full'/>
                        </div>
                    </div>
                </div>
            </div>

            { autori.map((autor, index) =>
                    <div id="pronalazac" className="form-block relative" key={index}>
                        <h3>Autor</h3>
                        <div className="flex items-center mb-4 col-span-2 self-start">
                                <input id="disabled-checkbox" type="checkbox" value="" className=" w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 rounded focus:ring-blue-500 focus:ring-2" onChange={e => changeAnonymity(index, e.target.checked)}/>
                                <label htmlFor="disabled-checkbox" className="ml-2 text-base">Autor je anoniman</label>
                        </div>
                        {
                            index > 0 && <AiOutlineClose className='absolute top-7 right-7 hover:scale-125 cursor-pointer' onClick={e => removeAutor(index)}/>
                        }
                        { !autor.anoniman &&

                            <div className="form-input-container">
                                <div id="licePronalazac" className="grid grid-cols-2 gap-4">                                                                                                                           
                                <div className='flex-col gap-1 items-start'>
                                    <p className='font-light text-sm'>Ime</p>
                                    <input type="text" name="ime-pronalazac" className='w-full'/>
                                </div>

                                <div className='flex-col gap-1 items-start'>
                                    <p className='font-light text-sm'>Prezime</p>
                                    <input type="text"  name="prezime-pronalazac" className='w-full'/>                                        
                                </div>

                                <div className='flex-col gap-1 items-start'>
                                    <p className='font-light text-sm'>Državljanstvo</p>
                                    <input type="text"  name="drzavljanstvo-pronalac" className='w-full'/>                                        
                                </div>

                                <div className='flex-col gap-1 items-start'>
                                    <p className='font-light text-sm'>Godina smrti</p>
                                    <input type="text"  name="drzavljanstvo-pronalac" className='w-full'/>                                        
                                </div>

                                <div className='flex-col gap-1 items-start'>
                                    <p className='font-light text-sm'>Pseudonim</p>
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
                        }
                    </div>
                )}
                <div className="border border-gray-dark cursor-pointer hover:bg-gray-dark hover:text-white px-4 py-2 rounded w-fit self-center mb-10 text-normal" onClick={e => setAutori([...autori, new Autor()])}>Dodaj autora </div>
                
                <div id="punomocnik" className="form-block">
                    <h3>Podaci o autorskom delu</h3>
                    <div className="form-input-container">
                        <div className="flex items-center mb-4 col-span-2 self-start">
                            <input id="disabled-checkbox" type="checkbox" value="" className=" w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 rounded focus:ring-blue-500 focus:ring-2"/>
                            <label htmlFor="disabled-checkbox" className="ml-2 text-base">Stvoreno u radnom odnosu</label>
                        </div>
                        <div id="adresaDostavljanje" className="flex flex-col gap-4">
                            <h2 className=" w-full"> Naslov autorskog dela</h2>
                            <input type="text" name="ulica-dostavljanje" className='w-full'/>

                            <h2 className=" col-span-full"> Vrsta autorskog dela</h2>
                            <div className='flex gap-4 w-full'>
                                <select className="w-1/2 py-2.5 px-0 bg-transparent border-0 border-b-2 appearance-none focus:!outline-none focus:ring-0 border-gray-dark">
                                    <option value="knjizevno">Književno delo</option>
                                    <option value="muzicko">Muzičko delo</option> 
                                    <option value="likovno">Likovno delo</option> 
                                    <option value="racunarsko">Računarski program</option> 
                                </select>
                                <div className='flex-col gap-1 items-start w-1/2'>
                                    <p className='font-light text-sm'>Druga vrsta:</p>
                                    <input type="text"  name="postanskiBroj-dostavljanje" className='w-full'/>
                                </div>
                            </div>
                            
                            <h2 className=" w-full"> Forma autorskog dela</h2>
                            <input type="text" name="ulica-dostavljanje" className='w-full'/>

                            <h2 className=" col-span-full">Podaci o naslovu autorskog dela na kome se zasnima delo prerade, ako je u pitanju delo prerade:</h2>
                            <div className='flex gap-4 w-full'>
                                <div className='flex-col gap-1 items-start w-1/2'>
                                    <p className='font-light text-sm'>Naslov autorskog dela:</p>
                                    <input type="text"  name="postanskiBroj-dostavljanje" className='w-full'/>
                                </div>
                                <div className='flex-col gap-1 items-start w-1/2'>
                                    <p className='font-light text-sm'>Ime autora:</p>
                                    <input type="text"  name="postanskiBroj-dostavljanje" className='w-full'/>
                                </div>
                            </div>


                            <h2 className=" w-full"> Način korišćenja</h2>
                            <input type="text" name="ulica-dostavljanje" className='w-full'/>                                                    
                        </div>
                    </div>
                </div>

                <div id="prilozi" className="form-block">
                    <h3>Prilozi uz zahtev:</h3>
                    <div className="form-input-container">
                        <div className="flex justify-between w-full items-center">
                            <p>Opis autorskog dela:</p>
                            <input type="file" name='opis-dela-fajl'  className="!border-none"/>
                        </div>
                        <div className="flex justify-between w-full items-center">
                            <p>Primer autorskog dela:</p>
                            <input type="file" name='primer-fajl'  className="!border-none"/>
                        </div>
                        <div className="flex justify-between w-full items-center">
                            <p>Dokaz o uplati takse:</p>
                            <input type="file" name='dokaz-o-uplati-takse-fajl' className="!border-none"/>
                        </div>
                        <div className="flex justify-between w-full items-center">
                            <p>Izjava o pravno osnovu za podnošenje prijave:</p>
                            <input type="file" name='izjava-pravni-osnov-fajl' className="!border-none"/>
                        </div>
                        <div className="flex justify-between w-full items-center">
                            <p>Izjava o zajedničkom predstavniku:</p>
                            <input type="file" name='zajednicki-predstavnik-fajl' className="!border-none"/>
                        </div>
                        <div className="flex justify-between w-full items-center">
                            <p>Punomoćje:</p>
                            <input type="file" name='punomocje-fajl' className="!border-none"/>
                        </div>
                    </div>
                </div>

                <button className="bg-blue-500 hover:bg-blue-700 text-white font-bold px-6 py-2 rounded w-fit self-center mt-10 text-lg">Pošalji</button>
                
        </form>
    </div> );
}
 
export default AutorskaForm;