# ![](./assets/logo.png)

## Save 1.0.0-rc.1 for Minecraft in-20100104  
*Please note: OrnitheMC has in-20100104 listed as in-20100105.*  
### Changes  
- Ported 1.0.0-release.1+in-20100110 to in-20100104.  
  - The following values will not be loaded, and will save default values:  
    - `CloudHeight`  
    - `SkyBrightness`  
    - `SurroundingGroundHeight`  
    - `SurroundingWaterType`  
    - `Entity/Fire`  
- Dead entities are no longer saved.  
- Updated Entity in Block fix.  
- Updated Converter surrounding heights.
- Added Save Block Items Config Option.  
  - Disabled by default, use the config screen to enable.  
  - When enabled, block items will be saved. However, you will need to save the world in 110 using Save 1.0.1 or higher to be compatible with vanilla.  

### How to Install
1. Goto [OrnitheMC](https://ornithemc.net/)'s website and download the latest installer.
2. Select the **Show snapshots** checkbox. (This may take a while to reload)
3. Select **in-20100110** as your Minecraft version.
4. Select your launcher type. You can either select the Official Minecraft Launcher or MultiMC/Prism.
5. Select **Quilt** as your Loader type. (This is important, this mod won't work on Fabric!)
6. Press Install and wait for your instance to be generated.
7. Add the `mclm_save-1.0.0-rc.1+in-20100110.jar` file to the instance's mods folder.
8. Launch and Enjoy!

### Issues
Please help us improve by submitting [bug reports](https://github.com/MCLegoMan/mclm_save/issues) if you encounter any issues.  
_This mod has been tested on Windows 10 and 11 (x64 native), and Linux (x64 virtual machine)._  

### Attributions
- [ClassicExplorer](https://github.com/bluecrab2/ClassicExplorer).
  - We include some code from ClassicExplorer with permission from bluecrab2 for our classic converter.
- [ReleaseTypeUtils](https://github.com/mclegoMan/releasetypeutils).
  - Licensed under [CC0-1.0](https://creativecommons.org/publicdomain/zero/1.0/legalcode.txt).
- [FlatLaf](https://github.com/JFormDesigner/FlatLaf/).
  - Licensed under [Apache 2.0](https://github.com/JFormDesigner/FlatLaf/blob/main/LICENSE).
- [Feather Mappings](https://github.com/OrnitheMC/feather-mappings).
  - Licensed under [CC0-1.0](https://github.com/OrnitheMC/feather-mappings/blob/main/LICENSE).
- [Quilt Loader](https://quiltmc.org/).
  - Licensed under [Apache 2.0](https://github.com/QuiltMC/quilt-loader/blob/develop/LICENSE).
- [Minecraft](https://www.minecraft.net/) [in-20100124-2310](https://minecraft.wiki/w/Java_Edition_Indev_0.31_20100124-2).
  - This mod uses some code from Minecraft in-20100124-2310, which was obtained using [Feather Mappings](https://github.com/OrnitheMC/feather-mappings), some classes and functions have been renamed to make them easier to use.

#  
Licensed under LGPL-3.0-or-later.

**This mod is not affiliated with/endorsed by Mojang Studios or Microsoft.**  
[Minecraft](https://minecraft.net/) is a trademark of Microsoft Corporation.  