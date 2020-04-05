package net.minecraft.util;

import com.mojang.blaze3d.platform.GLX;
import com.mojang.blaze3d.platform.GlStateManager;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.resources.SimpleResource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class ScreenShotHelper {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");

   public static void saveScreenshot(File gameDirectory, int width, int height, Framebuffer buffer, Consumer<ITextComponent> p_148260_4_) {
      saveScreenshot(gameDirectory, (String)null, width, height, buffer, p_148260_4_);
   }

   public static void saveScreenshot(File gameDirectory, @Nullable String screenshotName, int width, int height, Framebuffer buffer, Consumer<ITextComponent> p_148259_5_) {
      NativeImage nativeimage = createScreenshot(width, height, buffer);
      File file1 = new File(gameDirectory, "screenshots");
      file1.mkdir();
      File target;
      if (screenshotName == null) {
         target = getTimestampedPNGFileForDirectory(file1);
      } else {
         target = new File(file1, screenshotName);
      }

      try {
         target = target.getCanonicalFile(); // FORGE: Fix errors on Windows with paths that include \.\
      } catch (java.io.IOException e) {}
      net.minecraftforge.client.event.ScreenshotEvent event = net.minecraftforge.client.ForgeHooksClient.onScreenshot(nativeimage, target);
      if (event.isCanceled()) {
         p_148259_5_.accept(event.getCancelMessage());
         return;
      } else {
         target = event.getScreenshotFile();
      }

      final File file2 = target;
      SimpleResource.RESOURCE_IO_EXECUTOR.execute(() -> {
         try {
            nativeimage.write(file2);
            ITextComponent itextcomponent = (new StringTextComponent(file2.getName())).applyTextStyle(TextFormatting.UNDERLINE).applyTextStyle((p_212451_1_) -> {
               p_212451_1_.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_FILE, file2.getAbsolutePath()));
            });
            if (event.getResultMessage() != null) {
               p_148259_5_.accept(event.getResultMessage());
            } else
            p_148259_5_.accept(new TranslationTextComponent("screenshot.success", itextcomponent));
         } catch (Exception exception) {
            LOGGER.warn("Couldn't save screenshot", (Throwable)exception);
            p_148259_5_.accept(new TranslationTextComponent("screenshot.failure", exception.getMessage()));
         } finally {
            nativeimage.close();
         }

      });
   }

   public static NativeImage createScreenshot(int width, int height, Framebuffer framebufferIn) {
      if (GLX.isUsingFBOs()) {
         width = framebufferIn.framebufferTextureWidth;
         height = framebufferIn.framebufferTextureHeight;
      }

      NativeImage nativeimage = new NativeImage(width, height, false);
      if (GLX.isUsingFBOs()) {
         GlStateManager.bindTexture(framebufferIn.framebufferTexture);
         nativeimage.downloadFromTexture(0, true);
      } else {
         nativeimage.downloadFromFramebuffer(true);
      }

      nativeimage.flip();
      return nativeimage;
   }

   private static File getTimestampedPNGFileForDirectory(File gameDirectory) {
      String s = DATE_FORMAT.format(new Date());
      int i = 1;

      while(true) {
         File file1 = new File(gameDirectory, s + (i == 1 ? "" : "_" + i) + ".png");
         if (!file1.exists()) {
            return file1;
         }

         ++i;
      }
   }
}