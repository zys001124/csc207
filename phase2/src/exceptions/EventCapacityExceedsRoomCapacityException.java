package exceptions;

/**
 * An exception to be thrown when an attempt is made to book
 * an event with a greater capacity than the room it will be hosted in
 */
public class EventCapacityExceedsRoomCapacityException extends Exception {
}
