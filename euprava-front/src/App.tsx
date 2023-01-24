import Navbar from "./components/utils/Navbar";
import HomePage from "./page/HomePage";
import {BrowserRouter  as Router, Routes, Route} from "react-router-dom";
import {useState, useEffect} from 'react'
import UserContext, { User } from "./store/user-context";
import DocumentContext, { DocType } from "./store/docuemnt-context";
import ZigForm from "./components/zig/Form";

const App: React.FunctionComponent = () => {
    const [user, setUser] = useState<User | null>(JSON.parse(localStorage.getItem('user')!) || null);
    const [doc, setDoc] = useState<DocType>(JSON.parse(localStorage.getItem('doc')!));
    useEffect(() => {
    localStorage.setItem('user', JSON.stringify(user));
    localStorage.setItem('doc', JSON.stringify(doc));
    }, [user, doc]);

    return ( 
    <UserContext.Provider value={{ user, setUser }}>
            <div id="container flex h-[100%] w-full">
                {user && <Navbar/>}
                <Router>
                <DocumentContext.Provider value={{ doc, setDoc }}>
                    <div className="max-h-[45rem] overflow-auto">

                    <Routes>
                        <Route path='/' element={<HomePage/>}/>
                        <Route path='/zahtevi/podnesi/autorska' element={<ZigForm/>}/>
                        <Route path='/zahtevi/podnesi/zig' element={<ZigForm/>}/>
                        <Route path='/zahtevi/podnesi/patent' element={<ZigForm/>}/>
                    </Routes>
                    </div>
                </DocumentContext.Provider>
                </Router>
            </div>
    </UserContext.Provider>
    );
}
 
export default App;