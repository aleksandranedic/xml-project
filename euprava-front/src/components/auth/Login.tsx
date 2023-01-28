import {useContext, useState} from 'react'
import UserContext, {Role} from '../../store/user-context';
import {validEmailPassword} from './auth.service';
import {loginParam} from './types';
import axios from "axios";

interface LoginProps {

}

const Login: React.FunctionComponent<LoginProps> = () => {
    const {user, setUser} = useContext(UserContext);
    const [inputs, setInputs] = useState<loginParam>({email: '', password: ''})
    const login = (ev: any) => {
        ev.preventDefault()
        if (validEmailPassword(inputs.email, inputs.password)) {
            setUser({name: 'Pera', surname: 'Peric', email: inputs.email, role: Role.CLIENT})
            const LoginDTO = {
                email: inputs.email,
                password: inputs.password,
            }
            const xml2js = require('xml2js');
            const builder = new xml2js.Builder();
            let xml = builder.buildObject(LoginDTO);
            axios.post("http://localhost:8443/korisnik/login", xml, {
                headers: {
                    "Content-Type": "application/xml"
                }
            }).then(value => {
                const jwt = value.data
                axios.get("http://localhost:8443/korisnik", {
                    headers: {
                        "Authorization": "Bearer " + jwt,
                        "Content-Type": "application/xml",
                        "Accept": "application/xml",
                    }
                }).then(value => {
                    console.log(value.data)
                    const convert = require('xml-js');
                    console.log(convert.xml2js(value.data, {compact: true, alwaysChildren: true}));
                    console.log(convert.xml2js(value.data, {compact: true, alwaysChildren: true})['UserDTO']);
                    console.log(convert.xml2js(value.data, {compact: true, alwaysChildren: true})['UserDTO']['email']);
                    console.log(convert.xml2js(value.data, {
                        compact: true,
                        alwaysChildren: true
                    })['UserDTO']['email']["_text"]);
                })
            })

        }
    }

    return (
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
    );
}

export default Login;