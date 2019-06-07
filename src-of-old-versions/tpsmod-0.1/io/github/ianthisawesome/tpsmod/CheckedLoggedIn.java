package io.github.ianthisawesome.tpsmod;

import java.util.TimerTask;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
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
         if (worldName != null && !onWorld) {
            this.joinWorld(Minecraft.func_71410_x().field_71441_e);
         }

         onWorld = true;
      } catch (NullPointerException var3) {
         if (onWorld) {
            this.exitWorld();
         }

         onWorld = false;
      }

      try {
         worldNameOld = worldName;
      } catch (NullPointerException var2) {
         ;
      }

   }

   public void joinWorld(WorldClient theWorld) {
      LOGGER.info("Joined World " + worldName);
   }

   public void exitWorld() {
      LOGGER.info("Left World " + worldNameOld);
   }
}
