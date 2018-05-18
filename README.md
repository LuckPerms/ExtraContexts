# ExtraContexts
Provides a number of extra "contexts" for [LuckPerms](https://github.com/lucko/LuckPerms).


## About
Contexts are a concept in LuckPerms that allows permissions to be set to apply only in certain circumstances. See [here](https://github.com/lucko/LuckPerms/wiki/Context) for more info.

This plugin (ExtraContexts) provides a number of contexts for Bukkit servers. The supported contexts are listed below.


## Downloads and Usage

* The plugin can be downloaded from [Jenkins](https://ci.lucko.me/job/ExtraContexts/). [[direct link](https://ci.lucko.me/job/ExtraContexts/lastSuccessfulBuild/artifact/target/ExtraContexts.jar)]
* To use it, just add it to your plugins folder and enable the contexts you want to use.

##### How do I check if it's working?
A player's "current contexts" are listed when you run `/lp user <user> info`.


## Contexts
___
#### `wg-region`
Returns the name of each WorldGuard region the player is currently in

e.g.

> wg-region=spawn

___
#### `gamemode`
Returns the players current gamemode

e.g.

> gamemode=creative

___
#### `dimension`
Returns the type of the players current world

e.g.

> dimension=nether

___
