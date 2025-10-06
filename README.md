# Overview

This project was a stab at an idea I've had for a while: A lockscreen background that shows some really cool cosmic elements (elements or stars or whatever) 
animated in real time to reflect the current time from the system as an abstract clock. It was a proper intruction to the technical limitations of the devices I had to work with,
and thus the first working version of it is just a canvas element that some may call a prototype.

[Software Demo Video](https://youtu.be/FAMk8KPp3DQ)

# Development Environment

I used Kotlin's Jetpack Compose, which was the redeeming aspect of this whole thing, and very quick to build with once you get the hang of things. Te best tutorial by far was found in the
official Android website, a great crash course to Composable items, the backbone of Jetpack. Getting Android Studio setup was a tricky task because mine was way outdated and SDKs get specific
when using Jetpack AND canvas animations and took easily over 3 hours of the project.

# Useful Websites

Here were my best references for the app development, my LLM of choice were mostly ChatGPT and some Gemini

- [Android Composable tutorial](https://developer.android.com/develop/ui/compose/tutorial)
- [Land of Coding - Water bottle UI](https://www.youtube.com/watch?v=vmT0SScA2lA)

# Future Work

It was clear in the video that I fell short of my goals, especially because the size of the project was beyond my understanding. Here's a list of improvements and fixes to be done:

- Create and store Canvas elements as individual class objects
- Fix selection menus to keep them from applying changes to elements upon selection
- Align position of elements with current system time
- Fullscreen view if not background option to see the animation
