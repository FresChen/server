<template>
  <div>
    <template>
      <el-input v-model="input" placeholder="请输入搜索内容"></el-input>
      <el-button type="primary" icon="el-icon-search" @click="search">搜索</el-button>
      <el-button type="primary" @click="dialogFormVisible = true">新增</el-button>
    </template>
    <template>
      <el-table :data="tableData" style="width: 100%" :row-class-name="tableRowClassName">
        <el-table-column prop="id" label="ID" width="180"></el-table-column>
        <el-table-column prop="name" label="服务器名称" width="180"></el-table-column>
        <el-table-column prop="ip" label="服务器ip"></el-table-column>
        <el-table-column prop="state" label="状态"></el-table-column>
        <el-table-column prop="mark" label="备注"></el-table-column>
        <el-table-column prop="createTime" label="创建时间"></el-table-column>
        <el-table-column prop="user" label="归属人"></el-table-column>
        <el-table-column prop label="操作">
          <template slot-scope="scope">
            <el-button type="primary" icon="el-icon-edit" circle @click="open(scope.row)"></el-button>
            <el-button type="danger" icon="el-icon-delete" circle @click="handleDelete(scope.row)"></el-button>
          </template>
        </el-table-column>
      </el-table>

      <!--分页-->
      <el-pagination background layout="prev, pager, next" :total="currentnum" :page-size="pagesize" :current-page="current" @current-change="handleCurrentChange">
    </el-pagination>

      <!-- 新增 -->
      <el-dialog title="服务器信息" :visible.sync="dialogFormVisible">
        <el-form :model="form">
          <el-form-item label="服务器名称" :label-width="formLabelWidth">
            <el-input v-model="form.name" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="服务器ip " :label-width="formLabelWidth">
            <el-input v-model="form.ip" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="状态" :label-width="formLabelWidth">
            <el-input v-model="form.state" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="备注" :label-width="formLabelWidth">
            <el-input v-model="form.mark" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="创建时间" :label-width="formLabelWidth">
            <el-input v-model="form.createTime" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="归属人" :label-width="formLabelWidth">
            <el-input v-model="form.user" autocomplete="off"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="cancelAdd">取 消</el-button>
          <el-button type="primary" @click="add">确 定</el-button>
        </div>
      </el-dialog>

      <!-- 编辑 -->
      <el-dialog title="服务器信息" :visible.sync="dialogFormVisible1">
        <el-form :model="form1">
          <el-form-item label="服务器名称" :label-width="formLabelWidth">
            <el-input v-model="form1.name" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="服务器ip" :label-width="formLabelWidth">
            <el-input v-model="form1.ip" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="状态" :label-width="formLabelWidth">
            <el-input v-model="form1.state" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="备注" :label-width="formLabelWidth">
            <el-input v-model="form1.mark" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="归属人" :label-width="formLabelWidth">
            <el-input v-model="form1.user" autocomplete="off"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible1 = false">取 消</el-button>
          <el-button type="primary" @click="edit">确 定</el-button>
        </div>
      </el-dialog>
    </template>
  </div>
</template>


<script>
export default {
  //列表显示
  created() {
    this.$axios.post("/api/servers/list",{}).then(res => {
      // console.log(res);
      this.tableData = res.data.data.list;
    });
    // 获取总页码
    this.$axios.post('/api/servers/list',{pageNum:this.current}).then((res)=>{
      this.currentnum = res.data.data.total
    })
  },

  data() {
    return {
      current:1,
      currentnum:5,
      pagesize:5,
      input: "",
      name: "",
      ip: "",
      state: "",
      mark: "",
      createTime: "",
      user: "",
      tableData: [],
      dialogFormVisible: false,
      dialogFormVisible1: false,
      form: {
        name: "",
        ip: "",
        state: "",
        mark: "",
        createTime: "",
        user: ""
      },
      form1: {
        name: "",
        ip: "",
        state: "",
        mark: "",
        createTime: "",
        user: ""
      },
      formLabelWidth: "120px"
    };
  },
  methods: {
    // 页码更新
      handleCurrentChange:function(current){
      this.tableData=[];
      this.$axios.post('/api/servers/list',{pageNum:current}).then((res)=>{
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
    tableRowClassName({ row, rowIndex }) {
      if (rowIndex === 1) {
        return "warning-row";
      } else if (rowIndex === 3) {
        return "success-row";
      }
      return "";
    },
    // 查询
    search() {
      // console.log(this.input);
      this.$axios
        .post("/api/servers/list", { searchKey: this.input })
        .then(res => {
          console.log(res);
          this.tableData = res.data.data.list;
        });
    },
    //新增
    add() {
      this.$axios.post("/api/servers/add", this.form).then(res => {
        console.log(res);
        // 刷新网页
        window.location.reload();
      });
    },
    //新增对话框的取消事件
    cancelAdd() {
      this.form = {};
      this.dialogFormVisible = false;
    },

    //编辑
    edit() {
      this.$axios.post("/api/servers/update", this.form1).then(res => {
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
      this.form1 = JSON.parse(JSON.stringify(row));
      // 访问后台的接口
      // 某某某请求拿到实时的data
      // this.form1 = data
    },
    //删除
    handleDelete(row){
      this.$axios.post("/api/servers/del",row).then(res=>{
        window.location.reload();
      })
    }
  }
};
</script>

<style>
.el-input {
  width: 300px;
  margin-left: 10px;
  margin-right: 10px;
}
.el-table .warning-row {
  background: oldlace;
}

.el-table .success-row {
  background: #f0f9eb;
}
.el-pagination{
    margin-top: 10px;
    text-align: center;
}
</style>