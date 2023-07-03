1.  简介   
    nvm可以让本机同时安装多个版本的nodejs，对不同的项目切换不同版本的nodejs满足开发需求。  
    
2.  安装  
    *  [地址](https://github.com/coreybutler/nvm-windows/releases)  
    *  注意事项
        1.  在安装nvm前，将已经安装的nodejs卸载干净  
        2.  nvm要安装在c盘
        3.  最好将node输出位置更换到其他位置
    
3.  配置文件修改  
    node改为国内淘宝镜像。 `setting.txt`增加两行
    ```text
        node_mirror: https://npm.taobao.org/mirrors/node/
        npm_mirror: https://npm.taobao.org/mirrors/npm/
    ```  
    
4.  常用命令：   

|命令|作用|
|:------:|:-----:|
|nvm list |获取全部安装的nodejs   
|nvm install  xxx|安装该版本的nodejs
|nvm uninstall  xxx|卸载对应版本的nodejs
|nvm list available|显示所有可以下载的版本
|nvm use  xxxx|使用对应版本的nodejs




