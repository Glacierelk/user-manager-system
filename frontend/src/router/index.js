import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/',
            name: 'home',
            component: () => import('../views/LoginView.vue')
        },
        {
            path: '/user',
            name: 'user',
            component: () => import("../views/UserView.vue")
        },
        {
            path: '/hr',
            name: 'hr',
            component: () => import("../views/HRView.vue")
        },
        {
            path: '/admin',
            name: 'admin',
            component: () => import("../views/AdminView.vue")
        }
    ]
})

export default router;