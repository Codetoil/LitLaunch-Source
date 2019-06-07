package io.github.ianthisawesome.tpsmod;

import net.minecraft.client.Minecraft;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetTPS {
   protected static final Logger LOGGER = LogManager.getLogger();
   private long previousTotalWorldTime = -1L;
   private double previousMeasureTime = -1.0D;
   public static double tps = -1.0D;
   public static final double nanotimediv = 1.0E9D;
   private static String mostRecentChatMessage = null;

   public GetTPS(String name) {
   }

   public String getMostRecentChatMessage() {
      return (String)Minecraft.func_71410_x().field_71456_v.func_146158_b().func_146238_c().get(Minecraft.func_71410_x().field_71456_v.func_146158_b().func_146238_c().size() - 1);
   }

   public void doCommandStuffCommandTPS(ICommandSender sender, String[] argsString) {
      if (argsString.length != 1 || !argsString[0].equals("help") && !argsString[0].equals("?")) {
         if (this.previousTotalWorldTime == -1L) {
            this.updatePreviousTotalWorldTime(Minecraft.func_71410_x().field_71441_e);
         }

         this.updateTPS(Minecraft.func_71410_x().field_71441_e);
         sender.func_145747_a((new ChatComponentTranslation("message.tpsmod.tps", new Object[]{tps})).func_150255_a((new ChatStyle()).func_150238_a(EnumChatFormatting.YELLOW)));
      } else {
         sender.func_145747_a(new ChatComponentTranslation("message.tpsmod.tps.help", new Object[0]));
      }
   }

   public void doCommandStuffCommandALLTPS(ICommandSender sender, String[] argsString) {
      if (argsString.length != 1 || !argsString[0].equals("help") && !argsString[0].equals("?")) {
         if (this.previousTotalWorldTime == -1L) {
            this.updatePreviousTotalWorldTime(Minecraft.func_71410_x().field_71441_e);
         }

         this.updateTPS(Minecraft.func_71410_x().field_71441_e);
         Minecraft.func_71410_x().field_71439_g.func_71165_d(((EntityPlayer)sender).getDisplayName() + " measured the tps using the TPS Mod by Littoil, and got a tps of " + tps.toString());
      } else {
         sender.func_145747_a(new ChatComponentTranslation("message.tpsmod.alltps.help", new Object[0]));
      }
   }

   public void updatePreviousTotalWorldTime(World worldIn) {
      try {
         if (worldIn.func_82737_E() == 0L) {
            Thread.sleep(500L);
            if (worldIn.func_82737_E() == 0L) {
               Thread.sleep(1000L);
               if (worldIn.func_82737_E() == 0L) {
                  LOGGER.warn("The TotalWorldTime is 0 after waiting for the TotalWorldTime");
                  LOGGER.warn("Please tweet me to @littoil on twitter or email me at ianthisawesomep@gmail.com and tell me about this!");
               }
            }
         }
      } catch (InterruptedException var3) {
         var3.printStackTrace();
      }

      this.previousTotalWorldTime = worldIn.func_82737_E();
      this.previousMeasureTime = getTimeInSeconds();
   }

   public void updateTPS(World worldIn) {
      if (tps != -1.0D) {
         tps = (double)(worldIn.func_82737_E() - this.previousTotalWorldTime) / (getTimeInSeconds() - this.previousMeasureTime);
      }

      this.updatePreviousTotalWorldTime(worldIn);
      if (tps == -1.0D) {
         tps = (double)(worldIn.func_82737_E() - this.previousTotalWorldTime) / (getTimeInSeconds() - this.previousMeasureTime);
      }

   }

   public static double getTimeInSeconds() {
      return (double)System.nanoTime() / 1.0E9D;
   }
}
