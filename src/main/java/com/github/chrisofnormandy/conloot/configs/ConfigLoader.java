package com.github.chrisofnormandy.conloot.configs;

public class ConfigLoader {
    public String name;
    public ConfigOptions options;

    public ConfigLoader(String name, ConfigOptions options) {
        this.name = name;
        this.options = options;
    }

    public ConfigLoader(String name) {
        this.name = name;
        this.options = new ConfigOptions();
    }
}
