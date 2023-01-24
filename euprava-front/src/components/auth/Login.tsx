import {useContext, useState} from 'react'
import UserContext, { Role } from '../../store/user-context';
import { validEmailPassword } from './auth.service';
import { loginParam } from './types';

interface LoginProps {
    
}
 
const Login: React.FunctionComponent<LoginProps> = () => {
    const { user, setUser } = useContext(UserContext);
    const [inputs, setInputs] = useState<loginParam>({email:'', password:''})
    const login = (ev: any) => {
        ev.preventDefault()
        if (validEmailPassword(inputs.email, inputs.password)) {
            setUser({name:'Pera', surname:'Peric', email:inputs.email, role:Role.CLIENT})
        }
    }
    
    return ( 
        <form onSubmit={e => login(e)} className="rounded border flex flex-col gap-8 p-6 items-center h-full w-3/4 text-lg">
            <div className="flex flex-col items-center  w-full">
                <p className="font-light self-start">Email</p>
                <input type='text' value={inputs!.email} onChange={e => setInputs({...inputs, email:e.target.value})} className="w-full py-2.5 px-0 bg-transparent border-0 border-b-2 border-gray-dark appearance-none focus:!outline-none focus:ring-0"/>
            </div>
            <div className="flex flex-col items-center w-full">
                <p className="font-light self-start">Lozinka</p>
                <input type='password' value={inputs!.password} onChange={e => setInputs({...inputs, password:e.target.value})} className="w-full py-2.5 px-0 bg-transparent border-0 border-b-2 border-gray-dark appearance-none !focus:outline-none focus:ring-0"/>
            </div>
            <input type='submit' className="text-white rounded-3xl px-4 py-2 bg-green-700 w-1/3 mt-6" value='Uloguj se'/>
        </form>
     );
}
 
export default Login;