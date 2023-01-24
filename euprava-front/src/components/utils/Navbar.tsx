import {useContext, useState} from 'react'
import {AiFillHome, AiOutlineFileText, AiOutlineSearch, AiOutlineLogout} from 'react-icons/ai'
import {BiArrowToLeft, BiArrowToRight} from "react-icons/bi"
import {RiMailSendFill} from 'react-icons/ri'
import {FaUserAlt} from 'react-icons/fa'
import {IoIosArrowUp, IoIosArrowDown} from 'react-icons/io'
import UserContext, { Role } from '../../store/user-context';
import ChooseDoc from './ChooseDoc'



const Navbar: React.FunctionComponent = () => {
    const [sideBarClosed, setSideBarClosed] = useState<boolean>(true);
    const [mineRequestsClosed, setMineRequestsClosed] = useState<boolean>(true);
    const [searchRequestsClosed, setSearchRequestsClosed] = useState<boolean>(true);
    const [submitRequestsClosed, setSubmitRequestsClosed] = useState<boolean>(true);
    const [submittedRequestsClosed, setSubmittedRequestsClosed] = useState<boolean>(true);
    const { user, setUser } = useContext(UserContext);
    const logout = () => {
        setUser(null);
        window.location.href = 'http://localhost:3000'
    }
    return ( 
        <aside className={`z-[2000] h-[45rem] float-left duration-150 ease-in-out ${sideBarClosed ? "w-20" : "w-72"}`} aria-label="Sidebar">
        <div className={`h-full rounded-r bg-gray-800 ${sideBarClosed ? "py-6" : "py-6 px-5"}`}>
            <ul className=" flex flex-col justify-between h-full">
            <div className='space-y-3'>
                <li className={`flex w-full cursor-pointer px-2 ${sideBarClosed ? "justify-center" : "justify-end"}`} onClick={() => setSideBarClosed(!sideBarClosed)}>
                    { sideBarClosed ? 
                        <BiArrowToRight  color='rgb(156 163 175)' className="w-7 h-7" />
                        :
                        <BiArrowToLeft  color='rgb(156 163 175)' className=" w-7 h-7" />
                    }
                </li>
                    <div className={`w-full flex items-center p-2 text-lg font-normal  rounded-lg text-white  ${sideBarClosed ? "justify-center": ""}`}> 
                    <FaUserAlt fill='rgb(156 163 175)' className="w-6 h-6"/>
                    <span className={`font-semibold cursor-default ${sideBarClosed ? "hidden" : "flex ml-3"}`}>{`${user!.name} ${user?.surname}`}</span>
                    </div>
              
                <hr className="opacity-25"/>
                    <a href="/" className={`w-full flex items-center p-2 text-base font-normal  rounded-lg text-white hover:bg-gray-700 ${sideBarClosed ? "justify-center": ""}`}>
                    <AiFillHome fill='rgb(156 163 175)' className="w-5 h-5" title='Početna strana'/>
                    <span className={` ${sideBarClosed ? "hidden" : "flex ml-3"}`}>Početna strana</span>
                    </a>
                <hr className="opacity-25"/>

                { user?.role === Role.CLIENT &&
                    <>
                    <div onClick={e => setMineRequestsClosed(!mineRequestsClosed)} className={`cursor-pointer w-full flex items-center p-2 text-base font-normal  rounded-lg text-white hover:bg-gray-700 ${sideBarClosed ? "justify-center": ""}`}>
                    <AiOutlineFileText fill='rgb(156 163 175)' className="w-5 h-5" title='Moji zahtevi'/>
                    <span className={` ${sideBarClosed ? "hidden" : "flex ml-3 justify-between w-full"}`}>
                        Moji zahtevi
                        {mineRequestsClosed && <IoIosArrowDown fill='rgb(156 163 175)' className="w-5 h-5"/>}
                        {!mineRequestsClosed && <IoIosArrowUp fill='rgb(156 163 175)' className="w-5 h-5"/>}      
                    </span>
                    </div>
                    <div className={` ${mineRequestsClosed ? "hidden" : "flex flex-col pl-5 items-center w-full"}`}>
                        <ChooseDoc sideBarClosed={sideBarClosed} action="moji"/>
                    </div>
                    </>
                }

                { user?.role === Role.WORKER &&
                    <>
                    <div onClick={e => setSubmittedRequestsClosed(!submittedRequestsClosed)} className={`cursor-pointer w-full flex items-center p-2 text-base font-normal  rounded-lg text-white hover:bg-gray-700 ${sideBarClosed ? "justify-center": ""}`}>
                        <AiOutlineFileText fill='rgb(156 163 175)' className="w-5 h-5" title='Podneti zahtevi'/>
                        <span className={` ${sideBarClosed ? "hidden" : "flex ml-3 justify-between w-full"}`}>
                            Podneti zahtevi
                            {submittedRequestsClosed && <IoIosArrowDown fill='rgb(156 163 175)' className="w-5 h-5"/>}
                            {!submittedRequestsClosed && <IoIosArrowUp fill='rgb(156 163 175)' className="w-5 h-5"/>}      
                        </span>
                    </div>
                    <div className={` ${submittedRequestsClosed ? "hidden" : "flex flex-col pl-5 items-center w-full"}`}>
                        <ChooseDoc sideBarClosed={sideBarClosed} action="podneti"/>
                    </div>
                    </>
                }
                <hr className="opacity-25"/>
          
                <div onClick={e => setSearchRequestsClosed(!searchRequestsClosed)} className={`cursor-pointer w-full flex items-center p-2 text-base font-normal  rounded-lg text-white hover:bg-gray-700 ${sideBarClosed ? "justify-center": ""}`}>
                    <AiOutlineSearch fill='rgb(156 163 175)' className="w-5 h-5" title="Pretraga zahteva"/>
                    <span className={` ${sideBarClosed ? "hidden" : "flex ml-3 justify-between w-full"}`}>
                        Pretraga zahteva
                        {searchRequestsClosed && <IoIosArrowDown fill='rgb(156 163 175)' className="w-5 h-5"/>}
                        {!searchRequestsClosed && <IoIosArrowUp fill='rgb(156 163 175)' className="w-5 h-5"/>}      
                    </span>
                </div>
                <div className={` ${searchRequestsClosed ? "hidden" : "flex flex-col pl-5 items-center w-full"}`}>
                    <ChooseDoc sideBarClosed={sideBarClosed} action="pretraga"/>
                </div>
               
                <hr className="opacity-25"/>

                { user?.role === Role.CLIENT &&
                    <>
                    <div onClick={e => setSubmitRequestsClosed(!submitRequestsClosed)} className={`cursor-pointer w-full flex items-center p-2 text-base font-normal  rounded-lg text-white hover:bg-gray-700 ${sideBarClosed ? "justify-center": ""}`}>
                        <RiMailSendFill fill='rgb(156 163 175)' className="w-5 h-5" title='Podnesi zahteve'/>
                        <span className={` ${sideBarClosed ? "hidden" : "flex ml-3 justify-between w-full"}`}>
                            Podnesi zahtev
                            {submitRequestsClosed && <IoIosArrowDown fill='rgb(156 163 175)' className="w-5 h-5"/>}
                            {!submitRequestsClosed && <IoIosArrowUp fill='rgb(156 163 175)' className="w-5 h-5"/>}      
                        </span>
                    </div>
                    <div className={` ${submitRequestsClosed ? "hidden" : "flex flex-col pl-5 items-center w-full"}`}>
                        <ChooseDoc sideBarClosed={sideBarClosed} action="podnesi"/>
                    </div>
                    </>
                }
                       
            </div>
        
            <div onClick={logout} className={`cursor-pointer w-full flex items-center p-2 text-base font-normal rounded-lg text-white hover:bg-gray-700 ${sideBarClosed ? "justify-center": ""}`}>
                <AiOutlineLogout fill='rgb(156 163 175)' className="w-5 h-5" title='Odjavi se'/>
                <span className={`${sideBarClosed ? "hidden" : "flex ml-3"}`}>Odjavi se</span>
            </div>
                
            </ul>
        </div>
        </aside>
     );
}
 
export default Navbar;