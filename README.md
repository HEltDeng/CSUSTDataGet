# CSUSTDataGet — 当前安全开发与 Mac 恢复基线

本项目为校园卡、教务和 MOOC 的 Android library，保留现行日志安全修改。来源和许可见 [SOURCE_NOTICE.md](SOURCE_NOTICE.md) 与 [LICENSE](LICENSE)。

## 恢复与构建

需要 JDK 17、Android SDK 36；沿用仓库中的 Gradle 8.13 / Android Gradle Plugin 8.13.0 与现有依赖版本。

```sh
git clone --branch migration/clean-source-baseline https://github.com/HEltDeng/CSUSTDataGet.git
cd CSUSTDataGet
# 在 local.properties 写入本机 sdk.dir，或配置 ANDROID_HOME。
sh ./gradlew :csust_spider:verifyCampusCardAuth :csust_spider:assembleRelease --no-configuration-cache --no-build-cache
```

产物：`csust_spider/build/outputs/aar/csust_spider-release.aar`。这是 library release variant，不是获准公开发布的 App Release。构建本库不读取真实认证值，AAR 内无默认认证值。

## 认证配置由宿主 App 提供

`CampusCardAuthConfig.configure(campusCardBasicAuth, chargeAuth)` 由宿主 App 在首次校园卡认证调用之前调用。库保留原请求协议和公开业务方法，参数按原样传入；未配置、空值或换行值会明确失败，错误信息不包含传入值。

本地开发可从 `secrets.properties.example` 创建 **被 Git 忽略** 的 `secrets.properties`。模板只提供字段名，实际值通过项目所有者管理的安全渠道获得。**本 library 不将该文件编译进 AAR**。长理 Android 在自己的 ignored 配置中读取参数，仅给本地 Debug 的生成 BuildConfig 使用，再向本库提供值。

```properties
CAMPUS_CARD_BASIC_AUTH=
CHARGE_AUTH=
```

禁止提交或分发真实配置、含值 BuildConfig、Debug APK、构建缓存或日志。含值 Debug APK 仅允许开发机本地使用，不上传 GitHub Release/CI artifacts。认证值管理方仍为 `OWNER_UNKNOWN`；当前方案不表示 Release APK 或旧历史已经安全。

## 验证与范围

单元测试使用合成参数验证缺失配置、原值透传、非法配置拒绝、错误信息不泄露输入及配置清除。真实校园接口验证为 `PENDING`。不改变学校端点、认证协议、用户账号流程或生产服务。

当前 clean root history 与旧 CreaMakers history 独立；本地旧历史和 tags 仍保留。不要执行 `git push --all` / `--mirror` / `--tags` 将旧含值引用发送到新恢复仓库。
