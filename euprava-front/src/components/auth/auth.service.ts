import axios from "axios";
import { User } from "../../store/user-context";
import { LoginParam, RegisterError, RegisterParam } from "./types";

export const validEmailPassword = (
  email: string,
  password: string
): boolean => {
  return (
    /^.+[@].+[.].+$/.test(email) &&
    /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z]{8,20}$/.test(password)
  );
};

export const validRegisterParams = (
  params: RegisterParam,
  errors: RegisterError,
  setErrors: React.Dispatch<React.SetStateAction<RegisterError>>
): boolean => {
  let updatedErrors = { ...errors };
  let valid: boolean = true;
  if (params.ime === "") {
    updatedErrors = { ...updatedErrors, ime: "Ime ne sme biti prazno polje" };
    valid = false;
  } else updatedErrors = { ...updatedErrors, ime: "" };
  if (params.prezime === "") {
    updatedErrors = {
      ...updatedErrors,
      prezime: "Prezime ne sme biti prazno polje",
    };
    valid = false;
  } else updatedErrors = { ...updatedErrors, prezime: "" };
  if (!/^.+[@].+[.].+$/.test(params.email)) {
    updatedErrors = {
      ...updatedErrors,
      email: "Email je u formatu example@domen.com",
    };
    valid = false;
  } else updatedErrors = { ...updatedErrors, email: "" };
  if (!/^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z]{8,20}$/.test(params.sifra)) {
    updatedErrors = {
      ...updatedErrors,
      sifra:
        "Lozinka sadrži minimum 8 karaktera i sastoji se iz brojeva, velikih i malih slova",
    };
    valid = false;
  } else updatedErrors = { ...updatedErrors, sifra: "" };
  setErrors(updatedErrors);

  return valid;
};

export const registerUser = async (registrationDTO: RegisterParam) => {
  const xml2js = require("xml2js");
  const builder = new xml2js.Builder();
  const xml = builder.buildObject(registrationDTO);
  await axios.post("http://localhost:8443/korisnik/register", xml, {
    headers: {
      "Content-Type": "application/xml",
    },
  });
};

export const loginUser = async (loginDTO: LoginParam): Promise<User> => {
  const xml2js = require("xml2js");
  const builder = new xml2js.Builder();
  let xml = builder.buildObject(loginDTO);
  const jwtResponse = await axios.post(
    "http://localhost:8443/korisnik/login",
    xml,
    {
      headers: {
        "Content-Type": "application/xml",
      },
    }
  );
  const jwt = jwtResponse.data;
  localStorage.setItem("access_token", jwt);
  const userResponse = await axios.get("http://localhost:8443/korisnik", {
    headers: {
      Authorization: "Bearer " + jwt,
      "Content-Type": "application/xml",
      Accept: "application/xml",
    },
  });
  const userDto = userResponse.data;

  const convert = require("xml-js");
  const jsonData = convert.xml2js(userDto, {
    compact: true,
    alwaysChildren: true,
  });
  const userData = jsonData["UserDTO"];
  const user: User = {
    name: userData.name._text,
    surname: userData.surname._text,
    email: userData.email._text,
    role: userData.role._text,
  };
  return user;
};
