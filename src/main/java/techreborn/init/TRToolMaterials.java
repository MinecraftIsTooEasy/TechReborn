/*
 * This file is part of TechReborn, licensed under the MIT License (MIT).
 *
 * Copyright (c) 2020 TechReborn
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package techreborn.init;

import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import techreborn.TechReborn;

import static net.minecraft.item.ToolMaterials.*;

/**
 * We use custom materials to allow LevelZ to tweak the mining level of our tools.
 */
public class TRToolMaterials {
	public static final ToolMaterial BASIC_CHAINSAW = copy(IRON, "basic_chainsaw");
	public static final ToolMaterial BASIC_DRILL = copy(IRON, "basic_drill");
	//MITE CHANGE
	public static final ToolMaterial BASIC_JACKHAMMER = copy(MITHRIL, "basic_jackhammer");

	public static final ToolMaterial ADVANCED_CHAINSAW = copy(MITHRIL, "advanced_chainsaw");
	public static final ToolMaterial ADVANCED_DRILL = copy(MITHRIL, "advanced_drill");
	public static final ToolMaterial ADVANCED_JACKHAMMER = copy(MITHRIL, "advanced_jackhammer");

	public static final ToolMaterial INDUSTRIAL_CHAINSAW = copy(MITHRIL, "industrial_chainsaw");
	public static final ToolMaterial INDUSTRIAL_DRILL = copy(MITHRIL, "industrial_drill");
	public static final ToolMaterial INDUSTRIAL_JACKHAMMER = copy(MITHRIL, "industrial_jackhammer");

	public static final ToolMaterial ROCK_CUTTER = copy(MITHRIL, "rock_cutter");
	public static final ToolMaterial NANOSABER = copy(MITHRIL, "nanosaber");
	public static final ToolMaterial OMNI_TOOL = copy(MITHRIL, "omni_tool");
	//END

	public static ToolMaterial copy(ToolMaterial material, String id) {
		return new ToolMaterial() {
//			@Override
//			public int getDurability() {
//				return material.getDurability();
//			}

			//MITE CHANGE
			@Override
			public int getRepairDurability() {
				return 12800;
			}

			@Override
			public int getAcidResistance() {
				return 40;
			}

			//END

			@Override
			public float getMiningSpeedMultiplier() {
				return material.getMiningSpeedMultiplier();
			}

			@Override
			public float getAttackDamage() {
				return material.getAttackDamage();
			}

			@Override
			public int getMiningLevel() {
				return material.getMiningLevel();
			}

			@Override
			public int getEnchantability() {
				return material.getEnchantability();
			}

			@Override
			public Ingredient getRepairIngredient() {
				return material.getRepairIngredient();
			}

			// This allows LevelZ to identify the material.
			@Override
			public String toString() {
				return TechReborn.MOD_ID + ":" + id;
			}
		};
	}
}
