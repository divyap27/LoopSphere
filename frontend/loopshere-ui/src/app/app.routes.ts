import { Routes } from '@angular/router';

export const routes: Routes = [
  { path: '', loadComponent: () => import('./get-started/get-started').then(m => m.GetStarted) },
  { path: 'signup', loadComponent: () => import('./signup/signup').then(m => m.Signup) },
  { path: 'login', loadComponent: () => import('./login/login').then(m => m.LoginComponent) }
];
