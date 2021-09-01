在MySQL5.7版本之后对group by进行了优化。默认启动改进之后的版本启动了ONLY_FULL_GROUP_BY模式。

即ONLY_FULL_GROUP_BY是MySQL数据库提供的一个sql_mode，通过这个sql_mode来保证SQL语句“分组求最值”合法性的检查。这种模式采用了与Oracle、DB2等数据库的处理方式。即不允许select
target list中出现语义不明确的列。

```text
1. 这种模式的官方解释：ONLY_FULL_GROUP_BY是MySQL数据库提供的一个sql_mode, 通过这个 sql_mode 来保证, SQL语句 “分组求最值” 合法性的检查. 这种模式采用了与 Oracle、DB2 等数据库的处理方式。即不允许 select target list 中出现语义不明确的列.
2. 对于用到 GROUP BY 的 select 语句, 查出来的列必须是 group by 后面声明的列, 或者是聚合函数里面的列有这样一个数据库的表.
```


解决方案： 
1. 临时方案 
   ```text
     set @@GLOBAL.sql_mode='';
     set sql_mode='STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';
   ```
2. 修改配置文件
  ```text
  sql_mode="STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION"
  ```