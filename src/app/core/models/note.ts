import { Tag } from "./tag";

export interface Note {
    id?:number;
    content?:string;
    creationDate?: Date;
    modifiedDate?: Date;
    tags?:Tag[];
}
