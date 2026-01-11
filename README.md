尚庭公寓租赁平台
项目简介
尚庭公寓是一个现代化的公寓租赁管理平台，提供移动端应用和后台管理系统，致力于为租客和公寓管理者提供高效、便捷的租赁服务体验。
功能特性
移动端功能
房源检索：支持多条件筛选，包括地理位置、租金范围、支付方式等

看房预约：在线预约实地看房，选择合适的时间段

租约管理：查看租约合同，提交续租/退租申请

浏览历史：记录浏览过的房源，方便回溯查看

后台管理系统
公寓管理：公寓基本信息、房间信息管理

属性管理：公寓和房间的配套设施、标签管理

预约管理：处理用户的看房预约请求

租约管理：租约创建、修改、终止全流程管理

用户管理：后台用户和移动端用户账户管理

核心业务流程
租约状态流转包括：签约待确认、已签约、已取消、已到期、退租待确认、已退租、续约待确认七个状态。

技术栈
后端技术
Spring Boot 2.7+：后端框架

MyBatis 3.5+：ORM框架

MyBatis Plus 3.5+：MyBatis增强工具

Spring MVC：Web框架

MySQL 8.0+：关系型数据库

Redis 6.0+：缓存数据库

MinIO：对象存储服务

前端技术
Vue 3：前端框架

Element Plus：UI组件库

Vue Router 4.x：路由管理

Axios：HTTP客户端

部署运维
Nginx：前端部署、反向代理

Docker：容器化部署

Jenkins：CI/CD自动化部署

项目结构
text
shangting-apartment/
├── lease/                              # 后端项目
│   ├── common/                         # 公共模块
│   ├── model/                          # 数据模型层
│   ├── web/                            # Web应用层
│   │   └── web-app/                    # 主应用模块
│   │       ├── src/main/java/          # Java源代码
│   │       ├── src/main/resources/     # 配置文件
│   │       └── src/test/               # 测试代码
│   └── pom.xml                         # Maven配置
├── lease-admin/                        # 后台管理前端
│   ├── src/
│   │   ├── views/                      # 页面组件
│   │   ├── router/                     # 路由配置
│   │   └── api/                        # API接口
│   └── package.json
├── lease-mobile/                       # 移动端前端
│   ├── src/
│   │   ├── pages/                      # 页面组件
│   │   ├── router/                     # 路由配置
│   │   └── api/                        # API接口
│   └── package.json
└── deployment/                         # 部署配置
    ├── docker-compose.yml              # Docker编排
    ├── nginx/                          # Nginx配置
    └── scripts/                        # 部署脚本
快速开始
环境要求
JDK 8+

Maven 3.6+

MySQL 8.0+

Redis 6.0+

Node.js 16+

MinIO 或 阿里云OSS

后端启动
克隆项目

bash
git clone https://github.com/D198176W/lease.git
cd lease
配置数据库

sql
CREATE DATABASE lease_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
修改配置文件

bash
cp src/main/resources/application.yml.example src/main/resources/application.yml
vi src/main/resources/application.yml
启动应用

bash
mvn clean install
mvn spring-boot:run
前端启动
后台管理系统
bash
cd lease-admin
npm install
npm run serve
移动端应用
bash
cd lease-mobile
npm install
npm run serve
配置说明
配置文件示例
yaml
# application.yml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/lease_db
    username: root
    password: your_password
    driver-class-name: com.mysql.cj.jdbc.Driver
  
  redis:
    host: localhost
    port: 6379
    password: 
    database: 0

minio:
  endpoint: http://localhost:9000
  access-key: your_access_key
  secret-key: your_secret_key
  bucket-name: lease-images
环境变量配置
建议使用环境变量管理敏感信息：

bash
# Windows PowerShell
$env:DB_PASSWORD = "your_db_password"
$env:REDIS_PASSWORD = "your_redis_password"
测试
单元测试
bash
# 运行所有测试
mvn test

# 运行指定测试类
mvn test -Dtest=UserServiceTest
API测试
使用Postman或Swagger UI进行API测试：

Swagger UI地址：http://localhost:8080/swagger-ui.html

API文档地址：http://localhost:8080/v3/api-docs

数据库设计
主要数据表设计：

sql
-- 公寓信息表
CREATE TABLE apartment_info (
    id BIGINT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    address VARCHAR(200),
    latitude DECIMAL(10, 8),
    longitude DECIMAL(11, 8),
    status TINYINT DEFAULT 1,
    created_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- 房间信息表
CREATE TABLE room_info (
    id BIGINT PRIMARY KEY,
    apartment_id BIGINT NOT NULL,
    room_number VARCHAR(20),
    area DECIMAL(10, 2),
    rent DECIMAL(10, 2),
    status TINYINT DEFAULT 0,
    FOREIGN KEY (apartment_id) REFERENCES apartment_info(id)
);

-- 租约表
CREATE TABLE lease_contract (
    id BIGINT PRIMARY KEY,
    room_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    status VARCHAR(20) DEFAULT '签约待确认',
    created_time DATETIME DEFAULT CURRENT_TIMESTAMP
);
安全配置
认证授权
JWT令牌认证

RBAC权限控制

接口访问权限控制

数据安全
敏感信息加密存储

SQL注入防护

XSS攻击防护

CSRF防护

监控与日志
日志配置
Logback日志框架

日志级别分级

日志文件轮转

监控指标
Spring Boot Actuator

应用健康检查

性能监控

Docker部署
使用Docker Compose一键部署
yaml
# docker-compose.yml
version: '3.8'
services:
  mysql:
    image: mysql:8.0
    environment:
      MYSQL_ROOT_PASSWORD: root123
      MYSQL_DATABASE: lease_db
    ports:
      - "3306:3306"
    volumes:
      - mysql_data:/var/lib/mysql

  redis:
    image: redis:6-alpine
    ports:
      - "6379:6379"

  backend:
    build: ./lease
    ports:
      - "8080:8080"
    depends_on:
      - mysql
      - redis
    environment:
      SPRING_DATASOURCE_URL: jdbc:mysql://mysql:3306/lease_db
      SPRING_REDIS_HOST: redis
启动命令：

bash
docker-compose up -d
贡献指南
Fork 本仓库

创建功能分支 (git checkout -b feature/AmazingFeature)

提交更改 (git commit -m 'Add some AmazingFeature')

推送到分支 (git push origin feature/AmazingFeature)

开启 Pull Request

许可证
本项目采用 MIT 许可证。详见 LICENSE 文件。

联系方式
如有问题或建议，请通过以下方式联系：

GitHub Issues：https://github.com/D198176W/lease/issues

致谢
感谢所有为本项目做出贡献的开发者！

注意：本项目为学习交流使用，实际部署时请注意修改默认密码和密钥等安全配置。
