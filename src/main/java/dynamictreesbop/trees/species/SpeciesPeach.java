package dynamictreesbop.trees.species;

import biomesoplenty.api.enums.BOPTrees;
import biomesoplenty.api.item.BOPItems;
import biomesoplenty.common.block.BlockBOPLeaves;
import com.ferreusveritas.dynamictrees.ModBlocks;
import com.ferreusveritas.dynamictrees.api.TreeHelper;
import com.ferreusveritas.dynamictrees.api.TreeRegistry;
import com.ferreusveritas.dynamictrees.entities.EntityFallingTree;
import com.ferreusveritas.dynamictrees.models.ModelEntityFallingTree;
import com.ferreusveritas.dynamictrees.seasons.SeasonHelper;
import com.ferreusveritas.dynamictrees.systems.featuregen.FeatureGenFruit;
import com.ferreusveritas.dynamictrees.trees.Species;
import com.ferreusveritas.dynamictrees.trees.TreeFamily;
import com.ferreusveritas.dynamictrees.util.SafeChunkBounds;
import dynamictreesbop.DynamicTreesBOP;
import dynamictreesbop.ModConfigs;
import dynamictreesbop.ModContent;
import dynamictreesbop.dropcreators.DropCreatorFruit;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

import javax.annotation.Nullable;
import java.util.Random;

public class SpeciesPeach extends SpeciesFloweringOak {

	public static final int PEACH_FLOWER_COLOR = 0xffb8ec;

	public static float fruitingOffset = 0f; //summer

	public SpeciesPeach(TreeFamily treeFamily) {
		super(new ResourceLocation(DynamicTreesBOP.MODID, ModContent.PEACHOAK), treeFamily, ModContent.peachLeavesProperties[0]);

		addValidLeavesBlocks(ModContent.peachLeavesProperties);
		ModContent.peachLeavesProperties[0].setTree(treeFamily);
		ModContent.peachLeavesProperties[1].setTree(treeFamily);

		generateSeed();

		setFlowerSeasonHold(fruitingOffset - 0.5f, fruitingOffset + 0.5f);

		addDropCreator(new DropCreatorFruit(BOPItems.peach));

		addGenFeature(new FeatureGenFruit(ModContent.peachFruit).setRayDistance(4));
	}

	@Override
	public boolean generate(World world, BlockPos rootPos, Biome biome, Random random, int radius, SafeChunkBounds safeBounds) {
		if (ModConfigs.enablePeachTrees)
			return super.generate(world, rootPos, biome, random, radius, safeBounds);
		Species def = TreeRegistry.findSpecies(new ResourceLocation(DynamicTreesBOP.MODID, ModContent.FLOWERINGOAK));
		return def.generate(world, rootPos, biome, random, radius, safeBounds);
	}

	@Override
	public float seasonalFruitProductionFactor(World world, BlockPos pos) {
		float offset = fruitingOffset;
		return SeasonHelper.globalSeasonalFruitProductionFactor(world, pos, offset);
	}

	@Override
	public boolean testFlowerSeasonHold(World world, BlockPos pos, float seasonValue) {
		return SeasonHelper.isSeasonBetween(seasonValue, flowerSeasonHoldMin, flowerSeasonHoldMax);
	}

	@Override
	public int colorTreeQuads(int defaultColor, ModelEntityFallingTree.TreeQuadData treeQuad, @Nullable EntityFallingTree entity) {
		return treeQuad.bakedQuad.getTintIndex() != 0 ? PEACH_FLOWER_COLOR : defaultColor;
	}
	
	@Override
	public int saplingColorMultiplier(IBlockState state, IBlockAccess access, BlockPos pos, int tintIndex) {
		return tintIndex != 0 ? PEACH_FLOWER_COLOR : getLeavesProperties().foliageColorMultiplier(state, access, pos);
	}
}
