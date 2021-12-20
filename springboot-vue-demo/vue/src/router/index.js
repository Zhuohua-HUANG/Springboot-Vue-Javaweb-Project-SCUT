import { createRouter, createWebHistory } from 'vue-router'
import Login from '../views/Login.vue'
import Layout from "../layout/Layout.vue";
import User from "../views/User.vue";
import Register from "../views/Register";
import Person from "../views/Person";
import Storage from "../views/Storage"
import Customer from "../views/Customer";
import Cart from "../views/Cart";
import Order from "../views/Order";
import Log from "../views/Log";

const routes = [
  {
    path: '/',
    name: 'Layout',
    component: Layout,
    redirect: "/login",
    children:[
      {
        path: '/user',
        name: 'User',
        component: User
      }  ,
      {
        path: '/storage',
        name: 'Storage',
        component: Storage
      },
      {
        path: '/person',
        name: 'Person',
        component: Person
      },
      {
        path: '/customer',
        name: 'Customer',
        component: Customer
      },
      {
        path: '/cart',
        name: 'Cart',
        component: Cart
      },
      {
        path: '/order',
        name: 'Order',
        component: Order
      },
      {
        path: '/log',
        name: 'Log',
        component: Log
      }
    ]
  },
  {
    path: '/login',
    name: 'Login',
    component: Login
  }
  ,
  {
    path: '/register',
    name: 'Register',
    component: Register
  }

]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
