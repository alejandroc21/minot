import { ItemType } from "./item";

export interface ItemFilter {
    type?:ItemType[];
    text?: string;
    trashed?: boolean;
}
