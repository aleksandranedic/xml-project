import { createContext } from "react";

export enum Role {
  CLIENT = "Gradjanin",
  WORKER = "Sluzbenik",
}

export interface User {
  name: string;
  surname: string;
  email: string;
  role: string;
}

interface UserContextType {
  user: User | null;
  setUser: React.Dispatch<React.SetStateAction<User | null>>;
}

const UserContext = createContext<UserContextType>({
  user: null,
  setUser: () => {},
});

export default UserContext;
