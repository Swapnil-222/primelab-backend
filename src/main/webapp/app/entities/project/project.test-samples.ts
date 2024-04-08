import dayjs from 'dayjs/esm';

import { IProject, NewProject } from './project.model';

export const sampleWithRequiredData: IProject = {
  id: 4448,
};

export const sampleWithPartialData: IProject = {
  id: 14943,
  division: 'WATER_HEATER',
  department: 'MENTAINANCE',
};

export const sampleWithFullData: IProject = {
  id: 3260,
  projectName: 'porter confine eponym',
  reason: 'DELERSHIP',
  type: 'EXTERNAL',
  division: 'PUMPS',
  category: 'QUALITY_A',
  priority: 'MEDIUM',
  department: 'STARTEGY',
  startDate: dayjs('2024-04-08T02:27'),
  endDate: dayjs('2024-04-07T15:45'),
  location: 'below meanwhile whoa',
  status: 'successfully administer enormously',
};

export const sampleWithNewData: NewProject = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
