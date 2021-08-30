import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'


Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/about',
    name: 'About',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
  },
  {
    path:'/hello',
    name:'hello',
    component:()=>import('../components/hello.vue'),
    children:[
      {
        path:'/tab1',
        name:'tab1',
        component:()=>import('../components/tab1.vue')
      },
      {
        path:'/tab2',
        name:'tab2',
        component:()=>import('../components/tab2.vue')
      },
      {
        path:'/tab3',
        name:'tab3',
       component :()=>import('../components/tab3.vue')
      },
      {
        path:'/tab4',
        name:'tab4',
       component :()=>import('../components/tab4.vue')
      },
      {
        path:'/tab5',
        name:'tab5',
       component :()=>import('../components/tab5.vue')
      },
    ],
  }
]

const router = new VueRouter({
  routes
})

export default router
