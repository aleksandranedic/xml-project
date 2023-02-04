import { ZahtevZaPriznanjePatenta } from "../patent/types";

interface ZahtevPatentProps {
    zahtev: ZahtevZaPriznanjePatenta
}
 
const ZahtevPatent: React.FunctionComponent<ZahtevPatentProps> = ({zahtev}) => {
    return (  <>PATENT</>);
}
 
export default ZahtevPatent;