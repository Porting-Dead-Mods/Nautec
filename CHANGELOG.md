# Changelog

## [Unreleased]

### Added
- Four new ocean biomes, added to the world's biome layout without replacing
  any vanilla ocean outright:
  - **Abyssal Trench** - the deepest, coldest water, near-black fog you can
    barely see through, and far more Drowned than anywhere else.
  - **Bioluminescent Grove** - clear teal water, glow squid, kelp and sea
    pickles.
  - **Hydrothermal Vents** - murky warm water over a basalt and magma floor.
  - **Prismarine Reef** - bright shallow water at the edge of the drop-off,
    coral, tropical fish and prismarine outcrops.
- All four count as oceans, so salt water collection, the Deep Sea Drain,
  Crystal Geodes, Ocean Ruins and shipwrecks all work in them.
- `enableBiomeInjection` and `injectableWorldPresets` config options. Packs
  using a custom overworld preset should add it to the second one.
- Budding Prismarine, which grows Prismarine Buds and Clusters the way Budding
  Amethyst does. Clusters drop Prismarine Crystal Shards, so shards are now
  renewable. It generates rarely in all four new biomes.
- Six new plants: Deep Kelp, Luminescent Algae, Prismarine Frond, Vent
  Tubeworm, Abyssal Coral and Glow Polyp. Luminescent Algae and Glow Polyp
  give off light.
- Four new creatures: the schooling Silt Skipper, the drifting Lantern Jelly,
  the armoured Vent Crawler, and the Abyssal Maw, which hunts in the dark
  below y=40. They drop Luminous Membrane, Chitin Plate and Abyssal Organ.
- Ambient particles in three of the new biomes: vent bubbles, glow spores and
  abyssal motes.
- Twelve augments that were already in the mod but impossible to install now
  have body slots, parts and recipes: Hydraulic Leg, Servo Knee, Shock
  Absorber, Tendon Weave, Magnetic Coil Arm, Ender Coil Arm, Hydro Drill Arm,
  Trident Launcher Arm, Volley Trident Arm, Syringe Robot Arm, Buoyancy Tank
  and Auxiliary Ventricle.
- Three augments built from the new creatures' drops:
  - **Abyssal Eyes** (Abyssal Organ, eye slot) grants night vision, but only
    at depth. Near the surface it stays dormant.
  - **Photophore Skin** (Luminous Membrane, body slot) lights up every living
    thing near you while you are in water, through terrain.
  - **Vent Carapace** (4 Chitin Plates, head or body slot) adds armour,
    knockback resistance, and halves how long you burn.
- Two new guide entries: "Crafted Augments" for the twelve buildable parts and
  "Deep Fauna Augments" for the three organ augments.

### Fixed
- Augments that change your attributes or abilities no longer stop working
  after you die. Bonus Hearts, Step Up, Walking Speed and Creative Flight kept
  showing as installed after a respawn but had no effect until you reapplied
  them at the Augmentation Station.

### Changed
- The Spreading Trident augment is now on 'U' by default. It shared 'Y' with
  the Bouncing Trident augment, so both fired at once when installed together.

## [0.5.1]

### Fixed
- Spreading Tridents are no longer invisible when thrown.
- The Mutator and Bacterial Analyzer now respect their config settings for
  crafting speed and power requirement.
- Laser purity now drops right away when its source is removed, instead of
  sticking at the old value.
- Prismarine Sand now generates on the ocean floor as intended.
- Crystal Geodes and Ocean Ruins no longer generate on top of each other.
- Salt water can now be collected in modded ocean biomes.
- Machine particle effects no longer stutter when several machines run at
  once.

## [0.5.0]

### Changed
- Updated to Minecraft 26.1.

## [0.4.1]

### Fixed
- Augmentation Station no longer disconnects the player when clicking Apply
  without first selecting an augment slot. The Apply button is now disabled
  until a slot is highlighted in the side panel. ([FTB#12158])

### Internal
- Moved the Augmentation Station's Apply button into `init()` so it is built
  once per screen open instead of being re-added every render frame.

[FTB#12158]: https://github.com/FTBTeam/FTB-Modpack-Issues/issues/12158

## [0.4.0]

### Added
- Prism Monocle now also clears underwater fog (works in the head slot or in
  its curio slot). The Diving Helmet keeps its existing behavior. ([#27])

### Fixed
- Crates obtained from shipwrecks, ocean ruins, and ocean archaeology now
  generate loot when opened. ([#44])
- Game no longer crashes when reaching any GUI screen because Jade was missing
  translation entries for the mixer / aquatic catalyst / laser junction
  tooltip-config keys.
- Bio Reactor, Bacteria Incubation, and Bacteria Mutations recipe screens no
  longer show clipped or hidden tooltips when hovering bacteria slots
  (especially under EMI and TMRV). ([#41])
- Book recipe pages now show the correct recipes — fixed 13 stale recipe IDs
  across the Aquarine Steel tools and armor, Wrench, Prismarine Relay, Diving
  Chestplate Oxygen, and Etching Acid entries. ([#40])
- Last two pages of the Laser Power book entry now render their text and
  recipe contents. ([#39])
- Filled in missing display names for fluid blocks (Oil, Salt Water, EAS,
  Etching Acid) and for 13 augment types (Eldritch Heart, Magnet, Creative
  Flight, …). Fixed the "Eldritch_heart" book title typo. ([#38])
- Mixer Jade tooltip no longer prints an empty fluid line when a tank is
  empty.

### Performance / stability
- Fixed a memory leak in the etching-acid item processor; tracked entries are
  now evicted when the item entity leaves the level.
- Hardened null-safety in the Prism Monocle HUD overlay and the Crate block
  entity around world-transition / chunk-unload edge cases.

### Internal
- Refactored 30 Modonomicon book entries onto a shared `BaseNautecEntry` base
  class (~700 LoC of boilerplate removed).
- Standardized on `Nautec.rl(...)` for resource-location construction (83
  call-sites across 46 files).
- JEI recipe-category titles are now localizable.
- Added a gametest that validates every book recipe-id reference resolves to
  a real recipe.
- Removed dead code in `NTLootTables` and de-duplicated the
  Incubation / Mutation recipe builders.

[#27]: https://github.com/Porting-Dead-Mods/Nautec/issues/27
[#38]: https://github.com/Porting-Dead-Mods/Nautec/issues/38
[#39]: https://github.com/Porting-Dead-Mods/Nautec/issues/39
[#40]: https://github.com/Porting-Dead-Mods/Nautec/issues/40
[#41]: https://github.com/Porting-Dead-Mods/Nautec/issues/41
[#44]: https://github.com/Porting-Dead-Mods/Nautec/issues/44

## [0.3.3]

### Fixed
- Opening the Augment Viewer no longer crashes the game.
- Augments and the items used to install them no longer disappear after
  relogging.
- Augment effects (water breathing from Drowned Lungs, swim speed from Dolphin
  Fin, etc.) keep working reliably after installing multiple augments.

Thanks to FTB OceanBlock 2 for the report
([#12021](https://github.com/FTBTeam/FTB-Modpack-Issues/issues/12021)).
