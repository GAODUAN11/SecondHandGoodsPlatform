# 二手交易平台系统设计文档

## 系统架构

本系统采用经典的MVC（Model-View-Controller）架构模式，将应用程序分为三个核心层：

### 1. 模型层（Model）
- **User**: 用户实体类，包含用户的基本信息（ID、用户名、密码、邮箱）
- **Item**: 商品实体类，包含商品信息（ID、名称、描述、价格、用户ID）

### 2. 数据访问层（DAO）
- **UserDAO**: 处理用户相关的数据库操作（创建用户、查找用户等）
- **ItemDAO**: 处理商品相关的数据库操作（创建、查找、更新、删除商品等）
- **DBUtil**: 数据库连接工具类，负责建立和管理数据库连接

### 3. 业务逻辑层（Service）
- **UserService**: 处理用户相关的业务逻辑（用户注册、用户认证等）
- **ItemService**: 处理商品相关的业务逻辑（添加商品、搜索商品等）
- **PasswordUtil**: 密码加密工具类，使用SHA-256算法对用户密码进行加密

### 4. 控制层（Controller）
- **AuthServlet**: 处理用户认证相关的请求（登录、注册、登出）
- **ItemServlet**: 处理商品相关的请求（添加、编辑、删除、搜索商品等）

### 5. 视图层（View）
- **JSP页面**: 使用JSP技术构建用户界面
- **CSS样式**: 统一的样式表文件，保证界面美观
- **JavaScript脚本**: 提供基础的交互功能

## 数据库结构

### 数据库名称
`secondhandgoodsplatform`

### 表结构

#### 1. 用户表（users）
| 字段名 | 类型 | 约束 | 描述 |
|--------|------|------|------|
| id | INT | PRIMARY KEY, AUTO_INCREMENT | 用户唯一标识 |
| username | VARCHAR(50) | NOT NULL, UNIQUE | 用户名 |
| password | VARCHAR(255) | NOT NULL | 加密后的密码 |
| email | VARCHAR(100) | NOT NULL | 用户邮箱 |
| created_at | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP | 用户创建时间 |

#### 2. 商品表（items）
| 字段名 | 类型 | 约束 | 描述 |
|--------|------|------|------|
| id | INT | PRIMARY KEY, AUTO_INCREMENT | 商品唯一标识 |
| name | VARCHAR(100) | NOT NULL | 商品名称 |
| description | TEXT | - | 商品描述 |
| price | DECIMAL(10, 2) | NOT NULL | 商品价格 |
| user_id | INT | FOREIGN KEY (references users.id) | 商品发布者ID |
| created_at | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP | 商品创建时间 |

## 系统功能模块

### 1. 用户管理模块
- **用户注册**: 新用户可以通过注册功能创建账户
- **用户登录**: 已注册用户可以通过登录功能进入系统
- **用户登出**: 用户可以安全退出系统

### 2. 商品管理模块
- **发布商品**: 登录用户可以发布二手商品信息
- **浏览商品**: 登录用户可以浏览所有商品
- **搜索商品**: 登录用户可以根据关键词搜索商品
- **编辑商品**: 商品发布者可以编辑自己发布的商品
- **删除商品**: 商品发布者可以删除自己发布的商品

### 3. 访问控制模块
- **身份验证**: 未登录用户无法浏览商品信息
- **权限控制**: 只有商品发布者才能编辑或删除自己的商品

## 使用方法

### 系统部署
1. 确保已安装Java JDK 11或更高版本
2. 确保已安装Apache Tomcat 11.0或更高版本
3. 确保已安装MySQL数据库
4. 创建数据库并执行初始化脚本
5. 配置数据库连接信息（位于DBUtil类中）
6. 使用Maven构建项目
7. 将生成的WAR文件部署到Tomcat服务器

### 数据库初始化
```sql
-- 创建数据库
CREATE DATABASE IF NOT EXISTS secondhandgoodsplatform CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 使用数据库
USE secondhandgoodsplatform;

-- 创建用户表
CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 创建商品表
CREATE TABLE IF NOT EXISTS items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    price DECIMAL(10, 2) NOT NULL,
    user_id INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);
```

### 系统使用流程
1. **访问系统**: 在浏览器中输入系统地址
2. **注册账户**: 点击"注册"按钮，填写用户名、密码和邮箱完成注册
3. **登录系统**: 使用注册的用户名和密码登录系统
4. **浏览商品**: 登录后可以浏览所有商品信息
5. **搜索商品**: 使用搜索功能查找感兴趣的二手商品
6. **发布商品**: 点击"发布商品"按钮，填写商品信息并提交
7. **管理商品**: 在商品详情页面可以编辑或删除自己发布的商品
8. **退出系统**: 点击"退出"按钮安全退出系统

## 安全特性

1. **密码加密**: 用户密码使用SHA-256算法进行加密存储
2. **访问控制**: 未登录用户无法访问商品相关信息
3. **权限隔离**: 用户只能编辑和删除自己发布的商品
4. **SQL注入防护**: 使用PreparedStatement防止SQL注入攻击

## 技术栈

- **后端**: Java、Servlet、JSP
- **前端**: HTML、CSS、JavaScript、JSTL
- **数据库**: MySQL
- **构建工具**: Maven
- **服务器**: Apache Tomcat
- **加密算法**: SHA-256

## 注意事项

1. 系统不使用任何第三方框架，完全基于原生Java EE技术实现
2. 前端CSS和JavaScript文件分离存放，便于维护
3. 所有数据库操作均通过DAO层进行，保证数据访问的一致性和安全性
4. 系统具有良好的扩展性，可根据需要添加新功能模块