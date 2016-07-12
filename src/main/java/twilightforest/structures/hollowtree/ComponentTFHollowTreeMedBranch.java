package twilightforest.structures.hollowtree;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import twilightforest.block.TFBlocks;
import twilightforest.structures.StructureTFComponent;
import twilightforest.world.TFGenerator;


public class ComponentTFHollowTreeMedBranch extends StructureTFComponent {
	
	BlockPos src, dest;  // source and destination of branch, array of 3 ints representing x, y, z
	double length;
	double angle;
	double tilt;
	boolean leafy;
	

	public ComponentTFHollowTreeMedBranch() {
		super();
	}

	protected ComponentTFHollowTreeMedBranch(int i, int sx, int sy, int sz, double length, double angle, double tilt, boolean leafy) {
		super(i);
		
		this.src = new BlockPos(sx, sy, sz);
		this.dest = TFGenerator.translate(src, length, angle, tilt);

		this.length = length;
		this.angle = angle;
		this.tilt = tilt;
		this.leafy = leafy;
		
		this.setCoordBaseMode(0);
		
		this.boundingBox = new StructureBoundingBox(Math.min(src.posX, dest.posX), Math.min(src.posY, dest.posY), Math.min(src.posZ, dest.posZ), Math.max(src.posX, dest.posX), Math.max(src.posY, dest.posY), Math.max(src.posZ, dest.posZ));
		
		this.boundingBox.expandTo(makeExpandedBB(0.5F, length, angle, tilt));
		this.boundingBox.expandTo(makeExpandedBB(0.1F, length, 0.225, tilt));
		this.boundingBox.expandTo(makeExpandedBB(0.1F, length, -0.225, tilt));
	}
	
	private StructureBoundingBox makeExpandedBB(double outVar, double branchLength, double branchAngle, double branchTilt) 
	{
		BlockPos branchSrc = TFGenerator.translate(src, this.length * outVar, this.angle, this.tilt);
		BlockPos branchDest = TFGenerator.translate(branchSrc, branchLength, branchAngle, branchTilt);
		
		return new StructureBoundingBox(Math.min(branchSrc.posX, branchDest.posX), Math.min(branchSrc.posY, branchDest.posY), Math.min(branchSrc.posZ, branchDest.posZ), Math.max(branchSrc.posX, branchDest.posX), Math.max(branchSrc.posY, branchDest.posY), Math.max(branchSrc.posZ, branchDest.posZ));
	}

	
	/**
	 * Save to NBT
	 */
	@Override
	protected void func_143012_a(NBTTagCompound par1NBTTagCompound) {
		super.func_143012_a(par1NBTTagCompound);
		
        par1NBTTagCompound.setInteger("srcPosX", this.src.posX);
        par1NBTTagCompound.setInteger("srcPosY", this.src.posY);
        par1NBTTagCompound.setInteger("srcPosZ", this.src.posZ);
        par1NBTTagCompound.setInteger("destPosX", this.dest.posX);
        par1NBTTagCompound.setInteger("destPosY", this.dest.posY);
        par1NBTTagCompound.setInteger("destPosZ", this.dest.posZ);
        par1NBTTagCompound.setDouble("branchLength", this.length);
        par1NBTTagCompound.setDouble("branchAngle", this.angle);
        par1NBTTagCompound.setDouble("branchTilt", this.tilt);
        par1NBTTagCompound.setBoolean("branchLeafy", this.leafy);

	}

	/**
	 * Load from NBT
	 */
	@Override
	protected void func_143011_b(NBTTagCompound par1NBTTagCompound) {
		super.func_143011_b(par1NBTTagCompound);
		

        this.src = new BlockPos(par1NBTTagCompound.getInteger("srcPosX"), par1NBTTagCompound.getInteger("srcPosY"), par1NBTTagCompound.getInteger("srcPosZ"));
        this.dest = new BlockPos(par1NBTTagCompound.getInteger("destPosX"), par1NBTTagCompound.getInteger("destPosY"), par1NBTTagCompound.getInteger("destPosZ"));
        
        this.length = par1NBTTagCompound.getDouble("branchLength");
        this.angle = par1NBTTagCompound.getDouble("branchAngle");
        this.tilt = par1NBTTagCompound.getDouble("branchTilt");
        this.leafy = par1NBTTagCompound.getBoolean("branchLeafy");
	}

