<template>
  <div class="nn">
    <!--圆-->
    <div id="main" class="main" style="width:1000px ;height:350px" ></div><br><br>
    <!--柱状-->
    <div id="myChart" class="myChart" style="width:600px; height:520px"></div>
  </div>
  
    
  
</template>

<script>
import echarts from "echarts";
export default {
  created() {
    // this.$axios.post("/api/loophole/selectstate",{}).then((res)=>{
    //   console.log(res)
    //   this.form=res.data.data
    // })
  },
  name: "index",
  data() {
    return {
      form:[],
      form1:[],
    };
  },
  
  //圆
  mounted: function() {
    this.$nextTick(function() {
    //   this.$axios.post("/api/loophole/selectstate",{}).then((res)=>{
    //   console.log(res)
    //   this.form=res.data.data
    //   this.name=res.data.data[0].state
    //   console.log(this.form)
    //   console.log(this.form.state)
    // })
      this.getPie();
      //柱状
      this.drawLine()
    });
  },
  methods: {
    //柱状
     drawLine(){
    // 初始化echarts实例
    var myChart2 = echarts.init(document.getElementById("myChart"));
    // 绘制图表
    myChart2.setOption({
                title: {
        text: '服务器统计',
        subtext: '安全运维',
        left: 'center'
        },
        legend: {
            orient: 'vertical',
            left: 'left',
        },
        xAxis: {
        type: 'category',
        data: [
          // 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'
          ]
        },
        yAxis: {
        type: 'value'
        },
        color:"#b6a2de",
        series: [
            {
                name: '服务器数量',
                type: 'bar',
                // radius: '50%',
                data: [
                  // 120, 200, 150, 80, 70, 110, 130
                  ],
            //     emphasis: {
            //         itemStyle: {
            //             shadowBlur: 10,
            //             shadowOffsetX: 0,
            //             shadowColor: 'rgba(0, 0, 0, 0.5)'
            //         }
            // }
        }
      ]
    });
    this.$axios.post("/api/servers/selectstate",{}).then((res)=>{
      this.form1=res.data.data
      console.log(this.form1)
      myChart2.setOption({
        xAxis: {
        type: 'category',
        data: [
          this.form1[0].state1,this.form1[1].state1,'服务器总数量'
          ]
        },
        series: [
            {
                type: 'bar',
                // radius: '50%',
                data: [
                  this.form1[0].count,this.form1[1].count,this.form1[0].count+this.form1[1].count
                  ],
            //     emphasis: {
            //         itemStyle: {
            //             shadowBlur: 10,
            //             shadowOffsetX: 0,
            //             shadowColor: 'rgba(0, 0, 0, 0.5)'
            //         }
            // }
        }
      ],
      legend: {
          //图例  标注各种颜色代表的模块
          // orient: 'vertical',//图例的显示方式  默认横向显示
          left: "center", //控制图例的位置
          // itemWidth: 16,//图例颜色块的宽度和高度
          // itemHeight: 12,
          textStyle: {
            //图例中文字的样式
            color: "#000",
            fontSize: 16
          },
          data: [
            this.form1[0].state,this.form1[1].state
            ] //图例上显示的饼图各模块上的名字
        },

      })
    })
  },

    //圆
    getPie() {
      // 绘制图表
      var myChart = echarts.init(document.getElementById("main"));
      // 指定图表的配置项和数据
      
      var option = {
        //标题
        title: {
          text: "服务器漏洞统计",
          x: "left" //标题位置
          // textStyle: { //标题内容的样式
          //   color: '#000',
          //   fontStyle: 'normal',
          //   fontWeight: 100,
          //   fontSize: 16 //主题文字字体大小，默认为18px
          // },
        },
        // stillShowZeroSum: true,
        //鼠标划过时饼状图上显示的数据
        tooltip: {
          trigger: "item",
          formatter: "{a}<br/>{b}:{c} ({d}%)"
        },
        //图例
        legend: {
          //图例  标注各种颜色代表的模块
          // orient: 'vertical',//图例的显示方式  默认横向显示
          bottom: 10, //控制图例出现的距离  默认左上角
          left: "center", //控制图例的位置
          // itemWidth: 16,//图例颜色块的宽度和高度
          // itemHeight: 12,
          textStyle: {
            //图例中文字的样式
            color: "#000",
            fontSize: 16
          },
          data: [
            // "未处理", "处理中", "已处理"
            ] //图例上显示的饼图各模块上的名字
        },
        //饼图中各模块的颜色
        color: ["#32dadd", "#b6a2de", "#5ab1ef"],
        // 饼图数据
        series: {
          // name: 'bug分布',
          type: "pie", //echarts图的类型   pie代表饼图
          radius: "70%", //饼图中饼状部分的大小所占整个父元素的百分比
          center: ["50%", "50%"], //整个饼图在整个父元素中的位置
          // data:''               //饼图数据
          data: [
            //每个模块的名字和值
            // { name: "未处理", value: 15 },
            // { name: "已处理", value: 13},  
            // { name: this.form[0].state , value: 12}
          ],
          itemStyle: {
            normal: {
              label: {
                show: true //饼图上是否出现标注文字 标注各模块代表什么  默认是true
                // position: 'inner',//控制饼图上标注文字相对于饼图的位置  默认位置在饼图外
              },
              labelLine: {
                show: true //官网demo里外部标注上的小细线的显示隐藏    默认显示
              }
            }
          }
        }
      };
      // 使用刚指定的配置项和数据显示图表。
      myChart.setOption(option);
        this.$axios.post("/api/loophole/selectstate",{}).then((res)=>{
        console.log(res)
        this.form=res.data.data
        myChart.setOption({
          legend: {
          //图例  标注各种颜色代表的模块
          // orient: 'vertical',//图例的显示方式  默认横向显示
          bottom: 10, //控制图例出现的距离  默认左上角
          left: "center", //控制图例的位置
          // itemWidth: 16,//图例颜色块的宽度和高度
          // itemHeight: 12,
          textStyle: {
            //图例中文字的样式
            color: "#000",
            fontSize: 16
          },
          data: [
            this.form[0].state,this.form[1].state,this.form[2].state
            ] //图例上显示的饼图各模块上的名字
        },
          series: {
          // name: 'bug分布',
          type: "pie", //echarts图的类型   pie代表饼图
          radius: "70%", //饼图中饼状部分的大小所占整个父元素的百分比
          center: ["50%", "50%"], //整个饼图在整个父元素中的位置
          // data:''               //饼图数据
          data: [
            {name:this.form[0].state,value:this.form[0].count},
            {name:this.form[1].state,value:this.form[1].count},
            {name:this.form[2].state,value:this.form[2].count}
          ],
          itemStyle: {
            normal: {
              label: {
                show: true //饼图上是否出现标注文字 标注各模块代表什么  默认是true
                // position: 'inner',//控制饼图上标注文字相对于饼图的位置  默认位置在饼图外
              },
              labelLine: {
                show: true //官网demo里外部标注上的小细线的显示隐藏    默认显示
              }
            }
          }
        }
        })
    })
    }
  }
 
};

</script>

<style>
.nn {
  width: 100%;
  height: 100%;
  border-radius: 15px;
}
</style>