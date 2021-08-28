<template>
  <div  class="home">
    <el-container>
      <el-header>
        <el-breadcrumb separator="/">
          <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item>安全运维系统</el-breadcrumb-item>
        </el-breadcrumb>
      </el-header>

      <el-container>
        <el-aside width="200px">
          <el-menu default-active="2" class="el-menu-vertical-demo" :router="true">
            <el-menu-item-group>
              <el-menu-item index>
                <i class="el-icon-s-home"></i>
                <span slot="title">首页</span>
              </el-menu-item>
            </el-menu-item-group>
            <el-menu-item index="/tab1">
              <i class="el-icon-s-custom"></i>
              <span slot="title">用户管理</span>
            </el-menu-item>
            <el-menu-item index="/tab2">
              <i class="el-icon-s-platform"></i>
              <span slot="title">服务器管理</span>
            </el-menu-item>
          </el-menu>
        </el-aside>
        <el-main>
          <router-view></router-view>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
export default {
  data() {
    return {};
  },
  methods: {
    handleOpen(key, keyPath) {
      console.log(key, keyPath);
    },
    handleClose(key, keyPath) {
      console.log(key, keyPath);
    }
  }
};
</script>

 <style>
.home {
  height: 100%;
}
/* 通过类名选择器改变elementui的样式 */
.el-header {
  border-bottom: 1px solid black;
}
.el-aside {
  border-right: 1px solid black;
}
.el-container {
  height: 100%;
}
.el-breadcrumb {
  font-size: 24px;
  font-family: "黑体";
  margin-top: 10px;
}
</style>
