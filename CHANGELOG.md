# Changelog

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
