package twilightforest.structures.stronghold;

import java.util.List;
import java.util.Random;

import net.minecraft.block.BlockStairs;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import twilightforest.TFTreasure;

public class ComponentTFStrongholdDeadEnd extends StructureTFStrongholdComponent {


	private boolean chestTrapped;

	public ComponentTFStrongholdDeadEnd() {}

	public ComponentTFStrongholdDeadEnd(int i, EnumFacing facing, int x, int y, int z) {
		super(i, facing, x, y, z);
	}
	
	@Override
	protected void writeStructureToNBT(NBTTagCompound par1NBTTagCompound) {
		super.writeStructureToNBT(par1NBTTagCompound);

        par1NBTTagCompound.setBoolean("chestTrapped", this.chestTrapped);
	}

	@Override
	protected void readStructureFromNBT(NBTTagCompound par1NBTTagCompound) {
		super.readStructureFromNBT(par1NBTTagCompound);

        this.chestTrapped = par1NBTTagCompound.getBoolean("chestTrapped");
	}

	@Override
	public StructureBoundingBox generateBoundingBox(EnumFacing facing, int x, int y, int z)
	{
		return StructureTFStrongholdComponent.getComponentToAddBoundingBox(x, y, z, -4, -1, 0, 9, 6, 9, facing);
	}
	
	@Override
	public void buildComponent(StructureComponent parent, List list, Random random) {
		super.buildComponent(parent, list, random);

		// entrance
		this.addDoor(4, 1, 0);
		
		this.chestTrapped = random.nextInt(3) == 0;
	}

	@Override
	public boolean addComponentParts(World world, Random rand, StructureBoundingBox sbb) {
		placeStrongholdWalls(world, sbb, 0, 0, 0, 8, 6, 8, rand, deco.randomBlocks);
		
		// statues
		this.placeWallStatue(world, 1, 1, 4, 1, sbb);
		this.placeWallStatue(world, 7, 1, 4, 3, sbb);
		this.placeWallStatue(world, 4, 1, 7, 0, sbb);

		// doors
		placeDoors(world, rand, sbb);
		
		// treasure
		this.placeTreasureAtCurrentPosition(world, rand, 4, 1, 3, TFTreasure.stronghold_cache, this.chestTrapped, sbb);
		if (this.chestTrapped)
		{
			this.setBlockState(world, Blocks.TNT.getDefaultState(), 4, 0, 3, sbb);
		}
		
		for (int z = 2; z < 5; z++)
		{
			this.setBlockState(world, deco.stairState.withProperty(BlockStairs.FACING, getStructureRelativeRotation(0)), 3, 1, z, sbb);
			this.setBlockState(world, deco.stairState.withProperty(BlockStairs.FACING, getStructureRelativeRotation(2)), 5, 1, z, sbb);
		}
		
		this.setBlockState(world, deco.stairState.withProperty(BlockStairs.FACING, getStructureRelativeRotation(1)), 4, 1, 2, sbb);
		this.setBlockState(world, deco.stairState.withProperty(BlockStairs.FACING, getStructureRelativeRotation(3)), 4, 1, 4, sbb);
		this.setBlockState(world, deco.stairState.withProperty(BlockStairs.FACING, getStructureRelativeRotation(1)), 4, 2, 3, sbb);
		
		return true;
	}


}
