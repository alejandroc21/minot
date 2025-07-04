export interface Item {
  id?: number;
  name?: string;
  content?: string;
  status?: Status;
  trashed?: boolean;
  type?: ItemType;
  createdAt?: Date;
  updatedAt?: Date;
}

export enum Status {
  TODO = 'TODO',
  IN_PROGRESS = 'IN_PROGRESS',
  DONE = 'DONE',
}

export enum ItemType {
  TASK = 'TASK',
  NOTE = 'NOTE',
}
