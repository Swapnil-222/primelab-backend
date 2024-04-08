import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IProject, NewProject } from '../project.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IProject for edit and NewProjectFormGroupInput for create.
 */
type ProjectFormGroupInput = IProject | PartialWithRequiredKeyOf<NewProject>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IProject | NewProject> = Omit<T, 'startDate' | 'endDate'> & {
  startDate?: string | null;
  endDate?: string | null;
};

type ProjectFormRawValue = FormValueOf<IProject>;

type NewProjectFormRawValue = FormValueOf<NewProject>;

type ProjectFormDefaults = Pick<NewProject, 'id' | 'startDate' | 'endDate'>;

type ProjectFormGroupContent = {
  id: FormControl<ProjectFormRawValue['id'] | NewProject['id']>;
  projectName: FormControl<ProjectFormRawValue['projectName']>;
  reason: FormControl<ProjectFormRawValue['reason']>;
  type: FormControl<ProjectFormRawValue['type']>;
  division: FormControl<ProjectFormRawValue['division']>;
  category: FormControl<ProjectFormRawValue['category']>;
  priority: FormControl<ProjectFormRawValue['priority']>;
  department: FormControl<ProjectFormRawValue['department']>;
  startDate: FormControl<ProjectFormRawValue['startDate']>;
  endDate: FormControl<ProjectFormRawValue['endDate']>;
  location: FormControl<ProjectFormRawValue['location']>;
  status: FormControl<ProjectFormRawValue['status']>;
};

export type ProjectFormGroup = FormGroup<ProjectFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ProjectFormService {
  createProjectFormGroup(project: ProjectFormGroupInput = { id: null }): ProjectFormGroup {
    const projectRawValue = this.convertProjectToProjectRawValue({
      ...this.getFormDefaults(),
      ...project,
    });
    return new FormGroup<ProjectFormGroupContent>({
      id: new FormControl(
        { value: projectRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      projectName: new FormControl(projectRawValue.projectName),
      reason: new FormControl(projectRawValue.reason),
      type: new FormControl(projectRawValue.type),
      division: new FormControl(projectRawValue.division),
      category: new FormControl(projectRawValue.category),
      priority: new FormControl(projectRawValue.priority),
      department: new FormControl(projectRawValue.department),
      startDate: new FormControl(projectRawValue.startDate),
      endDate: new FormControl(projectRawValue.endDate),
      location: new FormControl(projectRawValue.location),
      status: new FormControl(projectRawValue.status),
    });
  }

  getProject(form: ProjectFormGroup): IProject | NewProject {
    return this.convertProjectRawValueToProject(form.getRawValue() as ProjectFormRawValue | NewProjectFormRawValue);
  }

  resetForm(form: ProjectFormGroup, project: ProjectFormGroupInput): void {
    const projectRawValue = this.convertProjectToProjectRawValue({ ...this.getFormDefaults(), ...project });
    form.reset(
      {
        ...projectRawValue,
        id: { value: projectRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ProjectFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      startDate: currentTime,
      endDate: currentTime,
    };
  }

  private convertProjectRawValueToProject(rawProject: ProjectFormRawValue | NewProjectFormRawValue): IProject | NewProject {
    return {
      ...rawProject,
      startDate: dayjs(rawProject.startDate, DATE_TIME_FORMAT),
      endDate: dayjs(rawProject.endDate, DATE_TIME_FORMAT),
    };
  }

  private convertProjectToProjectRawValue(
    project: IProject | (Partial<NewProject> & ProjectFormDefaults),
  ): ProjectFormRawValue | PartialWithRequiredKeyOf<NewProjectFormRawValue> {
    return {
      ...project,
      startDate: project.startDate ? project.startDate.format(DATE_TIME_FORMAT) : undefined,
      endDate: project.endDate ? project.endDate.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
