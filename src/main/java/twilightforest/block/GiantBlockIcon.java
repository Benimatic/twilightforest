package twilightforest.block;

import net.minecraft.util.IIcon;

public class GiantBlockIcon implements IIcon {

	private IIcon baseIcon;
	private int myX;
	private int myY;

	public GiantBlockIcon(IIcon blockIcon, int x, int y) {
		this.baseIcon = blockIcon;
		this.myX = x;
		this.myY = y;
	}

	@Override
	public int getIconWidth() {
		return this.baseIcon.getIconWidth() / 4;
	}

	@Override
	public int getIconHeight() {
		return this.baseIcon.getIconHeight() / 4;
	}

	@Override
	public float getMinU() {
        float f = this.baseIcon.getMaxU() - this.baseIcon.getMinU();
		return this.baseIcon.getMinU() + (f * 0.25F * myX);
	}

	@Override
	public float getMaxU() {
		float f = this.baseIcon.getMaxU() - this.baseIcon.getMinU();
		return this.baseIcon.getMaxU() - (f * 0.25F * (3 - myX));
	}

	@Override
	public float getInterpolatedU(double par1) {
		float f = this.getMaxU() - this.getMinU();
		return this.getMinU() + f * (float)par1 / 16.0F;
	}

	@Override
	public float getMinV() {
		float f = this.baseIcon.getMaxV() - this.baseIcon.getMinV();
		return this.baseIcon.getMinV() + (f * 0.25F * myY);
	}

	@Override
	public float getMaxV() {
		float f = this.baseIcon.getMaxV() - this.baseIcon.getMinV();
		return this.baseIcon.getMaxV() - (f * 0.25F * (3 - myY));	
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
