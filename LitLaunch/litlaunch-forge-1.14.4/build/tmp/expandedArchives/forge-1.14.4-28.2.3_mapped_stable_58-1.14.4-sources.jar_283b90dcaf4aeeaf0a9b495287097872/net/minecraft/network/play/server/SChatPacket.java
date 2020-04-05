package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SChatPacket implements IPacket<IClientPlayNetHandler> {
   private ITextComponent chatComponent;
   private ChatType type;

   public SChatPacket() {
   }

   public SChatPacket(ITextComponent componentIn) {
      this(componentIn, ChatType.SYSTEM);
   }

   public SChatPacket(ITextComponent message, ChatType type) {
      this.chatComponent = message;
      this.type = type;
   }

   public void readPacketData(PacketBuffer buf) throws IOException {
      this.chatComponent = buf.readTextComponent();
      this.type = ChatType.byId(buf.readByte());
   }

   public void writePacketData(PacketBuffer buf) throws IOException {
      buf.writeTextComponent(this.chatComponent);
      buf.writeByte(this.type.getId());
   }

   public void processPacket(IClientPlayNetHandler handler) {
      handler.handleChat(this);
   }

   @OnlyIn(Dist.CLIENT)
   public ITextComponent getChatComponent() {
      return this.chatComponent;
   }

   public boolean isSystem() {
      return this.type == ChatType.SYSTEM || this.type == ChatType.GAME_INFO;
   }

   public ChatType getType() {
      return this.type;
   }

   public boolean shouldSkipErrors() {
      return true;
   }
}