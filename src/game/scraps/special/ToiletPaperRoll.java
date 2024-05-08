package game.scraps.special;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import game.types.Buyable;
import game.utils.BuyUtils;
import game.utils.RandomUtils;

import java.util.Scanner;

/**
 * A toilet paper roll item that can be purchased by an actor.
 * There is a chance of receiving a discount when attempting to buy this item.
 * This class implements the Buyable interface.
 */
public class ToiletPaperRoll extends Item implements Buyable {
    private static final int WORTH_IN_CREDITS = 5;
    private static final double DISCOUNT_CHANCE = 0.75;
    private static final int DISCOUNTED_COST = 1;

    /**
     * Constructs a new ToiletPaperRoll instance.
     */
    public ToiletPaperRoll() {
        super("Toilet Paper Roll", 's', true);
    }

    /**
     * Attempts to buy this toilet paper roll for the given actor.
     * There is a 75% chance of receiving a discount on the purchase.
     *
     * @param actor The actor attempting to buy the toilet paper roll.
     * @return A message indicating the result of the purchase attempt.
     */
    @Override
    public String buy(Actor actor) {
        double randDouble = RandomUtils.getRandomDouble();
        Scanner scanner = new Scanner(System.in);

        if (randDouble < DISCOUNT_CHANCE) {
            System.out.println("The computer terminal is offering you a " + this + " for " + DISCOUNTED_COST + " credits. Do you want to purchase it? (y/n)");
            String choice = scanner.nextLine().toLowerCase();

            if (choice.equals("y") && actor.getBalance() >= DISCOUNTED_COST) {
                return BuyUtils.buyItem(actor, this, DISCOUNTED_COST);
            } else {
                return "Purchase cancelled.";
            }
        } else {
            return BuyUtils.buyItem(actor, this, WORTH_IN_CREDITS);
        }
    }

    /**
     * Returns the cost of this toilet paper roll in credits.
     *
     * @return The cost of this toilet paper roll in credits.
     */
    @Override
    public int getCost() {
        return WORTH_IN_CREDITS;
    }
}