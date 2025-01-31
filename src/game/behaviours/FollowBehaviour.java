package game.behaviours;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.actions.MoveActorAction;
import edu.monash.fit2099.engine.actors.Behaviour;

/**
 * A class that figures out a MoveAction that will move the actor one step
 * closer to a target Actor.
 *
 * @author Riordan D. Alfredo
 * Modified by: Bryan Chow
 * @see edu.monash.fit2099.demo.mars.Application
 * <p>
 * Created by:
 */
public class FollowBehaviour implements Behaviour {

    private final Actor target;

    /**
     * Constructor.
     *
     * @param subject the Actor to follow
     */
    public FollowBehaviour(Actor subject) {
        this.target = subject;
    }

    /**
     * Returns a MoveAction that will move the actor one step closer to the target Actor.
     * If the actor cannot move closer to the target, or if the target or actor is not on the map,
     * it returns null.
     *
     * @param actor The actor executing the behavior.
     * @param map   The game map containing the Actor.
     * @return A MoveActorAction that moves the actor one step closer to the target, or null if no move is possible.
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {
        if (!map.contains(target) || !map.contains(actor))
            return null;

        Location here = map.locationOf(actor);
        Location there = map.locationOf(target);

        int currentDistance = distance(here, there);
        for (Exit exit : here.getExits()) {
            Location destination = exit.getDestination();
            if (destination.canActorEnter(actor)) {
                int newDistance = distance(destination, there);
                if (newDistance < currentDistance) {
                    return new MoveActorAction(destination, exit.getName());
                }
            }
        }

        return null;
    }

    /**
     * Compute the Manhattan distance between two locations.
     *
     * @param a the first location
     * @param b the first location
     * @return the number of steps between a and b if you only move in the four cardinal directions.
     */
    private int distance(Location a, Location b) {
        return Math.abs(a.x() - b.x()) + Math.abs(a.y() - b.y());
    }
}
