module.exports = {
    devServer:{
        host:'localhost',
        port:8081,
        proxy:{
            '/api':{
                target:'http://127.0.0.1:8080', // 服务代理的地址
                changeOrigin:true
            }
        }
    }
}