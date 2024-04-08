import { Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'project',
    data: { pageTitle: 'Projects' },
    loadChildren: () => import('./project/project.routes'),
  },
  /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
];

export default routes;
