[ant](https://downloads.apache.org/ant/binaries/) 下载

1. 解压后设置系统变量
    ```
        ANT_HOME值为解压地址；
        在Path中添加：%ANT_HOME%\bin；
    ```
2. 使用：
    1. 编写build.xml的内容
        ```xml
        <?xml version="1.0" encoding="UTF-8" ?>
        <!--build.xml中的第一句话，没有实际的意义-->
        <!--Ant的所有内容必须包含在<project></project>里面，name是你给它取的名字，basedir指工作的根目录，.代表当前目录，         default代表默认要做的事情。-->
        <project name="HelloWorld" default="run" basedir=".">
            <!--<property />设置变量-->
            <property name="src" value="src"/>
            <property name="dest" value="classes"/>
            <property name="hello_jar" value="hello1.jar"/>
            <!--每个target代表你想做的操作，给这个操作命名，及name值，depends是它所依赖的target在执行这个target，例如这里的            compile之前ant会先检查init是否曾经被执行过，如果执行
                过则直接直接执行compile，如果没有则会先执行它依赖的target例如这里的init，然后在执行这个target-->
            <!--新建文件夹-->
            <target name="init">
                <mkdir dir="${dest}"/>
            </target>
            <!--开始编辑-->
            <target name="compile" depends="init">
                <javac srcdir="${src}" destdir="${dest}"/>
            </target>
            <!--创建jar包-->
            <target name="build" depends="compile">
                <jar jarfile="${hello_jar}" basedir="${dest}"/>
            </target>
            <!--开始运行-->
            <target name="run" depends="build">
                <java classname="HelloWorld" classpath="${hello_jar}"/>
            </target>
            <!--删除生成的文件-->
            <target name="clean" depends="run">
                <delete dir="${dest}"/>
                <delete file="${hello_jar}"/>
            </target>
            <target name="rerun" depends="clean,run">
                <ant target="clean" />
                <ant target="run" />
            </target>
        </project>
       ```