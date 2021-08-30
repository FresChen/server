<template>
  <div>
    <template>

      <el-input v-model="input" placeholder="请输入漏洞类型"></el-input>
      <el-select v-model="value" placeholder="请选择">
    <el-option
      v-for="item in textstate"
      :key="item.id"
      :label="item.msg"
      :value="item.msg">
    </el-option>
  </el-select>
      <el-button type="primary" icon="el-icon-search" @click="search">搜索</el-button>
      

    </template>
    <template>
      <el-table :data="tableData" style="width: 100%" :row-class-name="tableRowClassName">
        <el-table-column prop="ip" label="服务器ip" width="100"></el-table-column>
        <el-table-column prop="port" label="端口号"></el-table-column>
        <el-table-column prop="type" label="类型"></el-table-column>
        <el-table-column prop="state" label="状态"></el-table-column>
        <el-table-column prop="user" label="处理人"></el-table-column>
        <el-table-column prop="solveCont" label="处理内容"></el-table-column>
        <el-table-column prop label="操作">
          <template slot-scope="scope">
            <el-button type="text" @click="dialogVisible=true;iss(scope.row)">处理</el-button>
            
          </template>
        </el-table-column>
      </el-table>

     

      <!--分页-->
      <el-pagination
        background
        layout="prev, pager, next"
        :total="currentnum"
        :page-size="pagesize"
        :current-page="current"
        @current-change="handleCurrentChange"
      ></el-pagination>

      <!-- 处理 -->
      <el-dialog title="提示" :visible.sync="dialogVisible" width="30%" :before-close="handleClose">
        <span slot="footer" class="dialog-footer">
         <el-input v-model="solveCont" placeholder="处理内容"></el-input>
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="dialogVisible = false,save()">确 定</el-button>
        </span>
      </el-dialog>

      <!--处理-->
      <el-dialog title="处理" :visible.sync="dialogFormVisible2">
        <el-form :model="form">
          <el-form-item label="处理内容" :label-width="formLabelWidth">
             <el-select v-model="form1.solveCont" placeholder="请选择">
              <el-option
                v-for="item in options"
                :key="item.id"
                :label="item.name"
                :value="item.id"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="dialogFormVisible = false">确 定</el-button>
        </div>
      </el-dialog>
    </template>
  </div>
</template>


<script>
export default {
  //列表显示
  created() {
    this.$axios.post("/api/loophole/list", {}).then(res => {
      console.log(res);
      this.tableData = res.data.data.list;
    });
    // 获取总页码
    this.$axios
      .post("/api/loophole/list", { pageNum: this.current })
      .then(res => {
        this.currentnum = res.data.data.total;
      });
  },

  data() {
    return {
      current: 1,
      currentnum: 5,
      pagesize: 5,
      input: "",
      ip: "",
      port: "",
      type: "",
      state: "",
      User: "",
      solveCont: "",
      ImportdialogVisible:false,
      value:" ",
    
      tableData: [],
      dialogVisible:false,
      options: [],
      dialogFormVisible: false,
      dialogFormVisible1: false,
      dialogFormVisible2: false,
      loop:[{id:1,msg:"sql注入"},{id:2,msg:"XSS跨站脚本"},{id:3,msg:"弱口令漏洞"}],
      textstate:[{id:1,msg:"已处理"},{id:2,msg:"未处理"},{id:3,msg:"处理中"},{id:4,msg:""}],
      form: {
      ip: "",
      port: "",
      type: "",
      state: "",
      User: "",
      solveCont: "",
      },
      form1: {
      ip: "",
      port: "",
      type: "",
      state: "",
      User: "",
      solveCont: "",
      },
      form2:{
           solveCont:'',
      },

      formLabelWidth: "120px"
    };
  },
  methods: {
    // 页码更新
    handleCurrentChange: function(current) {
      this.tableData = [];
      this.$axios.post("/api/loophole/list", { pageNum: current }).then(res => {
        console.log(res);
        this.current = res.data.data.pageNum;
        this.currentnum = res.data.data.total;
        for (
          //将本页数据添加到tableData中 防止数据越界
          var j = 0;
          j < res.data.data.total - (this.current - 1) * 5;
          j++
        ) {
          if (res.data.data.list[j]) {
            this.tableData.push(res.data.data.list[j]);
          }
        }
      });
    },
    handleClose(){

    },
                 
    tableRowClassName({ row, rowIndex }) {
      if (rowIndex === 1) {
        return "warning-row";
      } else if (rowIndex === 3) {
        return "success-row";
      }
      return "";
    },
    //更新漏洞
    iss(row){
      console.log("数据"),
      this.$axios.post("/api/user/list",{}).then((res)=>{
        console.log(res),
        this.id=row.id
        this.options=res.data.data.list
      })
    },
    save(){
      this.$axios.post("/api/loophole/finishstate",{solveCont:this.solveCont,id:this.id}).then((res)=>{
        window.location.reload()
      })
    },
    // 查询
    search() {
     if(this.input==""){
      this.$axios.post("/api/loophole/list",{searchKey:this.value}).then((res)=>{
        this.tableData=res.data.data.list
      })
     }else{
       this.value=""
       this.$axios.post("/api/loophole/list", { searchKey:this.input}).then(res => {
        this.tableData = res.data.data.list;
      });
     }
    },
    
    //编辑
    edit() {
      this.$axios.post("/api/loophole/update", this.form1).then(res => {
        console.log(res);
        // 刷新网页
        window.location.reload();
      });
    },
    // 打开编辑对话框
    open(row) {
      // 获取当前行的数据
      console.log(row);
      this.dialogFormVisible1 = true;
      // 复制导致表单的数据改变，列表的数据也跟着改变
      // this.form1 = row
      this.$axios.post("/api/servers/list",{}).then(res=>{
        this.options = res.data.data.list
      })
      this.form1 = JSON.parse(JSON.stringify(row));
      // 访问后台的接口
      // 某某某请求拿到实时的data
      // this.form1 = data
    },
    
    //获取server表的数据 放入下拉框
    list(){
      this.$axios.post("/api/servers/list",{}).then(res=>{
        this.options = res.data.data.list
      })
    },
    
  }
};
</script>

<style>
.el-input {
  width: 150px;
  margin-left: 10px;
  margin-right: 10px;
}
.el-table .warning-row {
  background: oldlace;
}

.el-table .success-row {
  background: #f0f9eb;
}
.el-pagination {
  margin-top: 10px;
  text-align: center;
}
</style>