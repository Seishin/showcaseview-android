# ShowcaseView for Android
It's a small library for showcase and highlight specific parts of your app to the user. 

![alt tag](https://raw.githubusercontent.com/Seishin/showcase-tutorial/master/Images/Screenshot_1.png) ![alt tag](https://raw.githubusercontent.com/Seishin/showcase-tutorial/master/Images/Screenshot_2.png)

### Usage
It's very simple to create a showcase with the Builder Pattern:
```java
ShowcaseTutorial showcaseTutorial = new ShowcaseTutorial.Builder(this)
        .setTarget(new TargetView(buttonView, ShowcaseType.CIRCLE))
        .setDescription("Sample Description",ItemPosition.TOP_CENTER)
        .setButton("Button Text", ItemPosition.BOTTOM_CENTER)
        .build();
```

### Sample Project
There is a sample project included.
