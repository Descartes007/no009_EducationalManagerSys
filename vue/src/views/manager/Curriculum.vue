<template>
  <div>
    <div class="table">
      <el-table :data="tableData" stripe border>
        <el-table-column prop="segment" label="时间" width="200"></el-table-column>
        <el-table-column prop="monday" label="星期一" show-overflow-tooltip></el-table-column>
        <el-table-column prop="tuesday" label="星期二" show-overflow-tooltip></el-table-column>
        <el-table-column prop="wednesday" label="星期三" show-overflow-tooltip></el-table-column>
        <el-table-column prop="thursday" label="星期四" show-overflow-tooltip></el-table-column>
        <el-table-column prop="friday" label="星期五" show-overflow-tooltip></el-table-column>
        <el-table-column prop="saturday" label="星期六" show-overflow-tooltip></el-table-column>
        <el-table-column prop="sunday" label="星期日" show-overflow-tooltip></el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script>
export default {
  name: "Curriculum",
  data() {
    return {
      tableData: [],
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
    }
  },
  created() {
    this.load()
  },
  methods: {
    load() {
      this.$request.get('/choice/getCurriculum').then(res => {
        if (res.code === '200') {
          this.tableData = res.data
        } else {
          this.$message.error(res.msg)
        }
      })
    },
  }
}
</script>

<style scoped>

</style>
