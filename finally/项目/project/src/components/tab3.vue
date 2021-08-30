<template>
  <div>
    <template>
      <el-input v-model="input" placeholder="请输入搜索内容"></el-input>
      <el-button type="primary" icon="el-icon-search" @click="search">搜索</el-button>
      <el-button type="primary" @click="dialogFormVisible = true,list()">新增</el-button>
      <!-- 未完成 -->
      <el-button type="primary" @click="ImportdialogVisible=true">导入</el-button>
      <!-- 未完成 -->
      <el-button type="primary" @click="exportBook">导出</el-button>
    </template>
    <template>
      <el-table :data="tableData" style="width: 100%" :row-class-name="tableRowClassName">
        <el-table-column prop="id" label="id" width="80"></el-table-column>
        <el-table-column prop="ip" label="服务器ip" width="100"></el-table-column>
        <el-table-column prop="port" label="端口号"></el-table-column>
        <el-table-column prop="content" label="描述"></el-table-column>
        <el-table-column prop="type" label="类型"></el-table-column>
        <el-table-column prop="state" label="状态"></el-table-column>
        <el-table-column prop label="操作">
          <template slot-scope="scope">
            <el-button type="text" @click="open(scope.row)">编辑</el-button>
            <!-- 未完成 -->
            <el-button type="text" @click="dialogVisible=true;iss(scope.row)">发布任务</el-button>
            <el-button type="text" @click="handleDelete(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <!--导入Excel对话框-->

     <el-dialog customClass="customWidth"

        :modal="true" 

        :visible.sync="ImportdialogVisible"

         width="29%"

        :title="ImportTitle"    

     >


   <el-form :model="form3">

    <el-form-item>

      <el-upload class="upload-file"

         drag

         ref="uploadExcel"

         action="http://localhost:8080/api/loophole/import"

         :auto-upload="false"

         accept=".xls"

         :before-upload="beforeUploadFile"

         :on-change="fileChange"

         :on-exceed="exceedFile"

         :on-success="handleSuccess"

         :on-error="handleError"

         :file-list="fileList"

      >

      <i class="el-icon-upload"></i>

      <div class="el-upload__text">将文件拖到此处，或<em>点击选择Excel文件</em></div>

      <!-- <div slot="tip" class="el-upload-list__item-name">{{fileName}}</div> -->

      </el-upload>

   </el-form-item>

   <el-form-item>

      <div style="margin-left: 120px;">

         <el-button @click="ImportdialogVisible = false" size="small">取 消</el-button>

         <el-button size="small" type="primary" @click="submitUpload()">立即导入</el-button>

      </div>

   </el-form-item>

