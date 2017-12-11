package twilightforest.structures.courtyard;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Rotation;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.template.TemplateManager;
import twilightforest.TFFeature;
import twilightforest.enums.Diagonals;
import twilightforest.structures.StructureTFComponent;

import java.util.List;
import java.util.Random;

public abstract class StructureMazeGenerator extends StructureTFComponent {
    protected int[][] maze;
    private int[][] cornerClipping = new int[4][2];
    private int widthInCellCount;
    private int heightInCellCount;

    StructureMazeGenerator() {
        super();
    }

    StructureMazeGenerator(TFFeature feature, Random rand, int i, int widthInCellCount, int heightInCellCount) {
        super(feature, i);
        this.widthInCellCount = widthInCellCount;
        this.heightInCellCount = heightInCellCount;
        this.maze = new int[widthInCellCount-1][heightInCellCount-1];
        generateMaze(this.maze, this.cornerClipping, rand, this.widthInCellCount, this.heightInCellCount, 3);
    }

    // Actually assemble maze
    @Override
    public void buildComponent(StructureComponent structureComponent, List<StructureComponent> list, Random random) {
        super.buildComponent(structureComponent, list, random);
        final int offset = 6;

        for (int x = 0; x < widthInCellCount-1; x++) {
            for (int y = 0; y < heightInCellCount-1; y++) {
                if ((maze[x][y] & 0b10000) == 0b10000) continue;

                StructureTFComponent structure;

                int rotation = 0;
                Rotation[] rotations = Rotation.values();

                int xBB = boundingBox.minX + (x * 12) + offset;
                int yBB = boundingBox.minY + 1;
                int zBB = boundingBox.minZ + (y * 12) + offset;

                switch (maze[x][y] & 0b1111) { // These are inconsistent because I was stupid with the structures I saved to .nbt
                    case 0b0010:    // FACE SOUTH
                        rotation++; // rotate 270
                    case 0b0001:    // FACE EAST
                        rotation++; // rotate 180
                    case 0b1000:    // FACE NORTH
                        rotation++; // rotate 90
                    case 0b0100:    // FACE WEST
                        final Rotation rotationCap = rotations[rotation];

                        xBB += (rotationCap == Rotation.CLOCKWISE_180 || rotationCap == Rotation.CLOCKWISE_90 ? 4 : 0);
                        zBB += (rotationCap == Rotation.CLOCKWISE_180 || rotationCap == Rotation.COUNTERCLOCKWISE_90 ? 4 : 0);

                        structure = new ComponentNagaCourtyardCap(getFeatureType(), maze[x][y], xBB, yBB, zBB, rotationCap);
                        break;
                    case 0b1001:    // NORTH EAST
                        rotation++;
                    case 0b1100:    // NORTH WEST
                        rotation++;
                    case 0b0110:    // SOUTH WEST
                        rotation++;
                    case 0b0011:    // SOUTH EAST
                        final Rotation rotationCorner = rotations[rotation];

                        xBB += (rotationCorner == Rotation.CLOCKWISE_180 || rotationCorner == Rotation.CLOCKWISE_90 ? 4 : 0);
                        zBB += (rotationCorner == Rotation.CLOCKWISE_180 || rotationCorner == Rotation.COUNTERCLOCKWISE_90 ? 4 : 0);

                        structure = new ComponentNagaCourtyardCorner(getFeatureType(), maze[x][y], xBB, yBB, zBB, rotationCorner);
                        break;
                    case 0b1101:    // NOT SOUTH
                        rotation++;
                    case 0b1110:    // NOT EAST
                        rotation++;
                    case 0b0111:    // NOT NORTH
                        rotation++;
                    case 0b1011:    // NOT WEST
                        final Rotation rotationT = rotations[rotation];

                        xBB += (rotationT == Rotation.CLOCKWISE_180 || rotationT == Rotation.CLOCKWISE_90 ? 4 : 0);
                        zBB += (rotationT == Rotation.CLOCKWISE_180 || rotationT == Rotation.COUNTERCLOCKWISE_90 ? 4 : 0);

                        structure = new ComponentNagaCourtyardTJunction(getFeatureType(), maze[x][y], xBB, yBB, zBB, rotationT);
                        break;
                    case 0b1010:    // NORTH AND SOUTH
                        rotation++;
                    case 0b0101:    // EAST AND WEST
                        final Rotation rotationLine = rotations[rotation];

                        xBB += (rotationLine == Rotation.CLOCKWISE_180 || rotationLine == Rotation.CLOCKWISE_90 ? 4 : 0);
                        zBB += (rotationLine == Rotation.CLOCKWISE_180 || rotationLine == Rotation.COUNTERCLOCKWISE_90 ? 4 : 0);

                        structure = new ComponentNagaCourtyardLine(getFeatureType(), maze[x][y], xBB, yBB, zBB, rotationLine);
                        break;
                    case 0b1111:
                        structure = new ComponentNagaCourtyardIntersection(getFeatureType(), maze[x][y], xBB, yBB, zBB);
                        break;
                    default:
                        structure = new ComponentNagaCourtyardTerrace(getFeatureType(), maze[x][y], xBB-6, yBB-3, zBB-6);
                        break;
                }

                list.add(structure);
                structure.buildComponent(structureComponent, list, random);

                xBB = boundingBox.minX + (x * 12) + offset;
                zBB = boundingBox.minZ + (y * 12) + offset;

                boolean hasNoTerrace = (maze[x][y] & 0b1111) != 0;

                boolean westHasNoTerraceOrIsSafe  = x == 0 || (maze[x-1][y] & 0b1111) != 0 || (maze[x-1][y] & 0b10000) == 0b10000;
                boolean northHasNoTerraceOrIsSafe = y == 0 || (maze[x][y-1] & 0b1111) != 0 || (maze[x][y-1] & 0b10000) == 0b10000;
                boolean eastHasNoTerraceOrIsSafe  = x == widthInCellCount -2 || (maze[x+1][y] & 0b10000) == 0b10000;
                boolean southHasNoTerraceOrIsSafe = y == heightInCellCount-2 || (maze[x][y+1] & 0b10000) == 0b10000;

                boolean westNorthHasNoTerraceOrIsSafe = (x == 0 || y == 0 || maze[x - 1][y - 1] != 0);
                boolean westSouthHasNoTerraceOrIsSafe = (x == 0 || y >= heightInCellCount-2 || maze[x - 1][y + 1] != 0);
                boolean eastNorthHasNoTerraceOrIsSafe = (x >= widthInCellCount -2 || y == 0 || maze[x + 1][y - 1] != 0);
                boolean eastSouthHasNoTerraceOrIsSafe = (x >= widthInCellCount -2 || y >= heightInCellCount-2 || maze[x + 1][y + 1] != 0);

                if (hasNoTerrace) {
                    ComponentNagaCourtyardPath path = new ComponentNagaCourtyardPath(getFeatureType(), maze[x][y], xBB - 1, yBB - 1, zBB - 1);
                    list.add(path);
                    path.buildComponent(structureComponent, list, random);
                }

                if (WallFacing.WEST.unpackAndTest(maze[x][y])) {
                    ComponentNagaCourtyardPadder padding = new ComponentNagaCourtyardPadder(getFeatureType(), maze[x][y], xBB - 1, yBB, zBB, Rotation.NONE);
                    list.add(padding);
                    padding.buildComponent(structureComponent, list, random);

                    if (x > 0 && (maze[x-1][y] & 0b10000) != 0b10000) {
                        ComponentNagaCourtyardPadder padding2 = new ComponentNagaCourtyardPadder(getFeatureType(), maze[x][y], xBB - 7, yBB, zBB, Rotation.NONE);
                        list.add(padding2);
                        padding2.buildComponent(structureComponent, list, random);
                    }

                    ComponentNagaCourtyardLine structureLine = new ComponentNagaCourtyardLine(getFeatureType(), maze[x][y], xBB - 6, yBB, zBB, Rotation.NONE);
                    list.add(structureLine);
                    structureLine.buildComponent(structureComponent, list, random);
                }

                if (WallFacing.NORTH.unpackAndTest(maze[x][y])) {
                    ComponentNagaCourtyardPadder padding = new ComponentNagaCourtyardPadder(getFeatureType(), maze[x][y], xBB + 4, yBB, zBB - 1, Rotation.CLOCKWISE_90);
                    list.add(padding);
                    padding.buildComponent(structureComponent, list, random);

                    if (y > 0 && (maze[x][y-1] & 0b10000) != 0b10000) {
                        ComponentNagaCourtyardPadder padding2 = new ComponentNagaCourtyardPadder(getFeatureType(), maze[x][y], xBB + 4, yBB, zBB - 7, Rotation.CLOCKWISE_90);
                        list.add(padding2);
                        padding2.buildComponent(structureComponent, list, random);
                    }

                    ComponentNagaCourtyardLine structureLine = new ComponentNagaCourtyardLine(getFeatureType(), maze[x][y], xBB + 4, yBB, zBB - 6, Rotation.CLOCKWISE_90);
                    list.add(structureLine);
                    structureLine.buildComponent(structureComponent, list, random);
                }

                if ((x >= widthInCellCount - 2 || (maze[x+1][y] & 0b10000) == 0b10000) && WallFacing.EAST.unpackAndTest(maze[x][y])) {
                    ComponentNagaCourtyardPadder padding = new ComponentNagaCourtyardPadder(getFeatureType(), maze[x][y], xBB + 5, yBB, zBB, Rotation.NONE);
                    list.add(padding);
                    padding.buildComponent(structureComponent, list, random);

                    //ComponentNagaCourtyardPadder padding2 = new ComponentNagaCourtyardPadder(getFeatureType(), maze[x][y], xBB + 11, yBB, zBB, Rotation.NONE);
                    //list.add(padding2);
                    //padding2.buildComponent(structureComponent, list, random);

                    ComponentNagaCourtyardLine structureLine = new ComponentNagaCourtyardLine(getFeatureType(), maze[x][y], xBB + 6, yBB, zBB, Rotation.NONE);
                    list.add(structureLine);
                    structureLine.buildComponent(structureComponent, list, random);
                }

                if ((y >= heightInCellCount - 2 || (maze[x][y+1] & 0b10000) == 0b10000) && WallFacing.SOUTH.unpackAndTest(maze[x][y])) {
                    ComponentNagaCourtyardPadder padding = new ComponentNagaCourtyardPadder(getFeatureType(), maze[x][y], xBB + 4, yBB, zBB + 5, Rotation.CLOCKWISE_90);
                    list.add(padding);
                    padding.buildComponent(structureComponent, list, random);

                    //ComponentNagaCourtyardPadder padding2 = new ComponentNagaCourtyardPadder(getFeatureType(), maze[x][y], xBB + 4, yBB, zBB + 11, Rotation.CLOCKWISE_90);
                    //list.add(padding2);
                    //padding2.buildComponent(structureComponent, list, random);

                    ComponentNagaCourtyardLine structureLine = new ComponentNagaCourtyardLine(getFeatureType(), maze[x][y], xBB + 4, yBB, zBB + 6, Rotation.CLOCKWISE_90);
                    list.add(structureLine);
                    structureLine.buildComponent(structureComponent, list, random);
                }

                // Paths - cardinal

                if (hasNoTerrace && westHasNoTerraceOrIsSafe) {
                    ComponentNagaCourtyardPath path2 = new ComponentNagaCourtyardPath(getFeatureType(), maze[x][y], xBB - 7, yBB - 1, zBB - 1);
                    list.add(path2);
                    path2.buildComponent(structureComponent, list, random);
                }

                if (hasNoTerrace && northHasNoTerraceOrIsSafe) {
                    ComponentNagaCourtyardPath path2 = new ComponentNagaCourtyardPath(getFeatureType(), maze[x][y], xBB - 1, yBB - 1, zBB - 7);
                    list.add(path2);
                    path2.buildComponent(structureComponent, list, random);
                }

                if (hasNoTerrace && eastHasNoTerraceOrIsSafe) {
                    ComponentNagaCourtyardPath path2 = new ComponentNagaCourtyardPath(getFeatureType(), maze[x][y], xBB + 5, yBB - 1, zBB - 1);
                    list.add(path2);
                    path2.buildComponent(structureComponent, list, random);
                }

                if (hasNoTerrace && southHasNoTerraceOrIsSafe) {
                    ComponentNagaCourtyardPath path2 = new ComponentNagaCourtyardPath(getFeatureType(), maze[x][y], xBB - 1, yBB - 1, zBB + 5);
                    list.add(path2);
                    path2.buildComponent(structureComponent, list, random);
                }

                // Paths - Diagonal

                if (hasNoTerrace && westHasNoTerraceOrIsSafe && northHasNoTerraceOrIsSafe && westNorthHasNoTerraceOrIsSafe) {
                    ComponentNagaCourtyardPath path2 = new ComponentNagaCourtyardPath(getFeatureType(), maze[x][y], xBB - 7, yBB - 1, zBB - 7);
                    list.add(path2);
                    path2.buildComponent(structureComponent, list, random);
                }

                if (hasNoTerrace && westHasNoTerraceOrIsSafe && southHasNoTerraceOrIsSafe && westSouthHasNoTerraceOrIsSafe) {
                    ComponentNagaCourtyardPath path2 = new ComponentNagaCourtyardPath(getFeatureType(), maze[x][y], xBB - 7, yBB - 1, zBB + 5);
                    list.add(path2);
                    path2.buildComponent(structureComponent, list, random);
                }

                if (hasNoTerrace && eastHasNoTerraceOrIsSafe && northHasNoTerraceOrIsSafe && eastNorthHasNoTerraceOrIsSafe) {
                    ComponentNagaCourtyardPath path2 = new ComponentNagaCourtyardPath(getFeatureType(), maze[x][y], xBB + 5, yBB - 1, zBB - 7);
                    list.add(path2);
                    path2.buildComponent(structureComponent, list, random);
                }

                if (hasNoTerrace && eastHasNoTerraceOrIsSafe && southHasNoTerraceOrIsSafe && eastSouthHasNoTerraceOrIsSafe) {
                    ComponentNagaCourtyardPath path2 = new ComponentNagaCourtyardPath(getFeatureType(), maze[x][y], xBB + 5, yBB - 1, zBB + 5);
                    list.add(path2);
                    path2.buildComponent(structureComponent, list, random);
                }
            }
        }

        // Top / North
        for (int i = cornerClipping[3][1]; i < (widthInCellCount-1) - cornerClipping[0][1]; i ++) {
            ComponentNagaCourtyardWall wall = new ComponentNagaCourtyardWall(getFeatureType(), i, boundingBox.minX + (i * 12) + offset - 3, boundingBox.minY + 1, boundingBox.minZ - 3, Rotation.NONE);
            list.add(wall);
            wall.buildComponent(structureComponent, list, random);
        }

        // Bottom / South
        for (int i = cornerClipping[2][1]; i < (widthInCellCount-1) - cornerClipping[1][1]; i ++) {
            ComponentNagaCourtyardWall wall = new ComponentNagaCourtyardWall(getFeatureType(), i, boundingBox.minX + (i * 12) + offset - 3, boundingBox.minY + 1, boundingBox.maxZ + 1, Rotation.NONE);
            list.add(wall);
            wall.buildComponent(structureComponent, list, random);
        }

        // Left / West
        for (int i = cornerClipping[3][0]; i < (heightInCellCount-1) - cornerClipping[2][0]; i ++) {
            ComponentNagaCourtyardWall wall = new ComponentNagaCourtyardWall(getFeatureType(), i, boundingBox.minX - 1, boundingBox.minY + 1, boundingBox.minZ + (i * 12) + offset - 3, Rotation.CLOCKWISE_90);
            list.add(wall);
            wall.buildComponent(structureComponent, list, random);
        }

        // Right / East
        for (int i = cornerClipping[0][0]; i < (heightInCellCount-1) - cornerClipping[1][0]; i ++) {
            ComponentNagaCourtyardWall wall = new ComponentNagaCourtyardWall(getFeatureType(), i, boundingBox.maxX + 3, boundingBox.minY + 1, boundingBox.minZ + (i * 12) + offset - 3, Rotation.CLOCKWISE_90);
            list.add(wall);
            wall.buildComponent(structureComponent, list, random);
        }
    }

