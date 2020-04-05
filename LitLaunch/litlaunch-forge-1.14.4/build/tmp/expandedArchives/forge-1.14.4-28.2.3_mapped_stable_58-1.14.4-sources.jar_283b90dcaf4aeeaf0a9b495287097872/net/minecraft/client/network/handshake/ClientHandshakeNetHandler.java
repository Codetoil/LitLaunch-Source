package net.minecraft.client.network.handshake;

import net.minecraft.network.NetworkManager;
import net.minecraft.network.handshake.IHandshakeNetHandler;
import net.minecraft.network.handshake.client.CHandshakePacket;
import net.minecraft.network.login.ServerLoginNetHandler;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ClientHandshakeNetHandler implements IHandshakeNetHandler {
   private final MinecraftServer server;
   private final NetworkManager networkManager;

   public ClientHandshakeNetHandler(MinecraftServer mcServerIn, NetworkManager networkManagerIn) {
      this.server = mcServerIn;
      this.networkManager = networkManagerIn;
   }

   public void processHandshake(CHandshakePacket packetIn) {
      if (!net.minecraftforge.fml.server.ServerLifecycleHooks.handleServerLogin(packetIn, this.networkManager)) return;
      this.networkManager.setConnectionState(packetIn.getRequestedState());
      this.networkManager.setNetHandler(new ServerLoginNetHandler(this.server, this.networkManager));
   }

   public void onDisconnect(ITextComponent reason) {
   }

   public NetworkManager getNetworkManager() {
      return this.networkManager;
   }
}