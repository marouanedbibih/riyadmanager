export interface Room {
  id: number;
  number: number;
  status: RoomStatus;
  roomType: RoomType;
}

export interface RoomRequest {
  number: number;
  status: RoomStatus;
  roomType: RoomType;
}

export enum RoomStatus {
  AVAILABLE = "AVAILABLE",
  RESERVED = "RESERVED",
  OCCUPIED = "OCCUPIED",
}

export enum RoomType {
  SINGLE = "SINGLE",
  DOUBLE = "DOUBLE",
  SUITE = "SUITE",
  TWIN = "TWIN",
  DELUXE = "DELUXE",
  FAMILY = "FAMILY",
  PRESIDENTIAL = "PRESIDENTIAL"
}
