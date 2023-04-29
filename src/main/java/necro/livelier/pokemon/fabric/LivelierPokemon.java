package necro.livelier.pokemon.fabric;

import necro.livelier.pokemon.fabric.config.LivelierPokemonConfig;
import com.cobblemon.mod.common.Cobblemon;
//import com.cobblemon.mod.common.api.Priority;
//import com.cobblemon.mod.common.api.events.CobblemonEvents;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.mod.common.api.abilities.Ability;
import com.cobblemon.mod.common.api.types.ElementalType;
//import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import net.fabricmc.api.ModInitializer;
import net.minecraft.text.MutableText;
//import kotlin.Unit;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LivelierPokemon implements ModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger("livelier-pokemon");
    public static Map<String, Map<String,String>> map;
    public static Set<String> catSet;

    @Override
    public void onInitialize()
    {
        LOGGER.info("Initialising LivelierPokemon");
        try {
            this.createConfigFiles();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            String location = System.getProperty("user.dir");
            String file = "/config/livelierpokemon/LivelierPokemonConfig.json";
            Gson gson = new Gson();
            String json = new String(Files.readAllBytes(Paths.get(location + file)));
            map = gson.fromJson(json, HashMap.class);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            String location = System.getProperty("user.dir");
            String file = "/config/livelierpokemon/CatPokemonConfig.json";
            Gson gson = new Gson();
            String json = new String(Files.readAllBytes(Paths.get(location + file)));
            catSet = gson.fromJson(json, Set.class);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //this.onEntitySpawn();
    }
    /*
     * Future code to be implemented on Cobblemon Version 1.4
     * See @PokemonEntityMixin for current implementation
     */
    /*
    public void onEntitySpawn()
    {
        CobblemonEvents.INSTANCE.LEVEL_UP_EVENT.subscribe(Priority.NORMAL, event -> {
            PokemonEntity pokemonEntity = (PokemonEntity)event.getPokemon().getEntity();
            ArrayList<String> keyList = isNameInKey(getAttributes(pokemonEntity.getPokemon()));
            if (keyList.size() > 0)
            {
                for (String validKey : keyList)
                {
                    if (map.get(validKey).get("trigger").equals("onSend"))
                        new LivelierPokemonManager(pokemonEntity, map.get(validKey)).behaviourManager();
                }
            }    
            return Unit.INSTANCE;
        });
        CobblemonEvents.INSTANCE.POKEMON_ENTITY_SAVE.subscribe(Priority.NORMAL, event -> {
            PokemonEntity pokemonEntity = (PokemonEntity)event.getPokemonEntity();
            ArrayList<String> keyList = isNameInKey(getAttributes(pokemonEntity.getPokemon()));
            if (keyList.size() > 0)
            {
                for (String validKey : keyList)
                {
                    if (map.get(validKey).get("trigger").equals("onSpawn"))
                        new LivelierPokemonManager(pokemonEntity, map.get(validKey)).behaviourManager();
                }
            }     
            return Unit.INSTANCE;
        });
    }
    */

    public static ArrayList<String> isNameInKey(ArrayList<String> properties)
    {
        ArrayList<String> keyList = new ArrayList<String>();
        for (String property : properties)
        {
            if (map.containsKey(property))
                keyList.add(property);
        }
        return keyList;
    }

    public static ArrayList<String> getAttributes(Pokemon pokemon)
    {
        ArrayList<String> properties = new ArrayList<String>();
        Ability pokemonAbility = pokemon.getAbility();
        properties.add(pokemonAbility.getName());
        MutableText pokemonName = pokemon.getDisplayName();
        properties.add(pokemonName.getString());
        ElementalType pokemonPType = pokemon.getPrimaryType();
        properties.add(pokemonPType.getName());
        ElementalType pokemonSType = pokemon.getSecondaryType();
        if (pokemonSType != null)
        {
            if (!pokemonPType.getName().equals(pokemonSType.getName()))
                properties.add(pokemonSType.getName());
        }
        //LOGGER.info(pokemonName.getString() + " - " + pokemonAbility.getName() + " - " + pokemonPType.getName() + "/" + (pokemonSType != null ? pokemonSType.getName() : "none"));
        return properties;
    }

    public void createConfigFiles() throws IOException
    {
        File configFolder = new File(System.getProperty("user.dir") + "/config/livelierpokemon");
        File livelierPokemonConfigFile = new File(configFolder, "LivelierPokemonConfig.json");
        File catPokemonConfigFile = new File(configFolder, "CatPokemonConfig.json");
        Gson gson = new Gson();
        if (!configFolder.exists())
        {
            configFolder.mkdirs();
            try (PrintWriter out = new PrintWriter(new FileWriter(livelierPokemonConfigFile.getAbsolutePath()))) {
                out.write(gson.toJson(LivelierPokemonConfig.createHashMap(), new TypeToken<Map<String, Map<String,String>>>(){}.getType()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try (PrintWriter out = new PrintWriter(new FileWriter(catPokemonConfigFile.getAbsolutePath()))) {
                out.write(gson.toJson(LivelierPokemonConfig.createCatSet(), Set.class));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else
        {
            if (!livelierPokemonConfigFile.exists())
            {
                try (PrintWriter out = new PrintWriter(new FileWriter(livelierPokemonConfigFile.getAbsolutePath()))) {
                    out.write(gson.toJson(LivelierPokemonConfig.createHashMap(), new TypeToken<Map<String, Map<String,String>>>(){}.getType()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (!catPokemonConfigFile.exists())
            {
                try (PrintWriter out = new PrintWriter(new FileWriter(catPokemonConfigFile.getAbsolutePath()))) {
                    out.write(gson.toJson(LivelierPokemonConfig.createCatSet(), Set.class));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } 
        }
    }
}
