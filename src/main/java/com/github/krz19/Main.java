package com.github.krz19;
import com.github.krz19.WorldGen.WorldGenCities;
import com.github.krz19.lib.ModVariables;

import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLInitializationEvent;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;

@Mod(modid = ModVariables.MOD_ID,name= ModVariables.MOD_Name, version = ModVariables.MOD_Version)

public class Main
{
    @Mod.Instance
    public static Main instance = new Main();


    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        //Item and block init and register
        //config

    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        //Proxy, TileEntity, entity, GUI and Packet Registration
        new WorldGenCities("AbandonedCities");
    }
    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {

    }
}