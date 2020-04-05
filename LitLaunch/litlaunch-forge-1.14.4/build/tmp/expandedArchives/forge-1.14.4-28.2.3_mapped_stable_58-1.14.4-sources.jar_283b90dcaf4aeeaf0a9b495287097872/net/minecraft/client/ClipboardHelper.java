package net.minecraft.client;

import java.nio.ByteBuffer;
import net.minecraft.util.SharedConstants;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWErrorCallbackI;
import org.lwjgl.system.MemoryUtil;

@OnlyIn(Dist.CLIENT)
public class ClipboardHelper {
   private final ByteBuffer buffer = ByteBuffer.allocateDirect(1024);

   public String getClipboardString(long window, GLFWErrorCallbackI errorCallback) {
      GLFWErrorCallback glfwerrorcallback = GLFW.glfwSetErrorCallback(errorCallback);
      String s = GLFW.glfwGetClipboardString(window);
      s = s != null ? SharedConstants.func_215070_b(s) : "";
      GLFWErrorCallback glfwerrorcallback1 = GLFW.glfwSetErrorCallback(glfwerrorcallback);
      if (glfwerrorcallback1 != null) {
         glfwerrorcallback1.free();
      }

      return s;
   }

   private void setClipboardString(long window, ByteBuffer buffer, String string) {
      MemoryUtil.memUTF8(string, true, buffer);
      GLFW.glfwSetClipboardString(window, buffer);
   }

   public void setClipboardString(long window, String string) {
      int i = MemoryUtil.memLengthUTF8(string, true);
      if (i < this.buffer.capacity()) {
         this.setClipboardString(window, this.buffer, string);
         this.buffer.clear();
      } else {
         ByteBuffer bytebuffer = ByteBuffer.allocateDirect(i);
         this.setClipboardString(window, bytebuffer, string);
      }

   }
}