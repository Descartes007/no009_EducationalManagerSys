<template>
  <div class="manager-container">
    <!--  头部  -->
    <div class="manager-header">
      <div class="manager-header-left">
        <img src="@/assets/imgs/logo.png" />
        <div class="title">教务管理系统</div>
      </div>

      <div class="manager-header-center">
        <el-breadcrumb separator-class="el-icon-arrow-right">
          <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item :to="{ path: $route.path }">{{ $route.meta.name }}</el-breadcrumb-item>
        </el-breadcrumb>
      </div>

      <div class="manager-header-right">
        <el-dropdown placement="bottom">
          <div class="avatar">
            <img :src="user.avatar || 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png'" />
            <div>{{ user.name ||  '管理员' }}</div>
          </div>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item @click.native="goToPerson">个人信息</el-dropdown-item>
            <el-dropdown-item @click.native="$router.push('/password')">修改密码</el-dropdown-item>
            <el-dropdown-item @click.native="logout">退出登录</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </div>
    </div>

    <!--  主体  -->
    <div class="manager-main">
      <!--  侧边栏  -->
      <div class="manager-main-left">
        <el-menu router style="border: none" :default-active="$route.path">
          <el-menu-item index="/home">
            <i class="el-icon-s-home"></i>
            <span slot="title">系统首页</span>
          </el-menu-item>
          <el-submenu index="info">
            <template slot="title">
              <i class="el-icon-menu"></i><span>信息公告</span>
            </template>
            <el-menu-item index="/notice" v-if="user.role === 'ADMIN'">教务通知</el-menu-item>
            <el-menu-item index="/examPlan" v-if="user.role === 'ADMIN'">考试安排</el-menu-item>
            <el-menu-item index="/roomPlan">教室安排</el-menu-item>
          </el-submenu>
          <el-submenu index="administration">
            <template slot="title">
              <i class="el-icon-menu"></i><span>行政管理</span>
            </template>
            <el-menu-item index="/college">学院信息</el-menu-item>
            <el-menu-item index="/speciality">专业信息</el-menu-item>
            <el-menu-item index="/classes">班级信息</el-menu-item>
          </el-submenu>
          <el-submenu index="teach">
            <template slot="title">
              <i class="el-icon-menu"></i><span>教学管理</span>
            </template>
            <el-menu-item index="/course">课程信息</el-menu-item>
            <el-menu-item index="/choice">我的选课</el-menu-item>
            <el-menu-item index="/curriculum" v-if="user.role === 'STUDENT'">我的课表</el-menu-item>
            <el-menu-item index="/score">我的成绩</el-menu-item>
            <el-menu-item index="/comment">网上评教</el-menu-item>
          </el-submenu>
          <el-submenu index="educational">
            <template slot="title">
              <i class="el-icon-menu"></i><span>教务管理</span>
            </template>
            <el-menu-item index="/apply" v-if="user.role !== 'TEACHER'">请假申请</el-menu-item>
            <el-menu-item index="/homework">作业提交</el-menu-item>
            <el-menu-item index="/attendance">考勤信息</el-menu-item>
          </el-submenu>
          <el-submenu index="user" v-if="user.role === 'ADMIN'">
            <template slot="title">
              <i class="el-icon-menu"></i><span>用户管理</span>
            </template>
            <el-menu-item index="/admin">管理员信息</el-menu-item>
            <el-menu-item index="/teacher">教师信息</el-menu-item>
            <el-menu-item index="/student">学生信息</el-menu-item>
          </el-submenu>
        </el-menu>
      </div>

      <!--  数据表格  -->
      <div class="manager-main-right">
        <router-view @update:user="updateUser" />
      </div>
    </div>

  </div>
</template>

<script>
export default {
  name: "Manager",
  data() {
    return {
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
    }
  },
  created() {
    if (!this.user.id) {
      this.$router.push('/login')
    }
  },
  methods: {
    updateUser() {
      this.user = JSON.parse(localStorage.getItem('xm-user') || '{}')   // 重新获取下用户的最新信息
    },
    goToPerson() {
      if (this.user.role === 'ADMIN') {
        this.$router.push('/adminPerson')
      }
      if (this.user.role === 'TEACHER') {
        this.$router.push('/teacherPerson')
      }
      if (this.user.role === 'STUDENT') {
        this.$router.push('/studentPerson')
      }
    },
    logout() {
      localStorage.removeItem('xm-user')
      this.$router.push('/login')
    }
  }
}
</script>

<style scoped>
@import "@/assets/css/manager.css";
</style>