# swipe-refresh_recyclerview
可下拉刷新，上拉加载更多的RecyclerView，下拉效果同Google官方下拉刷新效果
用ItemInfo将不同的Type分隔开，让代码看起来更加简洁，降低耦合度
### Usage
- Gradle
	Add it in your root build.gradle at the end of repositories:
	```groovy
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
	```

	Add the dependency
	```groovy
	dependencies {
	        implementation 'com.github.PayneJay:swipe-refresh-recyclerview:1.0-release'
	}
	```
- Maven
	Add the JitPack repository to your build file
	```
	<repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>
	```

	Add the dependency
	```
	<dependency>
	    <groupId>com.github.PayneJay</groupId>
	    <artifactId>swipe-refresh-recyclerview</artifactId>
	    <version>1.0-release</version>
	</dependency>
	```

