import {createContext} from "react";


interface RequestTypeContextType {
    type: "patent" | "autor" | "zig" | null;
    setType: React.Dispatch<React.SetStateAction<"patent" | "autor" | "zig" | null>>;
    port: "8002"| "8000"|"8003"| null;
}

const RequestTypeContext = createContext<RequestTypeContextType>({
    port: null,
    type: null,
    setType: () => {
    }
});

export default RequestTypeContext;