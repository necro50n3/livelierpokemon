package necro.livelier.pokemon.fabric.behaviors;

import java.util.Iterator;
import java.util.List;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.FollowMobGoal;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.phys.AABB;

public class VillagerFollowDrifloonGoal extends FollowMobGoal {

    private Villager villagerEntity;
    List<PokemonEntity> pokemonList;
    int drifloonTicks;

    public VillagerFollowDrifloonGoal(AbstractVillager villagerEntity)
    {
        super(villagerEntity, 0.5, 0, 16);
        this.villagerEntity = (Villager) villagerEntity;
        drifloonTicks = 0;
    }

    @Override
    public boolean canUse()
    {
        if (!villagerEntity.isBaby())
        {
            villagerEntity.goalSelector.removeGoal(this);
            return false;
        }
        if (!getVillagerEntities().isEmpty())
            return false;
        pokemonList = getPokemonEntities();
        if (pokemonList.isEmpty())
            return false;
        this.followingMob = pokemonList.get(0);
        return true;
    }

    @Override
    public void start()
    {
        if (villagerEntity.distanceTo(this.followingMob) <= 4)
            drifloonTicks++;
        if (drifloonTicks >= 400)
            new Zombie(EntityType.ZOMBIE, villagerEntity.level).killedEntity((ServerLevel) villagerEntity.level, (LivingEntity) villagerEntity);

        super.start();
    }

    public List<PokemonEntity> getPokemonEntities()
    {
        AABB box = new AABB(villagerEntity.getX()-16,villagerEntity.getY()-16,villagerEntity.getZ()-16,villagerEntity.getX()+16,villagerEntity.getY()+16,villagerEntity.getZ()+16);
        List<PokemonEntity> list = villagerEntity.level().getEntitiesOfClass(PokemonEntity.class, box);
        Iterator<PokemonEntity> iterator = list.iterator();
        while (iterator.hasNext())
        {
            PokemonEntity pokemonEntity = (PokemonEntity) iterator.next();
            String name = pokemonEntity.getPokemon().getDisplayName().getString();
            if (!name.equals("Drifloon"))
            {
                iterator.remove();
            }
        }
        return list;
    }

    public List<Villager> getVillagerEntities()
    {
        AABB box = new AABB(villagerEntity.getX()-16,villagerEntity.getY()-16,villagerEntity.getZ()-16,villagerEntity.getX()+16,villagerEntity.getY()+16,villagerEntity.getZ()+16);
        List<Villager> list = villagerEntity.level().getEntitiesOfClass(Villager.class, box);
        Iterator<Villager> iterator = list.iterator();
        while (iterator.hasNext())
        {
            if (villagerEntity == (Villager) iterator.next())
            {
                iterator.remove();
            }
        }
        return list;
    }
}
