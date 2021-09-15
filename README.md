> ## 使用技术栈为：**组件化、Kotlin、MVVM、Jetpack、Repository、Kotlin-Coroutine-Flow**，文档下面会对框架中所使用的一些核心技术进行阐述。



## 框架图示

#### **谷歌 Android 团队 Jetpack 视图模型：**

<img src="https://github.com/Quyunshuo/AndroidBaseFrameMVVM/raw/master/img/img2.png" style="zoom:67%;" />

## 模块

- **app:**

  **app壳** 工程，是依赖所有组件的壳，该模块不应该包含任何代码，它只作为一个空壳存在，由于项目中使用了EventBusAPT技术，需要索引到各业务组件的对应的APT生成类，所以在 **app壳** 内有这一部分的代码。
  
  


- **buildSrc:**

  这是一个特殊的文件夹，负责项目的构建，里面存放着一些项目构建时用到的东西，比如项目配置，依赖。这里面还是存放 **Gradle** 插件的地方，一些自定义的 **Gradle** 的插件都需要放在此处。

  

- **lib_base:**

  项目的基础公共模块，存放着各种基类封装、对远程库的依赖、以及工具类、三方库封装，该组件是和项目业务无关的，和项目业务相关的公共部分需要放在 **lib_common** 中。

  

- **lib_common:**

  项目的业务公共模块，这里面存放着项目里各个业务组件的公共部分，还有一些项目特定需要的一些文件等，该组件是和项目业务有关系的。

  

  

## 组件化相关

#### 组件初始化

> &emsp;&emsp;为了更好的代码隔离与解耦，在特定组件内使用的SDK及三方库，应该只在该组件内依赖，不应该让该组件的特定SDK及三方库的API暴露给其他不需要用的组件。有一个问题就出现了，SDK及三方库常常需要手动去初始化，而且一般都需要在项目一启动（即 **Application** 中）初始化，但是一个项目肯定只能有一个自定义的 **Application**，该项目中的自定义 **Application** 在 **lib_base** 模块中，并且也是在 **lib_base** 模块中的清单文件中声明的，那其他组件该如何初始化呢？带着这个问题我们一起来深入研究下。



#### **本项目中所使用的方案：**

- **面向接口编程 + Java的SPI机制（ServiceLoader）+AutoService：**

  &emsp;&emsp;先来认识下 **Java** 的 **SPI** 机制：面向的对象的设计里，我们一般推荐模块之间基于接口编程，模块之间不对实现类进行硬编码。一旦代码里涉及具体的实现类，就违反了可拔插的原则，如果需要替换一种实现，就需要修改代码。为了实现在模块装配的时候不用在程序里动态指明，这就需要一种服务发现机制。**JavaSPI** 就是提供这样的一个机制：为某个接口寻找服务实现的机制。这有点类似 **IOC** 的思想，将装配的控制权移到了程序之外。这段话也是我复制的别人的，听起来很懵逼，大致意思就是我们可以通过 **SPI** 机制将实现类暴露出去。关于如何使用 **SPI**，这里不在陈述，总之是我们在各组件内通过 **SPI** 去将实现类暴露出去，在 **Application** 中我们通过 **Java** 提供的 **SPI** **API** 去获取这些暴露的服务，这样我们就拿到了这些类的实例，剩下的步骤就和上面的方案一样了，通过一个集合遍历实现类调用其相应的方法完成初始化的工作。由于使用 **SPI** 需要在每个模块创建对应的文件配置，这比较麻烦，所以我们使用 **Google** 的 **AutoService** 库来帮助我们自动创建这些配置文件，使用方式也非常的简单，就是在实现类添加一个 **AutoService** 注解。本框架中的核心类是这几个：**lib_base-LoadModuleProxy**、**lib_base-ApplicationLifecycle**。这种方案是我请教的一个米哈游的大佬，这位大佬告诉我在组件化中组件的初始化可以使用 **ServiceLoader** 来做，于是我就去研究了下，最后发现这种方案还不错，比前面提到的两种方案都要简单、安全。



#### 资源命名冲突

