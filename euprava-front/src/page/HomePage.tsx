import {useState, useContext} from 'react'
import { Carousel } from 'react-responsive-carousel';
import Login from '../components/auth/Login';
import Register from '../components/auth/Register';
import UserContext from '../store/user-context';
import "react-responsive-carousel/lib/styles/carousel.min.css";
import { BsTelephoneFill } from 'react-icons/bs';
import { IoLocation, IoMail } from 'react-icons/io5'
import { TbBulb, TbFileCertificate } from 'react-icons/tb';
import { GiStamper } from 'react-icons/gi';

interface HomePageProps {
    page:number;
    setPage: (page: number) => void
}
 
const Switcher: React.FunctionComponent<HomePageProps> = ({page, setPage}) => {
    if (page === 1) {
        return <>
            <p>Nemate nalog? <b className='ml-1 cursor-pointer hover:underline' onClick={e => setPage(2)}>Registrujte se</b></p>
        </>
    }
    return <>
        <p>Imate nalog? <b className='ml-1 cursor-pointer hover:underline' onClick={e => setPage(1)}>Ulogujte se</b></p>
    </>
}

const LoggedOutHomePage: React.FunctionComponent<HomePageProps> = ({page, setPage}) => {
    return (
        <div className="h-[100vh] flex justify-center">
            <div className={`w-1/2 flex flex-col items-center ${ page===2 ? 'gap-4 p-12' : 'gap-8 p-16'}`}>
                <img src='./logo.png' alt='icon' className='w-[12vh] h-[12vh]'/>
                <p className='text-2xl'>
                    Dobrodošli na <b>EUprava</b>
                </p>
                {page === 1 && <Login/>}
                {page === 2 && <Register/>}
                <Switcher page={page} setPage={setPage}/>
            </div>
            <div className="w-1/2">
                <img src='./3.jpg' alt='icon' className='w-[100%] h-[100%] object-cover'/>
            </div>
        </div>
    );
}

const LoggedInHomePage: React.FunctionComponent<HomePageProps> = ({page, setPage}) => {
    return (
        <div className='flex flex-col h-full relative z-0'>
            <div className='w-full flex justify-between items-center absolute pb-1 pt-2 px-6 top-0 right-0 z-20 backdrop-blur-sm'>
                <div className='w-1/2 flex gap-5'>
                <div className='bg-gray-300 rounded-full p-2'>
                    <BsTelephoneFill fill='rgb(32,44,52)' className="w-4 h-4" title='Broj telefona'/>
                </div>
                <div className='bg-gray-300 rounded-full p-2'>
                    <IoMail fill='rgb(32,44,52)' className="w-4 h-4" title='Email'/>
                </div>
                <div className='bg-gray-300 rounded-full p-2'>
                    <IoLocation fill='rgb(32,44,52)' className="w-4 h-4" title='Adresa'/>
                </div>
                </div>
                <div className='w-1/2 flex gap-2 justify-end'>
                    <img src='./logo.png' alt='icon' className='w-[5vh] h-[5vh]'/>
                    <p className='text-2xl font-semibold text-gray-300'>EUprava</p>
                </div>
            </div>
            <div className='flex h-[50vh] relative'>
                <p className='carousel-title'> Zavod za intelektualnu svojinu</p>
                <Carousel className='-z-[100]' infiniteLoop={true} swipeable={true} showThumbs={false} autoPlay={true} showArrows={false} showIndicators={false}>
                    <div>
                        <img src="./1.jpg" alt='zig' className='h-[50vh] object-cover brightness-50'/>
                    </div>
                    <div>
                        <img src="./4.jpg" alt='patent' className=' h-[50vh] object-cover brightness-50'/>   
                    </div>
                    <div>
                        <img src="./5.jpg" alt='autorska prava' className=' h-[50vh] object-cover brightness-50'/> 
                    </div>
                </Carousel>
            </div>
            <div className='flex h-[48vh] gap-x-12 py-10 px-20 justify-center'>
            <div className='w-[27%] bg-white drop-shadow-2xl shadow-around hover:scale-105 p-7 flex flex-col items-center gap-5 text-xl rounded-lg'>
                    <TbFileCertificate color='#4684f5' className="w-24 h-24"/>
                    <div className='flex flex-col gap-1 items-center'>
                         <p className='uppercase'> Autorska prava </p>
                         <p className='text-sm font-semibold text-gray-800'> Evidencija autorskih prava </p>
                    </div>
                    <a href='zahtevi/pretraga/autorska' className="text-lg bg-transparent hover:bg-blue-light text-blue-dark font-semibold hover:text-white py-2 px-4 border border-blue-light hover:border-transparent rounded-3xl">
                        Pretraga
                    </a>
                </div>
                <div className='w-[27%] bg-white drop-shadow-2xl shadow-around hover:scale-105 p-7 flex flex-col items-center gap-5 text-xl rounded-lg'>
                    <GiStamper fill='#fab82f' className="w-24 h-24"/>
                    <div className='flex flex-col gap-1 items-center'>
                         <p className='uppercase'> Žigovi </p>
                         <p className='text-sm font-semibold text-gray-800'> Svi registri i baze žigova </p>
                    </div>
                    <a href='zahtevi/pretraga/zig' className="text-lg bg-transparent hover:bg-yellow-light text-yellow-dark font-semibold hover:text-white py-2 px-4 border border-yellow-light hover:border-transparent rounded-3xl">
                        Pretraga
                    </a>
                </div>
                <div className='w-[27%] bg-white drop-shadow-2xl shadow-around hover:scale-105 p-7 flex flex-col items-center gap-5 text-xl rounded-lg'>
                    <TbBulb color='#14843c' className="w-24 h-24"/>
                    <div className='flex flex-col gap-1 items-center'>
                         <p className='uppercase'> Patenti </p>
                         <p className='text-sm font-semibold text-gray-800'> Svi registri i baze patenata </p>
                    </div>
                    <a href='zahtevi/pretraga/patent' className="text-lg bg-transparent hover:bg-green-600 text-green-800 font-semibold hover:text-white py-2 px-4 border border-green-600 hover:border-transparent rounded-3xl">
                        Pretraga
                    </a>
                </div>
            </div>
        </div>
    );
}

const HomePage: React.FunctionComponent = () => {
    const { user, setUser } = useContext(UserContext);
    const [page, setPage] = useState<number>(1);
    return ( 
        <>
            {!user && <LoggedOutHomePage page={page} setPage={setPage}/>}
            {user && <LoggedInHomePage page={page} setPage={setPage}/>}
        </>
     );
}
 
export default HomePage;