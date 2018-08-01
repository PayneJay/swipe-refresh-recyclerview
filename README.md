# swipe-refresh-recyclerview
一个支持下拉刷新，上拉加载更多的RecyclerView，下拉效果仿Google原生，使用SwipeRefreshLayout。利用ViewType区分不同的Item类型，使业务逻辑代码更加清晰。。。
## Usage
* Gradle
Step 1. Add it in your root build.gradle at the end of repositories:
```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
Step 2. Add the dependency
```
dependencies {
	        implementation 'com.github.PayneJay:swipe-refresh-recyclerview:2.0'
	}
```
* Maven
Step 1. Add the JitPack repository to your build file
```
<repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>
```
Step 2. Add the dependency
```
<dependency>
	    <groupId>com.github.PayneJay</groupId>
	    <artifactId>swipe-refresh-recyclerview</artifactId>
	    <version>-SNAPSHOT</version>
	</dependency>
```
