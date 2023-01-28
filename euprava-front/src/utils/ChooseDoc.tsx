import { GiStamper } from "react-icons/gi";
import { TbBulb, TbFileCertificate } from "react-icons/tb";

interface ChooseDocProps {
    sideBarClosed:boolean;
    action:string;
}
 
const ChooseDoc: React.FunctionComponent<ChooseDocProps> = ({sideBarClosed, action}) => {
    return ( 
        <>
        <a href={`/zahtevi/${action}/autorska`} className={`w-full flex items-center p-2 text-base font-normal  rounded-lg text-white hover:bg-gray-700 ${sideBarClosed ? "justify-center": ""}`}>
            <span className={` ${sideBarClosed ? "hidden" : "flex ml-3 gap-3"}`}>
                <TbFileCertificate color='rgb(156 163 175)' className="w-5 h-5"/>
                Autorska prava
            </span>
        </a>
        <a href={`/zahtevi/${action}/zig`} className={`w-full flex items-center p-2 text-base font-normal  rounded-lg text-white hover:bg-gray-700 ${sideBarClosed ? "justify-center": ""}`}>
            <span className={` ${sideBarClosed ? "hidden" : "flex ml-3 gap-3"}`}>
                <GiStamper fill='rgb(156 163 175)' className="w-5 h-5"/>
                Žig
            </span>
        </a>
        <a href={`/zahtevi/${action}/patent`} className={`w-full flex items-center p-2 text-base font-normal  rounded-lg text-white hover:bg-gray-700 ${sideBarClosed ? "justify-center": ""}`}>                        
            <span className={` ${sideBarClosed ? "hidden" : "flex ml-3 gap-3"}`}>
                <TbBulb color='rgb(156 163 175)' className="w-5 h-5"/>
                Patent
            </span>
        </a>
        </>
     );
}
 
export default ChooseDoc;