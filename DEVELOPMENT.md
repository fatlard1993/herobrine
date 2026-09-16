# Herobrine - Development Guide

For what the mod is and how it plays, see [README.md](README.md).

## Installation

Install server-side alongside its declared dependencies (see `fabric.mod.json`). Vanilla clients need nothing. Version targets live in `gradle.properties` (Minecraft, loader) and `fabric.mod.json` (Java).

## Architecture

Three packages, one direction of dependency: `manifestation` -> `observation`, `manifestation` -> `world`, and nothing back.

```
observation/    Conditions            one sample of a player's surroundings, and what it is worth
world/          WorldAccess           the seam; every block, sound and light read goes through it
                TunnelCarver          the corridors
                SignatureLeavings     the sand, the put-out torches
                Vec3i, Direction      block positions and headings, ours rather than Mojang's
manifestation/  SightingScheduler     the clock: one sample per player per cadence
                PlayerTrace           per-player carry-over between samples
                AmbientDread          smoothed accumulation of the conditions he prefers
                ManifestationGate     the last word on whether he shows up
                StalkVector           where he stands when he does
```

None of this runs yet. `Main` arms `SightingScheduler` with `ManifestationGate.DEFAULT`, but
there is no `WorldAccess` implementation and nothing calls `SightingScheduler.tick` or `forget`,
so no player is ever sampled. `SignatureLeavings.pyramid` is written and not called by
`ManifestationGate.manifest`.

`WorldAccess` is the whole point of the layout. Mojang's mappings move between snapshots and the
behaviour does not, so the behaviour is written against that interface and the version binding is
kept on the other side of it. Nothing under `manifestation` or `world` names a Minecraft type,
which is also why this mod declares no Fabric API dependency.

The approach is a state machine, not a dice roll. `Presence` moves one step per evaluation in
either direction, and `AmbientDread` closes a fixed fraction of the distance to what the present
conditions can hold, per sample. Dread therefore approaches its ceiling asymptotically, and
`ManifestationGate` requires the ceiling itself. That is deliberate. He is a rumour, and a rumour
that resolves stops being one.