    private static void generateMaze(int[][] maze, int[][] cornerClippings, Random random, int widthInCellCount, int heightInCellCount, int maximumClipping) {
        // Trying to keep this optimized for speed I guess

        // Generates a connection map for the walls. It modifies the two-dimensional int array, inserting packed ints.
        // A One in its binary interpretation means the wall connects in this direction.
        // As a result of this being a "connectome" of maze walls. It is "Size In Cell count" - 1.

        final WallFacing rotations[][] = new WallFacing[maze.length][maze[0].length];

        for (int x = 0; x < widthInCellCount-1; x++) {
            for (int y = 0; y < heightInCellCount-1; y++) {
                rotations[x][y] = WallFacing.values()[random.nextInt(WallFacing.values().length)];
                // set the initial base byte
                maze[x][y] |= rotations[x][y].BYTE;
            }
        }



        StringBuilder chartX2 = new StringBuilder();

        for (int i = 0; i < heightInCellCount-1; i++) {
            StringBuilder chartY2 = new StringBuilder("\n");

            for (int j = 0; j < widthInCellCount-1; j++) {
                int value = maze[j][i];

                if ((value & 0b10000) != 0b10000) chartY2.append(getStringFromFacings(value & 0b1111));
                else chartY2.append("   ");
            }

            chartX2.append(chartY2);
        }

        System.out.println(chartX2);

        final int[][] mazeLocal = maze.clone();

        for (int y = 0; y < heightInCellCount-1; y++) {
            for (int x = 0; x < widthInCellCount-1; x++) {
                // Did we pick west and will we not get an AIOOBException accessing array
                if (rotations[x][y] == WallFacing.WEST && x > 0) {
                    // If neighbor does not connect to west, connect it to east
                    if (!rotations[x][y].unpackAndTest(maze[x-1][y])) maze[x-1][y] |= rotations[x][y].OPPOSITE;
                    else { // else we cut the connection
                        // remove connection for the maze part we're looking at
                        maze[x][y] &= rotations[x][y].INVERTED;
                        // remove connection for the adjacent maze part
                        maze[x-1][y] &= rotations[x-1][y].INVERTED_OPPOSITE;
                    }
                }
                if (rotations[x][y] == WallFacing.NORTH && y > 0 ) {
                    if (!rotations[x][y].unpackAndTest(maze[x][y-1])) maze[x][y-1] |= rotations[x][y].OPPOSITE;
                    else {
                        maze[x][y] &= rotations[x][y].INVERTED;
                        maze[x][y-1] &= rotations[x][y-1].INVERTED_OPPOSITE;
                    }
                }
                if (rotations[x][y] == WallFacing.EAST && x < widthInCellCount-2 ) {
                    if (!rotations[x][y].unpackAndTest(maze[x+1][y])) maze[x+1][y] |= rotations[x][y].OPPOSITE;
                    else {
                        maze[x][y] &= rotations[x][y].INVERTED;
                        maze[x+1][y] &= rotations[x+1][y].INVERTED_OPPOSITE;
                    }
                }
                if (rotations[x][y] == WallFacing.SOUTH && y < heightInCellCount-2 ) {
                    if (!rotations[x][y].unpackAndTest(maze[x][y+1])) maze[x][y+1] |= rotations[x][y].OPPOSITE;
                    else {
                        maze[x][y] &= rotations[x][y].INVERTED;
                        maze[x][y+1] &= rotations[x][y+1].INVERTED_OPPOSITE;
                    }
                }
            }
        }//*/

        for (int x = 1; x < widthInCellCount-1; x++) {
            for (int y = 1; y < heightInCellCount-1; y++) {
                if (mazeLocal[x][y] == 0) {
                    if (mazeLocal[x-1][y] == 0) {
                        maze[x][y]   |= WallFacing.WEST.BYTE;
                        maze[x-1][y] |= WallFacing.WEST.OPPOSITE;
                    }

                    if (mazeLocal[x][y-1] == 0) {
                        maze[x][y]   |= WallFacing.NORTH.BYTE;
                        maze[x][y-1] |= WallFacing.NORTH.OPPOSITE;
                    }
                }
            }
        }

        /*for (int x = 0; x < widthInCellCount-1; x++) {
            for (int y = 0; y < heightInCellCount-1; y++) {
                // Did we pick west and will we not get an AOOBE accessing array
                if (rotations[x][y] == WallFacing.WEST && x > 0) {
                    // If neighbor does not connect to west, connect it to east
                    if (!rotations[x][y].unpackAndTest(maze[x-1][y])) maze[x-1][y] |= rotations[x][y].OPPOSITE;
                    else { // else we cut the connection
                        // remove connection for the maze part we're looking at
                        maze[x][y] &= rotations[x][y].INVERTED;
                        // remove connection for the adjacent maze part
                        maze[x-1][y] &= rotations[x-1][y].INVERTED_OPPOSITE;
                    }
                }
                if (rotations[x][y] == WallFacing.NORTH && y > 0 ) {
                    if (!rotations[x][y].unpackAndTest(maze[x][y-1])) maze[x][y-1] |= rotations[x][y].OPPOSITE;
                    else {
                        maze[x][y] &= rotations[x][y].INVERTED;
                        maze[x][y-1] &= rotations[x][y-1].INVERTED_OPPOSITE;
                    }
                }
                if (rotations[x][y] == WallFacing.EAST && x < widthInCellCount-2 ) {
                    if (!rotations[x][y].unpackAndTest(maze[x+1][y])) maze[x+1][y] |= rotations[x][y].OPPOSITE;
                    else {
                        maze[x][y] &= rotations[x][y].INVERTED;
                        maze[x+1][y] &= rotations[x+1][y].INVERTED_OPPOSITE;
                    }
                }
                if (rotations[x][y] == WallFacing.SOUTH && y < heightInCellCount-2 ) {
                    if (!rotations[x][y].unpackAndTest(maze[x][y+1])) maze[x][y+1] |= rotations[x][y].OPPOSITE;
                    else {
                        maze[x][y] &= rotations[x][y].INVERTED;
                        maze[x][y+1] &= rotations[x][y+1].INVERTED_OPPOSITE;
                    }
                }
            }
        }*/

        for (Diagonals diagonals : Diagonals.values()) {
            cornerClippings[diagonals.ordinal()][0] = random.nextInt(maximumClipping)+1;
            cornerClippings[diagonals.ordinal()][1] = random.nextInt(maximumClipping)+1;

            //maze[diagonals.operationY.convert(cornerClippings[diagonals.ordinal()][0], heightInCellCount)];

            for (int y = 0; y < cornerClippings[diagonals.ordinal()][0]; y++) {
                for (int x = 0; x < cornerClippings[diagonals.ordinal()][1]; x++) {
                    maze
                            [diagonals.operationX.convert(x, widthInCellCount-2)]
                            [diagonals.operationY.convert(y, heightInCellCount-2)]
                            |= 0b10000;
                }
            }
        }

        StringBuilder chartX = new StringBuilder();
        StringBuilder debugX = new StringBuilder();

        for (int i = 0; i < heightInCellCount-1; i++) {
            StringBuilder chartY = new StringBuilder("\n");
            StringBuilder debugY = new StringBuilder("\n");

            for (int j = 0; j < widthInCellCount-1; j++) {
                int value = maze[j][i];

                if ((value & 0b10000) != 0b10000) chartY.append(getStringFromFacings(value & 0b1111));
                else chartY.append("   ");

                debugY.append(value >= 0b10000 ? "      " : value > 0b111 ? " 0" : value > 0b11 ? " 00" : value > 0b1 ? " 000" : " 0000");
                if ((value & 0b10000) != 0b10000) debugY.append(Integer.toBinaryString(value));
            }

            chartX.append(chartY);
            debugX.append(debugY);
        }

        System.out.println(chartX);
        System.out.println(debugX);
    }

