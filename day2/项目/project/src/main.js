import Vue from 'vue'
import App from './App.vue'
import router from './router'
// 引入elementUI模块
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
// 导入axios模块，axios http模块
import axios from 'axios'
// 导入qs
import qs from 'qs'
Vue.prototype.qs = qs

// 配置一些模块的导入
Vue.prototype.$axios = axios

Vue.use(ElementUI);
Vue.config.productionTip = false


new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
