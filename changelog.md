# ![](./assets/logo.png)  

## Save 1.0.0-rc.5 for in-20100110
*fingers crossed*

### Changes  
- `xSpawn`, `ySpawn`, and `zSpawn` will now convert from classic.  
  - _Save was looking for `spawnX` instead of `xSpawn`._  
- The issue that spawned the player at 0,0,0 has been fixed.
  - When the entity is spawned under y:2, the entity will be instead spawned at x:128 (or half of width, whichever is smallest) z:128 (or half of length, whichever is smallest), and y: lowest air block + 1.

#  
Licensed under LGPL-3.0-or-later.  

**This mod is not affiliated with/endorsed by Mojang Studios or Microsoft.**  
