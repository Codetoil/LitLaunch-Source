package io.github.ianthisawesome.tpsmod;

import java.util.TimerTask;
import net.minecraft.client.Minecraft;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TPSCounter extends TimerTask {
   private static final Logger LOGGER = LogManager.getLogger();
   private long totalWorldTime;
   public static double tps;

   public void run() {
      try {
         try {
            if (Minecraft.func_71410_x().field_71441_e != null) {
               tps = (double)(Minecraft.func_71410_x().field_71441_e.func_72912_H().func_82573_f() - this.totalWorldTime) * Math.pow(TpsMod.timeToMeasure, -1.0D);
               this.totalWorldTime = Minecraft.func_71410_x().field_71441_e.func_72912_H().func_82573_f();
               LOGGER.info(tps + " tps");
            }
         } catch (NullPointerException var2) {
            this.totalWorldTime = Minecraft.func_71410_x().field_71441_e.func_72912_H().func_82573_f();
         } catch (ArithmeticException var3) {
            LOGGER.warn("The Math Broke... an ArithmeticException.");
         } catch (Exception var4) {
            var4.printStackTrace();
         }
      } catch (NullPointerException var5) {
         ;
      }

   }
}
