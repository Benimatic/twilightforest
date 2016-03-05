package twilightforest.item;

import net.minecraft.util.IIcon;

public class GiantItemIcon implements IIcon {

	private IIcon baseIcon;
	private float myX;
	private float myY;

	public GiantItemIcon(IIcon blockIcon, float x, float y) {
		this.baseIcon = blockIcon;
		this.myX = x;
		this.myY = y;
	}

	@Override
	public int getIconWidth() {
		return this.baseIcon.getIconWidth() / 2;
	}

	@Override
	public int getIconHeight() {
		return this.baseIcon.getIconHeight() / 2;
	}

	@Override
	public float getMinU() {
        float f = this.baseIcon.getMaxU() - this.baseIcon.getMinU();
		return this.baseIcon.getMinU() + (f * myX);
	}

	@Override
	public float getMaxU() {
		float f = this.baseIcon.getMaxU() - this.baseIcon.getMinU();
		return this.baseIcon.getMinU() + (f * (myX + 0.5F));
	}

	@Override
	public float getInterpolatedU(double par1) {
		float f = this.getMaxU() - this.getMinU();
		return this.getMinU() + f * (float)par1 / 16.0F;
	}

	@Override
	public float getMinV() {
		float f = this.baseIcon.getMaxV() - this.baseIcon.getMinV();
		return this.baseIcon.getMinV() + (f * myY);
	}

	@Override
	public float getMaxV() {
		float f = this.baseIcon.getMaxV() - this.baseIcon.getMinV();
		return this.baseIcon.getMinV() + (f * (myY + 0.5F));
	}

	@Override
	public float getInterpolatedV(double par1) {
		float f = this.getMaxV() - this.getMinV();
		return this.getMinV() + f * ((float)par1 / 16.0F);
	}

	@Override
	public String getIconName() {
		return this.baseIcon.getIconName();
	}
}
