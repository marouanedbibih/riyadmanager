export interface IAuthRES {
  token: string;
}

export interface ILoginREQ {
  username: string;
  password: string;
}

export interface IUser {
  username: string;
  lastName: string;
  firstName: string;
  role: UserRole;
}

export enum UserRole {
  ADMIN = 'ADMIN',
  MANAGER = 'MANAGER',
  GUEST = 'GUEST',
}
