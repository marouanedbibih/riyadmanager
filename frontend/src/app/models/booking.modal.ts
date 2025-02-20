export interface IBookingRequest {
  checkIn: String;
  checkOut: String;
  type: RoomType;
}

export interface IBookingRoom {
  id: number;
  createdAt: String;
  updatedAt: String;
  number: number;
  status: RoomStatus;
  roomCategoryTitle: string;
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