&emsp;&emsp;在组件化方案中，资源命名冲突是一个比较严重的问题，由于在打包时会进行资源的合并，如果两个模块中有两个相同名字的文件，那么最后只会保留一份，如果不知道这个问题的小伙伴，在遇到这个问题时肯定是一脸懵逼的状态。问题既然已经出现，那我们就要去解决，解决办法就是每个组件都用固定的命名前缀，这样就不会出现两个相同的文件的现象了，我们可以在 **build.gradle** 配置文件中去配置前缀限定，如果不按该前缀进行命名，**AS** 就会进行警告提示，配置如下：

```Groovy
android {
    resourcePrefix "前缀_"
}
```



#### 组件划分

- 其实组件的划分一直是一个比较难的部分，这里其实也给不到一些非常适合的建议，看是看具体项目而定。
- 关于**基础组件**通常要以**独立**可直接**复用**的角度出现，比如网络模块、二维码识别模块等。
- 关于业务组件，**业务组件**一般可以进行**单独调试**，也就是可以作为 **app** 运行，这样才能发挥组件化的一大用处，当项目越来越大，业务组件越来越多时，编译耗时将会是一个非常棘手的问题，但是如果每个业务模块都可以进行的单独调试，那就大大减少了编译时间，同时，开发人员也不需要关注其他组件。
- 关于公共模块，**lib_base** 放一些**基础性代码**，属于**框架基础层**，不应该和项目业务有牵扯，而和**项目业务相关**的公共部分则应该放在 **lib_common** 中，不要污染 **lib_base**。



#### 依赖版本控制

&emsp;&emsp;组件化常见的一个问题就是依赖版本，每个组件都有可能自己的依赖库，那我们应该统一管理各种依赖库及其版本，使项目所有使用的依赖都是同一个版本，而不是不同版本。本项目中使用 **buildSrc** 中的几个kt文件进行依赖版本统一性的管理，及其项目的一些配置。



## **MVVM相关**

* **MVVM** 采用 **Jetpack** 组件 + **Repository** 设计模式 实现，所使用的 **Jetpack** 并不是很多，像 **DataBinding**、**Room** 等并没有使用，如果需要可以添加。采用架构模式目的就是为了解偶代码，对代码进行分层，各模块各司其职，所以既然使用了架构模式那就要遵守好规范。

* **Repository** 仓库层负责数据的提供，**ViewModel** 无需关心数据的来源，**Repository** 内避免使用 **LiveData**，框架里使用了 **Kotlin** 协程的 **Flow** 进行处理请求(访问数据库)，**Repository** 的函数会返回一个 **Flow** 给 **ViewModel** 的调用函数，**Flow** 上游负责提供数据，下游也就是 **ViewModel** 获取到数据使用 **LiveData** 进行存储，**View** 层订阅 **LiveData**，实现数据驱动视图

* 三者的依赖都是单向依赖，**View** -> **ViewModel** -> **Repository**  

  

#### 项目使用的三方库及其简单示例和资料

