package game.scraps.special;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.ConsumeAction;
import game.actions.SellAction;
import game.types.Ability;
import game.types.Consumable;
import game.types.Sellable;

/**
 * Represents a Large Fruit item that can be consumed to heal the actor.
 * The Large Fruit is a type of item that implements the Consumable interface.
 */
public class LargeFruit extends Item implements Consumable, Sellable {
    private static final int HIT_POINTS = 2;
    private static final int CREDITS_TO_SELL = 30;

    /**
     * Constructs a new LargeFruit object.
     */
    public LargeFruit() {
        super("Large Fruit", 'O', true);
    }

    /**
     * Returns the list of actions that can be performed with this item.
     *
     * @param owner The actor that owns this item.
     * @return The list of allowable actions for this item.
     */
    @Override
    public ActionList allowableActions(Actor owner) {
        ActionList actionList = new ActionList();
        actionList.add(new ConsumeAction(this));
        return actionList;
    }

    /**
     * Handles the consumption of this item by the actor.
     *
     * @param actor The actor consuming this item.
     * @return A description of the consumption effect.
     */
    @Override
    public String handleConsume(Actor actor) {
        actor.heal(HIT_POINTS);
        actor.removeItemFromInventory(this);
        return actor + " is healed by " + HIT_POINTS + " hit points.";
    }

    @Override
    public String sell(Actor actorSelling, Actor actorToSellTo) {
        actorSelling.removeItemFromInventory(this);
        actorToSellTo.addItemToInventory(this);
        actorSelling.addBalance(CREDITS_TO_SELL);
        return this + " was sold to " + actorToSellTo + " for " + CREDITS_TO_SELL + " credits.";
    }

    @Override
    public int getSellCost() {
        return CREDITS_TO_SELL;
    }

    @Override
    public ActionList allowableActions(Actor otherActor, Location location) {
        ActionList actionList = new ActionList();
        if (otherActor.hasCapability(Ability.PURCHASE_ITEMS)) {
            actionList.add(new SellAction(otherActor,this));
        }
        return actionList;
    }
}
