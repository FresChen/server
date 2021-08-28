<template>
  <div>
    <img src="../assets/1.jpg" alt="" height="100%" width="100%" />
    <div class="kuang" :model="form">
      <p>注册</p>
      <el-input v-model="form.name" placeholder="请输入用户姓名:"></el-input>
      <br />
      <el-input
        v-model="form.userName"
        placeholder="请输入用户账号:"
      ></el-input>
      <br />
      <el-input v-model="form.sex" placeholder="请输入性别:"></el-input>
      <br />
      <el-input v-model="form.phone" placeholder="请输入电话:"></el-input>
      <br />
      <el-input
        v-model="form.createTime"
        placeholder="请输入创建时间:"
      ></el-input>
      <br />
      <el-input v-model="form.password" placeholder="请输入密码:"></el-input>

      <el-button type="primary" class="bian" @click="name">
        <router-link to>注册</router-link>
      </el-button>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      input: "",
      form: {
        name: "",
        userName: "",
        password: "",
        sex: "",
        phone: "",
        createTime: "",
      },
    };
  },
  methods: {
    name() {
      var a = this.form.name;
      var b = this.form.userName;
      var c = this.form.password;
      var d = this.form.sex;
      var e = this.form.phone;
      var f = this.form.createTime;

      this.$axios
        .post("/api/user/add", {
          name: a,
          userName: b,
          password: c,
          sex: d,
          phone: e,
          createTime: f,
        })
        .then((res) => {
          console.log(res);
          if (res.data.code == 0) {
            this.$router.push("/");
            alert("注册成功")   
          } else {
             alert("注册失败")

            window.location.reload();
          }
        });
    },
  },
};
</script>

<style scoped>

.kuang {
  margin-left: 40%;

  width: 400px;
  height: 420px;
  position: absolute;
  background-color: rgb(41, 39, 39, 0.3);
  border-radius: 5px;
  margin-top: 50px;
}
p {
  text-align: center;
  font-size: 22px;
  padding: 10px;
}
.el-input {
  width: 300px;
  margin: 10px;
  margin-left: 50px;
}
.bian {
  width: 300px;
  margin: 10px;
  margin-left: 50px;
}
.mima {
  margin-left: 220px;
}
</style>