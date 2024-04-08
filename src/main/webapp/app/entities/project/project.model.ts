import dayjs from 'dayjs/esm';
import { Reason } from 'app/entities/enumerations/reason.model';
import { Type } from 'app/entities/enumerations/type.model';
import { Division } from 'app/entities/enumerations/division.model';
import { Category } from 'app/entities/enumerations/category.model';
import { Priority } from 'app/entities/enumerations/priority.model';
import { Department } from 'app/entities/enumerations/department.model';

export interface IProject {
  id: number;
  projectName?: string | null;
  reason?: keyof typeof Reason | null;
  type?: keyof typeof Type | null;
  division?: keyof typeof Division | null;
  category?: keyof typeof Category | null;
  priority?: keyof typeof Priority | null;
  department?: keyof typeof Department | null;
  startDate?: dayjs.Dayjs | null;
  endDate?: dayjs.Dayjs | null;
  location?: string | null;
  status?: string | null;
}

export type NewProject = Omit<IProject, 'id'> & { id: null };
