## 使用说明

第一步
在项目中构建构建子模块

```git
git submodule add git@github.com:e9ab98e991ab/libcommon.git libcommon
```

第二步

在项目build.gradle中粘贴如下代码

```
buildscript {
    apply from: 'libcommon/version.gradle' 
    addRepos(repositories)
    dependencies {
        classpath deps.android_gradle_plugin
        classpath deps.kotlin_plugin 
    } 
}


allprojects { 
    addRepos(repositories)
    
    gradle.taskGraph.whenReady { taskGraph -> 
        taskGraph.allTasks.each { task -> 
            if (task.name.contains("Test")
                    || task.name.contains("test")
                    || task.name.contains("mockableAndroidJar")
                    || task.name.contains("Lint")
                    || task.name.contains("lint")
                    || task.name.contains("Aidl")
                    || task.name.contains("aidl")
                    || task.name.contains("Ndk")
                    || task.name.contains("ndk")
            ) {
                task.enabled = false
            }
        }
    }
}

apply from: "libcommon/configureLibrary.gradle"


afterEvaluate {

    tasks.matching {
        it.name.startsWith('dex')
    }.each { dx ->
        if (dx.additionalParameters == null) {
            dx.additionalParameters = []
        }
        dx.additionalParameters += '--set-max-idx-number=48000'
    }
}

tasks.withType(org.jetbrains.kotlin.gradle.tasks.KotlinCompile).all {
    kotlinOptions {
        kotlinOptions.suppressWarnings = true
    }
}

task clean(type: Delete) {
    delete rootProject.buildDir
}
```

 

第三步

在app下的settings.gradle中引用

```gradle
include 'libcommon'
```

第四步

在app下的gradle.properties中添加`isPlugin=false`是否开启插件选项

第五步

在app下的build.gradle中引用

```
implementation project(':libcommon')
```

第六步

在APP的AndroidManifest.xml中添加适配方案的UI图大小

```
<meta-data
android:name="design_width_in_dp"
android:value="360"/>
<meta-data
android:name="design_height_in_dp"
android:value="640"/>
```



##### 其他操作
更新libcommon

```
 git submodule update
```

提交libcommon

```
cd到libcommon目录下 同git 提交命令一致
```

