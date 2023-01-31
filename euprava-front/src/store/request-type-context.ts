import {createContext} from "react";


interface RequestTypeContextType {
    type: "patent" | "autor" | "zig" | null;
    setType: React.Dispatch<React.SetStateAction<"patent" | "autor" | "zig" | null>>;
}

const RequestTypeContext = createContext<RequestTypeContextType>({
    type: null,
    setType: () => {
    }
});

export default RequestTypeContext;