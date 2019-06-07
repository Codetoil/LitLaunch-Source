package io.github.ianthisawesome.tpsmod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ChunkProviderClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.Entity;
import net.minecraft.profiler.Profiler;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.storage.SaveHandlerMP;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MCThing extends World {
   Logger Log = LogManager.getLogger();
   private Minecraft mc;
   private ChunkProviderClient clientChunkProvider;

   public MCThing(NetHandlerPlayClient p_i45063_1_, WorldSettings p_i45063_2_, int p_i45063_3_, EnumDifficulty p_i45063_4_, Profiler p_i45063_5_) {
      super(new SaveHandlerMP(), "MpServer", WorldProvider.func_76570_a(p_i45063_3_), p_i45063_2_, p_i45063_5_);
      this.Log.info("Creating WorldThing");
   }

   public void func_72835_b() {
      super.func_72835_b();
      this.Log.info("A TICK");
   }

   protected IChunkProvider func_72970_h() {
      this.clientChunkProvider = new ChunkProviderClient(this);
      return this.clientChunkProvider;
   }

   protected int func_152379_p() {
      return this.mc.field_71474_y.field_151451_c;
   }

   public Entity func_73045_a(int p_73045_1_) {
      return this.mc.field_71441_e.func_73045_a(p_73045_1_);
   }
}
