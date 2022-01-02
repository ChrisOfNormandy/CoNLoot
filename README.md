# Welcome to CoNLoot

**CoNLoot** is a bit of a misnomer, because this is actually a "make your own mod" mod.

I have provided basic access points for registering new content into the game using methods straight from config files in TOML format.

The following sections outline a small portion of the code within this project in a small amount. For further documentation, see the additional markdown files in each
section of the codebase.

# Registering New Content - Configs

When first starting the game with this mod enabled, multiple configs are generated in the `configs/` directory. Each file is a `.toml` file prefixed with `conloot_` with the name of the content "category."

For example, `conloot_blocks` relates to registering new block content. `conloot_items` is any item that cannot be placed. Simple.

To register a new item (or block, which adds a block item, you get the idea), simply supply the name of the item in the list relative to the type of content you desire.

That's worded horribly, but here's an example:

```
    [Blocks.Suite."String Lists"]
        
        wood_suite_list = ["testblock"]

        stone_suite_list = []
```

The above is an example section of the `conloot_blocks.toml` file. "Wood suite list" refers to adding a log, stripped log, wood, stripped wood, planks, stairs, slab, button, pressure plate, fence, fence gate, trapdoor and door.

***Note***
In the future, these items may be configurable in another config, so take this guidence as a basic "how to" for adding a single item _or_ a suite of items. Do not expect this method to perform the exact same outcome for each list, as these features and functions may change. However, the method of registering new content (add name in quotes inside square brackets) will remain constant.

***

Each field is organized by subgroup, where each subgroup is organized by field data type. For instance, the subgroup "Gems" contains boolean properties and string list properties. As such, the format of each property name would be `Gems."String Lists"` or `Gems.Flags`, or something similar.

There are multiple different available config property types as defined by CoNLib, the library mod responsible for much of the heavy lifting of this mod project. Documentation can be found ~~here~~. 

**Strings** are any format of content encapsulated in double quotation marks, such as `"string"`.
**Integers** are whole numbers, such as `1`, `100`, etc...
**Doubles** are numbers that include a decimal. In some code instances, a float value must be supported. The largest difference between a double and float is decimal precision, where doubles are more precise. As such, when needed, floats are converted from doubles in the config.
**Booleans**, also known as **Flags**, are `true` or `false` values typically used for enabling or disabling content.

**Integer Ranges** are config values that are locked between two integers, such as a number between 0 and 10.

**String Lists** are lists of strings (duh) formatted as `["a", "b", "c"]`.
**Integer Lists** are lists of integers formatted as `[1, 2, 3]`.

***

Lists are used for a variety of operations within the mod, such as supplying multiple values to single functions, or providing a list of items to generate content for.
For example, string lists of colors are used for generating generic textures for content in the game. Supplying a single color will use only the single color when generating the texture. Providing more than one will use multiple. Simple.

For instances where lists are used for generating additional content, such as the main config file, a blank list is acceptable. This is not the case for ones that require at least one item. If the default config generated contains any values, chances are it requires at least one value within the list.

# Registering New Content - Generating Resources

When content is provided in the string lists for generating content in game, an individual default config will be generated in a subdirectory within the `./configs` game directory.

For example, adding a gem resource to the game will generate a config file named after the resource in the directory `./configs/conloot/gems`. A resource named `sapphire` will create a config named `sapphire.toml` that contains properties for the ore, item, tools and other content-related features.

***
**When generating new game content, be aware that once the content has been added to a world, it may become unstable if removed.**
***

# Registering New Content - Order of Execution

To add new content to the game, the order of events is fairly trivial.

First, add all the content, by name, to the main config - `conloot.toml`. 
Use a generic resource name such as `my_block` or `spruce_wood_chair`. This name is **NOT** what is displayed in the game - it simply acts as the registry name for the item. For vanilla dirt, the resource name is `minecraft:dirt`. The name you provide in this config is used for this name, which will be similar to `conloot:my_block` or `conloot:spruce_wood_chair`.

