import { Dispatch, SetStateAction, useContext } from "react";
import UserContext, { Role } from "../../store/user-context";
import { ZahtevZaPriznanjeAutorska } from "../autorska/types";
import { Zahtev } from "../types";
import { ZahtevZaPriznanjeZiga } from "../zig/types";
import ZahtevAutorska from "./ZahtevAutorska";
import ZahtevPatent from "./ZahtevPatent";
import ZahtevZig from "./ZahtevZig";

interface ZahtevModalProps {
    showModal: boolean,
    setShowModal: Dispatch<SetStateAction<boolean>>
    zahtev:  Zahtev | null;
}
 
const ZahtevBody: React.FunctionComponent<{zahtev: Zahtev}> = ({zahtev}) => {
  if (zahtev instanceof ZahtevZaPriznanjeAutorska) return <ZahtevAutorska zahtev={zahtev} />;
      else if (zahtev instanceof ZahtevZaPriznanjeZiga) return <ZahtevZig zahtev={zahtev} />;
      return <ZahtevPatent zahtev={zahtev} />;
}
const ZahtevModal: React.FunctionComponent<ZahtevModalProps> = ({zahtev, showModal, setShowModal}) => {
    const {user} = useContext(UserContext);
    const setType = (zahtev: Zahtev) => {
      if (zahtev instanceof ZahtevZaPriznanjeAutorska) return 'autorskog dela';
      else if (zahtev instanceof ZahtevZaPriznanjeZiga) return 'žiga';
      return 'patenta';
    }  
  
  return (      
    <>
    {
        showModal && zahtev && 
        <>
        <div className="justify-center items-center flex overflow-x-hidden overflow-y-auto fixed inset-0 z-50 outline-none focus:outline-none">
            <div className="relative w-auto my-6 mx-auto max-w-3xl">
              {/*content*/}
              <div className="border-0 rounded-lg shadow-lg relative flex flex-col w-full bg-white outline-none focus:outline-none">
                {/*header*/}
                <div className="flex items-start justify-between p-5 border-b border-solid border-slate-200 rounded-t">
                  <h3 className="text-3xl font-semibold flex justify-center w-full">
                  Zahtev za priznanje {setType(zahtev)}
                  </h3>
                  <button className="p-1 ml-auto bg-transparent border-0 text-black float-right text-3xl leading-none font-semibold outline-none focus:outline-none" onClick={() => setShowModal(false)}>
                    <span className="bg-transparent text-black h-6 w-6 block outline-none focus:outline-none">×</span>
                  </button>
                </div>
                {/*body*/}
                <div className="relative p-6 flex-auto">
                  <ZahtevBody zahtev = {zahtev} />
                </div>
                { user?.role === Role.WORKER && 
                <div className="flex w-full items-center justify-between p-6 border-t border-solid border-slate-200 rounded-b">                
                  <div className="flex gap-5">
                    <select className="w-full p-1" >
                      <option value="xhtml">XHTML</option>
                      <option value="pdf">PDF</option>
                      <option value="pdf">RDF</option>
                      <option value="pdf">JSON</option>
                    </select>
                    <button
                      className="bg-blue-500 hover:bg-blue-700 text-white font-bold px-6 py-2 rounded w-fit" type="button">
                      Preuzmi
                    </button>
                  </div>
                  <button
                    className="bg-blue-500 hover:bg-blue-700 text-white font-bold px-6 py-2 rounded w-fit" type="button">
                    Podnesi rešenje
                  </button>
                </div>
                }
              </div>
            </div>
          </div>
          <div className="opacity-25 fixed inset-0 z-40 bg-black"></div>
        </>
    }
    </>  );
}
 
export default ZahtevModal;