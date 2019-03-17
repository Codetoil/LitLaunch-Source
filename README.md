# tpsmodsrc
This is the source to the TPS Mod. This is currently only the dev source, sorry! Once the new update is released, I may 
be able to have a special branch for non-dev. That's for later though! (or I may put it as a release if that includes
 the code.)

## install dev enviorment
**INCOMPLETE, WILL COMPLETE LATER**<br><br>
To start, first create a folder named "tpsmod" anywhere, and then clone the master branch into a new folder "tpsmodsrc" within tpsmod with:

    git clone "https://github.com/Littoil/TPS-Mod-Source.git" tpsmodsrc

After that's done, create a new folder inside "tpsmod" called "versions", then, typing

(for both Windows and UNIX)
    
    mkdir versions
    cd versionss
    git clone -b mc1.13 "https://github.com/Littoil/TPS-Mod-Source.git" mc1.7
    git clone -b mc1.13 "https://github.com/Littoil/TPS-Mod-Source.git" mc1.8
    git clone -b mc1.13 "https://github.com/Littoil/TPS-Mod-Source.git" mc1.12
    git clone -b mc1.13 "https://github.com/Littoil/TPS-Mod-Source.git" mc1.13

Use the forge sdks for 1.7.10, 1.8, 1.12, and 1.13 in they're respective folders("mc1.7", etc.), skipping overriding the build.gradles when extracting the zip

Now open up your ide:

###Eclipse
####Gradle
After opening the project, first:
 
-Open the project properties (Right click the project in eclipse and then click properties)

-Click "Java Build Path"

-Click the tab "Source"

-Remove all sources except the tpsmodsrc source.



# Pull request format and acceptability
Before pull requesting, please change all extensions of java-type files back to ".java". Make sure the new workspace compiles for all four versions!

Once reviewed, the pull request might be accepted or declined based on whether the code is with the rules of the mod.
## the rules of the mod
-Don't change the code to:

-harm someone

-harm a group of people

-harm the public
	
-Don't change the code to give yourself an advantage over someone else in any way, besides the ability to see the tps. I'm not making this mod a cheat mod!

-All changes in the code must not change anything gameplaywise. Again, I'm not making this mod a cheat mod!

###This is not an inclusive list and I may have rules not written down here.

## Free speech - But keep on topic.
In the discussion pages, you may speak whatever you want, as long as it complies to the topic. This allows for naughty language in the discussion pages. People who are off topic may be punished though, so stay on topic!
The free speach statement can be overriden by Github's own rules if it has any against free speech, though off-topic-ness is still punished.

Thank you and have fun modding!

##Discord

https://discord.gg/XgrbTjm
