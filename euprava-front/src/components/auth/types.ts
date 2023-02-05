import { Role } from "../../store/user-context";

export type LoginParam = { email: string; password: string };

export type RegisterParam = {
  ime: string;
  prezime: string;
  email: string;
  sifra: string;
  uloga: Role;
};

export type RegisterError = {
  ime: string;
  prezime: string;
  email: string;
  sifra: string;
};
