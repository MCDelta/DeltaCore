package mcdelta.core;

import mcdelta.core.material.ItemMaterial;

public interface IContent
{
     void addContent ();
     
     
     
     
     void addMaterialBasedContent (ItemMaterial mat);
     
     
     
     
     void addRecipes ();




     void addMaterialBasedRecipes (ItemMaterial tmp);
}
