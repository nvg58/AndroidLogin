# AndroidLogin
Android Login using ParseLoginUI and MaterialNavigationDrawer

## ParseLoginUI
1. [ParseUI-Android](https://github.com/ParsePlatform/ParseUI-Android) is used to rapidly implement Login/Logout with Email/Facebook using [Parse.com](https://parse.com/) service.
2. I have custom some ui items from the sample: [ParseLoginSampleWithDispatchActivity](https://github.com/ParsePlatform/ParseUI-Android/tree/master/ParseLoginSampleWithDispatchActivity).
3. You can also implement Login/Logout by yourself following the guide [here](https://parse.com/docs/android_guide).
4. Please read the [README.md](https://github.com/ParsePlatform/ParseUI-Android/blob/master/README.md) carefully to make sure configure ParseLoginUI correctly.

## MaterialNavigationDrawer
1. [MaterialNavigationDrawer](https://github.com/neokree/MaterialNavigationDrawer) is used to create a [Navigation Drawer](https://developer.android.com/design/patterns/navigation-drawer.html) with Android L style.
2. Please take a look at [README.md](https://github.com/neokree/MaterialNavigationDrawer/blob/master/README.md) and [Wiki](https://github.com/neokree/MaterialNavigationDrawer/wiki) for further information.
3. You can also implement this feature by following [Creating a Navigation Drawer](http://developer.android.com/training/implementing-navigation/nav-drawer.html) for pre-Lollipop or [Navigation Drawer tutorials for Lollipop](https://www.youtube.com/watch?v=zWpEh9k8i7Q&index=6&list=PLonJJ3BVjZW6CtAMbJz1XD8ELUs1KXaTD)


## Others
1. Main functions of application is implemented on [FragmentListReasons.java](https://github.com/nvg58/AndroidLogin/blob/master/app/src/main/java/com/example/giapnv/androidlogin/fragment/FragmentListReasons.java)
2. Use `ListFragment` to make sure `Navigation Drawer` still works when user interact(scroll/tick/untick) with the list view.
3. `SubmitTask` is a `AsyncTask` to submit user's choosen to Parse.com cloud.

## Limitations
1. Get cover and avatar from user's Facebook account when they login in by Facebook.
2. Dummy data.

## Quick Demo
1. [APK file](https://drive.google.com/file/d/0BwoFGspF6fsIM1lmYW1KbFJmUWM/view?usp=sharing)
2. User: testzzz@gmail.com / Password: 123456

## Tools
Project is created by Android Studio 1.1.0 and has been tested on Android 5.0.
  
