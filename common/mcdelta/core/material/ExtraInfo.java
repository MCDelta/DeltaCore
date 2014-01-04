package mcdelta.core.material;

import net.minecraft.util.EnumChatFormatting;

public class ExtraInfo
{
     private EnumChatFormatting nameColor;
     
     
     
     
     public ExtraInfo (EnumChatFormatting e)
     {
          this.nameColor = e;
     }
     
     
     
     
     public EnumChatFormatting nameColor ()
     {
          return this.nameColor;
     }
}
