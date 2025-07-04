export interface IPage <T>{
    content:T[];
    pageable:{
        pageNumber: number;
        pageSize: number;
    };
    size:number;
    totalElements:number;
    totalPages: number;
}
