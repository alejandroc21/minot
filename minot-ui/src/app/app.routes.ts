import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: '',
    loadComponent: () => import('./pages/auth/auth.component'),
    children: [
      {
        path: 'login',
        loadComponent: () => import('./auth/components/login/login.component'),
      },
      {
        path: 'register',
        loadComponent: () =>
          import('./auth/components/register/register.component'),
      },
      {
        path: '',
        redirectTo: 'login',
        pathMatch: 'full',
      },
    ],
  },
  {
    path: 'home',
    loadComponent: () => import('./pages/home/home.component'),
    children:[
      // {
      //   path: 'items', loadComponent: ()=>import('./pages/items/items.component')
      // },
      {
        path: 'notes', loadComponent: ()=>import('./pages/notes/notes.component')
      },
      {
        path: 'tasks', loadComponent: ()=>import('./pages/tasks/tasks.component')
      },
      {
        path: 'trash', loadComponent: ()=>import('./pages/trash/trash.component')
      },
      {
        path: 'config', loadComponent: ()=>import('./pages/config/config.component')
      },
      {
        path: '**', redirectTo: 'notes', pathMatch: 'full'
      }
    ]
  },
  {
    path: '**',
    redirectTo: '',
    pathMatch: 'full',
  },
];