* [Kotlin](https://github.com/JetBrains/kotlin)
* [Kotlin-Coroutines-Flow](https://github.com/JetBrains/kotlin)
* [Lifecycle](https://developer.android.com/jetpack/androidx/releases/lifecycle)
* [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)
* [LiveData](https://developer.android.com/topic/libraries/architecture/livedata)
* [ViewBinding](https://developer.android.com/topic/libraries/view-binding)
* [Hilt](https://developer.android.com/jetpack/androidx/releases/hilt)
* [Android KTX](https://developer.android.com/kotlin/ktx)
* [OkHttp](https://github.com/square/okhttp)：网络请求
* [Retrofit](https://github.com/square/retrofit)：网络请求
* [MMKV](https://github.com/Tencent/MMKV)：腾讯基于 **mmap** 内存映射的 **key-value** 本地存储组件
* [Coil](https://github.com/coil-kt/coil)：一个 Android 图片加载库，通过 Kotlin 协程的方式加载图片
* [ARoute](https://github.com/alibaba/ARouter)：阿里用于帮助 **Android App** 进行组件化改造的框架 —— 支持模块间的路由、通信、解耦
* [BaseRecyclerViewAdapterHelper](https://github.com/CymChad/BaseRecyclerViewAdapterHelper)：一个强大并且灵活的 **RecyclerViewAdapter**
* [EventBus](https://github.com/greenrobot/EventBus)：适用于 **Android** 和 **Java** 的发布/订阅事件总线
* [Bugly](https://bugly.qq.com/v2/index)：腾讯异常上报及热更新(只集成了异常上报)
* [PermissionX](https://github.com/guolindev/PermissionX)：郭霖权限请求框架
* [LeakCanary](https://github.com/square/leakcanary)：**Android** 的内存泄漏检测库
* [AndroidAutoSize](https://github.com/JessYanCoding/AndroidAutoSize)：**JessYan** 大佬的 今日头条屏幕适配方案终极版



#### **Kotlin协程**

关于 **Kotlin 协程**，是真的香，具体教程可以看我的一篇文章：

- [万字长文 - Kotlin 协程进阶](https://juejin.cn/post/6950616789390721037)
- **Flow** 类似于 **RxJava**，它也有一系列的操作符，资料：  [Google 推荐在 MVVM 架构中使用 Kotlin Flow ](https://juejin.im/post/6854573211930066951)

- [即学即用Kotlin - 协程](https://juejin.im/post/6854573211418361864)
- [Kotlin Coroutines Flow 系列(1-5)](https://juejin.im/post/6844904057530908679)



#### **PermissionX**

**PermissionX** 是郭霖的一个权限申请框架
**使用方式:**

```
PermissionX.init(this)
     .permissions("需要申请的权限")
     .request { allGranted, grantedList, deniedList -> }
```

**资料:**  

GitHub: [https://github.com/guolindev/PermissionX](https://github.com/guolindev/PermissionX)



#### EventBus APT

事件总线这里选择的还是 **EventBus**，也有很多比较新的事件总线框架,还是选择了这个直接上手的
在框架内我对 **EventBus** 进行了基类封装，自动注册和解除注册，在需要注册的类上添加 **@EventBusRegister** 注解即可，无需关心内存泄漏及没及时解除注册的情况，基类里已经做了处理

```kotlin
@EventBusRegister
class MainActivity : AppCompatActivity() {}
```

很多使用 **EventBus** 的开发者其实都没有发现 **APT** 的功能，这是 **EventBus3.0** 的重大更新，使用 **EventBus APT** 可以在编译期生成订阅类，这样就可以避免使用低效率的反射，很多人不知道这个更新，用着**3.0**的版本，实际上却是**2.0**的效率。
项目中已经在各模块中开启了 **EventBus APT**，**EventBus** 会在编译器对各模块生成订阅类，需要我们手动编写代码去注册这些订阅类：

```kotlin
// 在APP壳的AppApplication类中
EventBus
     .builder()
	   .addIndex("各模块生成的订阅类的实例 类名在base_module.gradle脚本中进行了设置 比如 module_home 生成的订阅类就是 module_homeIndex")
     .installDefaultEventBus()
```



#### 屏幕适配 AndroidAutoSize

屏幕适配使用的是 **JessYan** 大佬的 今日头条屏幕适配方案终极版

GitHub: [https://github.com/JessYanCoding/AndroidAutoSize](https://github.com/JessYanCoding/AndroidAutoSize)

**使用方式:**

```
// 在清单文件中声明
<manifest>
    <application> 
    		// 主单位使用dp 没设置副单位
        <meta-data
            android:name="design_width_in_dp"
            android:value="360"/>
        <meta-data
            android:name="design_height_in_dp"
            android:value="640"/>           
     </application>           
</manifest>

// 默认是以竖屏的宽度为基准进行适配
// 如果是横屏项目要适配Pad(Pad适配尽量使用两套布局 因为手机和Pad屏幕宽比差距很大 无法完美适配)
<manifest>
    <application>            
    		// 以高度为基准进行适配 (还需要手动代码设置以高度为基准进行适配) 目前以高度适配比宽度为基准适配 效果要好
        <meta-data
            android:name="design_height_in_dp"
            android:value="400"/>           
     </application>           
</manifest>

// 在Application 中设置
// 屏幕适配 AndroidAutoSize 以横屏高度为基准进行适配
AutoSizeConfig.getInstance().isBaseOnWidth = false
```



#### ARoute

**ARoute** 是阿里巴巴的一个用于帮助 **Android App** 进行组件化改造的框架 —— 支持模块间的路由、通信、解耦  

**使用方式:**

```
// 1.在需要进行路由跳转的Activity或Fragment上添加 @Route 注解
@Route(path = "/test/activity")
public class YourActivity extend Activity {
    ...
}

// 2.发起路由跳转
ARouter.getInstance()
    .build("目标路由地址")
    .navigation()
    
// 3.携带参数跳转
ARouter.getInstance()
		.build("目标路由地址")
    .withLong("key1", 666L)
    .withString("key3", "888")
    .withObject("key4", new Test("Jack", "Rose"))
    .navigation()

// 4.接收参数
@Route(path = RouteUrl.MainActivity2)
class MainActivity : AppCompatActivity() {
    // 通过name来映射URL中的不同参数
    @Autowired(name = "key")
    lateinit var name: String
    
		override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        // ARouter 依赖注入 ARouter会自动对字段进行赋值，无需主动获取
        ARouter.getInstance().inject(this)
    }
}

// 5.获取Fragment
Fragment fragment = (Fragment) ARouter.getInstance().build("/test/fragment").navigation();
```

**资料:** 

官方文档:[https://github.com/alibaba/ARouter](https://github.com/alibaba/ARouter)



#### ViewBinding

通过视图绑定功能，可以更轻松地编写可与视图交互的代码。在模块中启用视图绑定之后，系统会为该模块中的每个 **XML** 布局文件生成一个绑定类。绑定类的实例包含对在相应布局中具有 **ID** 的所有视图的直接引用。
在大多数情况下，视图绑定会替代 **findViewById**

**使用方式:** 

按模块启用**ViewBinding**

```groovy
// 模块下的build.gradle文件
android {
		// 开启ViewBinding
    // 高版本AS
    buildFeatures {
        viewBinding = true
    }
    // 低版本AS 最低3.6
    viewBinding {
        enabled = true
    }
}
```

**Activity** 中 **ViewBinding** 的使用

```kotlin
// 之前设置视图的方法
setContentView(R.layout.activity_main)

// 使用ViewBinding后的方法
val mBinding = ActivityMainBinding.inflate(layoutInflater)
setContentView(mBinding.root)

// ActivityMainBinding类是根据布局自动生成的 如果没有请先build一下项目
// ViewBinding会将控件id转换为小驼峰命名法,所以为了保持一致规范,在xml里声明id时也请使用小驼峰命名法
// 比如你有一个id为mText的控件,可以这样使用
mBinding.mText.text = "ViewBinding"
```

**Fragment** 中 **ViewBinding** 的使用

```kotlin
// 原来的写法
return inflater.inflate(R.layout.fragment_blank, container, false)

// 使用ViewBinding的写法
mBinding = FragmentBlankBinding.inflate(inflater)
return mBinding.root
```

**资料:** 

官方文档: [https://developer.android.com/topic/libraries/view-binding](https://developer.android.com/topic/libraries/view-binding)  

CSDN: [https://blog.csdn.net/u010976213/article/details/104501830?depth_1-utm_source=distribute.pc_relevant.none-task-blog-BlogCommendFromBaidu-5&utm_source=distribute.pc_relevant.none-task-blog-BlogCommendFromBaidu-5](https://blog.csdn.net/u010976213/article/details/104501830?depth_1-utm_source=distribute.pc_relevant.none-task-blog-BlogCommendFromBaidu-5&utm_source=distribute.pc_relevant.none-task-blog-BlogCommendFromBaidu-5)



#### ViewModel

**ViewModel** 类旨在以注重生命周期的方式存储和管理界面相关的数据。**ViewModel** 类让数据可在发生屏幕旋转等配置更改后继续留存。

**使用方式:**

```kotlin
class MainViewModel : ViewModel(){}

class MainActivity : AppCompatActivity() {
		// 获取无参构造的ViewModel实例
    val mViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
}
```

**资料:** 

官方文档: [https://developer.android.com/topic/libraries/architecture/viewmodel](https://developer.android.com/topic/libraries/architecture/viewmodel)

Android ViewModel，再学不会你砍我: [https://juejin.im/post/6844903919064186888](https://juejin.im/post/6844903919064186888)



#### LiveData

**LiveData** 是一种可观察的数据存储器类。与常规的可观察类不同，**LiveData** 具有生命周期感知能力，意指它遵循其他应用组件（如 **Activity**、**Fragment** 或 **Service**）的生命周期。这种感知能力可确保 **LiveData** 仅更新处于活跃生命周期状态的应用组件观察者

**LiveData** 分为可变值的 **MutableLiveData** 和不可变值的 **LiveData**

**常用方法:**

```kotlin
fun test() {
        val liveData = MutableLiveData<String>()
        // 设置更新数据源
        liveData.value = "LiveData"
        // 将任务发布到主线程以设置给定值
        liveData.postValue("LiveData")
        // 获取值
        val value = liveData.value
        // 观察数据源更改(第一个参数应是owner:LifecycleOwner 比如实现了LifecycleOwner接口的Activity)
        liveData.observe(this, {
            // 数据源更改后触发的逻辑
        })
    }
```

**资料:**

官方文档: [https://developer.android.com/topic/libraries/architecture/livedata](https://developer.android.com/topic/libraries/architecture/livedata)



#### Lifecycle

**Lifecycle** 是一个类，用于存储有关组件（如 **Activity** 或 **Fragment**）的生命周期状态的信息，并允许其他对象观察此状态。**LifecycleOwner** 是单一方法接口，表示类具有 **Lifecycle**。它具有一种方法（即 **getLifecycle()**），该方法必须由类实现。实现 **LifecycleObserver** 的组件可与实现 **LifecycleOwner** 的组件无缝协同工作，因为所有者可以提供生命周期，而观察者可以注册以观察生命周期。

**资料:**  

官方文档: [https://developer.android.com/topic/libraries/architecture/lifecycle](https://developer.android.com/topic/libraries/architecture/lifecycle)



#### Hilt

**Hilt** 是 **Android** 的依赖项注入库，可减少在项目中执行手动依赖项注入的样板代码。执行手动依赖项注入要求您手动构造每个类及其依赖项，并借助容器重复使用和管理依赖项。

**Hilt** 通过为项目中的每个 **Android** 类提供容器并自动管理其生命周期，提供了一种在应用中使用 **DI（依赖项注入）**的标准方法。**Hilt** 在热门 **DI** 库 **Dagger** 的基础上构建而成，因而能够受益于 **Dagger** 的编译时正确性、运行时性能、可伸缩性和 **Android Studio** 支持。

**资料:**  

目前官方文档还没有更新正式版的，还是 **alpha** 版本的文档：[使用 Hilt 实现依赖项注入](https://developer.android.com/training/dependency-injection/hilt-android)

**Dagger** 的 **Hilt** 文档目前是最新的：[Dagger-Hilt](https://dagger.dev/hilt/)



#### Coil

**Coil** 是一个 Android 图片加载库，通过 Kotlin 协程的方式加载图片。特点如下：

- **更快**: Coil 在性能上有很多优化，包括内存缓存和磁盘缓存，把缩略图存保存在内存中，循环利用 bitmap，自动暂停和取消图片网络请求等。
- **更轻量级**: Coil 只有2000个方法（前提是你的 APP 里面集成了 OkHttp 和 Coroutines），Coil 和 Picasso 的方法数差不多，相比 Glide 和 Fresco 要轻量很多。
- **更容易使用**: Coil 的 API 充分利用了 Kotlin 语言的新特性，简化和减少了很多样板代码。
- **更流行**: Coil 首选 Kotlin 语言开发并且使用包含 Coroutines, OkHttp, Okio 和 AndroidX Lifecycles 在内最流行的开源库。

**Coil** 名字的由来：取 **Co**routine **I**mage **L**oader 首字母得来。

**资料:**  

官方文档: [https://coil-kt.github.io/coil/](https://coil-kt.github.io/coil/)

三方库源码笔记（13）-可能是全网第一篇 Coil 的源码分析文章：[https://juejin.cn/post/6897872882051842061](https://juejin.cn/post/6897872882051842061)

【奇技淫巧】新的图片加载库？基于Kotlin协程的图片加载库——Coil：[https://juejin.cn/post/6844904159527829518](https://juejin.cn/post/6844904159527829518)
