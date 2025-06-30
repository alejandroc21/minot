import { Item } from "../../item/model/item";

export interface Folder extends Item{
    parentId?:number;
}
