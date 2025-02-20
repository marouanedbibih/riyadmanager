export interface IBookingRequest {
  checkIn: String;
  checkOut: String;
  type: RoomType;
}

export interface IBookingRoom {
  id: number;
  createdAt: string;
  updatedAt: string;
  number: number;
  status: RoomStatus;
  roomType: string;
}

export interface IBookingResponse {
  room: IBookingRoom;
  amount: number;
  checkIn: string;
  checkOut: string;
}

export enum RoomType {
  SINGLE = 'SINGLE',
  DOUBLE = 'DOUBLE',
  SUITE = 'SUITE',
  TWIN = 'TWIN',
  DELUXE = 'DELUXE',
  FAMILY = 'FAMILY',
  PRESIDENTIAL = 'PRESIDENTIAL'
}

export enum RoomStatus {
  RESERVED = 'RESERVED',
  AVAILABLE = 'AVAILABLE',
  OCCUPIED = 'OCCUPIED',
}
