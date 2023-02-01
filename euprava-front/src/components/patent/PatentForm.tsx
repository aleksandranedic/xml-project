import {useContext, useRef, useState} from 'react'
import {IoIosAddCircle, IoIosCloseCircle} from 'react-icons/io'
import {AiOutlineClose} from 'react-icons/ai'
import {
    Dostavljanje,
    NazivPatent,
    Podnosilac,
    PrilozeniDokumenti,
    Pronalazac,
    PrvobitnaPrijava,
    Punomocnik,
    RanijaPrijava,
    TipPrvobitnePrijave
} from './types';
import {toast, ToastContainer} from 'react-toastify';
import UserContext from '../../store/user-context';
import {Lice} from "../types";
import axios from "axios";


const PatentForm: React.FunctionComponent = () => {
    const {user, setUser} = useContext(UserContext);
    const namePodnosilac: React.RefObject<HTMLInputElement> = useRef<HTMLInputElement>(null);
    const surnamePodnosilac: React.RefObject<HTMLInputElement> = useRef<HTMLInputElement>(null);
    const citizenshipPodnosilac: React.RefObject<HTMLInputElement> = useRef<HTMLInputElement>(null);

    const namePronalazac: React.RefObject<HTMLInputElement> = useRef<HTMLInputElement>(null);
    const surnamePronalazac: React.RefObject<HTMLInputElement> = useRef<HTMLInputElement>(null);
    const citizenshipPronalazac: React.RefObject<HTMLInputElement> = useRef<HTMLInputElement>(null);

    const namePunomocnik: React.RefObject<HTMLInputElement> = useRef<HTMLInputElement>(null);
    const surnamePunomocnik: React.RefObject<HTMLInputElement> = useRef<HTMLInputElement>(null);
    const citizenshipPunomocnik: React.RefObject<HTMLInputElement> = useRef<HTMLInputElement>(null);

    const [pronalazaci, setPronalazaci] = useState<Pronalazac[]>([new Pronalazac()]);
    const [punomocnik, setPunomocnik] = useState<Punomocnik>(new Punomocnik());
    const [nazivPatenta, setNazivPatenta] = useState<NazivPatent>(new NazivPatent());
    const [podnosilac, setPodnosilac] = useState<Podnosilac>(new Podnosilac());
    const [dostavljanje, setDostavljanje] = useState<Dostavljanje>(new Dostavljanje());
    const [pronalazaciNavedeni, setPronalazaciNavedeni] = useState<boolean>(true)
    const [prvobitnaPrijava, setPrvobitnaPrijava] = useState<PrvobitnaPrijava>(new PrvobitnaPrijava())
    const [ranijePrijave, setRanijePrijave] = useState<RanijaPrijava[]>([new RanijaPrijava()]);
    const [prilozeniDokumenti, setPrilozeniDokumenti] = useState<PrilozeniDokumenti>(new PrilozeniDokumenti())


    const addPreviousApplication = () => {
        setRanijePrijave([...ranijePrijave, new RanijaPrijava()])
    }

    const removePreviousApplication = (ind: number) => {
        const filtered = ranijePrijave.filter((application, index) => index !== ind)
        setRanijePrijave(filtered)
    }

    const removePronalazac = (ind: number) => {
        const filtered = pronalazaci.filter((pronalazac, index) => index !== ind)
        setPronalazaci(filtered)
    }

    const setPronalazacType = (type: string, value: boolean) => {
        if (type === 'prijem' && value && !punomocnik.zaZastupanje && !punomocnik.zajednickiPredstavnik) {
            toast.warning('Morate označiti i jednu od preostale dve opcije.')
        }
        if (type === 'zastupanje' && value && punomocnik.zajednickiPredstavnik) {
            toast.error('Punomoćnik za zastupanje ne može biti u isto vreme i zajednički predstavnik')
            return;
        } else if (type === 'predstavnik' && value && punomocnik.zaZastupanje) {
            toast.error('Punomoćnik za zastupanje ne može biti u isto vreme i zajednički predstavnik')
            return;
        }
        if (type === 'prijem') {
            setPunomocnik({...punomocnik, zaPrijem: value});
        } else if (type === 'zastupanje') {
            setPunomocnik({...punomocnik, zaZastupanje: value});
        } else {
            setPunomocnik({...punomocnik, zajednickiPredstavnik: value});
        }
    }

    const setPronalazac = (ind: number, updatedPronalazac: Pronalazac) => {
        const updated = pronalazaci.map((pronalazac, index) => index === ind ? updatedPronalazac : pronalazac);
        setPronalazaci(updated);
    }

    const setRanijaPrijava = (ind: number, updateRanijaPrijava: RanijaPrijava) => {
        const updated = ranijePrijave.map((ranijaPrijava, index) => index === ind ? updateRanijaPrijava : ranijaPrijava);
        setRanijePrijave(updated);
    }

    const showFizickoLice = (name: React.RefObject<HTMLInputElement>, surname: React.RefObject<HTMLInputElement>, citizenship: React.RefObject<HTMLInputElement>) => {
        name.current!.classList.remove("hidden");
        name.current!.classList.add("flex");
        surname.current!.classList.remove("hidden");
        surname.current!.classList.add("flex");
        citizenship.current!.classList.remove("hidden");
        citizenship.current!.classList.add("flex");
    }

    const showPoslovnoLice = (name: React.RefObject<HTMLInputElement>, surname: React.RefObject<HTMLInputElement>, citizenship: React.RefObject<HTMLInputElement>) => {
        name.current!.classList.remove("hidden");
        name.current!.classList.add("flex");
        surname.current!.classList.remove("flex");
        surname.current!.classList.add("hidden");
        citizenship.current!.classList.remove("flex");
        citizenship.current!.classList.add("hidden");
    }


    const validate = (): boolean => {

        let p = {...podnosilac}
        p.kontakt.eposta = "eposta"

        if (!Lice.validate(p)) {
            toast.error("Niste uneli sve podatke o podnosiocu.");
            return false;
        }
        if (pronalazaciNavedeni) {
            for (let pronalazac of pronalazaci) {
                if (!Pronalazac.validate(pronalazac)) {
                    toast.error("Niste uneli sve podatke o svim pronalazačima.");
                    return false;
                }
            }
        }
        if (!NazivPatent.validate(nazivPatenta)) {
            toast.error("Niste uneli oba naziva patenta.");
            return false;
        }

        if (!Dostavljanje.validate(dostavljanje)) {
            toast.error("Morate uneti da li se dostavljanje vrši elektornski ili pisano i ukoliko popunjavate adresu morate popuniti sva polja.");
            return false;
        }
        if (!Punomocnik.validate(punomocnik)) {
            toast.error("Ukoliko punomoćnik postoji morate uneti sve podatke o punomoćniku.");
            return false;
        }
        if (!PrvobitnaPrijava.validate(prvobitnaPrijava)) {
            toast.error("Ukoliko postoji prvobitna prijava morate uneti sve podatke o prvobitnoj prijavi.");
            return false;
        }

        for (let ranijaPrijava of ranijePrijave) {
            if (!RanijaPrijava.validate(ranijaPrijava)) {
                toast.error("Niste uneli sve podatke o svim ranijim prijavama.");
                return false;
            }
        }

        if (!prilozeniDokumenti.opisPronalaska) {
            toast.error("Niste uneli opis pronalaska.");
            return false;
        }
        if (!prilozeniDokumenti.nacrt) {
            toast.error("Niste uneli nacrt.");
            return false;
        }
        if (!prilozeniDokumenti.apstrakt) {
            toast.error("Niste uneli nacrt.");
            return false;
        }
        return true;

    }

    const uploadFile = (file: File): string => {
        let formData = new FormData();

        formData.append('file', file);

        axios.post('http://localhost:8002/upload', formData, {
            headers: {
                'Content-Type': 'multipart/form-data'
            }
        })
            .then(response => {
                toast.success("Uspešno je poslata datoteka: " + file.name);
                return "http://localhost:8002" + response.data;
            })
            .catch(() => {
                toast.error("Greška pri prilaganju datoteke: " + file.name);
                return "";
            });

        return "";
    }


    async function getPrilozi() {
        let prilozi = {
            apstrakt: "", nacrt: "", opis: "",
            izjavaPronalazaca: "", izajavaOSticanjuPrava: "",
            ranijePrijave: [""], prvobitnaPrijava: ""
        }

        let ranijePrijave: string[] = [];

        if (prilozeniDokumenti.apstrakt)
            prilozi.apstrakt = uploadFile(prilozeniDokumenti.apstrakt[0]);
        if (prilozeniDokumenti.nacrt) {
            prilozi.nacrt = uploadFile(prilozeniDokumenti.nacrt[0]);
        }
        if (prilozeniDokumenti.opisPronalaska) {
            prilozi.opis = uploadFile(prilozeniDokumenti.opisPronalaska[0]);
        }
        if (prilozeniDokumenti.osnovSticanja) {
            prilozi.izajavaOSticanjuPrava = uploadFile(prilozeniDokumenti.osnovSticanja[0])
        }
        if (prilozeniDokumenti.nenavedenPronalazac) {
            prilozi.izjavaPronalazaca = uploadFile(prilozeniDokumenti.nenavedenPronalazac[0])
        }
        if (prilozeniDokumenti.prvobitnaPrijava) {
            prilozi.prvobitnaPrijava = uploadFile(prilozeniDokumenti.prvobitnaPrijava[0])
        }
        if (prilozeniDokumenti.ranijePrijave) {
            for (let i = 0; i < prilozeniDokumenti.ranijePrijave.length; i++) {
                ranijePrijave.push(uploadFile(prilozeniDokumenti.ranijePrijave[i]))
            }
        }

        prilozi.ranijePrijave = ranijePrijave;

        return prilozi;
    }

    const onSubmit = async (e: any) => {
        e.preventDefault();

        if (validate()) {
            let p = podnosilac;
            p.kontakt.eposta = "smddfknjg"
            let dto = {
                prilozi: getPrilozi(),
                podnosilac: {info: p.info, kontakt: p.kontakt, adresa: p.adresa},
                podnosilacJePronalazac: podnosilac.pronalazac,
                pronalazaci: pronalazaci,
                punomocnik: punomocnik,
                nazivNaSrpskom: nazivPatenta.srpski,
                nazivNaEngleskom: nazivPatenta.engleski,
                adresaZaDostavljanje: {
                    ulica:dostavljanje.ulica,
                    grad: dostavljanje.grad,
                    broj: dostavljanje.broj,
                    drzava: dostavljanje.drzava,
                    postanskiBroj: dostavljanje.postanskiBroj
                },
                nacinDostavljanja: dostavljanje.pisano ? "pisano" : "elektornski",
                prvobitnaPrijava: prvobitnaPrijava,
                ranijePrijave: ranijePrijave
            }

            //const xml2js = require("xml2js");
            //const builder = new xml2js.Builder();
            //let xml = builder.buildObject(dto);

            axios.post("http://localhost:8002/patent/create", dto).then(response => {
                toast.success(response.data);
            }).catch(() => {
                toast.error("Greška pri čuvanju zahteva.")
            })
        }
    }

    return (
        <div className="flex flex-col gap-10 justify-center items-center p-10 pl-0">
            <h1> Zahtev za priznanje patenta </h1>
            <form className="flex flex-col gap-5">
                <div id="naziv-patenta" className="form-block">
                    <h2 className=" col-span-full">Naziv patenta</h2>
                    <div className="flex justify-between items-center w-full">
                        <p>Naziv na srpskom:</p>
                        <input type="text" name="naziv-srpski" className='w-3/4' value={nazivPatenta.srpski}
                               onChange={e => setNazivPatenta({...nazivPatenta, srpski: e.target.value})}/>
                    </div>
                    <div className="flex justify-between items-center w-full">
                        <p>Naziv na engleskom:</p>
                        <input type="text" name="naziv-engleski" className='w-3/4' value={nazivPatenta.engleski}
                               onChange={e => setNazivPatenta({...nazivPatenta, engleski: e.target.value})}/>
                    </div>
                </div>

                <div id="podnosilac" className="form-block">
                    <h3>Podnosilac prijave</h3>
                    <div className="form-input-container">
                        <div id="licePodnosilac" className="grid grid-cols-2 gap-4">
                            <h2 className=" col-span-full"> Tip podnosilaca </h2>
                            <div className="flex items-center mb-4 col-span-2 self-start">
                                <input id="disabled-checkbox" type="checkbox" checked={podnosilac.pronalazac}
                                       onChange={e => setPodnosilac({...podnosilac, pronalazac: e.target.checked})}
                                       className=" w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 rounded focus:ring-blue-500 focus:ring-2"/>
                                <label htmlFor="disabled-checkbox" className="ml-2 text-base">Podnosilac prijave je i
                                    pronalazač</label>
                            </div>
                            <div className="flex items-center mr-4">
                                <input type="radio" value="" name="podnosilac-radio" className="w-4 h-4"
                                       onClick={() => showFizickoLice(namePodnosilac, surnamePodnosilac, citizenshipPodnosilac)}/>
                                <label>Fizičko lice</label>
                            </div>
                            <div className="flex items-center mr-4 ml-9">
                                <input type="radio" value="" name="podnosilac-radio" className="w-4 h-4"
                                       onClick={() => showPoslovnoLice(namePodnosilac, surnamePodnosilac, citizenshipPodnosilac)}/>
                                <label>Poslovno lice</label>
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
                                <p className='font-light text-sm'>E-pošta</p>
                                {/*<input type="text" name="eposta" className='w-full' value={user!.email}/>*/}
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

                <div className="flex items-center mb-4 col-span-2 self-start">
                    <input id="disabled-checkbox-pronalazac" type="checkbox" checked={pronalazaciNavedeni}
                           onChange={e => setPronalazaciNavedeni(e.target.checked)}
                           className=" w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 rounded focus:ring-blue-500 focus:ring-2"/>
                    <label htmlFor="disabled-checkbox-pronalazac" className="ml-2 text-base">Pronalazač želi da bude
                        naveden</label>
                </div>
                {pronalazaciNavedeni && pronalazaci.map((pronalazac, index) =>
                    <div id="pronalazac" className="form-block relative" key={index}>
                        <h3>Pronalazač</h3>
                        {
                            index > 0 &&
                            <AiOutlineClose className='absolute top-7 right-7 hover:scale-125 cursor-pointer'
                                            onClick={e => removePronalazac(index)}/>
                        }
                        <div className="form-input-container">
                            <div id="licePronalazac" className="grid grid-cols-2 gap-4">
                                <h2 className=" col-span-full"> Tip pronalazača </h2>
                                <div className="flex items-center mr-4">
                                    <input type="radio" value="" name="pronalazac-radio" className="w-4 h-4"
                                           onClick={() => showFizickoLice(namePronalazac, surnamePronalazac, citizenshipPronalazac)}/>
                                    <label>Fizičko lice</label>
                                </div>
                                <div className="flex items-center mr-4 ml-9">
                                    <input type="radio" value="" name="pronalazac-radio" className="w-4 h-4"
                                           onClick={() => showPoslovnoLice(namePronalazac, surnamePronalazac, citizenshipPronalazac)}/>
                                    <label>Poslovno lice</label>
                                </div>
                                <div className='flex-col gap-1 items-start hidden' ref={namePronalazac}>
                                    <p className='font-light text-sm'>Ime</p>
                                    <input type="text" name="ime-pronalazac" className='w-full'
                                           value={pronalazac.info.ime} onChange={e => setPronalazac(index, {
                                        ...pronalazac,
                                        info: {...pronalazac.info, ime: e.target.value}
                                    })}/>
                                </div>

                                <div className='flex-col gap-1 items-start hidden' ref={surnamePronalazac}>
                                    <p className='font-light text-sm'>Prezime</p>
                                    <input type="text" name="prezime-pronalazac" className='w-full'
                                           value={pronalazac.info.prezime} onChange={e => setPronalazac(index, {
                                        ...pronalazac,
                                        info: {...pronalazac.info, prezime: e.target.value}
                                    })}/>
                                </div>

                                <div className='flex-col gap-1 items-start hidden' ref={citizenshipPronalazac}>
                                    <p className='font-light text-sm'>Državljanstvo</p>
                                    <input type="text" name="drzavljanstvo-pronalac" className='w-full'
                                           value={pronalazac.info.drzavljanstvo} onChange={e => setPronalazac(index, {
                                        ...pronalazac,
                                        info: {...pronalazac.info, drzavljanstvo: e.target.value}
                                    })}/>
                                </div>

                            </div>

                            <div id="adresaPronalazac" className="grid grid-cols-3 gap-4">
                                <h2 className=" col-span-full"> Podaci o adresi</h2>
                                <div className='flex-col gap-1 items-start'>
                                    <p className='font-light text-sm'>Ulica</p>
                                    <input type="text" name="ulica-pronalazac" className='w-full'
                                           value={pronalazac.adresa.ulica} onChange={e => setPronalazac(index, {
                                        ...pronalazac,
                                        adresa: {...pronalazac.adresa, ulica: e.target.value}
                                    })}/>
                                </div>
                                <div className='flex-col gap-1 items-start'>
                                    <p className='font-light text-sm'>Broj</p>
                                    <input type="text" name="broj-pronalazac" className='w-full'
                                           value={pronalazac.adresa.broj} onChange={e => setPronalazac(index, {
                                        ...pronalazac,
                                        adresa: {...pronalazac.adresa, broj: e.target.value}
                                    })}/>
                                </div>
                                <div className='flex-col gap-1 items-start'>
                                    <p className='font-light text-sm'>Poštanski broj</p>
                                    <input type="text" name="postanskiBroj-pronalazac" className='w-full'
                                           value={pronalazac.adresa.postanskiBroj} onChange={e => setPronalazac(index, {
                                        ...pronalazac,
                                        adresa: {...pronalazac.adresa, postanskiBroj: e.target.value}
                                    })}/>
                                </div>
                                <div className='flex-col gap-1 items-start'>
                                    <p className='font-light text-sm'>Grad</p>
                                    <input type="text" name="grad-pronalazac" className='w-full'
                                           value={pronalazac.adresa.grad} onChange={e => setPronalazac(index, {
                                        ...pronalazac,
                                        adresa: {...pronalazac.adresa, grad: e.target.value}
                                    })}/>
                                </div>
                                <div className='flex-col gap-1 items-start'>
                                    <p className='font-light text-sm'>Država</p>
                                    <input type="text" name="drzava-pronalazac" className='w-full'
                                           value={pronalazac.adresa.drzava} onChange={e => setPronalazac(index, {
                                        ...pronalazac,
                                        adresa: {...pronalazac.adresa, drzava: e.target.value}
                                    })}/>
                                </div>
                            </div>

                            <div id="kontaktPronalazac" className="grid grid-cols-3 gap-4">
                                <h2 className=" col-span-full"> Kontakt</h2>
                                <div className='flex-col gap-1 items-start'>
                                    <p className='font-light text-sm'>Telefon</p>
                                    <input type="text" name="telefon-pronalazac" className='w-full'
                                           value={pronalazac.kontakt.telefon} onChange={e => setPronalazac(index, {
                                        ...pronalazac,
                                        kontakt: {...pronalazac.kontakt, telefon: e.target.value}
                                    })}/>
                                </div>
                                <div className='flex-col gap-1 items-start'>
                                    <p className='font-light text-sm'>E-pošta</p>
                                    <input type="text" name="eposta-pronalazac" className='w-full'
                                           value={pronalazac.kontakt.eposta} onChange={e => setPronalazac(index, {
                                        ...pronalazac,
                                        kontakt: {...pronalazac.kontakt, eposta: e.target.value}
                                    })}/>
                                </div>
                                <div className='flex-col gap-1 items-start'>
                                    <p className='font-light text-sm'>Faks</p>
                                    <input type="text" name="faks-pronalazac" className='w-full'
                                           value={pronalazac.kontakt.faks} onChange={e => setPronalazac(index, {
                                        ...pronalazac,
                                        kontakt: {...pronalazac.kontakt, faks: e.target.value}
                                    })}/>
                                </div>
                            </div>
                        </div>
                    </div>
                )}
                {pronalazaciNavedeni &&
                    <div
                        className="border border-gray-dark cursor-pointer hover:bg-gray-dark hover:text-white px-4 py-2 rounded w-fit self-center mb-10 text-normal"
                        onClick={e => setPronalazaci([...pronalazaci, new Pronalazac()])}>Dodaj pronalazača </div>
                }

                <div id="punomocnik" className="form-block">
                    <h3>Punomoćnik</h3>
                    <div id="vrstaPunomocnik" className="grid grid-cols-3 gap-4 col-span-2">
                        <h2 className=" col-span-full"> Vrsta punomocnika </h2>
                        <div className="flex items-center mb-4 self-start">
                            <input id="disabled-checkbox-punomocnik" type="checkbox" checked={punomocnik.zaPrijem}
                                   className=" w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 rounded focus:ring-blue-500 focus:ring-2"
                                   onChange={e => setPronalazacType('prijem', e.target.checked)}/>
                            <label htmlFor="disabled-checkbox-punomocnik" className="ml-2 text-base">Punomocnik za
                                prijem pismena</label>
                        </div>
                        <div className="flex items-center mb-4 self-start">
                            <input id="disabled-checkbox-punomocnik" type="checkbox" checked={punomocnik.zaZastupanje}
                                   className=" w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 rounded focus:ring-blue-500 focus:ring-2"
                                   onChange={e => setPronalazacType('zastupanje', e.target.checked)}/>
                            <label htmlFor="disabled-checkbox-punomocnik" className="ml-2 text-base">Punomocnik za
                                zastupanje</label>
                        </div>
                        <div className="flex items-center mb-4 self-start">
                            <input id="disabled-checkbox-punomocnik" type="checkbox"
                                   checked={punomocnik.zajednickiPredstavnik}
                                   className=" w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 rounded focus:ring-blue-500 focus:ring-2"
                                   onChange={e => setPronalazacType('predstavnik', e.target.checked)}/>
                            <label htmlFor="disabled-checkbox-punomocnik" className="ml-2 text-base">Zajednicki
                                predstavnik</label>
                        </div>
                    </div>
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
                                <p className='font-light text-sm'>E-pošta</p>
                                <input type="text" name="eposta-punomocnik" className='w-full'
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


                <div id="punomocnik" className="form-block">
                    <h3>Dostavljanje</h3>
                    <div className="form-input-container">
                        <div id="adresaDostavljanje" className="grid grid-cols-3 gap-4">
                            <h2 className=" col-span-full"> Adresa za dostavljanje</h2>
                            <div className='flex-col gap-1 items-start'>
                                <p className='font-light text-sm'>Ulica</p>
                                <input type="text" name="ulica-dostavljanje" className='w-full'
                                       value={dostavljanje.ulica}
                                       onChange={e => setDostavljanje({...dostavljanje, ulica: e.target.value})}/>
                            </div>
                            <div className='flex-col gap-1 items-start'>
                                <p className='font-light text-sm'>Broj</p>
                                <input type="text" name="broj-dostavljanje" className='w-full' value={dostavljanje.broj}
                                       onChange={e => setDostavljanje({...dostavljanje, broj: e.target.value})}/>
                            </div>
                            <div className='flex-col gap-1 items-start'>
                                <p className='font-light text-sm'>Poštanski broj</p>
                                <input type="text" name="postanskiBroj-dostavljanje" className='w-full'
                                       value={dostavljanje.postanskiBroj} onChange={e => setDostavljanje({
                                    ...dostavljanje,
                                    postanskiBroj: e.target.value
                                })}/>
                            </div>
                            <div className='flex-col gap-1 items-start'>
                                <p className='font-light text-sm'>Grad</p>
                                <input type="text" name="grad-dostavljanje" className='w-full' value={dostavljanje.grad}
                                       onChange={e => setDostavljanje({...dostavljanje, grad: e.target.value})}/>
                            </div>
                            <div className='flex-col gap-1 items-start'>
                                <p className='font-light text-sm'>Država</p>
                                <input type="text" name="drzava-dostavljanje" className='w-full'
                                       value={dostavljanje.drzava}
                                       onChange={e => setDostavljanje({...dostavljanje, drzava: e.target.value})}/>
                            </div>
                        </div>

                        <div id="licePodnosilac" className="grid grid-cols-2 gap-4">
                            <h2 className=" col-span-full"> Način dostavljanja </h2>
                            <div className="flex items-center mr-4">
                                <input type="radio" name="podnosilac-radio" className="w-4 h-4"
                                       checked={dostavljanje.pisano} onChange={e => setDostavljanje({
                                    ...dostavljanje,
                                    pisano: e.target.checked,
                                    elektronski: !e.target.checked
                                })}/>
                                <label>Pisano</label>
                            </div>
                            <div className="flex items-center mr-4 ml-9">
                                <input type="radio" value="" name="podnosilac-radio" className="w-4 h-4"
                                       checked={dostavljanje.elektronski} onChange={e => setDostavljanje({
                                    ...dostavljanje,
                                    elektronski: e.target.checked,
                                    pisano: !e.target.checked
                                })}/>
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
                            <td className='items-end'>Broj prijave:</td>
                            <td className='w-3/5'>
                                <input type="text" placeholder="P-" name='prvobitna-prijava-broj' className='w-full'
                                       value={prvobitnaPrijava.brojPrijave} onChange={e => setPrvobitnaPrijava({
                                    ...prvobitnaPrijava,
                                    brojPrijave: e.target.value
                                })}/>
                            </td>
                        </tr>
                        <tr className='align-baseline'>
                            <td className='flex items-end'>Datum podnošenja:</td>
                            <td className='w-3/5'>
                                <input type="date" className="w-full" name='prvobitna-prijava-datum'
                                       value={prvobitnaPrijava.datum} onChange={e => setPrvobitnaPrijava({
                                    ...prvobitnaPrijava,
                                    datum: e.target.value
                                })}/>
                            </td>
                        </tr>
                        <tr>
                            <td className='flex items-end'>Tip prijave koja se podnosi:</td>
                            <td className='w-3/5 align-baseline'>
                                <select className="w-full p-1" name='tip-prvobitne-prijave' value={prvobitnaPrijava.tip}
                                        onChange={e => setPrvobitnaPrijava({
                                            ...prvobitnaPrijava,
                                            tip: e.target.value as TipPrvobitnePrijave
                                        })}>
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
                        {ranijePrijave.map((application, index) =>
                            <tr key={index}>
                                <th>
                                    {index + 1}.
                                </th>
                                <td>
                                    <input type="text" placeholder="P-" name={`ranija-prijava-broj-${index}`}
                                           className='w-full' value={application.brojPrijave}
                                           onChange={e => setRanijaPrijava(index, {
                                               ...application,
                                               brojPrijave: e.target.value
                                           })}/>
                                </td>
                                <td>
                                    <input type="date" className="w-full" name={`ranija-prijava-datum-${index}`}
                                           value={application.datum} onChange={e => setRanijaPrijava(index, {
                                        ...application,
                                        datum: e.target.value
                                    })}/>
                                </td>
                                <td>
                                    <input type="input" className="w-full" name={`ranija-prijava-oznaka-${index}`}
                                           value={application.oznaka} onChange={e => setRanijaPrijava(index, {
                                        ...application,
                                        oznaka: e.target.value
                                    })}/>
                                </td>
                                <td>
                                    <IoIosAddCircle fill='rgb(14 165 233)' className="w-7 h-7 cursor-pointer"
                                                    onClick={addPreviousApplication}/>
                                </td>
                                <td>
                                    {index > 0 &&
                                        <IoIosCloseCircle fill='#c53030' className="w-7 h-7 cursor-pointer"
                                                          onClick={e => removePreviousApplication(index)}/>
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
                            <input type="file" name='prvobitna-prijava-fajl' className="!border-none"
                                   onChange={e => setPrilozeniDokumenti({
                                       ...prilozeniDokumenti,
                                       prvobitnaPrijava: e.target.files as FileList
                                   })}/>
                        </div>
                        <div className="flex justify-between w-full items-center">
                            <p>Ranije prijave:</p>
                            <input type="file" name='ranije-prijave-fajl' multiple className="!border-none"
                                   onChange={e => setPrilozeniDokumenti({
                                       ...prilozeniDokumenti,
                                       ranijePrijave: e.target.files as FileList
                                   })}/>
                        </div>
                        <div className="flex justify-between w-full items-center">
                            <p>Izjava o osnovu sticanja prava na podnosenje prijave:</p>
                            <input type="file" name='osnova-sticanja-prava-fajl' className="!border-none"
                                   onChange={e => setPrilozeniDokumenti({
                                       ...prilozeniDokumenti,
                                       osnovSticanja: e.target.files as FileList
                                   })}/>
                        </div>
                        <div className="flex justify-between w-full items-center">
                            <p>Izjava pronalazaca da ne zeli da bude naveden:</p>
                            <input type="file" name='izjava-pronalazaca-fajl' className="!border-none"
                                   onChange={e => setPrilozeniDokumenti({
                                       ...prilozeniDokumenti,
                                       nenavedenPronalazac: e.target.files as FileList
                                   })}/>
                        </div>
                        <div className="flex justify-between w-full items-center">
                            <p>Opis pronalaska:</p>
                            <input type="file" name='opis-pronalaska-fajl' className="!border-none"
                                   onChange={e => setPrilozeniDokumenti({
                                       ...prilozeniDokumenti,
                                       opisPronalaska: e.target.files as FileList
                                   })}/>
                        </div>
                        <div className="flex justify-between w-full items-center">
                            <p>Patent zahtevi za zastitu pronalaska:</p>
                            <input type="file" name='patent-zahtevi-fajl' className="!border-none"
                                   onChange={e => setPrilozeniDokumenti({
                                       ...prilozeniDokumenti,
                                       zastitaPronalaska: e.target.files as FileList
                                   })}/>
                        </div>
                        <div className="flex justify-between w-full items-center">
                            <p>Nacrt na koji se poziva opis:</p>
                            <input type="file" name='nacrt-fajl' className="!border-none"
                                   onChange={e => setPrilozeniDokumenti({
                                       ...prilozeniDokumenti,
                                       nacrt: e.target.files as FileList
                                   })}/>
                        </div>
                        <div className="flex justify-between w-full items-center">
                            <p>Apstrakt:</p>
                            <input type="file" name='apstrakt-fajl' className="!border-none"
                                   onChange={e => setPrilozeniDokumenti({
                                       ...prilozeniDokumenti,
                                       apstrakt: e.target.files as FileList
                                   })}/>
                        </div>
                    </div>
                </div>

                <button onClick={e => onSubmit(e)}
                        className="bg-blue-500 hover:bg-blue-700 text-white font-bold px-6 py-2 rounded w-fit self-center mt-10 text-lg">Pošalji
                </button>

            </form>
            <ToastContainer position="top-center" draggable={false}/>
        </div>
    );
}

export default PatentForm;