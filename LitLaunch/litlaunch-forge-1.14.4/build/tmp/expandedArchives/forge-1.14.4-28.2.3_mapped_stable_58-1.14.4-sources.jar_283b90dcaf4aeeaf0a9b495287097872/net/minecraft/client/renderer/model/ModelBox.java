package net.minecraft.client.renderer.model;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ModelBox {
   private final PositionTextureVertex[] vertices;
   private final TexturedQuad[] quads;
   public final float posX1;
   public final float posY1;
   public final float posZ1;
   public final float posX2;
   public final float posY2;
   public final float posZ2;
   public String boxName;

   public ModelBox(RendererModel renderer, int texU, int texV, float x, float y, float z, int dx, int dy, int dz, float delta) {
      this(renderer, texU, texV, x, y, z, dx, dy, dz, delta, renderer.mirror);
   }

   public ModelBox(RendererModel renderer, int texU, int texV, float x, float y, float z, int dx, int dy, int dz, float delta, boolean mirror) {
      this.posX1 = x;
      this.posY1 = y;
      this.posZ1 = z;
      this.posX2 = x + (float)dx;
      this.posY2 = y + (float)dy;
      this.posZ2 = z + (float)dz;
      this.vertices = new PositionTextureVertex[8];
      this.quads = new TexturedQuad[6];
      float f = x + (float)dx;
      float f1 = y + (float)dy;
      float f2 = z + (float)dz;
      x = x - delta;
      y = y - delta;
      z = z - delta;
      f = f + delta;
      f1 = f1 + delta;
      f2 = f2 + delta;
      if (mirror) {
         float f3 = f;
         f = x;
         x = f3;
      }

      PositionTextureVertex positiontexturevertex7 = new PositionTextureVertex(x, y, z, 0.0F, 0.0F);
      PositionTextureVertex positiontexturevertex = new PositionTextureVertex(f, y, z, 0.0F, 8.0F);
      PositionTextureVertex positiontexturevertex1 = new PositionTextureVertex(f, f1, z, 8.0F, 8.0F);
      PositionTextureVertex positiontexturevertex2 = new PositionTextureVertex(x, f1, z, 8.0F, 0.0F);
      PositionTextureVertex positiontexturevertex3 = new PositionTextureVertex(x, y, f2, 0.0F, 0.0F);
      PositionTextureVertex positiontexturevertex4 = new PositionTextureVertex(f, y, f2, 0.0F, 8.0F);
      PositionTextureVertex positiontexturevertex5 = new PositionTextureVertex(f, f1, f2, 8.0F, 8.0F);
      PositionTextureVertex positiontexturevertex6 = new PositionTextureVertex(x, f1, f2, 8.0F, 0.0F);
      this.vertices[0] = positiontexturevertex7;
      this.vertices[1] = positiontexturevertex;
      this.vertices[2] = positiontexturevertex1;
      this.vertices[3] = positiontexturevertex2;
      this.vertices[4] = positiontexturevertex3;
      this.vertices[5] = positiontexturevertex4;
      this.vertices[6] = positiontexturevertex5;
      this.vertices[7] = positiontexturevertex6;
      this.quads[0] = new TexturedQuad(new PositionTextureVertex[]{positiontexturevertex4, positiontexturevertex, positiontexturevertex1, positiontexturevertex5}, texU + dz + dx, texV + dz, texU + dz + dx + dz, texV + dz + dy, renderer.textureWidth, renderer.textureHeight);
      this.quads[1] = new TexturedQuad(new PositionTextureVertex[]{positiontexturevertex7, positiontexturevertex3, positiontexturevertex6, positiontexturevertex2}, texU, texV + dz, texU + dz, texV + dz + dy, renderer.textureWidth, renderer.textureHeight);
      this.quads[2] = new TexturedQuad(new PositionTextureVertex[]{positiontexturevertex4, positiontexturevertex3, positiontexturevertex7, positiontexturevertex}, texU + dz, texV, texU + dz + dx, texV + dz, renderer.textureWidth, renderer.textureHeight);
      this.quads[3] = new TexturedQuad(new PositionTextureVertex[]{positiontexturevertex1, positiontexturevertex2, positiontexturevertex6, positiontexturevertex5}, texU + dz + dx, texV + dz, texU + dz + dx + dx, texV, renderer.textureWidth, renderer.textureHeight);
      this.quads[4] = new TexturedQuad(new PositionTextureVertex[]{positiontexturevertex, positiontexturevertex7, positiontexturevertex2, positiontexturevertex1}, texU + dz, texV + dz, texU + dz + dx, texV + dz + dy, renderer.textureWidth, renderer.textureHeight);
      this.quads[5] = new TexturedQuad(new PositionTextureVertex[]{positiontexturevertex3, positiontexturevertex4, positiontexturevertex5, positiontexturevertex6}, texU + dz + dx + dz, texV + dz, texU + dz + dx + dz + dx, texV + dz + dy, renderer.textureWidth, renderer.textureHeight);
      if (mirror) {
         for(TexturedQuad texturedquad : this.quads) {
            texturedquad.flipFace();
         }
      }

   }

   public void render(BufferBuilder renderer, float scale) {
      for(TexturedQuad texturedquad : this.quads) {
         texturedquad.draw(renderer, scale);
      }

   }

   public ModelBox setBoxName(String name) {
      this.boxName = name;
      return this;
   }
}