    private static String getStringFromFacings(int directions) {
        switch (directions & 0b1111) {
            case 0b0010:
                return " ╷ ";
            case 0b0001:
                return " ╶─";
            case 0b1000:
                return " ╵ ";
            case 0b0100:
                return "─╴ ";
            case 0b1001:
                return " └─";
            case 0b1100:
                return "─┘ ";
            case 0b0110:
                return "─┐ ";
            case 0b0011:
                return " ┌─";
            case 0b1101:
                return "─┴─";
            case 0b1110:
                return "─┤ ";
            case 0b0111:
                return "─┬─";
            case 0b1011:
                return " ├─";
            case 0b1010:
                return " │ ";
            case 0b0101:
                return "───";
            case 0b1111:
                return "─┼─";
            default:
                return " • ";
        }
    }

    protected enum WallFacing {
        EAST (0b0001, 0b0100, 0b1110, 0b1011, EnumFacing.EAST ),
        SOUTH(0b0010, 0b1000, 0b1101, 0b0111, EnumFacing.SOUTH),
        WEST (0b0100, 0b0001, 0b1011, 0b1110, EnumFacing.WEST ),
        NORTH(0b1000, 0b0010, 0b0111, 0b1101, EnumFacing.NORTH);

        private final int BYTE;
        private final int OPPOSITE;
        private final int INVERTED;
        private final int INVERTED_OPPOSITE;
        private final EnumFacing enumFacing;

