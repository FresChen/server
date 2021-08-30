<template>
  <div class="a1">
    <el-input v-model="input" placeholder="请输入内容" style="width: 200px; padding-right: 20px"></el-input>
    <el-button @click="search">查询</el-button>
    <el-button type="primary" @click="dialogFormVisible = true">新增</el-button>

    <el-table :data="tableData" style="width: 100%">
      <el-table-column prop="id" label="id"></el-table-column>
      <el-table-column prop="name" label="用户姓名"></el-table-column>
      <el-table-column prop="userName" label="用户账号"></el-table-column>
      <el-table-column prop="password" label="密码"></el-table-column>
      <el-table-column prop="sex" label="性别"></el-table-column>
      <el-table-column prop="createTime" label="创建时间"></el-table-column>
      <el-table-column prop="phone" label="电话号码"></el-table-column>
      <el-table-column prop label="操作">
        <template slot-scope="scope">
          <div>
            <el-button type="primary" icon="el-icon-edit" circle @click="open(scope.row)"></el-button>
            <el-button type="danger" icon="el-icon-delete" circle @click="handleDelete(scope.row)"></el-button>
          </div>
        </template>
      </el-table-column>
    </el-table>
    <!-- 新增 -->
    <el-dialog title="用户管理" :visible.sync="dialogFormVisible">
      <el-form :model="form">
        <el-form-item label="用户姓名" :label-width="formLabelWidth">
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="用户账号" :label-width="formLabelWidth">
          <el-input v-model="form.userName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="密码" :label-width="formLabelWidth">
          <el-input v-model="form.password" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="性别" :label-width="formLabelWidth">
          <!-- <el-input v-model="form.sex" autocomplete="off"></el-input> -->
          <el-select v-model="form.sex" placeholder="性别">
              <el-option
                v-for="item in options"
                :key="item.id"
                :label="item.msg"
                :value="item.msg"
              ></el-option>
            </el-select>
        </el-form-item>
        <el-form-item label="电话号码" :label-width="formLabelWidth">
          <el-input v-model="form.phone" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancelAdd">取 消</el-button>
        <el-button type="primary" @click="add">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="用户管理" :visible.sync="dialogFormVisible1">
      <el-form :model="form1">
        <el-form-item label="用户姓名" :label-width="formLabelWidth">
          <el-input v-model="form1.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="用户账号" :label-width="formLabelWidth">
          <el-input v-model="form1.userName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="性别" :label-width="formLabelWidth">
          <!-- <el-input v-model="form1.sex" autocomplete="off"></el-input> -->
          <el-select v-model="form1.sex" placeholder="性别">
              <el-option
                v-for="item in options"
                :key="item.id"
                :label="item.msg"
                :value="item.msg"
              ></el-option>
            </el-select>
        </el-form-item>
        <el-form-item label="电话号码" :label-width="formLabelWidth">
          <el-input v-model="form1.phone" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancelAdd">取 消</el-button>
        <el-button type="primary" @click="edit">确 定</el-button>
      </div>
    </el-dialog>
    <div class="block">
      <span class="demonstration"></span>


     <!--分页-->
      <el-pagination background layout="prev, pager, next" :total="currentnum" :page-size="pagesize" :current-page="current" @current-change="handleCurrentChange">
    </el-pagination>


    </div>
  </div>
</template>
<script>
export default {
  created() {
    this.$axios.post("/api/user/list", {}).then(res => {
      console.log(res);
      this.tableData = res.data.data.list;
    });
    // 获取总页码
    this.$axios.post('/api/user/list',{pageNum:this.current}).then((res)=>{
      this.currentnum = res.data.data.total
    })
  },
  methods: {
    // 页码更新
      handleCurrentChange:function(current){
      this.tableData=[];
      this.$axios.post('/api/user/list',{pageNum:current}).then((res)=>{
        console.log(res)
        this.current = res.data.data.pageNum
        this.currentnum = res.data.data.total
        for (//将本页数据添加到tableData中 防止数据越界 
        var j = 0;j < res.data.data.total - ((this.current-1) * 5);j++) { 
        if (res.data.data.list[j]) {
          this.tableData.push(res.data.data.list[j])
        }
      }
      })
    },
    add() {
      this.$axios.post("/api/user/add", this.form).then(res => {
        console.log(res);
        window.location.reload();
      });
    },
    cancelAdd() {
      this.form = {};
      this.dialogFormVisible = false;
      this.dialogFormVisible1 = false;
    },
    search() {
      this.$axios
        .post("/api/user/list", { searchKey: this.input})
        .then(res => {
          console.log(res);
          this.tableData = res.data.data.list;
        });
    },
    edit() {
      this.$axios.post("/api/user/update", this.form1).then(res => {
        console.log(res);
        window.location.reload();
      });
    },
    open(row) {
      console.log(row);
      this.dialogFormVisible1 = true;
      this.form1 = JSON.parse(JSON.stringify(row));
    },
    handleDelete(row) {
      console.log(row);
      this.$confirm("此操作将永远删除数据，是否继续？", "提示", {
        $confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          this.$axios.post("/api/user/del", { id: row.id }).then(res => {
            console.log(res);
          });
          this.$message({
            type: "success",
            message: "删除成功!"
          });

          setInterval(() => {
            window.location.reload();
          }, 500);
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除"
          });
        });
    }
  },

  data() {
    return {
      current:1,
      currentnum:5,
      pagesize:5,
      dialogFormVisible: false,
      dialogFormVisible1: false,
      input: "",
      row: "",
      tableData: [],
      currentPage1: 5,
      currentPage2: 5,
      currentPage3: 5,
      currentPage4: 4,
      options:[{id:1,msg:"男"},{id:2,msg:"女"}],
      form: {
        name: "",
        userName: "",
        password: "",
        sex: "",
        phone: "",
        createTime: "",
        time:''
      },
      formLabelWidth: "100px",
      form1: {
        name: "",
        userName: "",
        sex: "",
        createTime: "",
        phone: ""
      }
    };
  }
};
</script>
<style>

</style>
