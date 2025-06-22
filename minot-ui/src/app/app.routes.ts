import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: '',
    loadComponent: () => import('./pages/public/public.component'),
    children: [
      {
        path: 'login',
        loadComponent: () => import('./auth/components/login/login.component'),
      },
      {
        path: 'register',
        loadComponent: () => import('./auth/components/register/register.component'),
      },
      {
        path:'', redirectTo:'login', pathMatch:'full'
      }
    ],
  },
  {
    path: 'home',
    loadComponent: () => import('./pages/home/home.component'),
    children:[
      {
        path:'notes',
        loadComponent: ()=>import('./notes/components/note/note.component')
      },
      {
        path: '**', redirectTo: 'notes', pathMatch:'full'
      }
    ]
  },
  {
    path: '**',
    redirectTo: '',
    pathMatch: 'full',
  },
];
