# eChinese Android 客户端

四类模块的工程骨架：`app`（框架）、`feature`（界面）、`domain`/`data`（业务）、`core`（基础）。

## 模块
- app：启动、主题、路由宿主
- core：设计 token、网络接口（后续补 i18n/存储/媒体）
- domain：实体与仓储接口
- data：mock 仓储（接后端时替换实现）
- feature/auth：登录页（Compose）

## 运行
用 Android Studio 打开本目录，Gradle 同步后运行 `app`。首次同步需要联网下载依赖。

## 约定
- UI 只依赖 domain 接口，不直接碰 data 实现；
- 新增题型在练习模块内加插件，不改页面；
- 接口未定前用 data 层 mock，接口就绪只替换实现。