	/**
	 * Add a leaf ball to the end
	 * TODO: go back to adding more leaves if this is successful?
	 */
	@Override
	public void buildComponent(StructureComponent structurecomponent, List list, Random rand) {
		int index = getComponentType();

//		// with leaves!
//		if (leafy) {
//			int numLeafBalls = Math.min(rand.nextInt(3) + 1, (int)(length / 5));
//			for(int i = 0; i < numLeafBalls; i++) {
//
//				double slength = (rand.nextDouble() * 0.6 + 0.2) * length;
//				int[] bdst = TFGenerator.translate(src.posX, src.posY, src.posZ, slength, angle, tilt);
//
//				//drawBlob(bdst[0], bdst[1], bdst[2], 2, leafBlock, false);		
//				ComponentTFLeafSphere leafBlob = new ComponentTFLeafSphere(index + 1, bdst[0], bdst[1], bdst[2], 2);
//		        list.add(leafBlob);
//		        leafBlob.buildComponent(this, list, rand); // doesn't really need to be here for leaves.
//			}
//
//			
//			ComponentTFLeafSphere leafBlob = new ComponentTFLeafSphere(index + 1, dest.posX, dest.posY, dest.posZ, 2);
//	        list.add(leafBlob);
//	        leafBlob.buildComponent(this, list, rand); // doesn't really need to be here for leaves.
//		}
//
//		// and several small branches
//		int numShoots = Math.min(rand.nextInt(3) + 1, (int)(length / 5));
//		double angleInc, angleVar, outVar, tiltVar;
//
//		angleInc = 0.8 / numShoots;
//
//		for(int i = 0; i < numShoots; i++) {
//
//			angleVar = (angleInc * i) - 0.4;
//			outVar = (rand.nextDouble() * 0.8) + 0.2;
//			tiltVar = (rand.nextDouble() * 0.75) + 0.15;
//
//			BlockPos bSrc = TFGenerator.translateCoords(src.posX, src.posY, src.posZ, length * outVar, angle, tilt);
//			double slength = length * 0.4;
//
//			makeSmallBranch(list, rand, index + 1, bSrc.posX, bSrc.posY, bSrc.posZ, slength, angle + angleVar, tilt * tiltVar, leafy);
//		}
	}
	
	public void makeSmallBranch(List list, Random rand, int index, int x, int y, int z, double branchLength, double branchRotation, double branchAngle, boolean leafy) {
        ComponentTFHollowTreeSmallBranch branch = new ComponentTFHollowTreeSmallBranch(index, x, y, z, branchLength, branchRotation, branchAngle, leafy);
        list.add(branch);
        branch.buildComponent(this, list, rand);
	}
	

	
	@Override
	public boolean addComponentParts(World world, Random random, StructureBoundingBox sbb) {
		
		BlockPos rSrc = new BlockPos(src.posX - boundingBox.minX, src.posY - boundingBox.minY, src.posZ - boundingBox.minZ);
		BlockPos rDest = new BlockPos(dest.posX - boundingBox.minX, dest.posY - boundingBox.minY, dest.posZ - boundingBox.minZ);

//		placeVoid(world, sbb, boundingBox.minX, boundingBox.minY, boundingBox.minZ, boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ, TFBlocks.wood, 0, false);
//		System.out.println("Drawing a medium branch from " + rsrc.posX + ", " + rsrc.posY + ", " + rsrc.posZ + " to " + rdest.posX + ", " + rdest.posY + ", " + rdest.posZ);
//		System.out.println("My bounding box is  " + boundingBox.minX + ", " + boundingBox.minY + ", " + boundingBox.minZ + " to "  + boundingBox.maxX + ", " + boundingBox.maxY + ", " + boundingBox.maxZ);
//		System.out.println("Draw bounding box is  " + sbb.minX + ", " + sbb.minY + ", " + sbb.minZ + " to "  + sbb.maxX + ", " + sbb.maxY + ", " + sbb.maxZ);

		drawBresehnam(world, sbb, rSrc.posX, rSrc.posY, rSrc.posZ, rDest.posX, rDest.posY, rDest.posZ, TFBlocks.log, 12);
		drawBresehnam(world, sbb, rSrc.posX, rSrc.posY + 1, rSrc.posZ, rDest.posX, rDest.posY, rDest.posZ, TFBlocks.log, 12);

		Random decoRNG = new Random(world.getSeed() + (this.boundingBox.minX * 321534781) ^ (this.boundingBox.minZ * 756839));

		// and several small branches
		int numShoots = Math.min(decoRNG.nextInt(3) + 1, (int)(length / 5));
		double angleInc, angleVar, outVar, tiltVar;

		angleInc = 0.8 / numShoots;

		for(int i = 0; i < numShoots; i++) {

			angleVar = (angleInc * i) - 0.4;
			outVar = (decoRNG.nextDouble() * 0.8) + 0.2;
			tiltVar = (decoRNG.nextDouble() * 0.75) + 0.15;

			BlockPos bSrc = TFGenerator.translate(rSrc, length * outVar, angle, tilt);
			double slength = length * 0.4;

			drawSmallBranch(world, sbb, bSrc.posX, bSrc.posY, bSrc.posZ, Math.max(length * 0.3F, 2F), angle + angleVar, tilt, leafy);
		}
		
		// with leaves!
		if (leafy) {
			int numLeafBalls = Math.min(decoRNG.nextInt(3) + 1, (int)(length / 5));
			for(int i = 0; i < numLeafBalls; i++) {

				double slength = (decoRNG.nextFloat() * 0.6F + 0.2F) * length;
				BlockPos bdst = TFGenerator.translate(rSrc, slength, angle, tilt);

				makeLeafBlob(world, sbb, bdst.posX, bdst.posY, bdst.posZ, decoRNG.nextBoolean() ? 2 : 3);		

			}

			makeLeafBlob(world, sbb, rDest.posX, rDest.posY, rDest.posZ, 3);		

		}


		return true;
	}

	
	/**
	 * Draws a line
	 */
	protected void drawBresehnam(World world, StructureBoundingBox sbb, int x1, int y1, int z1, int x2, int y2, int z2, Block blockValue, int metaValue) {
		BlockPos lineCoords[] = TFGenerator.getBresehnamArrayCoords(x1, y1, z1, x2, y2, z2);
		
		for (BlockPos coords : lineCoords)
		{
			this.placeBlockAtCurrentPosition(world, blockValue, metaValue, coords.posX, coords.posY, coords.posZ, sbb);
		}
	}

