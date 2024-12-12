import { createRouter, createWebHistory } from 'vue-router';
import HomeView from '../views/HomeView.vue';
import RegisterView from '../views/RegisterView.vue';
import LoginView from '../views/LoginView.vue';
import UserMenuView from '../views/UserMenu.vue';
import HistorialCompra from '../components/HistorialCompra.vue';
import ListaOrdenes from '../components/ListaOrdenes.vue';
import Devolucion from '../components/Devolucion.vue';

const routes = [
  {
    path: '/',
    name: 'Home',
    component: HomeView,
  },
  {
    path: '/register',
    name: 'Register',
    component: RegisterView,
  },
  {
    path: '/login',
    name: 'Login',
    component: LoginView,
  },
  {
    path: '/user-menu', 
    name: 'UserMenu',
    component: UserMenuView,
  },
  {
    path: '/historial-compras',
    name: 'HistorialCompra',
    component: HistorialCompra,
  },
  {
    path: '/lista-ordenes',
    name: 'ListaOrdenes',
    component: ListaOrdenes,
  },
  {
    path: '/devolucion-productos',
    name: 'Devolucion',
    component: Devolucion,
  }
  
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;