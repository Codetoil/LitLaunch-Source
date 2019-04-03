# TPS Mod Source
This is the source to the TPS Mod. This is currently only the dev source, sorry! Once the new update is released, I may 
be able to have a special branch for non-dev. That's for later though! (or I may put it as a release if that includes
 the code.)

## install dev enviorment
**INCOMPLETE, WILL COMPLETE LATER**<br><br>
To start, create a new folder, further more called the tpsmod root This will be the workspace directory of the project. 
To start, open up the command promt/terminal in the tpsmod root and execute within the folder to get the main code:

    git clone "https://github.com/Littoil/TPS-Mod-Source.git" tpsmodsrc

After that's done, execute this in the same folder for the unique setups required for each version:

    git clone -b versions "https://github.com/Littoil/TPS-Mod-Source.git" versions

This will create a sub-directory called `versions` inside the root, and inside a folder called forge with many subdirectories
inside.

Afterwards, go to the official minecraft forge download page (https://files.minecraftforge.net/maven/net/minecraftforge/forge)
 and select versions 1.7.\*, 1.8.\*, 1.12.\*, and 1.13.\*. Download the mdks for each(called src for 1.7). Don't click
 download on the ad if you don't know already. Wait until `skip` button appears and press that button. I recommend that
 you close the adfly tab afterwards.
 
Now, from the zip files downloaded, extract the 1.7.\* one into the mc1.7 subfolder in the project versions folder.
Do this for 1.8.\*, 1.12.\*, and 1.13.\*

However, make sure to **<u>NOT OVERRIDE THE BUILD.GRADLES!!!</u>**<br><br>The build.gradles contain some important code
for the project to build (or find the source at all) 

Now, initialize the environments as is listed in the `README.txt`s.

If you don't use an ide, you are complete, no additional setup needed. The source is in the tpsmodsrc folder within the
tpsmod root. It is also where you push/pull the project code.

However, IntelliJ nor Eclipse doesn't appear to auto-exclude packages mentioned in the build.gradle. So here's how you do it:

### IntelliJ

To exclude a package in IntelliJ, go to:

<u>File</u>-><u>Project Structure</u>

Select *Modules*

Select *Sources*

Under the module you want to exclude packages in (in this case `io.github.littoil.litlaunch.versions.mc1.*.main`), find the package
in the hierarchy that opens, select it and press <u>`Excluded`</u>s

In this case, exclude all versions other than the minecraft version you're on. 
<ul>
    <li>For mc1.13, exclude m1.12, mc1.7, and mc1.8.</li>
    <li>For mc1.12, exclude mc1.13, mc1.7, and mc1.8</li>
    <li>For mc1.7, exclude mc1.8, mc1.12, and mc1.13</li>
</ul>
...etc.
<br>
<br>

You must also exclude `io.github.littoil.litlaunch.sponge` because it contains code incompatable with forge.

### Eclipse

Create a workspace in `[tpsmodroot]`
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

### Discord
https://discord.gg/XgrbTjm
