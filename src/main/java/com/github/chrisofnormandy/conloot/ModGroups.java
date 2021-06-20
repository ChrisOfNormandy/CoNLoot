package com.github.chrisofnormandy.conloot;

import com.github.chrisofnormandy.conlib.itemgroup.Groups;
import com.github.chrisofnormandy.conlib.registry.Items;

import net.minecraft.item.Item;

public class ModGroups {
    public static final Groups itemGroup = Groups.createGroup("item_group", Items.register("item_group_icon", new Item.Properties()));
    public static final Groups toolGroup = Groups.createGroup("tool_group", Items.register("tool_group_icon", new Item.Properties()));
    public static final Groups blockGroup = Groups.createGroup("block_group", Items.register("block_group_icon", new Item.Properties()));
    public static final Groups cropGroup = Groups.createGroup("crop_group", Items.register("crop_group_icon", new Item.Properties()));
}
