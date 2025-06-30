export interface PaginatedResponse <T>{
    content:T[];
    pageable:{
        pageNumber:  number;
        pageSize: number;   
    };
    totalElements: number;
    totalPages: number;
}
