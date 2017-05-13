twilightforest
==============

Twilight Forest repository

notes for contributors:

* Here are current goals, in necessary order of completion
* Don't let yourself get distracted by lower priority goals while dealing with higher priority ones. This is how most ports burn out.
* MINOR hacks to get it to run are okay for now, just put a todo on it

1. modernize all code. There's LOTS of copying from vanilla in the old codebase, and all of it must be clearly marked a "// [VanillaCopy]" comment stating what changed and why, or modernized
    a) Most notably, lots of bosses contain lots of copying of the old dragon logic and other old pre-1.8 AI system stuff
    b) the custom particle system needs to switch to an enum
1. get all serverside "logic" code compiling
    a) entities
    b) worldgen
    c) items/blocks
2. get it to start up (commenting out rendering as needed)
3. get it to enter the dimension
4. switch entity drops to loot tables
5. fix all the easy models
6. fix all the hard models and rendering
7. port to 1.11
    * why is this so low? 1.11 is a relatively brain-dead port, but it's also extremely tedious - the ROI is pretty tiny.
    * so deferring it in favour of actually getting it to run first
    * this may change