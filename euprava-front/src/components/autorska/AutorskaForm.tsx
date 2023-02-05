import {useRef, useState, useContext} from 'react'
import {AiOutlineClose} from 'react-icons/ai';
import UserContext from '../../store/user-context';
import {Autor, AutorskoDelo, Podnosilac, PrilozeniDokumenti, Punomocnik, VrstaDela} from './types';
import axios from "axios";
import {toast, ToastContainer} from "react-toastify";
import {Lice} from "../types";
import {Dostavljanje, NazivPatent, Pronalazac, PrvobitnaPrijava, RanijaPrijava} from "../patent/types";

const AutorskaForm: React.FunctionComponent = () => {
    const {user, setUser} = useContext(UserContext);

    const namePodnosilac: React.RefObject<HTMLInputElement> = useRef<HTMLInputElement>(null);
    const surnamePodnosilac: React.RefObject<HTMLInputElement> = useRef<HTMLInputElement>(null);
    const citizenshipPodnosilac: React.RefObject<HTMLInputElement> = useRef<HTMLInputElement>(null);
    const pseudonimPodnosilac: React.RefObject<HTMLInputElement> = useRef<HTMLInputElement>(null);

    const namePunomocnik: React.RefObject<HTMLInputElement> = useRef<HTMLInputElement>(null);
    const surnamePunomocnik: React.RefObject<HTMLInputElement> = useRef<HTMLInputElement>(null);
    const citizenshipPunomocnik: React.RefObject<HTMLInputElement> = useRef<HTMLInputElement>(null);

    const [podnosilac, setPodnosilac] = useState<Podnosilac>(new Podnosilac());
    const [punomocnik, setPunomocnik] = useState<Punomocnik>(new Punomocnik());
    const [autori, setAutori] = useState<Autor[]>([new Autor()]);
    const [autorskoDelo, setAutorskoDelo] = useState<AutorskoDelo>(new AutorskoDelo());
    const [prilozeniDokumenti, setPrilozeniDokumenti] = useState<PrilozeniDokumenti>(new PrilozeniDokumenti());

    const [jeAutor, setJeAutor] = useState(false);

    const showFizickoLice = (name: React.RefObject<HTMLInputElement>, surname: React.RefObject<HTMLInputElement>, citizenship: React.RefObject<HTMLInputElement>, pseudonim?: React.RefObject<HTMLInputElement>) => {
        name.current!.classList.remove("hidden");
        name.current!.classList.add("flex");
        surname.current!.classList.remove("hidden");
        surname.current!.classList.add("flex");
        citizenship.current!.classList.remove("hidden");
        citizenship.current!.classList.add("flex");
        pseudonim?.current!.classList.remove('flex')
        pseudonim?.current!.classList.add("hidden");
    }

    const showPoslovnoLice = (name: React.RefObject<HTMLInputElement>, surname: React.RefObject<HTMLInputElement>, citizenship: React.RefObject<HTMLInputElement>, pseudonim?: React.RefObject<HTMLInputElement>) => {
        name.current!.classList.remove("hidden");
        name.current!.classList.add("flex");
        surname.current!.classList.remove("flex");
        surname.current!.classList.add("hidden");
        citizenship.current!.classList.remove("flex");
        citizenship.current!.classList.add("hidden");
        pseudonim?.current!.classList.remove('flex')
        pseudonim?.current!.classList.add("hidden");
    }

    const showAutor = (name: React.RefObject<HTMLInputElement>, surname: React.RefObject<HTMLInputElement>, citizenship: React.RefObject<HTMLInputElement>, pseudonim: React.RefObject<HTMLInputElement>) => {
        name.current!.classList.remove("hidden");
        name.current!.classList.add("flex");
        surname.current!.classList.remove("hidden");
        surname.current!.classList.add("flex");
        citizenship.current!.classList.remove("hidden");
        citizenship.current!.classList.add("flex");
        pseudonim.current!.classList.remove('hidden')
        pseudonim.current!.classList.add("flex");
    }

    const validate = (): boolean => {

        if (!Lice.validate(podnosilac)) {
            toast.error("Niste uneli sve podatke o podnosiocu.");
            return false;
        }
        if (autorskoDelo.naslov === "") {
            toast.error("Niste uneli naslov autorskog dela.");
            return false;
        }
        if (autorskoDelo.drugaVrsta === "" && autorskoDelo.vrstaDela === null) {
            toast.error("Niste uneli vrstu autorskog dela.");
            return false;
        }
        if (autorskoDelo.forma === "") {
            toast.error("Niste uneli formu autorskog dela.");
            return false;
        }
        return true;
    }
    const onSubmit = async (e: any) => {
        e.preventDefault();

        let p = podnosilac;
        p.kontakt.eposta = "milos2000.mm@gmail.com"
        if (validate()) {
            let p = podnosilac;
            p.kontakt.eposta = "milos2000.mm@gmail.com"
            let autori_list = []
            let anonimni_autori: number = 0;
            for (const autoriListElement of autori) {
                if (autoriListElement.anoniman) {
                    anonimni_autori += 1;
                } else {
                    autori_list.push({Autor: autoriListElement})
                }
            }
            console.log(getPrilozi())
            let Zahtev = {
                podnosilac: podnosilac,
                podnosilacJeAutor: jeAutor,
                brojAnonimnihAutora: anonimni_autori,
                punomocnik: punomocnik,
                autori: autori_list,
                autorskoDelo: autorskoDelo,
                prilozi: await getPrilozi()
            }
            const xml2js = require("xml2js");
            const builder = new xml2js.Builder();
            Zahtev = builder.buildObject(Zahtev);
            axios.post("http://localhost:8003/autor/create", Zahtev, {
                headers: {
                    "Content-Type": "application/xml",
                    "Accept": "*/*",
                }
            }).then(response => {
                console.log(response)
                toast.success(response.data);
            }).catch(() => {
                toast.error("Greška pri čuvanju zahteva.")
            })
        }
    }
    const uploadFile = async (file: File): Promise<string> => {
        let formData = new FormData();
        formData.append('file', file);

        try {
            let fileName = await axios.post('http://localhost:8003/upload', formData, {
                headers: {
                    'Content-Type': 'multipart/form-data'
                }
            })
            toast.success("Uspešno je poslata datoteka: " + file.name);
            return fileName.data
        }
        catch {
            toast.error("Greška pri prilaganju datoteke: " + file.name);
            return "";
        }
    }


    async function getPrilozi() {
        let prilozi = {
            punomocje: "", zajednickiPredstavnik: "", pravniOsnov: "",
            uplataTakse: "", primerDela: "", opisDela: ""
        }

        if (prilozeniDokumenti.punomocje) {
            prilozi.punomocje = await uploadFile(prilozeniDokumenti.punomocje[0]);
            console.log(prilozi.punomocje)
        }
        if (prilozeniDokumenti.zajednickiPredstavnik) {
            prilozi.zajednickiPredstavnik = await uploadFile(prilozeniDokumenti.zajednickiPredstavnik[0]);
        }
        if (prilozeniDokumenti.pravniOsnov) {
            prilozi.pravniOsnov = await uploadFile(prilozeniDokumenti.pravniOsnov[0]);
        }
        if (prilozeniDokumenti.uplataTakse) {
            prilozi.uplataTakse = await uploadFile(prilozeniDokumenti.uplataTakse[0])
        }
        if (prilozeniDokumenti.primerDela) {
            prilozi.primerDela = await uploadFile(prilozeniDokumenti.primerDela[0])
        }
        if (prilozeniDokumenti.opisDela) {
            prilozi.opisDela = await uploadFile(prilozeniDokumenti.opisDela[0])
        }

        return prilozi;
    }

    const removeAutor = (ind: number) => {
        const filtered = autori.filter((autor, index) => index !== ind)
        setAutori(filtered)
    }

    const changeAnonymity = (ind: number, value: boolean) => {
        const filtered = autori.map((autor, index) => (index === ind ? {...autor, anoniman: value} : {...autor}));
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
                                    <input type="radio" value="" name="podnosilac-radio" className="w-4 h-4"
                                           onClick={() => {
                                               showFizickoLice(namePodnosilac, surnamePodnosilac, citizenshipPodnosilac, pseudonimPodnosilac)
                                               setJeAutor(false);
                                           }}/>
                                    <label>Fizičko lice</label>
                                </div>
                                <div className="flex items-center w-1/3">
                                    <input type="radio" value="" name="podnosilac-radio" className="w-4 h-4"
                                           onClick={() => {
                                               showPoslovnoLice(namePodnosilac, surnamePodnosilac, citizenshipPodnosilac, pseudonimPodnosilac)
                                               setJeAutor(false)
                                           }}/>
                                    <label>Poslovno lice</label>
                                </div>
                                <div className="flex items-center w-1/3">
                                    <input type="radio" value="" name="podnosilac-radio" className="w-4 h-4"
                                           onClick={() => {
                                               showAutor(namePodnosilac, surnamePodnosilac, citizenshipPodnosilac, pseudonimPodnosilac)
                                               setJeAutor(true)
                                           }}/>
                                    <label>Autor</label>
                                </div>
                            </div>
                            <div className='flex-col gap-1 items-start hidden' ref={namePodnosilac}>
                                <p className='font-light text-sm'>Ime</p>
                                <input type="text" name="ime" className='w-full' value={podnosilac.info.ime}
                                       onChange={e => setPodnosilac({
                                           ...podnosilac,
                                           info: {...podnosilac.info, ime: e.target.value}
                                       })}/>
                            </div>

                            <div className='flex-col gap-1 items-start hidden' ref={surnamePodnosilac}>
                                <p className='font-light text-sm'>Prezime</p>
                                <input type="text" name="prezime" className='w-full' value={podnosilac.info.prezime}
                                       onChange={e => setPodnosilac({
                                           ...podnosilac,
                                           info: {...podnosilac.info, prezime: e.target.value}
                                       })}/>
                            </div>

                            <div className='flex-col gap-1 items-start hidden' ref={citizenshipPodnosilac}>
                                <p className='font-light text-sm'>Državljanstvo</p>
                                <input type="text" name="drzavljanstvo" className='w-full'
                                       value={podnosilac.info.drzavljanstvo} onChange={e => setPodnosilac({
                                    ...podnosilac,
                                    info: {...podnosilac.info, drzavljanstvo: e.target.value}
                                })}/>
                            </div>

                            <div className='flex-col gap-1 items-start hidden' ref={pseudonimPodnosilac}>
                                <p className='font-light text-sm'>Pseudonim</p>
                                <input type="text" name="drzavljanstvo" className='w-full' value={podnosilac.pseudonim}
                                       onChange={e => setPodnosilac({...podnosilac, pseudonim: e.target.value})}/>
                            </div>

                        </div>

                        <div id="adresaPodnosilac" className="grid grid-cols-3 gap-4">
                            <h2 className=" col-span-full"> Podaci o adresi</h2>
                            <div className='flex-col gap-1 items-start'>
                                <p className='font-light text-sm'>Ulica</p>
                                <input type="text" name="ulica" className='w-full' value={podnosilac.adresa.ulica}
                                       onChange={e => setPodnosilac({
                                           ...podnosilac,
                                           adresa: {...podnosilac.adresa, ulica: e.target.value}
                                       })}/>
                            </div>
                            <div className='flex-col gap-1 items-start'>
                                <p className='font-light text-sm'>Broj</p>
                                <input type="text" name="broj" className='w-full' value={podnosilac.adresa.broj}
                                       onChange={e => setPodnosilac({
                                           ...podnosilac,
                                           adresa: {...podnosilac.adresa, broj: e.target.value}
                                       })}/>
                            </div>
                            <div className='flex-col gap-1 items-start'>
                                <p className='font-light text-sm'>Poštanski broj</p>
                                <input type="text" name="postanskiBroj" className='w-full'
                                       value={podnosilac.adresa.postanskiBroj} onChange={e => setPodnosilac({
                                    ...podnosilac,
                                    adresa: {...podnosilac.adresa, postanskiBroj: e.target.value}
                                })}/>
                            </div>
                            <div className='flex-col gap-1 items-start'>
                                <p className='font-light text-sm'>Grad</p>
                                <input type="text" name="grad" className='w-full' value={podnosilac.adresa.grad}
                                       onChange={e => setPodnosilac({
                                           ...podnosilac,
                                           adresa: {...podnosilac.adresa, grad: e.target.value}
                                       })}/>
                            </div>
                            <div className='flex-col gap-1 items-start'>
                                <p className='font-light text-sm'>Država</p>
                                <input type="text" name="drzava" className='w-full' value={podnosilac.adresa.drzava}
                                       onChange={e => setPodnosilac({
                                           ...podnosilac,
                                           adresa: {...podnosilac.adresa, drzava: e.target.value}
                                       })}/>
                            </div>
                        </div>

                        <div id="kontaktPodnosilac" className="grid grid-cols-3 gap-4">
                            <h2 className=" col-span-full"> Kontakt</h2>
                            <div className='flex-col gap-1 items-start'>
                                <p className='font-light text-sm'>Telefon</p>
                                <input type="text" name="telefon" className='w-full' value={podnosilac.kontakt.telefon}
                                       onChange={e => setPodnosilac({
                                           ...podnosilac,
                                           kontakt: {...podnosilac.kontakt, telefon: e.target.value}
                                       })}/>
                            </div>
                            <div className='flex-col gap-1 items-start'>
                                <p className='font-light text-sm'>Email</p>
                                <input type="text" name="email" className='w-full'
                                       value={user?.email ? user?.email : ""}/>
                            </div>
                            <div className='flex-col gap-1 items-start'>
                                <p className='font-light text-sm'>Faks</p>
                                <input type="text" name="faks" className='w-full' value={podnosilac.kontakt.faks}
                                       onChange={e => setPodnosilac({
                                           ...podnosilac,
                                           kontakt: {...podnosilac.kontakt, faks: e.target.value}
                                       })}/>
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
                                <input type="radio" value="" name="punomocnik-radio" className="w-4 h-4"
                                       onClick={() => showFizickoLice(namePunomocnik, surnamePunomocnik, citizenshipPunomocnik)}/>
                                <label>Fizičko lice</label>
                            </div>
                            <div className="flex items-center mr-4 ml-9">
                                <input type="radio" value="" name="punomocnik-radio" className="w-4 h-4"
                                       onClick={() => showPoslovnoLice(namePunomocnik, surnamePunomocnik, citizenshipPunomocnik)}/>
                                <label>Poslovno lice</label>
                            </div>
                            <div className='flex-col gap-1 items-start hidden' ref={namePunomocnik}>
                                <p className='font-light text-sm'>Ime</p>
                                <input type="text" name="ime-punomocnik" className='w-full' value={punomocnik.info.ime}
                                       onChange={e => setPunomocnik({
                                           ...punomocnik,
                                           info: {...punomocnik.info, ime: e.target.value}
                                       })}/>
                            </div>

                            <div className='flex-col gap-1 items-start hidden' ref={surnamePunomocnik}>
                                <p className='font-light text-sm'>Prezime</p>
                                <input type="text" name="prezime-punomocnik" className='w-full'
                                       value={punomocnik.info.prezime} onChange={e => setPunomocnik({
                                    ...punomocnik,
                                    info: {...punomocnik.info, prezime: e.target.value}
                                })}/>
                            </div>

                            <div className='flex-col gap-1 items-start hidden' ref={citizenshipPunomocnik}>
                                <p className='font-light text-sm'>Državljanstvo</p>
                                <input type="text" name="drzavljanstvo-punomocnik" className='w-full'
                                       value={punomocnik.info.drzavljanstvo} onChange={e => setPunomocnik({
                                    ...punomocnik,
                                    info: {...punomocnik.info, drzavljanstvo: e.target.value}
                                })}/>
                            </div>
                        </div>
                        <div id="adresaPunomocnik" className="grid grid-cols-3 gap-4">
                            <h2 className=" col-span-full"> Podaci o adresi</h2>
                            <div className='flex-col gap-1 items-start'>
                                <p className='font-light text-sm'>Ulica</p>
                                <input type="text" name="ulica-punomocnik" className='w-full'
                                       value={punomocnik.adresa.ulica} onChange={e => setPunomocnik({
                                    ...punomocnik,
                                    adresa: {...punomocnik.adresa, ulica: e.target.value}
                                })}/>
                            </div>
                            <div className='flex-col gap-1 items-start'>
                                <p className='font-light text-sm'>Broj</p>
                                <input type="text" name="broj-punomocnik" className='w-full'
                                       value={punomocnik.adresa.broj} onChange={e => setPunomocnik({
                                    ...punomocnik,
                                    adresa: {...punomocnik.adresa, broj: e.target.value}
                                })}/>
                            </div>
                            <div className='flex-col gap-1 items-start'>
                                <p className='font-light text-sm'>Poštanski broj</p>
                                <input type="text" name="postanskiBroj-punomocnik" className='w-full'
                                       value={punomocnik.adresa.postanskiBroj} onChange={e => setPunomocnik({
                                    ...punomocnik,
                                    adresa: {...punomocnik.adresa, postanskiBroj: e.target.value}
                                })}/>
                            </div>
                            <div className='flex-col gap-1 items-start'>
                                <p className='font-light text-sm'>Grad</p>
                                <input type="text" name="grad-punomocnik" className='w-full'
                                       value={punomocnik.adresa.grad} onChange={e => setPunomocnik({
                                    ...punomocnik,
                                    adresa: {...punomocnik.adresa, grad: e.target.value}
                                })}/>
                            </div>
                            <div className='flex-col gap-1 items-start'>
                                <p className='font-light text-sm'>Država</p>
                                <input type="text" name="drzava-punomocnik" className='w-full'
                                       value={punomocnik.adresa.drzava} onChange={e => setPunomocnik({
                                    ...punomocnik,
                                    adresa: {...punomocnik.adresa, drzava: e.target.value}
                                })}/>
                            </div>
                        </div>
                        <div id="kontaktPunomocnik" className="grid grid-cols-3 gap-4">
                            <h2 className=" col-span-full"> Kontakt</h2>
                            <div className='flex-col gap-1 items-start'>
                                <p className='font-light text-sm'>Telefon</p>
                                <input type="text" name="telefon-punomocnik" className='w-full'
                                       value={punomocnik.kontakt.telefon} onChange={e => setPunomocnik({
                                    ...punomocnik,
                                    kontakt: {...punomocnik.kontakt, telefon: e.target.value}
                                })}/>
                            </div>
                            <div className='flex-col gap-1 items-start'>
                                <p className='font-light text-sm'>Email</p>
                                <input type="text" name="email-punomocnik" className='w-full'
                                       value={punomocnik.kontakt.eposta} onChange={e => setPunomocnik({
                                    ...punomocnik,
                                    kontakt: {...punomocnik.kontakt, eposta: e.target.value}
                                })}/>
                            </div>
                            <div className='flex-col gap-1 items-start'>
                                <p className='font-light text-sm'>Faks</p>
                                <input type="text" name="faks-punomocnik" className='w-full'
                                       value={punomocnik.kontakt.faks} onChange={e => setPunomocnik({
                                    ...punomocnik,
                                    kontakt: {...punomocnik.kontakt, faks: e.target.value}
                                })}/>
                            </div>
                        </div>
                    </div>
                </div>

                {autori.map((autor, index) =>
                    <div id="pronalazac" className="form-block relative" key={index}>
                        <h3>Autor</h3>
                        <div className="flex items-center mb-4 col-span-2 self-start">
                            <input id="disabled-checkbox" type="checkbox" value=""
                                   className=" w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 rounded focus:ring-blue-500 focus:ring-2"
                                   checked={autor.anoniman} onChange={e => changeAnonymity(index, e.target.checked)}/>
                            <label htmlFor="disabled-checkbox" className="ml-2 text-base">Autor je anoniman</label>
                        </div>
                        {
                            index > 0 &&
                            <AiOutlineClose className='absolute top-7 right-7 hover:scale-125 cursor-pointer'
                                            onClick={e => removeAutor(index)}/>
                        }
                        {!autor.anoniman &&

                            <div className="form-input-container">
                                <div id="licePronalazac" className="grid grid-cols-2 gap-4">
                                    <div className='flex-col gap-1 items-start'>
                                        <p className='font-light text-sm'>Ime</p>
                                        <input type="text" name="ime-pronalazac" className='w-full'
                                               value={autor.info.ime} onChange={e => setAutor(index, {
                                            ...autor,
                                            info: {...autor.info, ime: e.target.value}
                                        })}/>
                                    </div>

                                    <div className='flex-col gap-1 items-start'>
                                        <p className='font-light text-sm'>Prezime</p>
                                        <input type="text" name="prezime-pronalazac" className='w-full'
                                               value={autor.info.prezime} onChange={e => setAutor(index, {
                                            ...autor,
                                            info: {...autor.info, prezime: e.target.value}
                                        })}/>
                                    </div>

                                    <div className='flex-col gap-1 items-start'>
                                        <p className='font-light text-sm'>Državljanstvo</p>
                                        <input type="text" name="drzavljanstvo-pronalac" className='w-full'
                                               value={autor.info.drzavljanstvo} onChange={e => setAutor(index, {
                                            ...autor,
                                            info: {...autor.info, drzavljanstvo: e.target.value}
                                        })}/>
                                    </div>

                                    <div className='flex-col gap-1 items-start'>
                                        <p className='font-light text-sm'>Godina smrti</p>
                                        <input type="number" name="drzavljanstvo-pronalac" className='w-full'
                                               value={autor.godinaSmrti} onChange={e => setAutor(index, {
                                            ...autor,
                                            godinaSmrti: +e.target.value
                                        })}/>
                                    </div>

                                    <div className='flex-col gap-1 items-start'>
                                        <p className='font-light text-sm'>Pseudonim</p>
                                        <input type="text" name="drzavljanstvo-pronalac" className='w-full'
                                               value={autor.pseudonim}
                                               onChange={e => setAutor(index, {...autor, pseudonim: e.target.value})}/>
                                    </div>

                                </div>

                                <div id="adresaPronalazac" className="grid grid-cols-3 gap-4">
                                    <h2 className=" col-span-full"> Podaci o adresi</h2>
                                    <div className='flex-col gap-1 items-start'>
                                        <p className='font-light text-sm'>Ulica</p>
                                        <input type="text" name="ulica-pronalazac" className='w-full'
                                               value={autor.adresa.ulica} onChange={e => setAutor(index, {
                                            ...autor,
                                            adresa: {...autor.adresa, ulica: e.target.value}
                                        })}/>
                                    </div>
                                    <div className='flex-col gap-1 items-start'>
                                        <p className='font-light text-sm'>Broj</p>
                                        <input type="text" name="broj-pronalazac" className='w-full'
                                               value={autor.adresa.broj} onChange={e => setAutor(index, {
                                            ...autor,
                                            adresa: {...autor.adresa, broj: e.target.value}
                                        })}/>
                                    </div>
                                    <div className='flex-col gap-1 items-start'>
                                        <p className='font-light text-sm'>Poštanski broj</p>
                                        <input type="text" name="postanskiBroj-pronalazac" className='w-full'
                                               value={autor.adresa.postanskiBroj} onChange={e => setAutor(index, {
                                            ...autor,
                                            adresa: {...autor.adresa, postanskiBroj: e.target.value}
                                        })}/>
                                    </div>
                                    <div className='flex-col gap-1 items-start'>
                                        <p className='font-light text-sm'>Grad</p>
                                        <input type="text" name="grad-pronalazac" className='w-full'
                                               value={autor.adresa.grad} onChange={e => setAutor(index, {
                                            ...autor,
                                            adresa: {...autor.adresa, grad: e.target.value}
                                        })}/>
                                    </div>
                                    <div className='flex-col gap-1 items-start'>
                                        <p className='font-light text-sm'>Država</p>
                                        <input type="text" name="drzava-pronalazac" className='w-full'
                                               value={autor.adresa.drzava} onChange={e => setAutor(index, {
                                            ...autor,
                                            adresa: {...autor.adresa, drzava: e.target.value}
                                        })}/>
                                    </div>
                                </div>

                                <div id="kontaktPronalazac" className="grid grid-cols-3 gap-4">
                                    <h2 className=" col-span-full"> Kontakt</h2>
                                    <div className='flex-col gap-1 items-start'>
                                        <p className='font-light text-sm'>Telefon</p>
                                        <input type="text" name="telefon-pronalazac" className='w-full'
                                               value={autor.kontakt.telefon} onChange={e => setAutor(index, {
                                            ...autor,
                                            kontakt: {...autor.kontakt, telefon: e.target.value}
                                        })}/>
                                    </div>
                                    <div className='flex-col gap-1 items-start'>
                                        <p className='font-light text-sm'>E-pošta</p>
                                        <input type="text" name="eposta-pronalazac" className='w-full'
                                               value={autor.kontakt.eposta} onChange={e => setAutor(index, {
                                            ...autor,
                                            kontakt: {...autor.kontakt, eposta: e.target.value}
                                        })}/>
                                    </div>
                                    <div className='flex-col gap-1 items-start'>
                                        <p className='font-light text-sm'>Faks</p>
                                        <input type="text" name="faks-pronalazac" className='w-full'
                                               value={autor.kontakt.faks} onChange={e => setAutor(index, {
                                            ...autor,
                                            kontakt: {...autor.kontakt, faks: e.target.value}
                                        })}/>
                                    </div>
                                </div>
                            </div>
                        }
                    </div>
                )}
                <div
                    className="border border-gray-dark cursor-pointer hover:bg-gray-dark hover:text-white px-4 py-2 rounded w-fit self-center mb-10 text-normal"
                    onClick={e => setAutori([...autori, new Autor()])}>Dodaj autora
                </div>

                <div id="punomocnik" className="form-block">
                    <h3>Podaci o autorskom delu</h3>
                    <div className="form-input-container">
                        <div className="flex items-center mb-4 col-span-2 self-start">
                            <input id="disabled-checkbox" type="checkbox" checked={autorskoDelo.radniOdnos}
                                   onChange={e => setAutorskoDelo({...autorskoDelo, radniOdnos: e.target.checked})}
                                   className=" w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 rounded focus:ring-blue-500 focus:ring-2"/>
                            <label htmlFor="disabled-checkbox" className="ml-2 text-base">Stvoreno u radnom
                                odnosu</label>
                        </div>
                        <div id="adresaDostavljanje" className="flex flex-col gap-4">
                            <h2 className=" w-full"> Naslov autorskog dela</h2>
                            <input type="text" name="ulica-dostavljanje" className='w-full' value={autorskoDelo.naslov}
                                   onChange={e => setAutorskoDelo({...autorskoDelo, naslov: e.target.value})}/>

                            <h2 className=" col-span-full"> Vrsta autorskog dela</h2>
                            <div className='flex gap-4 w-full'>
                                <select
                                    className="w-1/2 py-2.5 px-0 bg-transparent border-0 border-b-2 appearance-none focus:!outline-none focus:ring-0 border-gray-dark"
                                    value={autorskoDelo.vrstaDela ? autorskoDelo.vrstaDela : ''}
                                    onChange={e => setAutorskoDelo({
                                        ...autorskoDelo,
                                        vrstaDela: e.target.value as VrstaDela,
                                        drugaVrsta: ''
                                    })}>
                                    <option value=""></option>
                                    <option value="knjizevno">Književno delo</option>
                                    <option value="muzicko">Muzičko delo</option>
                                    <option value="likovno">Likovno delo</option>
                                    <option value="racunarsko">Računarski program</option>
                                </select>
                                <div className='flex-col gap-1 items-start w-1/2'>
                                    <p className='font-light text-sm'>Druga vrsta:</p>
                                    <input type="text" name="postanskiBroj-dostavljanje" className='w-full'
                                           value={autorskoDelo.drugaVrsta} onChange={e => setAutorskoDelo({
                                        ...autorskoDelo,
                                        drugaVrsta: e.target.value,
                                        vrstaDela: null
                                    })}/>
                                </div>
                            </div>

                            <h2 className=" w-full"> Forma autorskog dela</h2>
                            <input type="text" name="ulica-dostavljanje" className='w-full' value={autorskoDelo.forma}
                                   onChange={e => setAutorskoDelo({...autorskoDelo, forma: e.target.value})}/>

                            <h2 className=" col-span-full">Podaci o naslovu autorskog dela na kome se zasnima delo
                                prerade, ako je u pitanju delo prerade:</h2>
                            <div className='flex gap-4 w-full'>
                                <div className='flex-col gap-1 items-start w-1/2'>
                                    <p className='font-light text-sm'>Naslov autorskog dela:</p>
                                    <input type="text" name="postanskiBroj-dostavljanje" className='w-full'
                                           value={autorskoDelo.naslovPrerade} onChange={e => setAutorskoDelo({
                                        ...autorskoDelo,
                                        naslovPrerade: e.target.value
                                    })}/>
                                </div>
                                <div className='flex-col gap-1 items-start w-1/2'>
                                    <p className='font-light text-sm'>Ime autora:</p>
                                    <input type="text" name="postanskiBroj-dostavljanje" className='w-full'
                                           value={autorskoDelo.imeAutoraPrerade} onChange={e => setAutorskoDelo({
                                        ...autorskoDelo,
                                        imeAutoraPrerade: e.target.value
                                    })}/>
                                </div>
                            </div>


                            <h2 className=" w-full"> Način korišćenja</h2>
                            <input type="text" name="ulica-dostavljanje" className='w-full'
                                   value={autorskoDelo.nacinKoriscenja}
                                   onChange={e => setAutorskoDelo({...autorskoDelo, nacinKoriscenja: e.target.value})}/>
                        </div>
                    </div>
                </div>

                <div id="prilozi" className="form-block">
                    <h3>Prilozi uz zahtev:</h3>
                    <div className="form-input-container">
                        <div className="flex justify-between w-full items-center">
                            <p>Opis autorskog dela:</p>
                            <input type="file" name='opis-dela-fajl' className="!border-none" size={10485760}
                                   onChange={e => setPrilozeniDokumenti({
                                       ...prilozeniDokumenti,
                                       opisDela: e.target.files as FileList
                                   })}/>
                        </div>
                        <div className="flex justify-between w-full items-center">
                            <p>Primer autorskog dela:</p>
                            <input type="file" name='primer-fajl' className="!border-none" size={10485760}
                                   onChange={e => setPrilozeniDokumenti({
                                       ...prilozeniDokumenti,
                                       primerDela: e.target.files as FileList
                                   })}/>
                        </div>
                        <div className="flex justify-between w-full items-center">
                            <p>Dokaz o uplati takse:</p>
                            <input type="file" name='dokaz-o-uplati-takse-fajl' className="!border-none" size={10485760}
                                   onChange={e => setPrilozeniDokumenti({
                                       ...prilozeniDokumenti,
                                       uplataTakse: e.target.files as FileList
                                   })}/>
                        </div>
                        <div className="flex justify-between w-full items-center">
                            <p>Izjava o pravnom osnovu za podnošenje prijave:</p>
                            <input type="file" name='izjava-pravni-osnov-fajl' className="!border-none" size={10485760}
                                   onChange={e => setPrilozeniDokumenti({
                                       ...prilozeniDokumenti,
                                       pravniOsnov: e.target.files as FileList
                                   })}/>
                        </div>
                        <div className="flex justify-between w-full items-center">
                            <p>Izjava o zajedničkom predstavniku:</p>
                            <input type="file" name='zajednicki-predstavnik-fajl' className="!border-none"
                                   size={10485760}
                                   onChange={e => setPrilozeniDokumenti({
                                       ...prilozeniDokumenti,
                                       zajednickiPredstavnik: e.target.files as FileList
                                   })}/>
                        </div>
                        <div className="flex justify-between w-full items-center">
                            <p>Punomoćje:</p>
                            <input type="file" name='punomocje-fajl' className="!border-none" size={10485760}
                                   onChange={e => setPrilozeniDokumenti({
                                       ...prilozeniDokumenti,
                                       punomocje: e.target.files as FileList
                                   })}/>
                        </div>
                    </div>
                </div>

                <button onClick={e => onSubmit(e)}
                        className="bg-blue-500 hover:bg-blue-700 text-white font-bold px-6 py-2 rounded w-fit self-center mt-10 text-lg">Pošalji
                </button>

            </form>
            <ToastContainer position="top-center" draggable={false}/>
        </div>);
}

export default AutorskaForm;