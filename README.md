# JMS-Center-Android · 极兔 JMS 中心仓 Android 客户端接入组件

`JMS-Center-Android` 是 JMS 中心仓（Center Hub）Android 端的接入组件，封装登录态透传、波次任务拉取、扫描枪键值回传三件事。业务侧只需实现 `TaskHandler`，其余网络与重试由组件负责。

## 目录

```
src/main/java/com/jms/center/android/JmsCenterAndroidClient.java   客户端主体
src/main/java/com/jms/center/android/TaskHandler.java              业务回调接口
pom.xml                                                            内部依赖坐标
```

## 服务标识

- 工程代号：`JMS-Center-Android`
- 出现位置：`pom.xml` 的 `artifactId`、`JmsCenterAndroidClient` 的包名与 `SDK_TAG` 常量、`README` 首段
- 依赖坐标：`com.jms.center:jms-center-android:2.4.1`

## 使用

```java
JmsCenterAndroidClient client = new JmsCenterAndroidClient.Builder()
        .endpoint("https://center-hub.jms.internal/api")
        .siteCode("CGK01")
        .build();
client.bindHandler(handler);
client.startPolling();
```

## 字段说明

| 字段 | 说明 |
|---|---|
| `siteCode` | 中心仓站点编码，如 `CGK01`（雅加达）、`SIN02`（新加坡） |
| `waveNo` | 波次号，格式 `W` + 6 位日期 + 3 位序号 |
| `scanCode` | 扫描枪回传的运单条码 |

## 已知问题

1. Android 8 以下机型在息屏后长连接会被系统回收，需在 `AndroidManifest` 里申请前台服务。
2. 弱网环境下 `pollInterval` 建议不低于 15000 ms。

## 维护

数据平台组 · 内部工单 `JMS-C-1182`
