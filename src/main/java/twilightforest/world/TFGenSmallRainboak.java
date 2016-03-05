package twilightforest.world;


public class TFGenSmallRainboak extends TFGenSmallTwilightOak 
{

	public TFGenSmallRainboak() 
	{
		this(false);
	}


	public TFGenSmallRainboak(boolean notify) 
	{
		super(notify);
		this.leafMeta = 3;
	}

}
