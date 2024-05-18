# ![](./assets/logo.png)  

## Save 1.0.0-rc.2 for in-20100110  

### Changes  
- Blocks are now loaded into a `byte[]` array as bytes, instead of ByteFields in an ArrayField.  
  - This should make conversion of large worlds faster.  
- When converting a world, the Height, Length, and Width are now turned into a string before getting parsed as a short.
  - Some classic saves store these values as an int, whereas others store them as a short, this allows us to easily read either.
- Added an error catcher for a chunk sorter function in WorldRenderer.  
  - This function was causing issues when loading a large world at some positions, from my limited testing it seems fine.  
- The death screen will now close if the player has more than 0 health.
  - This fixes an issue where on loading a world after you have died, the death screen would still be open.
#  
Licensed under LGPL-3.0-or-later.  

**This mod is not affiliated with/endorsed by Mojang Studios or Microsoft.**  
