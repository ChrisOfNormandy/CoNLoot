# Welcome to CoNLoot

**CoNLoot** is a bit of a misnomer, because this is actually a "make your own mod" mod.

I have provided basic access points for registering new content into the game using methods straight from config files in TOML format.

In the main config, named `conloot.toml` in the `./configs` game directory, there are a handful of lists and properties you can adjust to add content to the game.

The following sections outline a small portion of the code within this project in a small amount. For further documentation, see the additional markdown files in each
section of the codebase, the PDF versions downloadable from ~~my site~~, or the documentation I have provided online.

# Registering New Content - Configs

The following is a basic example of a config file (as of 15-5-21 v1.0) in TOML format:
```toml
[Blocks]

	[Blocks."String Lists"]
		#A list of slabs.
		slab_list = []
		#A list of walls.
		wall_list = []
		#A list of blocks.
		block_list = []
		#A list of content that should generate a block, slab, stair, and depending on type, wall or fence.
		suite_list = []
		#A list of stairs.
		stairs_list = []

[Gems]

	[Gems."String Lists"]
		#A list of gem types.
		gem_list = []
		#A list of custom gem types.
		alt_gem_list = []

	[Gems.Flags]
		#Should crops be a thing?
		allow_crops = true
		#Should generic blocks be a thing?
		allow_blocks = true
		#Should metals be a thing?
		allow_metals = true
		#Should gems be a thing?
		allow_gems = true

[Metals]

	[Metals."String Lists"]
		#A list of custom metal types.
		alt_metal_list = []
		#A list of metal types.
		metal_list = []

[Crops]

	[Crops."String Lists"]
		#A list of crops.
		crop_list = []
```

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