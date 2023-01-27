import {useContext, useState} from 'react'
import UserContext, { Role } from '../../store/user-context';
import { validRegisterParams } from './auth.service';
import { RegisterParam } from './types';

const Register:React.FunctionComponent = () => {
    const { user, setUser } = useContext(UserContext);
    const [inputs, setInputs] = useState<RegisterParam>({name:'', surname:'',email:'', password:'', role:Role.CLIENT})
    const register = (ev: any) => {
        ev.preventDefault()
        if (validRegisterParams(inputs)) {
            console.log(inputs)
            setUser({name:inputs.name, surname:inputs.surname, email:inputs.email, role:inputs.role})
        }
    }
   
    return ( 
    <form onSubmit={e => register(e)} className="rounded border flex flex-col gap-8 p-6 items-center h-full w-3/4 text-lg">
        <div className="w-full flex gap-3">
            <div className="flex flex-col items-center w-full">
                <p className="font-light self-start">Ime</p>
                <input type='text' value={inputs!.name} onChange={e => setInputs({...inputs, name:e.target.value})} className="h-[5vh] w-full py-2.5 px-0 bg-transparent border-0 border-b-2 border-gray-dark appearance-none focus:!outline-none focus:ring-0"/>
            </div>
            <div className="flex flex-col items-center w-full">
                <p className="font-light self-start">Prezime</p>
                <input type='text' value={inputs!.surname} onChange={e => setInputs({...inputs, surname:e.target.value})} className="h-[5vh] w-full py-2.5 px-0 bg-transparent border-0 border-b-2 border-gray-dark appearance-none focus:!outline-none focus:ring-0"/>
            </div>
        </div>
        <div className="flex flex-col items-center w-full">
            <p className="font-light self-start">Email</p>
            <input type='text' value={inputs!.email} onChange={e => setInputs({...inputs, email:e.target.value})} className="h-[5vh] w-full py-2.5 px-0 bg-transparent border-0 border-b-2 border-gray-dark appearance-none focus:!outline-none focus:ring-0"/>
        </div>
        <div className="flex flex-col items-center w-full">
            <p className="font-light self-start">Lozinka</p>
            <input type='password' value={inputs!.password} onChange={e => setInputs({...inputs, password:e.target.value})} className="h-[5vh] w-full py-2.5 px-0 bg-transparent border-0 border-b-2 border-gray-dark appearance-none !focus:outline-none focus:ring-0"/>
        </div>
        <div className="flex justify-between items-center w-full">
            <p className="font-light">Registrujem se kao</p>
            <select value={inputs!.role} onChange={e => setInputs({...inputs, role:e.target.value as Role})} className="h-[5vh] w-1/2 bg-transparent border-0 border-b-2 border-gray-dark appearance-none !focus:outline-none focus:ring-0">
                <option value='CLIENT'>Gradjanin</option>
                <option value='WORKER'>Službenik</option>
            </select>
        </div>
        <button className="text-white rounded-3xl px-4 py-2 bg-green-700 w-1/3">Registruj se</button>
    </form> 
);
}
 
export default Register;