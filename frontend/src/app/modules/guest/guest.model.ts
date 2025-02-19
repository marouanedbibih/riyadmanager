export interface IGuest {
  id: number;
  lastName: string;
  firstName: string;
  username: string;
  email: string;
  phone: string;
}


export interface IGuestREQ {
  email: string;
  username: string;
  firstName: string;
  lastName: string;
  phone: string;
}
