/*
 * Copyright 2014 Matthew Collins
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.co.thinkofdeath.thinkcraft.shared.block.blocks;

import uk.co.thinkofdeath.thinkcraft.shared.Face;
import uk.co.thinkofdeath.thinkcraft.shared.IMapViewer;
import uk.co.thinkofdeath.thinkcraft.shared.Texture;
import uk.co.thinkofdeath.thinkcraft.shared.block.Block;
import uk.co.thinkofdeath.thinkcraft.shared.block.BlockFactory;
import uk.co.thinkofdeath.thinkcraft.shared.block.states.EnumState;
import uk.co.thinkofdeath.thinkcraft.shared.block.states.StateKey;
import uk.co.thinkofdeath.thinkcraft.shared.block.states.StateMap;

public class BlockSandstone extends BlockFactory {

    public final StateKey<Variant> VARIANT = stateAllocator.alloc("variant", new EnumState<>(Variant.class));

    private final Texture[] textures;
    private final Texture sandstoneTop;
    private final Texture sandstoneBottom;

    public BlockSandstone(IMapViewer iMapViewer) {
        super(iMapViewer);

        textures = new Texture[Variant.values().length];
        for (Variant variant : Variant.values()) {
            textures[variant.ordinal()] = iMapViewer.getTexture("sandstone_" + variant.texture);
        }
        sandstoneTop = iMapViewer.getTexture("sandstone_top");
        sandstoneBottom = iMapViewer.getTexture("sandstone_bottom");
    }

    @Override
    protected Block createBlock(StateMap states) {
        return new BlockImpl(states);
    }

    public static enum Variant {
        DEFAULT("normal"),
        CHISELED("carved"),
        SMOOTH("smooth");

        private final String texture;

        Variant(String texture) {
            this.texture = texture;
        }

        @Override
        public String toString() {
            return super.toString().toLowerCase();
        }
    }

    private class BlockImpl extends Block {

        BlockImpl(StateMap state) {
            super(BlockSandstone.this, state);
        }

        @Override
        public int getLegacyData() {
            return getState(VARIANT).ordinal();
        }

        @Override
        public Texture getTexture(Face face) {
            if (face == Face.TOP) {
                return sandstoneTop;
            } else if (face == Face.BOTTOM) {
                return sandstoneBottom;
            }
            return textures[getState(VARIANT).ordinal()];
        }
    }
}