export interface IPageRES<T> {
  content: T[];
  currentPage: number;
  size: number;
  totalPages: number;
  totalElements: number;
  sortBy: string;
  orderBy: string;
}

export interface IFetchParams {
  page: number;
  size: number;
  sortBy?: string;
  orderBy?: string;
  search?: string;
}

export interface IPagination {
  currentPage: number;
  size: number;
  totalPages: number;
  totalElements: number;
  sortBy: string;
  orderBy: string;
}

export interface IFormLoading {
  button: boolean;
  body: boolean;
}
