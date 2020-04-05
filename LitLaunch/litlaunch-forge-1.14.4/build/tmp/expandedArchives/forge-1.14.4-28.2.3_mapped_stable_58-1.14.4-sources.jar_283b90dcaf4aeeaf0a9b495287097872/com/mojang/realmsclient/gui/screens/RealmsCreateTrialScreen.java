package com.mojang.realmsclient.gui.screens;

import com.mojang.realmsclient.RealmsMainScreen;
import com.mojang.realmsclient.util.RealmsTasks;
import net.minecraft.realms.Realms;
import net.minecraft.realms.RealmsButton;
import net.minecraft.realms.RealmsEditBox;
import net.minecraft.realms.RealmsScreen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RealmsCreateTrialScreen extends RealmsScreen {
   private final RealmsMainScreen field_224204_a;
   private RealmsEditBox field_224205_b;
   private RealmsEditBox field_224206_c;
   private boolean field_224207_d;
   private RealmsButton field_224208_e;

   public RealmsCreateTrialScreen(RealmsMainScreen p_i51771_1_) {
      this.field_224204_a = p_i51771_1_;
   }

   public void tick() {
      if (this.field_224205_b != null) {
         this.field_224205_b.tick();
         this.field_224208_e.active(this.func_224203_b());
      }

      if (this.field_224206_c != null) {
         this.field_224206_c.tick();
      }

   }

   public void init() {
      this.setKeyboardHandlerSendRepeatsToGui(true);
      if (!this.field_224207_d) {
         this.field_224207_d = true;
         this.field_224205_b = this.newEditBox(3, this.width() / 2 - 100, 65, 200, 20, getLocalizedString("mco.configure.world.name"));
         this.focusOn(this.field_224205_b);
         this.field_224206_c = this.newEditBox(4, this.width() / 2 - 100, 115, 200, 20, getLocalizedString("mco.configure.world.description"));
      }

      this.buttonsAdd(this.field_224208_e = new RealmsButton(0, this.width() / 2 - 100, this.height() / 4 + 120 + 17, 97, 20, getLocalizedString("mco.create.world")) {
         public void onPress() {
            RealmsCreateTrialScreen.this.func_224202_a();
         }
      });
      this.buttonsAdd(new RealmsButton(1, this.width() / 2 + 5, this.height() / 4 + 120 + 17, 95, 20, getLocalizedString("gui.cancel")) {
         public void onPress() {
            Realms.setScreen(RealmsCreateTrialScreen.this.field_224204_a);
         }
      });
      this.field_224208_e.active(this.func_224203_b());
      this.addWidget(this.field_224205_b);
      this.addWidget(this.field_224206_c);
   }

   public void removed() {
      this.setKeyboardHandlerSendRepeatsToGui(false);
   }

   public boolean charTyped(char p_charTyped_1_, int p_charTyped_2_) {
      this.field_224208_e.active(this.func_224203_b());
      return false;
   }

   public boolean keyPressed(int p_keyPressed_1_, int p_keyPressed_2_, int p_keyPressed_3_) {
      switch(p_keyPressed_1_) {
      case 256:
         Realms.setScreen(this.field_224204_a);
         return true;
      default:
         this.field_224208_e.active(this.func_224203_b());
         return false;
      }
   }

   private void func_224202_a() {
      if (this.func_224203_b()) {
         RealmsTasks.TrialCreationTask realmstasks$trialcreationtask = new RealmsTasks.TrialCreationTask(this.field_224205_b.getValue(), this.field_224206_c.getValue(), this.field_224204_a);
         RealmsLongRunningMcoTaskScreen realmslongrunningmcotaskscreen = new RealmsLongRunningMcoTaskScreen(this.field_224204_a, realmstasks$trialcreationtask);
         realmslongrunningmcotaskscreen.func_224233_a();
         Realms.setScreen(realmslongrunningmcotaskscreen);
      }

   }

   private boolean func_224203_b() {
      return this.field_224205_b != null && this.field_224205_b.getValue() != null && !this.field_224205_b.getValue().trim().isEmpty();
   }

   public void render(int p_render_1_, int p_render_2_, float p_render_3_) {
      this.renderBackground();
      this.drawCenteredString(getLocalizedString("mco.trial.title"), this.width() / 2, 11, 16777215);
      this.drawString(getLocalizedString("mco.configure.world.name"), this.width() / 2 - 100, 52, 10526880);
      this.drawString(getLocalizedString("mco.configure.world.description"), this.width() / 2 - 100, 102, 10526880);
      if (this.field_224205_b != null) {
         this.field_224205_b.render(p_render_1_, p_render_2_, p_render_3_);
      }

      if (this.field_224206_c != null) {
         this.field_224206_c.render(p_render_1_, p_render_2_, p_render_3_);
      }

      super.render(p_render_1_, p_render_2_, p_render_3_);
   }
}