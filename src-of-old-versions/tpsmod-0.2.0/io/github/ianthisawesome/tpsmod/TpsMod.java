package io.github.ianthisawesome.tpsmod;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Timer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(
   modid = "tpsmod",
   version = "0.1"
)
public class TpsMod {
   public static final String MODID = "tpsmod";
   public static final String VERSION = "0.1";
   private static final Logger LOGGER = LogManager.getLogger();
   public static double timeToMeasure = 0.5D;

   @EventHandler
   public void preinit(FMLPreInitializationEvent event) {
      LOGGER.info("[TPS Mod]: Preinit");
   }

   @EventHandler
   public void init(FMLInitializationEvent event) {
      LOGGER.info("[TPS Mod]: Init");
   }

   @SideOnly(Side.CLIENT)
   public static void displayThing() {
      (new GuiF3()).func_73863_a(50, 50, 10.0F);
   }

   @EventHandler
   public void postinit(FMLPostInitializationEvent event) {
      LOGGER.info("[TPS Mod]: Postinit");
      Timer timer = new Timer();
      timer.schedule(new TPSCounter(), 0L, (long)(timeToMeasure * 1000.0D));
   }
}
