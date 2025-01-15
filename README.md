# course_design_ibrary-system

# 图书借阅管理系统

本项目是一个基于 Android 平台开发的图书借阅管理系统，旨在为用户提供便捷的图书借阅和管理服务，充分结合了 "互联网+" 的智能化设计理念。

## 功能概述

### 1. 管理入口

- 图书管理功能：支持对图书信息的 **新增、删除、查询、修改** 操作。
- 数据存储：使用 SQLite 数据库实现。

### 2. 用户入口

- **注册功能**：支持新用户注册。
- **登录功能**：支持用户登录，包含 "记住密码" 功能。
- **导航页面**：
  - **图书列表**：展示可借阅图书，支持查看图书详情并进行借阅操作。
  - **借阅信息**：记录用户的借阅历史，包括书籍名称、借阅时间及归还时间，支持归还操作。

## 技术亮点

- **轻量级存储技术**：采用 SharedPreferences 存储用户登录状态及设置。
- **本地数据库管理**：使用 SQLite 数据库进行数据持久化。
- **自定义界面设计**：
  - 自定义按钮样式，支持渐变色和圆角设计。
  - RecyclerView 实现动态图书列表。
- **模块化设计**：功能模块清晰，易于扩展和维护。

#### 安装与使用

- ### 环境要求

- Android Studio：最新稳定版本

- JDK 版本：8 或更高

- Gradle：兼容 Android 项目的版本

### 使用步骤

1. 克隆项目到本地：
   
   ```bash
   git clone https://github.com/kaiyaolnayyds/course_design_ibrary-system.git
   ```

2. 使用 Android Studio 打开项目。

3. 同步项目以安装所需依赖：
   
   - 确保 Gradle 文件已成功同步。

4. 连接 Android 模拟器或实际设备。

5. 编译并运行应用。

## 使用截图（部分）

### 登录页面

![Login Page](https://github.com/kaiyaolnayyds/course_design_ibrary-system/blob/main/ScreenImage/%E7%99%BB%E5%BD%95.png)

### 图书列表

![Book List](https://github.com/kaiyaolnayyds/course_design_ibrary-system/blob/main/ScreenImage/%E5%9B%BE%E4%B9%A6%E5%88%97%E8%A1%A8.png)

### 借阅信息

![Borrow Info](https://github.com/kaiyaolnayyds/course_design_ibrary-system/blob/main/ScreenImage/%E5%80%9F%E9%98%85%E4%BF%A1%E6%81%AF.png)

## 未来扩展

- 添加用户评论和评分功能。
- 支持在线图书推荐与搜索。
- 集成云端同步功能。

## 贡献

欢迎提交 issue 和 pull request。如果有任何改进建议或问题，请联系：

1748623654@qq.com。
