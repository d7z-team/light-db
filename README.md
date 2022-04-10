# LightDB

<a href="https://github.com/d7z-team/light-db" target="_blank"><img alt="Jenkins" src="https://github.com/d7z-team/light-db/actions/workflows/task-push.yml/badge.svg?branch=main&color=green&style=flat-square"/></a>
<a href="LICENSE"><img alt="GitHub license" src="https://img.shields.io/github/license/d7z-team/light-db"></a>
<a href="https://jitpack.io/#d7z-team/light-db" target="_blank"> <img alt="JitPack" src="https://img.shields.io/jitpack/v/github/d7z-team/light-db"></a>
<a href="https://jitpack.io/#d7z-team/light-db" target="_blank"> <img alt="JitPack" src="https://img.shields.io/maven-metadata/v?metadataUrl=https%3A%2F%2Frepo.open-edgn.cn%2Fmaven%2Fcom%2Fgithub%2Fd7z-team%2Flight-db%2Fbom%2Fmaven-metadata.xml&color=red&style=flat"></a>

> LightDB 是一套轻量级数据缓存模块.

## 特性

- 开箱即用，使用简单
- 支持与各种现有的框架组合

## 快速开始

请查看各个模块的 `Test` 用例,或者参考 [Test 模块](./db-test)。

如何引入项目请参照 [Jitpack](https://jitpack.io/#d7z-team/light-db).

或者你可以使用：

```kotlin
implementation(platform("com.github.d7z-team.light-db:bom:$lightDBVersion"))
implementation("com.github.d7z-team.light-db:db-api")
implementation("com.github.d7z-team.light-db:db-jedis")
```

其中，`$lightDBVersion` 是LightDB 的版本.

## 更新日志

- [0.4.0](https://github.com/d7z-team/light-db/releases/tag/0.4.0) 新增 Spring Boot 支持
  - 新增 Spring Boot Starter ，兼容 2.6.x 及以上版本

- [0.3.0](https://github.com/d7z-team/light-db/releases/tag/0.3.0) 重构 API，**与 0.2.0 不兼容**
  - api: 增加设置过期时间的 API，移除旧的 SPI 加载器
  - memory: 重构 API
  - jedis：重构 API，优化 jedis 原子操作

- [0.2.0](https://github.com/d7z-team/light-db/releases/tag/0.2.0) 修复打包问题，新增批量依赖方案


- [0.1.0](https://github.com/d7z-team/light-db/releases/tag/0.1.0) 新增Session、Cache
  - 新增 Session 实现
  - 新增 Cache 实现

- [0.0.1](https://github.com/d7z-team/light-db/releases/tag/0.0.1) 第一个技术预览版
  - 内存实现
  - redis 后端实现
  - 支持 List 、Set 、Map

## LICENSE

此项目使用 MIT License ，更多详情请查看 [License文件](./LICENSE)
