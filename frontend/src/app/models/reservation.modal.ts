import { RoomType } from "./booking.modal";

export interface IReservationRequest {
  lastName: string;
  firstName: string;
  email: string;
  phoneNumber: string;
  dateIn: string;
  dateOut: string;
  roomId: number;
}

export interface ReservationDetails {
  dateIn: string;
  dateOut: string;
  roomType: RoomType;
  amount: number;
}
