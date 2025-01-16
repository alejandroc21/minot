import { Note } from "./note";

export interface Tag {
    id?:number;
    name:string;
    note:Note
}
