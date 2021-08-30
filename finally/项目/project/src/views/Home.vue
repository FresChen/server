

<template>
  <div>
    <img src="../assets/1.jpg" />
    <div class="kuang" :model="ruleForm" ref="ruleForm" :rules="ruleForm">
      <p>登录</p>
       <div class="cc">
         <el-input v-model="ruleForm.user" placeholder="请输入内容" ></el-input>
       </div>
       <br><br>
     <div class="cc">
    <el-input placeholder="请输入密码" v-model="ruleForm.pass" show-password></el-input>
     </div>
      <br><br>
      <div class="cc">
      <el-button type="success" class="bian" @click="submitForm()">
       <router-link to="hello"  class="se">登录</router-link> 
      </el-button>
      </div>
      <br><br>
      <div class="mima">
        <el-button type="text" @click="dialogVisible = true" class="se" >忘记密码</el-button>&nbsp;
        <el-dialog title="忘记密码" :visible.sync="dialogVisible" width="30%" :before-close="handleClose" >
          用户名
          <el-input type="text" v-model="form.username" prop="username"></el-input>
          <br />&nbsp;
          电话
          <el-input type="text" v-model="form.phone" prop="phone"></el-input>
          <br />新密码
          <el-input type="text" v-model="form.password" prop="password"></el-input>
          <br />

          <span slot="footer" class="dialog-footer">
            <el-button @click="dialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="pass">重置</el-button>
          </span>
        </el-dialog>

        <router-link to="about" class="se">去注册</router-link>
      </div>
    </div>
  </div>
</template>


<script>
export default {
  data() {
    return {
      input: "",
      input1:"",
      dialogVisible: false,
      ruleForm:{
        user:"",
        pass:"",
        chekPass:"",
      },
      form:{
        username:'',
        phone:'',
        password:'',
      } 
    };
  },
  methods: {
    handleClose(done) {
      this.$confirm("确认关闭？")
        .then(_ => {
          done();
        })
        .catch(_ => {});
    },
    //登录
    submitForm() {
      var a = this.ruleForm.user;
      var b = this.ruleForm.pass;
      console.log(a)
      
      this.$axios.post("/api/user/login", {userName:a , password:b }).then((res) => {
            console.log(res);
            // console.log(res.data.code);
            if(res.data.code == 0 ){
              this.$router.push("/hello");
            }else{
              alert("账号或密码错误");
            }
          });
    },
      resetForm(formName) {
        this.$refs[formName].resetFields();
      },
      //修改密码
      pass(){
        var a=this.form.username;
        var b=this.form.phone;
        var c=this.form.password;
        this.$axios.post("/api/user/reset",{userName:a ,phone:b,password:c}).then((res)=>{
          if(res.data.code == 0){
            this.$message({
              message:"修改成功",
            });
          }else{
            this.$message({
              message:"修改失败",
            });
          }
        });
        window.location.reload();
      }
    
  }
}
</script>

<style>
img {
  position: absolute;
  width: 100%;
  height: 100%;
}

.kuang {
  margin-left: 40%;
  width: 350px;
  height: 400px;
  position: absolute;
  background-color: rgb(41, 39, 39, 0.3);
  border-radius: 5px;
  margin-top: 150px;
}
p {
  text-align: center;
  font-size: 25px;
  padding: 10px;
}

.bian {
  width: 300px;
  margin: 10px;
  margin-left: 50px;
}
.mima {
  margin-left: 220px;
}
.se {
  color: chartreuse;
  text-decoration: none;
}
.cc{
  width: 300px;
  margin-left: 25px;
}

</style>