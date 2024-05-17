# ![](./assets/logo.png)  

## Save 1.0.0-rc.2 for in-20100110  

### Changes  
- Blocks are now loaded into a `byte[]` array as bytes, instead of ByteFields in an ArrayField.  
  - This should make conversion of large worlds faster.  
- Added an error catcher for a chunk sorter function in WorldRenderer.  
  - This function was causing issues when loading a large world at some positions, from my limited testing it seems fine.  

#  
Licensed under LGPL-3.0-or-later.  

**This mod is not affiliated with/endorsed by Mojang Studios or Microsoft.**  
