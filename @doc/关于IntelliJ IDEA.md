如何利用IDEA提升开发效率

#### 工程中文件的版权信息
* Settings --> Editor --> File and Code Templates --> Includes
* 修改其中的各个Header模板为以下内容：
```
/**
 * <描述>
 * 
 * @date ${DATE}
 * @author 赵梓彧 - kitchen_dev@163.com
 */
```
 * 保存应用后，即可在创建类或其它文件时，自动加入所填版权信息  
 `注：选择Scheme为Project，则只应用在此工程中`

#### 快速生成指定模板内容（代码、注释等）
* Settings --> Editor --> Live Templates
* 创建一个自定义的模板组
* 仿照其它模板生成自定义模板

