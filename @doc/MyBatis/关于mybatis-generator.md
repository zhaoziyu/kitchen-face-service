mybatis-generator插件可以帮助你通过已经存在的数据结构生成相应的dao、mapper、po，提升开发效率。
当需要采用不同的mybatis集成方式（注解、xml、混合），可以在generatorConfig.xml中指定类型：
```xml
        <!--
            TODO MyBatis的dao层文件生成类型
            1，ANNOTATEDMAPPER：会生成使用Mapper接口+Annotation的方式创建（SQL生成在annotation中），不会生成对应的XML；
            2，MIXEDMAPPER：使用混合配置，会生成Mapper接口，并适当添加合适的Annotation，但是XML会生成在XML中；
            3，XMLMAPPER：会生成Mapper接口，接口完全依赖XML；
        -->
        <javaClientGenerator targetProject="src/main/java" targetPackage="mybatis.generate.dao" type="ANNOTATEDMAPPER">
            <!-- 是否允许子包，即targetPackage.schemaName.tableName -->
            <property name="enableSubPackages" value="false"/>
            <!-- 生成类的继承接口 -->
            <property name="rootInterface" value=""/>
        </javaClientGenerator>
```