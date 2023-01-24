import { Role } from "../../store/user-context";

export type loginParam = { email: string; password: string };
export type RegisterParam = {
  name: string;
  surname: string;
  email: string;
  password: string;
  role: Role;
};
