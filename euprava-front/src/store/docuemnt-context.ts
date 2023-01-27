import { createContext } from "react";

export enum DocType {
  NONE = "none",
  ZIG = "zig",
  AUTORSKA = "autorska",
  PATENT = "patent",
}

interface DocumentContextType {
  doc: DocType;
  setDoc: React.Dispatch<React.SetStateAction<DocType>>;
}
const DocumentContext = createContext<DocumentContextType>({
  doc: DocType.NONE,
  setDoc: () => {},
});

export default DocumentContext;
