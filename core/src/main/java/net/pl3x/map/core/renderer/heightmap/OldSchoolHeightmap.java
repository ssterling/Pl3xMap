package net.pl3x.map.core.renderer.heightmap;

import net.pl3x.map.core.world.Chunk;
import net.pl3x.map.core.world.Region;
import org.checkerframework.checker.nullness.qual.NonNull;

public class OldSchoolHeightmap extends Heightmap {
    public OldSchoolHeightmap() {
        super("old_school");
    }

    @Override
    @SuppressWarnings("DuplicatedCode")
    public int getColor(@NonNull Region region, int blockX, int blockZ) {
        Chunk.BlockData origin = region.getWorld().getChunk(region, blockX >> 4, blockZ >> 4).getData(blockX, blockZ);
        Chunk.BlockData west = region.getWorld().getChunk(region, (blockX - 1) >> 4, blockZ >> 4).getData(blockX - 1, blockZ);
        int heightColor = 0x22;
        if (origin != null && west != null) {
            heightColor = getColor(origin.getBlockY(), west.getBlockY(), heightColor, 0x22);
        }
        return heightColor << 24;
    }
}
