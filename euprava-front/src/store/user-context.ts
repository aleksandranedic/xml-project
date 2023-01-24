import { createContext, useState } from "react";

export enum Role {
  CLIENT = "CLIENT",
  WORKER = "WORKER",
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
