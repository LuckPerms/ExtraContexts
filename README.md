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
#### PlaceholderAPI
Returns the result for a defined set of placeholders.

e.g.

**config:**
```yml
placeholderapi-placeholders:
  allowflight: "%player_allow_flight%"
```

> allowflight=true

___
#### TownyAdvanced
#### `towny:in-town`
Returns true if player's location within a Town

e.g.

> towny:in-town=false

#### `towny:town`
Returns name of the town (if any) at a player's current location

e.g.

> towny:town=
>
> towny:town=Atlantis

**config:**
```yaml
towny-town: true
```

#### `towny:in-nation`
Returns true if player's location within a Nation

e.g.

> towny:in-nation=true

#### `towny:town`
Returns name of the nation (if any) at a player's current location

e.g.

> towny:nation=Rome

**config:**
```yaml
towny-nation: true
```

#### `towny:in-wilds`
Returns true if player's location in the wilderness

e.g.

> towny:in-wilds=false

**config:**
```yaml
towny-wilds: true
```