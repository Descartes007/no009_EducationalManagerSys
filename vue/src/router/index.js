import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

// 解决导航栏或者底部导航tabBar中的vue-router在3.0版本以上频繁点击菜单报错的问题。
const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push (location) {
  return originalPush.call(this, location).catch(err => err)
}

const routes = [
  {
    path: '/',
    name: 'Manager',
    component: () => import('../views/Manager.vue'),
    redirect: '/home',  // 重定向到主页
    children: [
      { path: '403', name: 'NoAuth', meta: { name: '无权限' }, component: () => import('../views/manager/403') },
      { path: 'home', name: 'Home', meta: { name: '系统首页' }, component: () => import('../views/manager/Home') },
      { path: 'admin', name: 'Admin', meta: { name: '管理员信息' }, component: () => import('../views/manager/Admin') },
      { path: 'teacher', name: 'Teacher', meta: { name: '教师信息' }, component: () => import('../views/manager/Teacher') },
      { path: 'student', name: 'Student', meta: { name: '学生信息' }, component: () => import('../views/manager/Student') },
      { path: 'adminPerson', name: 'AdminPerson', meta: { name: '个人信息' }, component: () => import('../views/manager/AdminPerson') },
      { path: 'teacherPerson', name: 'TeacherPerson', meta: { name: '个人信息' }, component: () => import('../views/manager/TeacherPerson') },
      { path: 'studentPerson', name: 'StudentPerson', meta: { name: '个人信息' }, component: () => import('../views/manager/StudentPerson') },
      { path: 'password', name: 'Password', meta: { name: '修改密码' }, component: () => import('../views/manager/Password') },
      { path: 'notice', name: 'Notice', meta: { name: '公告信息' }, component: () => import('../views/manager/Notice') },
      { path: 'examPlan', name: 'ExamPlan', meta: { name: '考试安排' }, component: () => import('../views/manager/ExamPlan') },
      { path: 'roomPlan', name: 'RoomPlan', meta: { name: '教室安排' }, component: () => import('../views/manager/RoomPlan') },
      { path: 'college', name: 'College', meta: { name: '学院信息' }, component: () => import('../views/manager/College') },
      { path: 'speciality', name: 'Speciality', meta: { name: '专业信息' }, component: () => import('../views/manager/Speciality') },
      { path: 'classes', name: 'Classes', meta: { name: '班级信息' }, component: () => import('../views/manager/Classes') },
      { path: 'course', name: 'Course', meta: { name: '课程信息' }, component: () => import('../views/manager/Course') },
      { path: 'choice', name: 'Choice', meta: { name: '我的选课' }, component: () => import('../views/manager/Choice') },
      { path: 'curriculum', name: 'Curriculum', meta: { name: '我的课表' }, component: () => import('../views/manager/Curriculum') },
      { path: 'score', name: 'Score', meta: { name: '我的成绩' }, component: () => import('../views/manager/Score') },
      { path: 'comment', name: 'Comment', meta: { name: '网上评教' }, component: () => import('../views/manager/Comment') },
      { path: 'apply', name: 'Apply', meta: { name: '请假申请' }, component: () => import('../views/manager/Apply') },
      { path: 'homework', name: 'Homework', meta: { name: '作业提交' }, component: () => import('../views/manager/Homework') },
      { path: 'attendance', name: 'Attendance', meta: { name: '考勤信息' }, component: () => import('../views/manager/Attendance') },
    ]
  },
  {
    path: '/front',
    name: 'Front',
    component: () => import('../views/Front.vue'),
    children: [
      { path: 'home', name: 'Home', meta: { name: '系统首页' }, component: () => import('../views/front/Home') },
      { path: 'person', name: 'Person', meta: { name: '个人信息' }, component: () => import('../views/front/Person') },
    ]
  },
  { path: '/login', name: 'Login', meta: { name: '登录' }, component: () => import('../views/Login.vue') },
  { path: '/register', name: 'Register', meta: { name: '注册' }, component: () => import('../views/Register.vue') },
  { path: '*', name: 'NotFound', meta: { name: '无法访问' }, component: () => import('../views/404.vue') },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

// 注：不需要前台的项目，可以注释掉该路由守卫
// 路由守卫
// router.beforeEach((to ,from, next) => {
//   let user = JSON.parse(localStorage.getItem("xm-user") || '{}');
//   if (to.path === '/') {
//     if (user.role) {
//       if (user.role === 'USER') {
//         next('/front/home')
//       } else {
//         next('/home')
//       }
//     } else {
//       next('/login')
//     }
//   } else {
//     next()
//   }
// })

export default router
