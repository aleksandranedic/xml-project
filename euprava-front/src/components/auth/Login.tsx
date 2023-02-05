import {useContext, useState} from 'react'
import { ToastContainer, toast } from 'react-toastify';
import UserContext, { User} from '../../store/user-context';
import {loginUser, validEmailPassword} from './auth.service';
import {LoginParam} from './types';

interface LoginProps {

}

const Login: React.FunctionComponent<LoginProps> = () => {
    const {user, setUser} = useContext(UserContext);
    const [inputs, setInputs] = useState<LoginParam>({email: '', password: ''})
    const login = async (ev: any) => {
        ev.preventDefault()
        if (validEmailPassword(inputs.email, inputs.password)) {
            const loginDTO: LoginParam = {
                email: inputs.email,
                password: inputs.password,
            }
            try{
                const loggedUser: User = await loginUser(loginDTO);             
                toast.success("Uspesno ste se ulogovali.")
                setTimeout(() => {
                    setUser(loggedUser);
                  }, 1500);
            } catch (error) {
                toast.error("Pogresni kredencijali.")
            }
        } else toast.warning("Email ima format example@domen.com. Lozinka sadrži minimum 8 karaktera i sastoji se iz brojeva, velikih i malih slova.")
    }

    return (
        <>
        <form onSubmit={e => login(e)}
              className="rounded border flex flex-col gap-8 p-6 items-center h-full w-3/4 text-lg">
            <div className="flex flex-col items-center  w-full">
                <p className="font-light self-start">Email</p>
                <input type='text' value={inputs!.email} onChange={e => setInputs({...inputs, email: e.target.value})}
                       className="w-full py-2.5 px-0 bg-transparent border-0 border-b-2 border-gray-dark appearance-none focus:!outline-none focus:ring-0"/>
            </div>
            <div className="flex flex-col items-center w-full">
                <p className="font-light self-start">Lozinka</p>
                <input type='password' value={inputs!.password}
                       onChange={e => setInputs({...inputs, password: e.target.value})}
                       className="w-full py-2.5 px-0 bg-transparent border-0 border-b-2 border-gray-dark appearance-none !focus:outline-none focus:ring-0"/>
            </div>
            <input type='submit' className="text-white rounded-3xl px-4 py-2 bg-green-700 w-1/3 mt-6"
                   value='Uloguj se' onClick={e => login(e)}/>
        </form>
        <ToastContainer position="top-center" draggable={false}/>
        </>
    );
}

export default Login;