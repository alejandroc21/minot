import { Item } from '../../item/model/item';

export interface Note extends Item {
  preview?: string;
  content?: string;
  parentId?: number;
}
