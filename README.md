<img src="/.github/icon.png" width="190px" alt="Icon"/>

## Waterfall Toolbar
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Waterfall%20Toolbar-brightgreen.svg)](https://android-arsenal.com/details/1/6232)
[![JitPack](https://jitpack.io/v/hugocastelani/waterfall-toolbar.svg)](https://jitpack.io/#hugocastelani/waterfall-toolbar)<br>
Made in Kotlin, Waterfall Toolbar is an Android version of Material Design's web component waterfall toolbar. Basically, what it does is dynamize an ordinary Toolbar, increasing and decreasing its shadow when an associated view is scrolled.<br>

## Sample
<a href='https://play.google.com/store/apps/details?id=com.hugocastelani.waterfalltoolbar.sample&pcampaignid=MKT-Other-global-all-co-prtnr-py-PartBadge-Mar2515-1'><img height='60px' alt='Get it on Google Play' src='https://play.google.com/intl/en_us/badges/images/generic/en_badge_web_generic.png'/></a><br>
You can install the <a href="https://raw.githubusercontent.com/hugocastelani/waterfall-toolbar/master/sample.apk">sample.apk</a> to get a better notion of what's going on. Also available at Play Store (click in the badge above).<br><br>
<img src="/.github/sample.gif" alt="Sample" width="270"/>

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
    implementation 'com.github.hugocastelani:waterfall-toolbar:0.5.0'
}
```

## Implementation
Since almost everyone who knows Java knows Kotlin, I'm going to base the implementation in Java, but Kotlin is fully supported.<br>
Implementing Waterfall Toolbar is quite simple. All you gotta do is add it to your layout and refer a RecyclerView or a ScrollView.<br>
Your XML code:
```xml
<com.hugocastelani.waterfalltoolbar.WaterfallToolbar
    android:id="@+id/waterfall_toolbar"
    android:layout_width="match_parent"
    android:layout_height="wrap_content">
    
    <androidx.appcompat.widget.Toolbar
        android:id="@+id/toolbar"
        android:layout_width="match_parent"
        android:layout_height="?actionBarSize"/>
        
</com.hugocastelani.waterfalltoolbar.WaterfallToolbar>
```

Your Java code:
```java
public class MainActivity extends AppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ...
        WaterfallToolbar waterfallToolbar = (WaterfallToolbar) findViewById(R.id.waterfall_toolbar);
        waterfallToolbar.setRecyclerView(recyclerView);
    }
}
```

Congratulations, you're all set!<br>
Note: you can do whatever you want with your inner Toolbar, Waterfall Toolbar won't interfere.

## Customization
Well, there are people who follow standards and people who create their standards. These last ones can customize three aspects of Waterfall Toolbar with Java or XML.

Note: The sample project provides a nice environment to test all of these aspects. Maybe you should give it a try.<br>

### How to use Px and Dp classes
I've created these classes to simplify the conversion between px and dp, which happens often inside WaterfallToolbar class.
Using them is quite simple. If you have a WaterfallToolbar object instantiated in your class, just create a new object passing the intended size:
``` java
final Pixel pixel = new Pixel(50);
```
But if you're using them before instantiating, you have to give your display density:
``` java
DimensionUnitsKt.setDensity(getResources().getDisplayMetrics().density);
```

### Initial elevation
The elevation with which the toolbar starts. Default value: 0 dp. Java and XML implementation, respectively:<br>
``` java
waterfallToolbar.setInitialElevation(new Dp(1).toPx());
```
``` xml
app:initial_elevation="1dp"
```

Result:<br><br>
<img src="/.github/initial.gif" alt="New initial shadow" width="270"/>

As you can see, there's now a tiny initial shadow.<br>
Note: Waterfall Toolbar extends CardView, and its elevation in taken seriously by Android. Since elevation is set to 0dp, if there's another view below it, Waterfall Toolbar is going to be overlaid. Fortunately, if you set the views' limits properly, you won't have any related trouble.  

### Final elevation
The elevation the toolbar gets when it reaches final scroll elevation. Default value: 4 dp. Java and XML implementation, respectively:<br>
``` java
waterfallToolbar.setFinalElevation(new Dp(10).toPx());
```
``` xml
app:final_elevation="10dp"
```

Result:<br><br>
<img src="/.github/final.gif" alt="New final shadow" width="270"/>

As result, the final shadow gets much bigger.

### Scroll final position
The percentage of the screen's height that is going to be scrolled to reach the final elevation. Default value: 12%. Java and XML implementation, respectively:<br>
Short value:<br>
``` java
waterfallToolbar.setScrollFinalPosition(4);
```
``` xml
app:scroll_final_elevation="4"
```

Long value:<br>
``` java
waterfallToolbar.setScrollFinalPosition(40);
```
``` xml
app:scroll_final_elevation="40"
```

Respective results:<br><br>
<img src="/.github/short.gif" alt="Short value to scroll final position" width="270"/>
<img src="/.github/long.gif" alt="Long value to scroll final position" width="270"/>

Now the final shadow takes much less and much longer to completely appear, respectively.

## Current project situation
Waterfall Toolbar is stable, but ListView would come in handy. If you want to contribute, please read <a href="https://github.com/HugoCastelani/waterfall-toolbar/issues/2">this</a>.

## License
    MIT License
     
    Copyright (c) 2019 Hugo Castelani
     
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
