<template>
  <div>
    <div class="search">
      <el-input placeholder="请输入课程名称" style="width: 200px" v-model="name"></el-input>
      <el-button type="info" plain style="margin-left: 10px" @click="load(1)">查询</el-button>
      <el-button type="warning" plain style="margin-left: 10px" @click="reset">重置</el-button>
    </div>

    <div class="operation" v-if="user.role === 'ADMIN'">
      <el-button type="primary" plain @click="handleAdd">新增</el-button>
      <el-button type="danger" plain @click="delBatch">批量删除</el-button>
    </div>

    <div class="table">
      <el-table :data="tableData" stripe  @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center" v-if="user.role === 'ADMIN'"></el-table-column>
        <el-table-column prop="id" label="序号" width="80" align="center" sortable></el-table-column>
        <el-table-column prop="name" label="课程名称" show-overflow-tooltip></el-table-column>
        <el-table-column prop="type" label="课程类型" show-overflow-tooltip></el-table-column>
        <el-table-column prop="teacherName" label="授课教师" show-overflow-tooltip></el-table-column>
        <el-table-column prop="score" label="学分" show-overflow-tooltip></el-table-column>
        <el-table-column prop="num" label="上课人数" show-overflow-tooltip></el-table-column>
        <el-table-column prop="room" label="上课教室" show-overflow-tooltip></el-table-column>
        <el-table-column prop="week" label="周几" show-overflow-tooltip></el-table-column>
        <el-table-column prop="segment" label="第几大节" show-overflow-tooltip></el-table-column>
        <el-table-column prop="status" label="上课状态" show-overflow-tooltip></el-table-column>

        <el-table-column label="操作" width="180" align="center">
          <template v-slot="scope">
            <el-button plain type="primary" @click="handleEdit(scope.row)" size="mini" v-if="user.role !== 'STUDENT'">编辑</el-button>
            <el-button plain type="primary" @click="choiceCourse(scope.row)" size="mini" v-if="user.role === 'STUDENT'" :disabled="scope.row.status !== '未开课'">选课</el-button>
            <el-button plain type="danger" size="mini" @click=del(scope.row.id) v-if="user.role === 'ADMIN'">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <div class="pagination">
        <el-pagination
            background
            @current-change="handleCurrentChange"
            :current-page="pageNum"
            :page-sizes="[5, 10, 20]"
            :page-size="pageSize"
            layout="total, prev, pager, next"
            :total="total">
        </el-pagination>
      </div>
    </div>


    <el-dialog title="信息" :visible.sync="fromVisible" width="40%" :close-on-click-modal="false" destroy-on-close>
      <el-form label-width="100px" style="padding-right: 50px" :model="form" :rules="rules" ref="formRef">
        <el-form-item prop="name" label="课程名称">
          <el-input v-model="form.name" autocomplete="off" :disabled="user.role !== 'ADMIN'"></el-input>
        </el-form-item>
        <el-form-item prop="type" label="课程类型">
          <el-select v-model="form.type" placeholder="请选择类型" style="width: 100%" :disabled="user.role !== 'ADMIN'">
            <el-option label="必修" value="必修"></el-option>
            <el-option label="选修" value="选修"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item prop="teacherId" label="授课教师">
          <el-select v-model="form.teacherId" placeholder="请选择教师" style="width: 100%" :disabled="user.role !== 'ADMIN'">
            <el-option v-for="item in teacherData" :label="item.name" :value="item.id"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item prop="score" label="学分">
          <el-input v-model="form.score" autocomplete="off" :disabled="user.role !== 'ADMIN'"></el-input>
        </el-form-item>
        <el-form-item prop="num" label="上课人数">
          <el-input v-model="form.num" autocomplete="off" :disabled="user.role !== 'ADMIN'"></el-input>
        </el-form-item>
        <el-form-item prop="room" label="上课教室">
          <el-input v-model="form.room" autocomplete="off" :disabled="user.role !== 'ADMIN'"></el-input>
        </el-form-item>
        <el-form-item prop="week" label="周几">
          <el-select v-model="form.week" placeholder="请选择类型" style="width: 100%" :disabled="user.role !== 'ADMIN'">
            <el-option label="星期一" value="星期一"></el-option>
            <el-option label="星期二" value="星期二"></el-option>
            <el-option label="星期三" value="星期三"></el-option>
            <el-option label="星期四" value="星期四"></el-option>
            <el-option label="星期五" value="星期五"></el-option>
            <el-option label="星期六" value="星期六"></el-option>
            <el-option label="星期日" value="星期日"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item prop="segment" label="第几大节">
          <el-select v-model="form.segment" placeholder="请选择类型" style="width: 100%" :disabled="user.role !== 'ADMIN'">
            <el-option label="第一大节（08:00 ~ 09:30）" value="第一大节（08:00 ~ 09:30）"></el-option>
            <el-option label="第二大节（09:40 ~ 12:00）" value="第二大节（09:40 ~ 12:00）"></el-option>
            <el-option label="第三大节（14:00 ~ 15:30）" value="第三大节（14:00 ~ 15:30）"></el-option>
            <el-option label="第四大节（15:40 ~ 17:00）" value="第四大节（15:40 ~ 17:00）"></el-option>
            <el-option label="第五大节（18:00 ~ 20:00）" value="第五大节（18:00 ~ 20:00）"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item prop="status" label="上课状态">
          <el-select v-model="form.status" placeholder="请选择类型" style="width: 100%">
            <el-option label="未开课" value="未开课"></el-option>
            <el-option label="已开课" value="已开课"></el-option>
            <el-option label="已结课" value="已结课"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="fromVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>


  </div>
