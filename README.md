<img src="/.github/icon.png" width="190px" alt="Icon"/>

## Waterfall Toolbar
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Waterfall%20Toolbar-brightgreen.svg)](https://android-arsenal.com/details/1/6232)
[![JitPack](https://jitpack.io/v/hugocastelani/waterfall-toolbar.svg)](https://jitpack.io/#hugocastelani/waterfall-toolbar)
[![License](https://img.shields.io/github/license/hugocastelani/waterfall-toolbar.svg)](https://github.com/hugocastelani/waterfall-toolbar/blob/master/LICENSE)<br>
Waterfall Toolbar is an Android version of Material Design's web component waterfall toolbar. Basically, what it does is dynamize an ordinary Toolbar, increasing and decreasing its shadow when an associated view is scrolled.<br>
You can download the <a href="https://raw.githubusercontent.com/hugocastelani/waterfall-toolbar/master/sample.apk">sample.apk</a> to get a better notion of what's going on.<br><br>
<img src="/.github/sample.gif" alt="Sample"/>

## Setup
### Gradle dependency
Waterfall Toolbar is available on <a href="https://jitpack.io/#hugocastelani/waterfall-toolbar">Jitpack</a>.<br>
To add this library to your project, add the code below to your root (not module) ``build.gradle`` file:
```gradle
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
 ```

And this one to your module `build.gradle` file:
``` gradle
dependencies {
    ...
    compile 'com.github.hugocastelani:waterfall-toolbar:0.2'
}
```

## Implementation
Implementing Waterfall Toolbar is quite simple. All you gotta do is add it to your layout via XML or Java and refer a RecyclerView or a ScrollView.<br>
Your XML code: 
```xml
<com.hugocastelani.waterfalltoolbar.library.WaterfallToolbar
    android:id="@+id/waterfall_toolbar"
    android:layout_width="match_parent"
    android:layout_height="wrap_content">
    
    <android.support.v7.widget.Toolbar
        android:id="@+id/toolbar"
        android:layout_width="match_parent"
        android:layout_height="?actionBarSize"/>
        
</com.hugocastelani.waterfalltoolbar.library.WaterfallToolbar>
```

Your Java code:
```java
public class MainActivity extends AppCompatActivity {
 
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ...
        WaterfallToolbar waterfallToolbar = (WaterfallToolbar) findViewById(R.id.waterfall_toolbar);
        waterfallToolbar.addRecyclerView(mRecyclerView);
    }
    
}
```

Congratulations, you're all set!<br>
Note: you can do whatever you want with your inner Toolbar, Waterfall Toolbar won't interfere.

## Customization
Well, there are people who follow standards and people who create their standards. These last ones can customize three aspects of Waterfall Toolbar.<br>
Note: sample project provides a nice environment to test all these things. Maybe you should give it a try.  

### Initial elevation
The elevation with which the toolbar starts. Default value: 1 dp.<br>
Your Java code:
```java
mWaterfallToolbar.setInitialElevation(0);
```

Result:<br><br>
<img src="/.github/initial.gif" alt="New initial shadow"/>

As you can see, that initial tiny shadow no longer exists.<br>
Note: Waterfall Toolbar extends CardView, and its elevation in taken seriously by Android. If you set elevation as 0 and there's another view below it, Waterfall Toolbar is going to be overlaid. Fortunately, if you set the views' limits properly, you won't have any related trouble.  

### Final elevation
The elevation the toolbar gets when it reaches final scroll elevation. Default value: 6 dp.<br>
Your Java code:
```java
mWaterfallToolbar.setFinalElevation(10);
```

Result:<br><br>
<img src="/.github/final.gif" alt="New final shadow"/>

As result, the final shadow gets much bigger.

### Scroll final position
The percentage of the screen's height that is going to be scrolled to reach the final elevation. Default value: 6%.<br>
Your Java code:
```java
// first gif
mWaterfallToolbar.setScrollFinalPosition(2);
 
// second gif
mWaterfallToolbar.setScrollFinalPosition(20);
```

Result:<br><br>
<img src="/.github/short.gif" alt="Short value to scroll final position"/>
<img src="/.github/long.gif" alt="Long value to scroll final position"/>

Now, the final shadow takes much less and much longer to completely appear, respectively.

## Current project situation
Waterfall Toolbar can be used, but there's one inconvenient bugs yet. I'm going to try to fix it in my spare time.

## Developer
### Contact me
<a href="https://plus.google.com/+HugoCastelaniBP">Google+</a><br>
<a href="https://t.me/HugoCastelani">Telegram</a>

### Support me
HugoCastelaniBP@gmail.com<br>
If you enjoy my work and have a lots of money left over, please support me via PayPal :)<br>

## License
    MIT License
     
    Copyright (c) 2017 Hugo Castelani
     
    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:
     
    The above copyright notice and this permission notice shall be included in all
    copies or substantial portions of the Software.
     
    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
    SOFTWARE.
