1. 下载node js   
    [官网地址](https://nodejs.org/zh-cn/download/)
2.  查看版本  
     cmd下
     ```text
        node -v
        npm  -v
     ```
3.   创建两个目录，设置全局存储地址
     ```text
         npm config set prefix "F:\develop\nodejs\node_global"
         npm config set cache "F:\develop\nodejs\node_cache"
     ```
4.  设置环境变量
     新建 NODE_PATH
     F:develop\nodejs\node_global\node_modules     
    
5.  下载cnmp  
     ```text
        npm install -g cnpm --registry=https://registry.npm.taobao.org
     ```
6.   安装 Vue-cli    
     ```text
        cnpm install -g vue-cli
     ```
     
