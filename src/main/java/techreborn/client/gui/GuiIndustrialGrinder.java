/*
 * This file is part of TechReborn, licensed under the MIT License (MIT).
 *
 * Copyright (c) 2018 TechReborn
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

package techreborn.client.gui;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import reborncore.RebornCoreClient;
import reborncore.client.gui.builder.GuiBase;
import reborncore.client.gui.builder.widget.GuiButtonExtended;
import reborncore.client.gui.guibuilder.GuiBuilder;
import reborncore.client.multiblock.Multiblock;
import reborncore.client.multiblock.MultiblockRenderEvent;
import reborncore.client.multiblock.MultiblockSet;
import techreborn.init.TRContent;
import techreborn.blockentity.machine.multiblock.IndustrialGrinderBlockEntity;

public class GuiIndustrialGrinder extends GuiBase {

	IndustrialGrinderBlockEntity blockEntity;

	public GuiIndustrialGrinder(int syncID, final PlayerEntity player, final IndustrialGrinderBlockEntity blockEntity) {
		super(player, blockEntity, blockEntity.createContainer(syncID, player));
		this.blockEntity = blockEntity;
	}

	@Override
	public void init() {
		super.init();
		RebornCoreClient.multiblockRenderEvent.setMultiblock(null);
	}

	@Override
	protected void drawBackground(final float f, final int mouseX, final int mouseY) {
		super.drawBackground(f, mouseX, mouseY);
		final Layer layer = Layer.BACKGROUND;
		
		// Battery slot
		drawSlot(8, 72, layer);
		// Liquid input slot
		drawSlot(34, 35, layer);
		// Liquid output slot
		drawSlot(34, 55, layer);
		// Solid material input slot
		drawSlot(84, 43, layer);
		// Output slots
		drawSlot(126, 18, layer);
		drawSlot(126, 36, layer);
		drawSlot(126, 54, layer);
		drawSlot(126, 72, layer);

		builder.drawJEIButton(this, 158, 5, layer);
		if (blockEntity.getMultiBlock()) {
			builder.drawHologramButton(this, 6, 4, mouseX, mouseY, layer);
		}
	}

	@Override
	protected void drawForeground(final int mouseX, final int mouseY) {
		super.drawForeground(mouseX, mouseY);
		final Layer layer = Layer.FOREGROUND;

		builder.drawProgressBar(this, blockEntity.getProgressScaled(100), 100, 105, 47, mouseX, mouseY, GuiBuilder.ProgressDirection.RIGHT, layer);
		builder.drawTank(this, 53, 25, mouseX, mouseY, blockEntity.tank.getFluidInstance(), blockEntity.tank.getCapacity(), blockEntity.tank.isEmpty(), layer);
		if (blockEntity.getMultiBlock()) {
			addHologramButton(6, 4, 212, layer).clickHandler(this::onClick);
		} else {
			builder.drawMultiblockMissingBar(this, layer);
			addHologramButton(76, 56, 212, layer).clickHandler(this::onClick);
			builder.drawHologramButton(this, 76, 56, mouseX, mouseY, layer);
		}
		builder.drawMultiEnergyBar(this, 9, 19, (int) blockEntity.getEnergy(), (int) blockEntity.getMaxPower(), mouseX, mouseY, 0, layer);
	}

	public void onClick(GuiButtonExtended button, double mouseX, double mouseY){
		if (GuiBase.slotConfigType == SlotConfigType.NONE) {
			if (RebornCoreClient.multiblockRenderEvent.currentMultiblock == null) {
				{
					// This code here makes a basic multiblock and then sets to the selected one.
					final Multiblock multiblock = new Multiblock();
					BlockState standardCasing = TRContent.MachineBlocks.BASIC.getCasing().getDefaultState();
					BlockState reinforcedCasing = TRContent.MachineBlocks.ADVANCED.getCasing().getDefaultState();
					
					addComponent(0, -1, 0, standardCasing, multiblock);
					addComponent(1, -1, 0, standardCasing, multiblock);
					addComponent(0, -1, 1, standardCasing, multiblock);
					addComponent(-1, -1, 0, standardCasing, multiblock);
					addComponent(0, -1, -1, standardCasing, multiblock);
					addComponent(-1, -1, -1, standardCasing, multiblock);
					addComponent(-1, -1, 1, standardCasing, multiblock);
					addComponent(1, -1, -1, standardCasing, multiblock);
					addComponent(1, -1, 1, standardCasing, multiblock);

					addComponent(0, 0, 0, Blocks.WATER.getDefaultState(), multiblock);
					addComponent(1, 0, 0, reinforcedCasing, multiblock);
					addComponent(0, 0, 1, reinforcedCasing, multiblock);
					addComponent(-1, 0, 0, reinforcedCasing, multiblock);
					addComponent(0, 0, -1, reinforcedCasing, multiblock);
					addComponent(-1, 0, -1, reinforcedCasing, multiblock);
					addComponent(-1, 0, 1, reinforcedCasing, multiblock);
					addComponent(1, 0, -1, reinforcedCasing, multiblock);
					addComponent(1, 0, 1, reinforcedCasing, multiblock);

					addComponent(0, 1, 0, standardCasing, multiblock);
					addComponent(0, 1, 0, standardCasing, multiblock);
					addComponent(1, 1, 0, standardCasing, multiblock);
					addComponent(0, 1, 1, standardCasing, multiblock);
					addComponent(-1, 1, 0, standardCasing, multiblock);
					addComponent(0, 1, -1, standardCasing, multiblock);
					addComponent(-1, 1, -1, standardCasing, multiblock);
					addComponent(-1, 1, 1, standardCasing, multiblock);
					addComponent(1, 1, -1, standardCasing, multiblock);
					addComponent(1, 1, 1, standardCasing, multiblock);

					final MultiblockSet set = new MultiblockSet(multiblock);
					RebornCoreClient.multiblockRenderEvent.setMultiblock(set);
					RebornCoreClient.multiblockRenderEvent.parent = blockEntity.getPos();
					MultiblockRenderEvent.anchor = new BlockPos(
							this.blockEntity.getPos().getX()
									- Direction.byId(this.blockEntity.getFacingInt()).getOffsetX() * 2,
							this.blockEntity.getPos().getY() - 1, this.blockEntity.getPos().getZ()
									- Direction.byId(this.blockEntity.getFacingInt()).getOffsetZ() * 2);
				}
			} else {
				RebornCoreClient.multiblockRenderEvent.setMultiblock(null);
			}
		}
	}

	public void addComponent(final int x, final int y, final int z, final BlockState blockState, final Multiblock multiblock) {
		multiblock.addComponent(new BlockPos(x, y, z), blockState);
	}

}
