package io.github.ianthisawesome.tpsmod.commands;

import io.github.ianthisawesome.tpsmod.TpsMod;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;

public class CommandALLTPS implements ICommand {
   private final List aliases = new ArrayList();

   public CommandALLTPS() {
      this.aliases.add("tpstoall");
      this.aliases.add("alltps");
   }

   public String func_71517_b() {
      return "tpstoall";
   }

   public String func_71518_a(ICommandSender p_71518_1_) {
      return "message.tpsmod.alltps.help";
   }

   public void func_71515_b(ICommandSender p_71515_1_, String[] p_71515_2_) {
      TpsMod.theWorldLogger.getChatMessage.doCommandStuffCommandALLTPS(p_71515_1_, p_71515_2_);
   }

   public int compareTo(Object arg0) {
      return 0;
   }

   public List func_71514_a() {
      return this.aliases;
   }

   public boolean func_71519_b(ICommandSender p_71519_1_) {
      return true;
   }

   public List func_71516_a(ICommandSender p_71516_1_, String[] p_71516_2_) {
      List tabCompletions = new ArrayList();
      tabCompletions.add("help");
      tabCompletions.add("?");
      return tabCompletions;
   }

   public boolean func_82358_a(String[] p_82358_1_, int p_82358_2_) {
      return false;
   }
}
