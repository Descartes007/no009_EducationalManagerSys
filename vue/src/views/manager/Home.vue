<template>
  <div>
    <div class="card" style="padding: 15px">
      您好，{{ user?.name }}！欢迎使用本系统
    </div>

    <div style="display: flex; margin: 10px 0">
      <div style="width: 50%;" class="card">
        <div style="margin-bottom: 30px; font-size: 20px; font-weight: bold">教务通知</div>
        <div >
          <el-timeline  reverse slot="reference">
            <el-timeline-item v-for="item in notices" :key="item.id" :timestamp="item.time">
              <el-popover
                  placement="right"
                  width="200"
                  trigger="hover"
                  :content="item.content">
                <span slot="reference">{{ item.title }}</span>
              </el-popover>
            </el-timeline-item>
          </el-timeline>
        </div>
      </div>

      <div style="width: 50%;" class="card">
        <div style="margin-bottom: 30px; font-size: 20px; font-weight: bold">考试安排</div>
        <div >
          <el-timeline  reverse slot="reference">
            <el-timeline-item v-for="item in examplans" :key="item.id" :timestamp="item.time">
              <el-popover
                  placement="right"
                  width="200"
                  trigger="hover"
                  :content="item.content">
                <span slot="reference">{{ item.name }}</span>
              </el-popover>
            </el-timeline-item>
          </el-timeline>
        </div>
      </div>

    </div>
    <div style="display: flex">
      <div class="card" id="pie" style="height: 400px; width: 50%"></div>
      <div class="card" id="line" style="height: 400px; width: 50%"></div>
    </div>
  </div>
</template>

<script>
import * as echarts from "echarts";

let pieOptions = {
  title: {
    text: '', // 主标题
    subtext: '', // 副标题
    left: 'center'
  },
  tooltip: {
    trigger: 'item',
    formatter: '{a} <br/>{b} : {c} ({d}%)'
  },
  legend: {
    orient: 'vertical',
    left: 'left'
  },
  series: [
    {
      name: '', // 鼠标移上去显示内容
      type: 'pie',
      radius: '50%',
      center: ['50%', '60%'],
      data: [
        {value: 1048, name: '瑞幸咖啡'}, // 示例数据：name表示维度，value表示对应的值
        {value: 735, name: '雀巢咖啡'},
        {value: 580, name: '星巴克咖啡'},
        {value: 484, name: '栖巢咖啡'},
        {value: 300, name: '小武哥咖啡'}
      ]
    }
  ]
}

let lineOptions = {
  title: {
    text: '', // 主标题
    subtext: '', // 副标题
    left: 'center'
  },
  xAxis: {
    type: 'category',
    data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'] // 示例数据：统计的维度（横坐标）
  },
  yAxis: {
    type: 'value'
  },
  tooltip: {
    trigger: 'item'
  },
  series: [
    {
      data: [120, 200, 150, 80, 70, 110, 130], // 示例数据：横坐标维度对应的值（纵坐标）
      type: 'line',
    }
  ]
}

export default {
  name: 'Home',
  data() {
    return {
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      notices: [],
      examplans: []
    }
  },
  created() {
    this.$request.get('/notice/selectAll').then(res => {
      this.notices = res.data || []
    })
    this.$request.get('/examplan/selectAll').then(res => {
      if (res.code === '200') {
        this.examplans = res.data || []
      } else {
        this.$message.error(res.msg)
      }
    })
    this.getPie()
    this.getLine()
  },
  methods: {
    getPie() {
      this.$request.get('/attendance/getPie').then(res => {
        if (res.code === '200') {
          let chartDom = document.getElementById('pie');
          let myChart = echarts.init(chartDom);

          pieOptions.title.text = res.data.text
          pieOptions.title.subtext = res.data.subtext
          pieOptions.series.name = res.data.name
          pieOptions.series[0].data = res.data.data

          myChart.setOption(pieOptions);
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    getLine() {
      this.$request.get('/score/getLine').then(res => {
        if (res.code === '200') {
          let chartDom = document.getElementById('line');
          let myChart = echarts.init(chartDom);

          lineOptions.title.text = res.data.text
          lineOptions.title.subtext = res.data.subtext
          lineOptions.xAxis.data = res.data.xAxis
          lineOptions.series[0].data = res.data.yAxis

          myChart.setOption(lineOptions);
        } else {
          this.$message.error(res.msg)
        }
      })
    }
  }
}
</script>
