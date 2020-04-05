package net.minecraft.block;

import java.util.Random;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class MyceliumBlock extends SpreadableSnowyDirtBlock {
   public MyceliumBlock(Block.Properties properties) {
      super(properties);
   }

   @OnlyIn(Dist.CLIENT)
   public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
      super.animateTick(stateIn, worldIn, pos, rand);
      if (rand.nextInt(10) == 0) {
         worldIn.addParticle(ParticleTypes.MYCELIUM, (double)((float)pos.getX() + rand.nextFloat()), (double)((float)pos.getY() + 1.1F), (double)((float)pos.getZ() + rand.nextFloat()), 0.0D, 0.0D, 0.0D);
      }

   }
}