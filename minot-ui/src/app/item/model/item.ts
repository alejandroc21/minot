import { ItemType } from "./item-filter";

export interface Item {
    id?:number;
    name?:string;
    trashed?:boolean;
    type?: ItemType;
    updatedAt?: Date;
    createdAt?: Date;
}
