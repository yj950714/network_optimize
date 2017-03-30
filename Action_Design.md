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
        fileName : String, //文件名
        updateTime : Date //修改日期
      }
      ...
	]
}
```