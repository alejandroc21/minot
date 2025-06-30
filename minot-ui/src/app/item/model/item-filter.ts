export interface ItemFilter {
  types?: ItemType[];
  text?: string;
  trashed?: boolean;
  parentId?: number;
  root?: boolean;
}

export type ItemType='FOLDER'| 'NOTE';