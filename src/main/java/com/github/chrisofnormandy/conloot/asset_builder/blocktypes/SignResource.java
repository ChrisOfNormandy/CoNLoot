// package com.github.chrisofnormandy.conloot.asset_builder.blocktypes;

// import com.github.chrisofnormandy.conlib.collections.JsonBuilder;
// import com.github.chrisofnormandy.conlib.collections.JsonBuilder.JsonObject;
// import com.github.chrisofnormandy.conloot.Main;
// import com.github.chrisofnormandy.conloot.asset_builder.AssetBuilder;

// public class BlockResource {
//     /**
//      * 
//      * @param name
//      * @param path
//      * @param builder
//      * @return
//      */
//     public static JsonObject blockstate(String name, JsonBuilder builder) {
//         JsonObject json = builder.createJsonObject();
//         json.addObject("variants").addObject("").set("model",
//                 Main.MOD_ID + ":block/" + name);
//         return json;
//     }

//     /**
//      * 
//      * @param name
//      * @param builder
//      * @return
//      */
//     public static JsonObject blockModel(String name, JsonBuilder builder) {
//         JsonObject json = builder.createJsonObject();

//         json.set("parent", "minecraft:block/cube_all");
//         json.addObject("textures").set("all", Main.MOD_ID + ":block/" + name);

//         return json;
//     }

//     /**
//      * 
//      * @param name
//      * @param builder
//      * @return
//      */
//     public static JsonObject blockModel(String name, String[] textures, JsonBuilder builder) {
//         JsonObject json = builder.createJsonObject();

//         json.set("parent", "minecraft:block/cube_all");
//         json.addObject("textures").set("all", Main.MOD_ID + ":block/" + textures[0]);

//         return json;
//     }

//     /**
//      * 
//      * @param name
//      * @param builder
//      * @return
//      */
//     public static JsonObject itemModel(String name, JsonBuilder builder) {
//         return builder.createJsonObject().set("parent", Main.MOD_ID + ":block/" + name);
//     }
// }
