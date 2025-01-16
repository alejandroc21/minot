import { Tag } from "./tag";

export interface Note {
    id?:number;
    content?:string;
    tags?:Tag[];
}