        WallFacing(int bite, int oppositeBite, int inverted, int invertedOpposite, EnumFacing enumFacing) {
            this.BYTE = bite;
            this.OPPOSITE = oppositeBite;
            this.INVERTED = inverted;
            this.INVERTED_OPPOSITE = invertedOpposite;
            this.enumFacing = enumFacing;
        }

        protected boolean unpackAndTest(int directions) {
            return (this.BYTE & directions) == this.BYTE;
        }

        protected boolean unpackAndTestOpposite(int directions) {
            return (this.OPPOSITE & directions) == this.OPPOSITE;
        }

        public EnumFacing getEnumFacing() {
            return enumFacing;
        }
    }

    @Override
    protected void writeStructureToNBT(NBTTagCompound nbtTagCompound) {
        super.writeStructureToNBT(nbtTagCompound);

        NBTTagList mazeX = new NBTTagList();

        for (int x = 0; x < widthInCellCount-1; x++) {
            NBTTagList mazeY = new NBTTagList();

            for (int y = 0; y < heightInCellCount-1; y++) mazeY.appendTag(new NBTTagInt(maze[x][y]));

            mazeX.appendTag(mazeY);
        }

        nbtTagCompound.setInteger("mazeWidth", widthInCellCount);
        nbtTagCompound.setInteger("mazeHeight", heightInCellCount);
        nbtTagCompound.setTag("maze", mazeX);
    }

    @Override
    protected void readStructureFromNBT(NBTTagCompound nbtTagCompound, TemplateManager templateManager) {
        super.readStructureFromNBT(nbtTagCompound, templateManager);

        this.widthInCellCount = nbtTagCompound.getInteger("mazeWidth");
        this.heightInCellCount = nbtTagCompound.getInteger("mazeHeight");

        maze = new int[this.widthInCellCount-1][this.heightInCellCount-1];

        NBTTagList mazeX = nbtTagCompound.getTagList("maze", 9);

        for (int x = 0; x < widthInCellCount-1; x++) {
            NBTBase mazeY = mazeX.get(x);

            if (mazeY instanceof NBTTagList)
                for (int y = 0; y < heightInCellCount - 1; y++) maze[x][y] = ((NBTTagList) mazeY).getIntAt(y);
        }
    }
}
