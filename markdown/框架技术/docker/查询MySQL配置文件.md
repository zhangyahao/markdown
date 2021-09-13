
1.  进入docker
    ```text
         docker exec -it mysql bash
    ```
2. 查询MySQL配置文件
   ```text
         mysql --help | grep my.cnf
   ```
3. 查询文件
   ```text
        cat  /***/***/my.cnf
   ```
4. 读取文件内容
   ```text
        !includedir /etc/mysql/conf.d/
        !includedir /etc/mysql/mysql.conf.d/
   ``` 
5. 到   conf.d 下查看配置文件 my.cnf

    