package twilightforest.world.components.layer.vanillalegacy.area;

public interface AreaFactory <A extends Area> {
	A make();
}