When the readable name is created in the `en_us.json` file in the resource pack, it takes all underscores and replaces them with spaces, then capitalizes each first letter of each word. As such, `my_block` becomes `My Block` and `spruce_wood_chair` becomes `Spruce Wood Chair`. Each name must be unique - duplicates are not allowed. Do not name content like `my_block_1` or `my_block1`, as this naming scheme does not accurately describe the type of content. Instead, use a naming scheme such as `large_my_block` or `red_my_block`.

***
After you have added all the basic content items you would like generated, run the game. This will generate all the default configs for each content item in the `./configs/conloot` directory.

Close the game and make all desired changes to each of the configs.

After you have finished all desired changes, start the game again.
This process can be repeated as many times as you would like, so testing is advised during this portion of setup.

***
Finally, to finalize your content in a game, you must add the data pack to the game save. This can be done by opening the `./saves` directory client side or `world` on dedicated servers. Within the save is a folder `datapacks`.

The datapack for the mod is released in a directory `resources` in the game directory. Within should be a folder called `conloot_datapack` or something similar. Copy this folder and paste it within the `datapacks` directory in your game save or server world.

To enable, ensure the datapack was added correctly using the following in game or in the terminal:
> /datapack list

There should be an item available called `files:conloot_datapack`. To enable:
> /datapack enable files:conloot_datapack

The datapack includes all server-side configurations for adding content. Clients do not require this ***unless*** it is a singleplayer world (because it goes within the game save, and clients connecting to a server do not have access to this).

***

The resource pack generated by the mod is automatically placed in a client's `./resourcepacks` directory. Enabling this is done similarly to any other resource pack, by clicking the arrow on the icon. Everything listed on the right side of the resource pack menu is enabled.
This pack contains blockstate and model content and must be enabled by clients to render content in game correctly.

However, the textures generated by the mod can be overwritten by another resource pack by listing it _after_ the default resource pack (meaning it is the last texture enabled for those resources). Do not make changes to the default assets within the generated resource pack, as they may be overwritten.

# Naming Conventions

The following placeholders are available for registering content names in a specific manner, or registering multiple items with a similar naming scheme.

- `[x-y]` : provides a range of numbers from `x` to `y` and applies the number to the string. This can be used to register multiple copies of an object, such as "brick_style_0", "brick_style_1," etc. **1

- `{dyed}` : registers an item with the name of each available color (16 in total). The portion defined by this tag is replaced with the color, so "{dyed}wood" would become "redwood," "yellowwood", "bluewood," etc.

- `@` : when supplying names for textures, you can supply this character to insert the mod name. For example, "@:stone" would become "conloot:stone." This is useful for reusing existing textures.

- `%` : when supplying names for textures, you can supply this character to insert the name of the item. For example, "@_log" for an item "redwood" would become "redwood_log." This is useful for reusing existing textures (such as a log block and a wood block, both using a shared 'bark' texture).

- `&` : when supplying names for textures, you can supply this character to insert the name of the item _before_ the first underscore. Similar to `%`, this takes names like "archane_bricks" and only supplies "archane." If no underscore exists, it will use the full name ("arcanebricks" would stay as provided).

- `>` : when supplying names for textures, you can supply this character to state the string preceding the char should be aliased as the string proceding. For example, "tex1>tex2" would pull a texture named "tex1" and create a copy named "tex2." This can be used with other chars to create a "relative" texture with a unique name.

All textures can be supplied as full "paths" such as "minecraft:stone" or "mod:block." Without the mod ID, the mod will generate a new texture with the given name. As such, "stone" would become "conloot:stone," but "minecraft:stone" will use the vanilla stone texture.

You can also provide multiple textures separated with a semicolon (;).

***Notes***

1. Although this naming scheme is frowned upon in 1.13+ versions of Minecraft, some blocks and objects like tall plant tops and bamboo tops (the part that grows) cannot be obtained. As such, their names do not matter in the long term. Too, names viewed by users are supplied in a lang file, so their registry names are ultimately unimportant.