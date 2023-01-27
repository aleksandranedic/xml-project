import { RegisterParam } from "./types";

export const validEmailPassword = (
  email: string,
  password: string
): boolean => {
  return (
    /^.+[@].+[.].+$/.test(email) &&
    /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z]{8,20}$/.test(password)
  );
};

export const validRegisterParams = (params: RegisterParam): boolean => {
  const valid: boolean = validEmailPassword(params.email, params.password);
  if (!valid) return false;
  if (params.name === "" || params.surname === "") return false;
  return true;
};
