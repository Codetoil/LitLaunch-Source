package io.github.ianthisawesome.tpsmod;

import java.util.TimerTask;
import net.minecraft.client.Minecraft;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CheckedLoggedIn extends TimerTask {
   private static final Logger LOGGER = LogManager.getLogger();
   public static String worldName;
   public static String worldNameOld;
   public static boolean onWorld = false;

   public void run() {
      try {
         worldName = Minecraft.func_71410_x().field_71441_e.func_72912_H().func_76065_j();
         LOGGER.info(Long.valueOf(Minecraft.func_71410_x().field_71441_e.func_72912_H().func_82573_f()));
         if (!worldName.isEmpty() && !onWorld) {
            this.joinWorld();
         }

         onWorld = true;
      } catch (NullPointerException var4) {
         if (onWorld) {
            this.exitWorld();
         }

         onWorld = false;
      }

      try {
         worldNameOld = worldName;
      } catch (NullPointerException var3) {
         ;
      }

      try {
         Thread.sleep(2000L);
      } catch (InterruptedException var2) {
         var2.printStackTrace();
      }

   }

   public void joinWorld() {
      LOGGER.info("Joined World " + worldName);

      try {
         TpsMod.displayThing();
      } catch (NullPointerException var2) {
         LOGGER.error("The image doesn't want to display... :P");
      } catch (RuntimeException var3) {
         LOGGER.info("Broken OpenGL!");
      }

   }

   public void exitWorld() {
      LOGGER.info("Left World " + worldNameOld);
   }
}
