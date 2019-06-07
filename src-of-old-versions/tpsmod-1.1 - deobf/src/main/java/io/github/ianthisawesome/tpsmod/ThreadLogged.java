package io.github.ianthisawesome.tpsmod;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class ThreadLogged extends Thread {
   protected static final Logger LOGGER = LogManager.getLogger();
   private final String name;
   private final String threadName;

   public ThreadLogged(String name) {
      super(name + " thread");
      this.name = name;
      this.threadName = name + " thread";
      this.logThread();
   }

   public void logThread() {
      LOGGER.info("Starting the " + this.name + " Thread");
   }

   public void exitThread() {
      LOGGER.info("Exiting the " + this.name + " Thread");
   }

   public static void exitThreadDueToThrowable(ThreadLogged thread, Throwable e) {
      LOGGER.error("Exiting the " + thread.getName() + " Thread due to Throwable " + e.toString());
      e.printStackTrace();
   }

   public static void exitThreadDueToException(ThreadLogged thread, Exception e) {
      LOGGER.error("Exiting the " + thread.getName() + " Thread due to Exception " + e.toString());
      e.printStackTrace();
   }

   /** @deprecated */
   @Deprecated
   public void run() {
      try {
         this.runOn();
      } catch (ThreadDeath var2) {
         this.exitThread();
      } catch (Exception var3) {
         exitThreadDueToException(this, var3);
      }

   }

   public abstract void runOn();
}
