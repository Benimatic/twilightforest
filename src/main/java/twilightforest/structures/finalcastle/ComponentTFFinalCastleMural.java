package twilightforest.structures.finalcastle;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import twilightforest.TFFeature;
import twilightforest.block.BlockTFCastleMagic;
import twilightforest.block.TFBlocks;
import twilightforest.structures.StructureTFComponentOld;

import java.util.Random;

public class ComponentTFFinalCastleMural extends StructureTFComponentOld {

	private int height;
	private int width;

	// we will model the mural in this byte array
	private byte[][] mural;

	public ComponentTFFinalCastleMural() {
	}

	public ComponentTFFinalCastleMural(TFFeature feature, Random rand, int i, int x, int y, int z, int width, int height, EnumFacing direction) {
		super(feature, i);
		this.setCoordBaseMode(direction);
		this.boundingBox = StructureTFComponentOld.getComponentToAddBoundingBox2(x, y, z, 0, -height / 2, -width / 2, 1, height - 1, width - 1, direction);
	}

	@Override
	public boolean addComponentParts(World world, Random rand, StructureBoundingBox sbb) {
		this.height = this.boundingBox.getYSize();
		this.width = (this.coordBaseMode == EnumFacing.SOUTH || this.coordBaseMode == EnumFacing.NORTH) ? this.boundingBox.getZSize() : this.boundingBox.getXSize();

		Random decoRNG = new Random(world.getSeed() + (this.boundingBox.minX * 321534781) ^ (this.boundingBox.minZ * 756839));

		if (mural == null) {
			// only make it once
			mural = new byte[width][height];

			int startX = width / 2 - 1;
			int startY = 2;

			// make mural, fill in dot by start
			for (int x = -1; x < 2; x++) {
				for (int y = -1; y < 2; y++) {
					mural[startX + x][startY + y] = 1;
				}
			}

			// side branches
			makeHorizontalTree(decoRNG, mural, startX + 1, startY, decoRNG.nextInt(width / 6) + width / 6, true);
			makeHorizontalTree(decoRNG, mural, startX - 1, startY, decoRNG.nextInt(width / 6) + width / 6, false);

			// main tree
			makeVerticalTree(decoRNG, mural, startX, startY + 1, decoRNG.nextInt(height / 6) + height / 6, true);

			// stripes
			makeStripes(decoRNG, mural);
		}

		final IBlockState castleMagic = TFBlocks.castle_rune_brick.getDefaultState()
				.withProperty(BlockTFCastleMagic.COLOR, BlockTFCastleMagic.VALID_COLORS.get(1));

		// copy mural to world
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (mural[x][y] > 0) {

					this.setBlockState(world, castleMagic, 0, y, x, sbb);
				} else {
					//this.setBlockState(world, TFBlocks.forceField, 0, 0, y, x, sbb);
				}
			}
		}

		return true;
	}

	private void makeHorizontalTree(Random decoRNG, byte[][] mural, int centerX, int centerY, int branchLength, boolean positive) {
		this.fillHorizontalLine(mural, centerX, centerY, branchLength, positive);

		this.makeHorizontalBranch(mural, decoRNG, centerX, centerY, branchLength, positive, true);
		this.makeHorizontalBranch(mural, decoRNG, centerX, centerY, branchLength, positive, false);
	}

	private void makeVerticalTree(Random decoRNG, byte[][] mural, int centerX, int centerY, int branchLength, boolean positive) {
		this.fillVerticalLine(mural, centerX, centerY, branchLength, positive);

		this.makeVerticalBranch(mural, decoRNG, centerX, centerY, branchLength, positive, true);
		this.makeVerticalBranch(mural, decoRNG, centerX, centerY, branchLength, positive, false);
	}

	private boolean makeHorizontalBranch(byte[][] mural, Random rand, int sx, int sy, int length, boolean plusX, boolean plusY) {
		int downLine = (length / 2) + 1 + rand.nextInt(Math.max(length / 2, 2));
		int branchLength = rand.nextInt(width / 8) + width / 8;

		// check if the area is clear
		boolean clear = true;
		for (int i = 0; i <= branchLength; i++) {
			int cx = sx + (plusX ? downLine - 1 + i : -(downLine - 1 + i));
			int cy = sy + (plusY ? 2 : -2);
			if (cx < 0 || cx >= width || cy < 0 || cy >= height || mural[cx][cy] > 0) {
				clear = false;
				break;
			}
		}
		if (clear) {
			int bx = sx + (plusX ? downLine : -downLine);
			int by = sy;

			// jag
			this.fillVerticalLine(mural, bx, by, 1, plusY);

			by += (plusY ? 2 : -2);

			this.fillHorizontalLine(mural, bx, by, branchLength, plusX);

			// recurse?
			if (bx > 0 && bx < width && by > 0 && by < height) {
				if (!this.makeHorizontalBranch(mural, rand, bx, by, branchLength, plusX, true)) {
					if (!this.makeHorizontalBranch(mural, rand, bx, by, branchLength, plusX, true)) {
						if (!this.makeHorizontalBranch(mural, rand, bx, by, branchLength, plusX, true)) {
							this.makeHorizontalBranch(mural, rand, bx, by, branchLength, plusX, true);
						}
					}
				}
				if (!this.makeHorizontalBranch(mural, rand, bx, by, branchLength, plusX, false)) {
					if (!this.makeHorizontalBranch(mural, rand, bx, by, branchLength, plusX, false)) {
						if (!this.makeHorizontalBranch(mural, rand, bx, by, branchLength, plusX, false)) {
							this.makeHorizontalBranch(mural, rand, bx, by, branchLength, plusX, false);
						}
					}
				}
			}
			return true;
		} else {
			return false;
		}
	}

	private boolean makeVerticalBranch(byte[][] mural, Random rand, int sx, int sy, int length, boolean plusY, boolean plusX) {
		int downLine = (length / 2) + 1 + rand.nextInt(Math.max(length / 2, 2));
		int branchLength = rand.nextInt(height / 8) + height / 8;

		// check if the area is clear
		boolean clear = true;
		for (int i = 0; i <= branchLength; i++) {
			int cx = sx + (plusX ? 2 : -2);
			int cy = sy + (plusY ? downLine - 1 + i : -(downLine - 1 + i));
			if (cx < 0 || cx >= width || cy < 0 || cy >= height || mural[cx][cy] > 0) {
				clear = false;
				break;
			}
		}
		if (clear) {
			int bx = sx;
			int by = sy + (plusY ? downLine : -downLine);
			;

			// jag
			this.fillHorizontalLine(mural, bx, by, 1, plusX);

			bx += (plusX ? 2 : -2);

			this.fillVerticalLine(mural, bx, by, branchLength, plusY);

			// recurse?
			if (bx > 0 && bx < width && by > 0 && by < height) {
				if (!this.makeVerticalBranch(mural, rand, bx, by, branchLength, plusY, true)) {
					if (!this.makeVerticalBranch(mural, rand, bx, by, branchLength, plusY, true)) {
						if (!this.makeVerticalBranch(mural, rand, bx, by, branchLength, plusY, true)) {
							this.makeVerticalBranch(mural, rand, bx, by, branchLength, plusY, true);
						}
					}
				}
				if (!this.makeVerticalBranch(mural, rand, bx, by, branchLength, plusY, false)) {
					if (!this.makeVerticalBranch(mural, rand, bx, by, branchLength, plusY, false)) {
						if (!this.makeVerticalBranch(mural, rand, bx, by, branchLength, plusY, false)) {
							this.makeVerticalBranch(mural, rand, bx, by, branchLength, plusY, false);
						}
					}
				}
			}
			return true;
		} else {
			return false;
		}
	}

	private void fillHorizontalLine(byte[][] mural, int sx, int sy, int length, boolean positive) {
		int x = sx;
		int y = sy;

		for (int i = 0; i <= length; i++) {
			if (x >= 0 && x < width && y >= 0 && y < height) {
				mural[x][y] = (byte) (positive ? 1 : 4);

				x += positive ? 1 : -1;
			}
		}
	}

	private void fillVerticalLine(byte[][] mural, int sx, int sy, int length, boolean positive) {
		int x = sx;
		int y = sy;

		for (int i = 0; i <= length; i++) {
			if (x >= 0 && x < width && y >= 0 && y < height) {
				mural[x][y] = (byte) (positive ? 2 : 3);

				y += positive ? 1 : -1;
			}
		}
	}

	private void makeStripes(Random decoRNG, byte[][] mural2) {
		// stagger slightly on our way down
		for (int y = this.height - 2; y > this.height / 3; y -= (2 + decoRNG.nextInt(2))) {
			makeSingleStripe(mural2, y);
		}
	}

	private void makeSingleStripe(byte[][] mural2, int y) {
		for (int x = 0; x < this.width - 2; x++) {
			if (mural[x + 1][y] == 0 && mural[x + 1][y + 1] == 0) {
				mural[x][y] = 1;
			} else {
				break;
			}
		}
		for (int x = this.width - 1; x > 2; x--) {
			if (mural[x - 1][y] == 0 && mural[x - 1][y + 1] == 0) {
				mural[x][y] = 1;
			} else {
				break;
			}
		}
	}

}
