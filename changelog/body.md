*Please make sure you use Quilt Loader and Minecraft in-20091223-1!*  
*OrnitheMC lists this version as in-20091223-1459.*  

## Changes  
- Ported 1.0.1+in-20091231-2 to in-20091223-1.   
- Block Items will be saved as `itemId` if save block items config is disabled.
  - If the config option is enabled, they will be saved as `blockId`.
  - This allows items to be saved no matter what. If you want the item to be loaded as the block in future versions use the config option and mclm_save in a future version, otherwise they will be converted to another item.  