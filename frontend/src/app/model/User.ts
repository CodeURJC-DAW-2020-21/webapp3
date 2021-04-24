export interface User{
  email: string;
  username: string;
  name: string;
  surname: string;
  encodedPassword : string;
  confirmEncodedPassword : string,
  roles: string[];
}
