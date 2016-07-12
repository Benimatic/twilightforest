package twilightforest.structures.hollowtree;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import twilightforest.block.TFBlocks;
import twilightforest.structures.StructureTFComponent;
import twilightforest.world.TFGenerator;


public class ComponentTFHollowTreeTrunk extends StructureTFComponent {
	
	int radius;
	int height;
    int groundLevel = -1;

	public ComponentTFHollowTreeTrunk() {
		super();
	}

	public ComponentTFHollowTreeTrunk(World world, Random rand, int index, int x, int y, int z) {
		super(index);

		height = rand.nextInt(64) + 32;
		radius =  rand.nextInt(4) + 1;

		this.setCoordBaseMode(0);
		
		
		boundingBox = new StructureBoundingBox(x, y, z, (x + radius * 2) + 2, y + height, (z + radius * 2) + 2);
	}
	
	/**
	 * Save to NBT
	 */
	@Override
	protected void func_143012_a(NBTTagCompound par1NBTTagCompound) {
		super.func_143012_a(par1NBTTagCompound);
		
        par1NBTTagCompound.setInteger("trunkRadius", this.radius);
        par1NBTTagCompound.setInteger("trunkHeight", this.height);
        par1NBTTagCompound.setInteger("trunkGroundLevel", this.groundLevel);

	}

	/**
	 * Load from NBT
	 */
	@Override
	protected void func_143011_b(NBTTagCompound par1NBTTagCompound) {
		super.func_143011_b(par1NBTTagCompound);

        this.radius = par1NBTTagCompound.getInteger("trunkRadius");
        this.height = par1NBTTagCompound.getInteger("trunkHeight");
        this.groundLevel = par1NBTTagCompound.getInteger("trunkGroundLevel");
	}

	/**
	 * Add on the various bits and doo-dads we need to succeed
	 */
	@Override
	public void buildComponent(StructureComponent structurecomponent, List list, Random rand) {
		int index = getComponentType();

		// 3-5 couple branches on the way up...
		int numBranches = rand.nextInt(3) + 3;
		for (int i = 0; i <= numBranches; i++) {
			int branchHeight = (int)(height * rand.nextDouble() * 0.9) + (height / 10);
			double branchRotation = rand.nextDouble();

			makeSmallBranch(list, rand, index + i + 1, branchHeight, 4, branchRotation, 0.35D, true);
		}
		
		// build the crown
		buildFullCrown(list, rand, index + numBranches + 1);
		
		// roots
		// 3-5 roots at the bottom
		buildBranchRing(list, rand, index, 3, 2, 6, 0, 0.75D, 0, 3, 5, 3, false);


		// several more taproots
		buildBranchRing(list, rand, index, 1, 2, 8, 0, 0.9D, 0, 3, 5, 3, false);

	}
	
	/**
	 * Build the crown of the tree
	 */
	protected void buildFullCrown(List list, Random rand, int index) {
		int crownRadius = radius * 4 + 4;
		int bvar = radius + 2;
		
		// okay, let's do 3-5 main branches starting at the bottom of the crown
		index += buildBranchRing(list, rand, index, height - crownRadius, 0, crownRadius, 0, 0.35D, 0, bvar, bvar + 2, 2, true);

		// then, let's do 3-5 medium branches at the crown middle
		index += buildBranchRing(list, rand, index, height - (crownRadius / 2), 0, crownRadius, 0, 0.28D, 0, bvar, bvar + 2, 1, true);
		
		// finally, let's do 2-4 main branches at the crown top
		index += buildBranchRing(list, rand, index, height, 0, crownRadius, 0, 0.15D, 0, 2, 4, 2, true);
		
		// and extra finally, let's do 3-6 medium branches going straight up
		index += buildBranchRing(list, rand, index, height, 0, (crownRadius / 2), 0, 0.05D, 0, bvar, bvar + 2, 1, true);
	}

	/**
	 * Build a ring of branches around the tree
	 * size 0 = small, 1 = med, 2 = large, 3 = root
	 */
	protected int buildBranchRing(List list, Random rand, int index, int branchHeight, int heightVar, int length, int lengthVar, double tilt, double tiltVar, int minBranches, int maxBranches, int size, boolean leafy) {
		//let's do this!
		int numBranches = rand.nextInt(maxBranches - minBranches + 1) + minBranches;
		double branchRotation = 1.0 / numBranches;
		double branchOffset = rand.nextDouble();
		
		for (int i = 0; i <= numBranches; i++) {
			int dHeight;
			if (heightVar > 0) {
				dHeight = branchHeight - heightVar + rand.nextInt(2 * heightVar);
			} else {
				dHeight = branchHeight;
			}
			
			if (size == 2) {
				makeLargeBranch(list, rand, index, dHeight, length, i * branchRotation + branchOffset, tilt, leafy);
			} else if (size == 1) {
				makeMedBranch(list, rand, index, dHeight, length, i * branchRotation + branchOffset, tilt, leafy);
			} else if (size == 3) {
				makeRoot(list, rand, index, dHeight, length, i * branchRotation + branchOffset, tilt);
			} else {
				makeSmallBranch(list, rand, index, dHeight, length, i * branchRotation + branchOffset, tilt, leafy);
			}
		}
		
		return numBranches;
	}

	
	public void makeSmallBranch(List list, Random rand, int index, int branchHeight, int branchLength, double branchRotation, double branchAngle, boolean leafy) {
		BlockPos bSrc = getBranchSrc(branchHeight, branchRotation);
        ComponentTFHollowTreeSmallBranch branch = new ComponentTFHollowTreeSmallBranch(index, bSrc.posX, bSrc.posY, bSrc.posZ, branchLength, branchRotation, branchAngle, leafy);
        list.add(branch);
        branch.buildComponent(this, list, rand);
	}