	/**
	 * Make a leaf blob
	 */
	protected void makeLeafBlob(World world, StructureBoundingBox sbb, int sx, int sy, int sz, int radius) {
		// then trace out a quadrant
		for (int dx = 0; dx <= radius; dx++)
		{
			for (int dy = 0; dy <= radius; dy++)
			{
				for (int dz = 0; dz <= radius; dz++)
				{
					// determine how far we are from the center.
					int dist = 0;

					if (dx >= dy && dx >= dz) 
					{
						dist = (int) (dx + ((Math.max(dy, dz) * 0.5F) + (Math.min(dy, dz) * 0.25F)));
					} 
					else if (dy >= dx && dy >= dz)
					{
						dist = (int) (dy + ((Math.max(dx, dz) * 0.5F) + (Math.min(dx, dz) * 0.25F)));
					} 
					else 
					{
						dist = (int) (dz + ((Math.max(dx, dy) * 0.5F) + (Math.min(dx, dy) * 0.25F)));
					}


					// if we're inside the blob, fill it
					if (dist <= radius) {
						// do eight at a time for easiness!
						placeLeafBlock(world, TFBlocks.leaves, 0, sx + dx, sy + dy, sz + dz, sbb);
						placeLeafBlock(world, TFBlocks.leaves, 0, sx + dx, sy + dy, sz - dz, sbb);
						placeLeafBlock(world, TFBlocks.leaves, 0, sx - dx, sy + dy, sz + dz, sbb);
						placeLeafBlock(world, TFBlocks.leaves, 0, sx - dx, sy + dy, sz - dz, sbb);
						placeLeafBlock(world, TFBlocks.leaves, 0, sx + dx, sy - dy, sz + dz, sbb);
						placeLeafBlock(world, TFBlocks.leaves, 0, sx + dx, sy - dy, sz - dz, sbb);
						placeLeafBlock(world, TFBlocks.leaves, 0, sx - dx, sy - dy, sz + dz, sbb);
						placeLeafBlock(world, TFBlocks.leaves, 0, sx - dx, sy - dy, sz - dz, sbb);

					}
				}
			}
		}
	}

	/**
	 * Puts a block only if leaves can go there.
	 */
	protected void placeLeafBlock(World world, Block blockID, int meta, int x, int y, int z, StructureBoundingBox sbb) {

		int offX = this.getXWithOffset(x, z);
		int offY = this.getYWithOffset(y);
		int offZ = this.getZWithOffset(x, z);

		if (sbb.isVecInside(offX, offY, offZ))
		{
			Block whatsThere = world.getBlock(offX, offY, offZ);

			if (whatsThere == null || whatsThere.canBeReplacedByLeaves(world, offX, offY, offZ))
			{
				world.setBlock(offX, offY, offZ, blockID, meta, 2);
			}
		}
	}

	/**
	 * This is like the small branch component, but we're just drawing it directly into the world 
	 */
	protected void drawSmallBranch(World world, StructureBoundingBox sbb, int sx, int sy, int sz, double branchLength, double branchAngle, double branchTilt, boolean leafy) {
		// draw a line
		BlockPos branchDest = TFGenerator.translate(sx, sy, sz, branchLength, branchAngle, branchTilt);

		drawBresehnam(world, sbb, sx, sy, sz, branchDest.posX, branchDest.posY, branchDest.posZ, TFBlocks.log, 12);

		// leaf blob at the end
		makeLeafBlob(world, sbb,  branchDest.posX, branchDest.posY, branchDest.posZ, 2);

	}


}
