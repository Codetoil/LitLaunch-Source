package io.github.ianthisawesome.tpsmod;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;

@SideOnly(Side.CLIENT)
public class GuiF3 extends GuiScreen
{
	public void func_73866_w_()
	{
	}

	public void func_73863_a(int mouseX, int mouseY, float partialTicks)
	{
		this.func_73733_a(0, 0, this.field_146294_l, this.field_146295_m, -1072689136, -804253680);
		this.func_73732_a(this.field_146289_q, "Our First Centered String!", this.field_146294_l / 2, 40, -1);
		super.func_73863_a(mouseX, mouseY, partialTicks);
	}

	public void func_73876_c()
	{
		super.func_73876_c();
	}
}