</el-form> 

  </el-dialog>

      <!--分页-->
      <el-pagination
        background
        layout="prev, pager, next"
        :total="currentnum"
        :page-size="pagesize"
        :current-page="current"
        @current-change="handleCurrentChange"
      ></el-pagination>

      <!-- 发布任务 -->
      <el-dialog title="提示" :visible.sync="dialogVisible" width="30%" :before-close="handleClose">
        <span slot="footer" class="dialog-footer">
          <!-- 下拉选择 -->
        <el-select v-model="value" placeholder="请选择接受任务人员">
          <el-option v-for="item in options" :key="item.id" :label="item.name" :value="item.name"></el-option>
        </el-select>
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="dialogVisible = false,save()">确 定</el-button>
        </span>
      </el-dialog>

      <!-- 新增 -->
      <el-dialog title="漏洞资源" :visible.sync="dialogFormVisible">
        <el-form :model="form">
          <el-form-item label="服务器ip" :label-width="formLabelWidth">
            <el-select v-model="form.ip" placeholder="请选择">
              <el-option
                v-for="item in options"
                :key="item.id"
                :label="item.ip"
                :value="item.ip"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="端口号 " :label-width="formLabelWidth">
            <el-input v-model="form.port" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="描述" :label-width="formLabelWidth">
            <el-input v-model="form.content" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="类型" :label-width="formLabelWidth">
            <el-select v-model="form.type" placeholder="请选择">
              <el-option
                v-for="item in loop"
                :key="item.id"
                :label="item.msg"
                :value="item.msg"
              ></el-option>
            </el-select>
          </el-form-item>
          <!-- <el-form-item label="创建时间" :label-width="formLabelWidth">
            <el-input v-model="form.createTime" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="归属人" :label-width="formLabelWidth">
            <el-input v-model="form.User" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="处理人ID" :label-width="formLabelWidth">
            <el-input v-model="form.UserIDe" autocomplete="off"></el-input>
          </el-form-item> -->
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="cancelAdd">取 消</el-button>
          <el-button type="primary" @click="add">确 定</el-button>
        </div>
      </el-dialog>

      <!-- 编辑 -->
      <el-dialog title="漏洞资源" :visible.sync="dialogFormVisible1">
        <el-form :model="form1">
          <el-form-item label="id" :label-width="formLabelWidth">
            <el-input v-model="form1.id" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="服务器ip" :label-width="formLabelWidth">
            <el-select v-model="form1.ip" placeholder="请选择">
              <el-option
                v-for="item in options"
                :key="item.id"
                :label="item.ip"
                :value="item.ip"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="端口号" :label-width="formLabelWidth">
            <el-input v-model="form1.port" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="描述" :label-width="formLabelWidth">
            <el-input v-model="form1.content" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="类型" :label-width="formLabelWidth">
            <el-select v-model="form1.type" placeholder="请选择">
              <el-option
                v-for="item in loop"
                :key="item.id"
                :label="item.msg"
                :value="item.msg"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="状态" :label-width="formLabelWidth">
            <el-input v-model="form1.state" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="创建时间" :label-width="formLabelWidth">
            <el-input v-model="form1.createTime" autocomplete="off"></el-input>
          </el-form-item>
          <!-- <el-form-item label="归属人" :label-width="formLabelWidth">
            <el-input v-model="form1.User" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="处理内容" :label-width="formLabelWidth">
            <el-input v-model="form1.solveCont" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="处理人ID" :label-width="formLabelWidth">
            <el-input v-model="form1.UserID" autocomplete="off"></el-input>
          </el-form-item> -->
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible1 = false">取 消</el-button>
          <el-button type="primary" @click="edit">确 定</el-button>
        </div>
      </el-dialog>

      <!--发布任务-->
      <el-dialog title="发布任务" :visible.sync="dialogFormVisible2">
        <el-form :model="form">
          <el-form-item label="归属人" :label-width="formLabelWidth">
             <el-select v-model="form1.User" placeholder="请选择">
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
      // console.log(res);
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
      id: "",
      ip: "",
      port: "",
      content: "",
      type: "",
      state: "",
      createTime: "",
      ImportdialogVisible:false,
      value:"",
      form3: {    //"导入"表单绑定file，并初始化
        file: ''
      },
      fileList: [],//"导入"文件列表
      limitNum: 1,//导入文件的个数
      ImportTitle:'导入Excel', //"导入"对话框的标题
      tableData: [],
      dialogVisible:false,
      options: [],
      dialogFormVisible: false,
      dialogFormVisible1: false,
      dialogFormVisible2: false,
      loop:[{id:1,msg:"sql注入"},{id:2,msg:"XSS跨站脚本"},{id:3,msg:"弱口令漏洞"}],
      form: {
        ip: "",
        port: "",
        content: "",
        type: "",
        state: "",
        User: "",
        UserID: ""
      },
      form1: {
        id: "",
        ip: "",
        port: "",
        content: "",
        type: "",
        state: "",
        createTime: "",
        User: "",
        solveCont: "",
        UserID: ""
      },
      form2:{
            User:'',
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
     // 文件超出个数限制时的钩子

                exceedFile(files, fileList) {

                     this.$notify.warning({

                     title: '警告',

                     message: `只能选择 ${this.limitNum} 个文件，当前共选择了 ${files.length + fileList.length} 个`

                  });

                },

                // 文件状态改变时的钩子

                fileChange(file, fileList) {

                    console.log('change')

                    console.log(file)

                    this.form3.file = file.raw

                    // console.log(this.form.file)

                    console.log(fileList)

                 },

               // 上传文件之前的钩子, 参数为上传的文件,若返回 false 或者返回 Promise 且被 reject，则停止上传

               beforeUploadFile(file) {

                   console.log('before upload')

                   console.log(file)

                  // let extension = file.name.substring(file.name.lastIndexOf('.')+1); //获取上传文件的扩展名

                  //  console.log("文件扩展名为:"+extension);

                   let size = file.size / 1024 / 1024; //设置上传文件的大小

                   const isXLS = file.name.split(".")[1] === 'xls'; //获取上传文件的扩展名

                   if(!isXLS){

                       this.$notify.warning({

                       title: '警告',

                       message: `只能上传Excel2017（即后缀是.xls）的文件`

                     });

                     

                    }

                   if(size > 10) {

                       this.$notify.warning({

                       title: '警告',

                       message: `文件大小不得超过10M`

                     });

                     

                    }

                    

                  },

                  // 文件上传成功时的钩子

                  handleSuccess(res, file, fileList) {

                     let self = this;

                     this.$notify.success({

                     title: '成功',

                     message: `文件导入成功`

                     });

                    //  this.getData();

                     self.ImportdialogVisible = false;  

                  },

                  // 文件上传失败时的钩子

                  handleError(err, file, fileList) {

                      this.$notify.error({

                      title: '错误',

                      message: `文件导入失败`

                     });

                  },

                  submitUpload(){

                     let self = this;

                     if(self.form3.file == ''){

                        this.$notify.error({

                          title: '错误',

                          message: `请选择要导入的文件`

                        });

                        return;

                     }

                     self.$refs.uploadExcel.submit();

                  },
                //   getData(){ //初始化获取后台数据的函数

                //     let self = this;

                //     this.$axios.post("/api/loophole/list",{}).then((res)=>{
                //       this.tableData =res.data.data.list
                //     })
                //  },
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
        this.id = row.id,
        this.options=res.data.data.list
      })
    },
    save(){
      this.$axios.post("/api/loophole/updateuser",{User:this.value,id:this.id}).then((res)=>{
        
      })
      this.$axios.post("/api/loophole/updatestate",{id:this.id}).then((res)=>{
        window.location.reload()
      })
    },
    // 查询
    search() {
      // console.log(this.input);
      this.$axios
        .post("/api/loophole/list", { searchKey: this.input })
        .then(res => {
          console.log(res);
          this.tableData = res.data.data.list;
        });
    },
    //新增
    add() {
      this.$axios.post("/api/loophole/add", this.form).then(res => {
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
    //删除
    handleDelete(row) {
      this.$axios.post("/api/loophole/del", row).then(res => {
        window.location.reload();
      });
    },
    //获取server表的数据 放入下拉框
    list(){
      this.$axios.post("/api/servers/list",{}).then(res=>{
        this.options = res.data.data.list
      })
    },
    exportBook(){
    //这里不能用ajax请求，ajax请求无法弹出下载保存对话框
    location.href="http://localhost:8080/api/loophole/export";
    },
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
.el-pagination {
  margin-top: 10px;
  text-align: center;
}
</style>