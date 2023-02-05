import { useState} from 'react'
import { ToastContainer, toast  } from 'react-toastify';
import  {Role} from '../../store/user-context';
import {registerUser, validRegisterParams} from './auth.service';
import {RegisterError, RegisterParam} from './types';
import 'react-toastify/dist/ReactToastify.css';

const Register: React.FunctionComponent = () => {
    const [inputs, setInputs] = useState<RegisterParam>({
        ime: '',
        prezime: '',
        email: '',
        sifra: '',
        uloga: Role.CLIENT
    })
    const [errors, setErrors] = useState<RegisterError>({
        ime: '',
        prezime: '',
        email: '',
        sifra: '',
    })
    const register = async (ev: any) => {
        ev.preventDefault()
        if (validRegisterParams(inputs, errors, setErrors)) {        
            const registrationDTO: RegisterParam = {
                ime: inputs.ime,
                prezime: inputs.prezime,
                uloga: inputs.uloga,
                email: inputs.email,
                sifra: inputs.sifra
            }
            try {
                await registerUser(registrationDTO);
                toast.success("Registracija uspesna.");
            }
            catch (error) {
                toast.error("Korisnik sa unetim mailom vec postoji.");
            }       
        }
    }

    return (
        <>
        <form onSubmit={e => register(e)}
              className="rounded border flex flex-col gap-8 p-6 items-center h-full w-3/4 text-lg">
            <div className="w-full flex gap-3">
                <div className="flex flex-col items-center w-full">
                    <p className={`font-light self-start ${errors.ime ? 'text-red-600' : 'text-inherit'}`}>Ime</p>
                    <input type='text' value={inputs!.ime} onChange={e => setInputs({...inputs, ime: e.target.value})}
                           className={`h-[5vh] w-full py-2.5 px-0 bg-transparent border-0 border-b-2 appearance-none focus:!outline-none focus:ring-0 ${errors.ime ? 'border-red-600' : ' border-gray-dark'}`}/>
                    <p className='text-red-600 text-sm'>{errors.ime}</p>
                </div>
                <div className="flex flex-col items-center w-full">
                    <p className={`font-light self-start ${errors.prezime ? 'text-red-600' : 'text-inherit'}`}>Prezime</p>
                    <input type='text' value={inputs!.prezime}
                           onChange={e => setInputs({...inputs, prezime: e.target.value})}
                           className={`h-[5vh] w-full py-2.5 px-0 bg-transparent border-0 border-b-2 appearance-none focus:!outline-none focus:ring-0 ${errors.prezime ? 'border-red-600' : ' border-gray-dark'}`}/>
                    <p className='text-red-600 text-sm'>{errors.prezime}</p>
                </div>
            </div>
            <div className="flex flex-col items-center w-full">
            <p className={`font-light self-start ${errors.email ? 'text-red-600' : 'text-inherit'}`}>Email</p>
                <input type='text' value={inputs!.email} onChange={e => setInputs({...inputs, email: e.target.value})}
                       className={`h-[5vh] w-full py-2.5 px-0 bg-transparent border-0 border-b-2 appearance-none focus:!outline-none focus:ring-0 ${errors.email ? 'border-red-600' : ' border-gray-dark'}`}/>
                <p className='text-red-600 text-sm'>{errors.email}</p>
            </div>
            <div className="flex flex-col items-center w-full">
            <p className={`font-light self-start ${errors.sifra ? 'text-red-600' : 'text-inherit'}`}>Lozinka</p>
                <input type='password' value={inputs!.sifra}
                       onChange={e => setInputs({...inputs, sifra: e.target.value})}
                       className={`h-[5vh] w-full py-2.5 px-0 bg-transparent border-0 border-b-2 appearance-none focus:!outline-none focus:ring-0 ${errors.sifra ? 'border-red-600' : ' border-gray-dark'}`}/>
                <p className='text-red-600 text-sm'>{errors.sifra}</p>
            </div>
            <div className="flex justify-between items-center w-full">
                <p className="font-light">Registrujem se kao</p>
                <select value={inputs!.uloga} onChange={e => setInputs({...inputs, uloga: e.target.value as Role})}
                        className="h-[5vh] w-1/2 bg-transparent border-0 border-b-2 border-gray-dark appearance-none !focus:outline-none focus:ring-0">
                    <option value='Gradjanin'>Gradjanin</option>
                    <option value='Službenik'>Službenik</option>
                </select>
            </div>
            <button className="text-white rounded-3xl px-4 py-2 bg-green-700 w-1/3"
                    onClick={(e) => register(e)}>Registruj se
            </button>
        </form>
        <ToastContainer position="top-center" draggable={false}/>
        </>
    );
}

export default Register;