</template>

<script>
export default {
  name: "Course",
  data() {
    return {
      tableData: [],  // 所有的数据
      pageNum: 1,   // 当前的页码
      pageSize: 10,  // 每页显示的个数
      total: 0,
      name: null,
      fromVisible: false,
      form: {},
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      rules: {
        name: [
          {required: true, message: '请输入课程名称', trigger: 'blur'},
        ],
      },
      ids: [],
      teacherData: []
    }
  },
  created() {
    this.load(1)
    this.loadTeacher()
  },
  methods: {
    choiceCourse(row) {
      let data = {
        studentId: this.user.id,
        teacherId: row.teacherId,
        courseId: row.id
      }
      this.$request.post('/choice/add', data).then(res => {
        if (res.code === '200') {
          this.$message.success('选课成功')
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    loadTeacher() {
      this.$request.get('/teacher/selectAll').then(res => {
        if (res.code === '200') {
          this.teacherData = res.data
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    handleAdd() {   // 新增数据
      this.form = {}  // 新增数据的时候清空数据
      this.fromVisible = true   // 打开弹窗
    },
    handleEdit(row) {   // 编辑数据
      this.form = JSON.parse(JSON.stringify(row))  // 给form对象赋值  注意要深拷贝数据
      this.fromVisible = true   // 打开弹窗
    },
    save() {   // 保存按钮触发的逻辑  它会触发新增或者更新
      this.$refs.formRef.validate((valid) => {
        if (valid) {
          this.$request({
            url: this.form.id ? '/course/update' : '/course/add',
            method: this.form.id ? 'PUT' : 'POST',
            data: this.form
          }).then(res => {
            if (res.code === '200') {  // 表示成功保存
              this.$message.success('保存成功')
              this.load(1)
              this.fromVisible = false
            } else {
              this.$message.error(res.msg)  // 弹出错误的信息
            }
          })
        }
      })
    },
    del(id) {   // 单个删除
      this.$confirm('您确定删除吗？', '确认删除', {type: "warning"}).then(response => {
        this.$request.delete('/course/delete/' + id).then(res => {
          if (res.code === '200') {   // 表示操作成功
            this.$message.success('操作成功')
            this.load(1)
          } else {
            this.$message.error(res.msg)  // 弹出错误的信息
          }
        })
      }).catch(() => {
      })
    },
    handleSelectionChange(rows) {   // 当前选中的所有的行数据
      this.ids = rows.map(v => v.id)   //  [1,2]
    },
    delBatch() {   // 批量删除
      if (!this.ids.length) {
        this.$message.warning('请选择数据')
        return
      }
      this.$confirm('您确定批量删除这些数据吗？', '确认删除', {type: "warning"}).then(response => {
        this.$request.delete('/course/delete/batch', {data: this.ids}).then(res => {
          if (res.code === '200') {   // 表示操作成功
            this.$message.success('操作成功')
            this.load(1)
          } else {
            this.$message.error(res.msg)  // 弹出错误的信息
          }
        })
      }).catch(() => {
      })
    },
    load(pageNum) {  // 分页查询
      if (pageNum) this.pageNum = pageNum
      this.$request.get('/course/selectPage', {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          name: this.name,
        }
      }).then(res => {
        this.tableData = res.data?.list
        this.total = res.data?.total
      })
    },
    reset() {
      this.name = null
      this.load(1)
    },
    handleCurrentChange(pageNum) {
      this.load(pageNum)
    },
  }
}
</script>

<style scoped>

</style>
