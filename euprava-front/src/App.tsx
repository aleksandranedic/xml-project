import Navbar from "./utils/Navbar";
import HomePage from "./page/HomePage";
import {BrowserRouter  as Router, Routes, Route} from "react-router-dom";
import {useState, useEffect} from 'react'
import UserContext, { User } from "./store/user-context";
import ZigForm from "./components/zig/ZigForm";
import SearchZig from "./components/zig/SearchZig";
import SearchAutorska from "./components/autorska/SearchAutorska";
import SearchPatent from "./components/patent/SearchPatent";
import PatentForm from "./components/patent/PatentForm";
import AutorskaForm from "./components/autorska/AutorskaForm";
import {PatentRequests} from "./components/patent/PatentRequests";
import PatentRichEdit from "./components/patent/PatentRichEdit";
import {AutorskaRequests} from "./components/autorska/AutorskaRequests";
import {ZigRequests} from "./components/zig/ZigRequests";
import Zig from "./components/zig/Zig";

const App: React.FunctionComponent = () => {
    const [user, setUser] = useState<User | null>(JSON.parse(localStorage.getItem('user')!) || null);
    useEffect(() => {
    localStorage.setItem('user', JSON.stringify(user));
    }, [user]);

    return ( 
    <UserContext.Provider value={{ user, setUser }}>
            <div id="container flex h-[100%] w-full">
                {user && <Navbar/>}
                <Router>
                    <div className="max-h-[45rem] overflow-auto">
                        <Routes>
                            <Route path='/zig/:broj' element={<Zig/>}/>
                            <Route path='/' element={<HomePage/>}/>
                            <Route path='/zahtevi/pretraga/autorska' element={<SearchAutorska/>}/>
                            <Route path='/zahtevi/pretraga/zig' element={<SearchZig/>}/>
                            <Route path='/zahtevi/pretraga/patent' element={<SearchPatent/>}/>
                            <Route path='/zahtevi/podnesi/autorska' element={<AutorskaForm/>}/>
                            <Route path='/zahtevi/podnesi/zig' element={<ZigForm/>}/>
                            <Route path='/zahtevi/podnesi/patent' element={<PatentForm/>}/>
                            <Route path='/zahtevi/podnesi/detaljno/patent/:brojPrijave' element={<PatentRichEdit/>}/>
                            <Route path='/zahtevi/moji/patent' element={<PatentRequests/>}/>
                            <Route path='/zahtevi/moji/autorska' element={<AutorskaRequests/>}/>
                            <Route path='/zahtevi/moji/zig' element={<ZigRequests/>}/>
                            <Route path='/zahtevi/podneti/patent' element={<PatentRequests/>}/>
                            <Route path='/zahtevi/podneti/zig' element={<ZigRequests/>}/>
                            <Route path='/zahtevi/podneti/autorska' element={<AutorskaRequests/>}/>
                        </Routes>
                    </div>
                </Router>
            </div>
    </UserContext.Provider>
    );
}
 
export default App;