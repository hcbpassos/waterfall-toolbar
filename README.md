<img src="/.github/icon.png" width="190px" alt="Icon"/>

## Waterfall Toolbar
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Waterfall%20Toolbar-brightgreen.svg)](https://android-arsenal.com/details/1/6232)
[![JitPack](https://jitpack.io/v/hugocastelani/waterfall-toolbar.svg)](https://jitpack.io/#hugocastelani/waterfall-toolbar)
[![License](https://img.shields.io/github/license/hugocastelani/waterfall-toolbar.svg)](https://github.com/hugocastelani/waterfall-toolbar/blob/master/LICENSE)<br>
Waterfall Toolbar is an Android version of Material Design's web component waterfall toolbar. Basically, what it does is dynamize an ordinary Toolbar, increasing and decreasing its shadow when an associated view is scrolled.<br>
You can download the <a href="https://raw.githubusercontent.com/hugocastelani/waterfall-toolbar/master/sample.apk">sample.apk</a> to get a better notion of what's going on.<br><br>
<iframe src="https://giphy.com/embed/3o6nV9SerSYfR05nQk" width="270" height="480" frameBorder="0" class="giphy-embed" allowFullScreen></iframe><p><a href="https://giphy.com/gifs/3o6nV9SerSYfR05nQk">via GIPHY</a></p>

## Setup
### Gradle dependency
Waterfall Toolbar is available on <a href="https://jitpack.io/#hugocastelani/waterfall-toolbar">Jitpack</a>.<br>
To add this library to your project, write the code below in your root (not module) ``build.gradle`` file:
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
    implementation 'com.github.hugocastelani:waterfall-toolbar:0.3.1'
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
Well, there are people who follow standards and people who create their standards. These last ones can customize three aspects of Waterfall Toolbar with Java or XML.<br>
Note: sample project provides a nice environment to test all these things. Maybe you should give it a try.  

### Initial elevation
The elevation with which the toolbar starts. Default value: 0 dp.<br>
Through Java: `waterfallToolbar.setInitialElevationDp(1);`<br>
Through XML: `app:initial_elevation="1dp"`

Result:<br><br>
<img src="/.github/initial.gif" alt="New initial shadow" width="270"/>

As you can see, there's now a tiny initial shadow.<br>
Note: Waterfall Toolbar extends CardView, and its elevation in taken seriously by Android. Since elevation is set to 0dp, if there's another view below it, Waterfall Toolbar is going to be overlaid. Fortunately, if you set the views' limits properly, you won't have any related trouble.  

### Final elevation
The elevation the toolbar gets when it reaches final scroll elevation. Default value: 4 dp.<br>
Through Java: `waterfallToolbar.setFinalElevationDp(10);`<br>
Through XML: `app:final_elevation="10dp"`

Result:<br><br>
<img src="/.github/final.gif" alt="New final shadow" width="270"/>

As result, the final shadow gets much bigger.

### Scroll final position
The percentage of the screen's height that is going to be scrolled to reach the final elevation. Default value: 6%.<br>
Short value:<br>
Through Java: `waterfallToolbar.setScrollFinalPosition(2);`<br>
Through XML: `app:scroll_final_elevation="2"`

Long value:<br>
Through Java: `waterfallToolbar.setScrollFinalPosition(20);`<br>
Through XML: `app:scroll_final_elevation="20"`

Respective results:<br><br>
<img src="/.github/short.gif" alt="Short value to scroll final position" width="270"/>
<img src="/.github/long.gif" alt="Long value to scroll final position" width="270"/>

Now the final shadow takes much less and much longer to completely appear, respectively.

## Current project situation
Waterfall Toolbar is stable, but ListView would come in handy. If you want to contribute, please read <a href="https://github.com/HugoCastelani/waterfall-toolbar/issues/2">this</a>.

## Developer
### Contact me
<a href="https://plus.google.com/+HugoCastelaniBP">Google+</a><br>
<a href="https://t.me/HugoCastelani">Telegram</a>

### Support me
HugoCastelaniBP@gmail.com<br>
If you enjoy my work and have lots of money left over, please support me via PayPal using the email above :)<br>

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
