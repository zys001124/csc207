package controllers;

/**
 * enums for the different InputProcessResults that are to be used/handled by the controllers
 */
public enum InputProcessResult {
    SUCCESS,
    SUCCESSFUL_SPEAKER_LOGIN,
    SUCCESSFUL_ORGANIZER_LOGIN,
    SUCCESSFUL_ATTENDEE_LOGIN,
    SUCCESSFUL_ADMIN_LOGIN,
    SUCCESSFUL_VIP_LOGIN,
    USERNAME_TAKEN,
    INVALID_INPUT,
    BACK,
    EVENT_DOES_NOT_EXIST,
    USER_DID_NOT_ORGANIZE_EVENT,
    INCORRECT_EVENT_ID,
    INCORRECT_PASSWORD,
    USER_NOT_FOUND,
    INVALID_USERNAME,
    SPEAKER_OCCUPIED,
    TIMESLOT_FULL,
    CAPACITY_OVER,
    ROOM_FULL,
    WRONG_ROOM,
    INVALID_TIME,
    USER_NOT_SPEAKER,
    EVENT_NOT_FOUND,
    EVENT_IS_FULL,
    EVENT_FOR_VIPONLY,
    UNQUALIFIED_USER,
    USER_ALREADY_ENROLLED,
    USER_NEVER_ENROLLED,
    NAVIGATE_TO_LOGIN,
    NAVIGATE_TO_MAIN_MENU,
    NAVIGATE_TO_MESSAGE_USERS_MENU,
    NAVIGATE_TO_MESSAGE_USER,
    NAVIGATE_TO_EVENT_LIST_FOR_MESSAGING,
    NAVIGATE_TO_MESSAGE_ALL_ATTENDING_EVENT,
    NAVIGATE_TO_MESSAGE_ALL_SPEAKERS,
    NAVIGATE_TO_MESSAGE_ALL_ATTENDEES,
    NAVIGATE_TO_EVENT_SCHEDULE,
    NAVIGATE_TO_CREATE_EVENT,
    NAVIGATE_TO_CANCEL_EVENT,
    NAVIGATE_TO_ENROLL_IN_EVENT,
    NAVIGATE_TO_UNENROLL_IN_EVENT,
    NAVIGATE_TO_CREATE_SPEAKER_ACCOUNT,
    NAVIGATE_TO_DELETE_USER_ACCOUNT,
    NAVIGATE_TO_CHANGE_CAPACITY,
    NAVIGATE_TO_VIEW_MESSAGES,
    INVALID_USER_TYPE,
    NO_MESSAGE_HISTORY,
    NO_MESSAGE_DETECTED,
    EVENT_NAME_TAKEN,
    NO_PASSWORD_GIVEN,
    NO_USERNAME_GIVEN,
    INVALID_TIME_INPUT,
}