	public void makeMedBranch(List list, Random rand, int index, int branchHeight, int branchLength, double branchRotation, double branchAngle, boolean leafy) {
		BlockPos bSrc = getBranchSrc(branchHeight, branchRotation);
        ComponentTFHollowTreeMedBranch branch = new ComponentTFHollowTreeMedBranch(index, bSrc.posX, bSrc.posY, bSrc.posZ, branchLength, branchRotation, branchAngle, leafy);
        list.add(branch);
        branch.buildComponent(this, list, rand);
	}

	public void makeLargeBranch(List list, Random rand, int index, int branchHeight, int branchLength, double branchRotation, double branchAngle, boolean leafy) {
		BlockPos bSrc = getBranchSrc(branchHeight, branchRotation);
        ComponentTFHollowTreeMedBranch branch = new ComponentTFHollowTreeLargeBranch(index, bSrc.posX, bSrc.posY, bSrc.posZ, branchLength, branchRotation, branchAngle, leafy);
        list.add(branch);
        branch.buildComponent(this, list, rand);
	}


	public void makeRoot(List list, Random rand, int index, int branchHeight, int branchLength, double branchRotation, double branchAngle) {
		BlockPos bSrc = getBranchSrc(branchHeight, branchRotation);
	    ComponentTFHollowTreeRoot branch = new ComponentTFHollowTreeRoot(index, bSrc.posX, bSrc.posY, bSrc.posZ, branchLength, branchRotation, branchAngle, false);
	    list.add(branch);
	    branch.buildComponent(this, list, rand);
	}

	/**
	 * Where should we start this branch?
	 */
	private BlockPos getBranchSrc(int branchHeight, double branchRotation) {
		return TFGenerator.translate(boundingBox.minX + radius + 1, boundingBox.minY + branchHeight, boundingBox.minZ + radius + 1, radius, branchRotation, 0.5);
	}

	/**
	 * Generate the tree trunk
	 */
	@Override
	public boolean addComponentParts(World world, Random random, StructureBoundingBox sbb) {
		
		// offset bounding box to average ground level
        if (this.groundLevel < 0)
        {
            this.groundLevel = this.getAverageGroundLevel(world, sbb);

            if (this.groundLevel < 0)
            {
                return true;
            }

            this.height = this.boundingBox.maxY - this.groundLevel;
            this.boundingBox.minY = this.groundLevel;
        }

		int hollow = radius / 2;
		
		for (int dx = 0; dx <= 2 * radius; dx++)
		{
			for (int dz = 0; dz <= 2 * radius; dz++)
			{
				// determine how far we are from the center.
				int ax = Math.abs(dx - radius);
				int az = Math.abs(dz - radius);
				int dist = (int)(Math.max(ax, az) + (Math.min(ax, az) * 0.5));

				for (int dy = 0; dy <= height; dy++)
				{
					// fill the body of the trunk
					if (dist <= radius && dist > hollow) {
						this.placeBlockAtCurrentPosition(world, TFBlocks.log, 0, dx + 1, dy, dz + 1, sbb); // offset, since our BB is slightly larger than the trunk
					}
				}
				
				// fill to ground
				if (dist <= radius) {
					this.func_151554_b(world, TFBlocks.log, 0, dx + 1, -1, dz + 1, sbb);
				}
				
				// add vines
				if (dist == hollow && dx == hollow + radius) {
					this.func_151554_b(world, Blocks.VINE, 8, dx + 1, height, dz + 1, sbb);
				}
			}
		}
		
		// fireflies & cicadas
		int numInsects = random.nextInt(3 * radius) + random.nextInt(3 * radius) + 10;
		for (int i = 0; i <= numInsects; i++) {
			int fHeight = (int)(height * random.nextDouble() * 0.9) + (height / 10);
			double fAngle = random.nextDouble();
			addInsect(world, fHeight, fAngle, random, sbb);
		}

		return true;
	}
	
	/**
	 * Add a random insect
	 */
	protected void addInsect(World world, int fHeight, double fAngle, Random random, StructureBoundingBox sbb)
	{
		BlockPos bugSpot = TFGenerator.translate(this.radius + 1, fHeight, this.radius + 1, this.radius + 1, fAngle, 0.5);
		
		fAngle = fAngle % 1.0;
		int insectMeta = 0;
		
		if (fAngle > 0.875 || fAngle <= 0.125)
		{
			insectMeta = 3;
		}
		else if (fAngle > 0.125 && fAngle <= 0.375)
		{
			insectMeta = 1;
		}
		else if (fAngle > 0.375 && fAngle <= 0.625)
		{
			insectMeta = 4;
		}
		else if (fAngle > 0.625 && fAngle <= 0.875)
		{
			insectMeta = 2;
		}
		
		addInsect(world, random.nextBoolean() ? TFBlocks.firefly :  TFBlocks.cicada, insectMeta, bugSpot.posX, bugSpot.posY, bugSpot.posZ, sbb);
	}

	/**
	 * Add an insect if we can at the position specified
	 */
	private void addInsect(World world, Block blockID, int insectMeta, int posX, int posY, int posZ, StructureBoundingBox sbb) {
        int ox = this.getXWithOffset(posX, posZ);
        int oy = this.getYWithOffset(posY);
        int oz = this.getZWithOffset(posX, posZ);

        if (sbb.isVecInside(ox, oy, oz) && blockID != null && blockID.canPlaceBlockAt(world, ox, oy, oz))
        {
        	world.setBlock(ox, oy, oz, blockID, insectMeta, 2);
        }
	}

}
