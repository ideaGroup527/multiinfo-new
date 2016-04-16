function Charts (data, opt, content) {

    this.data = data;//数据json
    this.opt = opt;//配置json
    this.content = content;//散点图容器的id
    this.option = null;
    
    this.render = function () {
        //渲染到页面
        var myChart = echarts.init(document.getElementById(this.content)); //这里dom选择需要为js对象
        myChart.setOption(this.option);
    }
};