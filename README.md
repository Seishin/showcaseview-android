# ShowcaseView for Android
It's a small library for showcase and highlight specific parts of your app to the user. 

![alt tag](https://raw.githubusercontent.com/Seishin/showcase-tutorial/master/Images/Screenshot_1.png) ![alt tag](https://raw.githubusercontent.com/Seishin/showcase-tutorial/master/Images/Screenshot_2.png)

### Usage
It's very simple to create a showcase with the Builder Pattern:
```java
new ShowcaseView.Builder(this)
    .setTarget(target)
    .setDescription("This is the super-duper-mega-cool app! Click on this button to start it now!",
            ItemPosition.TOP_CENTER)
    .setButton(ItemPosition.BOTTOM_CENTER)
    .build();
```

#### Customizing with custom defined styles
The only thing you have to do is to define a new style and pass it to the Builder.
For example we'll change the background color of the ShowcaseView as well as the text size of the button and its background.

```xml
<style name="CustomShowcaseViewTheme" parent="ShowcaseView">
    <item name="showcase_background">#F0000000</item>

    <!-- Target -->
    <item name="showcase_target_border_color">#96000000</item>

    <!-- Button -->
    <item name="showcase_btn_text_size">23sp</item>
    <item name="showcase_btn_background">#B4FFFFFF</item>
</style>
```

Then the only thing we have to do is to pass the new style to the ShowcaseView Builder
```java
new ShowcaseView.Builder(this)
    .setTarget(target)
    .setDescription("Showcase with custom theme! Yahoo! :)", PositionsUtil.ItemPosition.CENTER)
    .setButton(PositionsUtil.ItemPosition.TOP_CENTER)
    .setCustomTheme(R.style.CustomShowcaseViewTheme)
    .build();
```

### Sample Project
There is a sample project included.

### Contributions
Everyone could contribute to this project. :)