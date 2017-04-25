# Network_Optimize 接口文档

[TOC]


## 用户相关

#### 获取用户列表

http请求方式：GET

/admin/user

客户端发送
```java
{
null
}
```

服务器返回
```java
{
  	  errorCode : int,//错误码
      data:[
        {
          id : Long, // 用户id
          userName : String, //用户账号
          realName : String, //姓名
          email : String, //Email
          createTime : Date //创建时间
        }
        ...
      ]
}
```

#### 获取单个用户

http请求方式：GET

/admin/user/{id}

客户端发送
```java
{
//路径参数
id:long //用户id
}
```

服务器返回
```java
{
  	  errorCode : int,//错误码
      id : Long, // 用户id
      userName : String, //用户账号
      realName : String, //姓名
      email : String, //Email
      createTime : Date //创建时间
}
```

#### 用户登录

http请求方式：POST

/user/get_token

客户端发送
```java
{
	userName : String, //用户名
	password : String //用户密码
}
```

服务器返回
```java
{
	errorCode : int, //错误码
	token : String, //用户token
	realName : String //用户姓名
}
```

####忘记密码-重置密码并发送邮件

http请求方式：POST

/user/forget_password

客户端发送
```java
{
	email : String, //用户EMAIL
}
```

服务器返回
```java
{
	errorCode : int, //错误码
}
```


## 文件相关

#### 上传文件到服务器

http请求方式：POST

/file/upload

客户端发送
```java
{
	file : File, //上传的文件
}
```

服务器返回
```java
{
	errorCode : int, //错误码
}
```

#### 获取用户的所有文件信息

http请求方式：GET

/user/files/{id}

客户端发送
```java
{
	//路径参数
	id:long //用户id
}
```

服务器返回
```java
{
	errorCode : int, //错误码
	data:[
      {
        id : Long, // 文件Id
        userId : Long, //所属者用户Id
        fileTypeId : Long, //文件类型Id
        fileTypeName : String, //文件类型名称
        fileName : String, //文件名
        fileNameToUser : String, //向用户展示的文件名
        fileIdToUser : String, //用户的文件序数
        fileSize : Long, //文件大小
        updateTime : Date //修改日期
      }
      ...
	]
}
```

####更改文件信息

http请求方式：POST

/user/files

客户端发送
```java
{
	action : String, //用户操作动作
	File_Id : Long, //文件Id(给用户展示的，相当于该用户的文件序数)
	File_Name : String, //文件名 如果是修改会用
}
```

服务器返回
```java
{
	errorCode : int, //错误码
}
```

####根据url直接让用户下载文件

http请求方式：GET

/file/download/{id}

客户端发送
```java
{
	//路径参数
	id:long //该用户文件序数
}
```

服务器返回
```java
{
	errorCode : int, //错误码
}
```

##任务模块

####获取一个用户的所有任务

http请求方式：GET

/tasks

客户端发送
```java
{
	null //有token
}
```

服务器返回
```java
{
	errorCode : int, //错误码
	data :[
      {
        id : Long,
        taskTypeId : String,
        taskTypeName : String,
        status : Integer,
        createTime : Date,
        updateTime : Date
      }
      ....
	]
}
```

####获取单个任务的详细信息

http请求方式：GET

/tasks/{id}

客户端发送
```java
{
	//路径参数
	id:long //该用户文件序数
}
```

服务器返回
```java
{
	errorCode : int, //错误码
	taskId : Long, //任务Id
	taskTypeId : Long, //任务类型Id
	taskTypeName : String, //任务类型
	status : Integer, //任务状态
	paramId : List<Long>, // 任务参数Id
	paramName : List<String>, // 任务参数名
	paramValue : List<String>, // 任务参数值
	createTime : Date, //创建时间
	updateTime : Date //更新时间
}
```

####新建任务

http请求方式：GET

/tasks/add/{type}

客户端发送
```java
{
	//路径参数
	type : Long
}
```

服务器返回
```java
{
	errorCode : int //错误码
}
```

####新建任务--设定文件

http请求方式：POST

/tasks/add/file

客户端发送
```java
{
	taskId : Long,
	fileList : List<String>
}
```

服务器返回
```java
{
	errorCode : int, //错误码
}
```

####新建任务-设定参数

http请求方式：POST

/tasks/add/param

客户端发送
```java
{
	taskId : Long,
	paramName : List<String>,
	paramValue : List<String>,
}
```

服务器返回
```java
{
	errorCode : int, //错误码
}
```

####开始任务

http请求方式：GET

/tasks/start/{id}

客户端发送
```java
{
	//路径参数
	id:long //任务id
}
```

服务器返回
```java
{
	errorCode : int, //错误码
}
```