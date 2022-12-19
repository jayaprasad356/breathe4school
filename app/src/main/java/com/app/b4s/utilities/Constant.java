package com.app.b4s.utilities;

public class Constant {
    public static final String MainBaseUrl = "http://143.244.132.170:3001/api/v1/";
    public static final String BaseUrl = MainBaseUrl + "studentAuth/";
    public static final String MessageBoard = "messageBoard/";
    public static final String NoticeBoard = "noticeBoard/notice/";
    public static final String VALIDATEUNIQUEID_URL = BaseUrl + "validateUniqueId";
    public static final String VALIDATE_EMAIL_OTP = BaseUrl + "validateEmailOtp";
    public static final String VALIDATE_MOBILE_OTP = BaseUrl + "validateMobileOtp";
    public static final String RESET_PASSWORD = BaseUrl + "reset/password";
    public static final String REGISTER_USER = BaseUrl + "register";
    public static final String RESET_MPIN = BaseUrl + "reset/mpin";
    public static final String LOGIN_WITH_MPIN = BaseUrl + "loginWithMpin";
    public static final String LOGIN_WITH_PASSWORD = BaseUrl + "loginWithPassword";
    public static final String GENERATE_TEMP_MPIN = BaseUrl + "generateTempMpinTicket";
    public static final String GET_ON_THIS_DAT = MainBaseUrl + MessageBoard + "getOnThisDay";
    public static final String GET_ALL_TIME_TABLE = MainBaseUrl + "timetable/get";
    public static final String GET_THOUGHT_OF_THE_DAY = MainBaseUrl + MessageBoard + "getThoughtOfTheDay";
    public static final String GET_NOTICE_BY_STUDENT_ID = MainBaseUrl + NoticeBoard + "getByStudent";
    public static final String UNIQUE_ID = "unique_id";
    public static final String IS_REGISTER = "isRegistered";
    public static final String STATUS = "status";
    public static final String MESSAGE = "message";
    public static final String EMAIL_OTP = "email_otp";
    public static final String MOBILE_OTP_VERIFY = "mobile_otp_verify";
    public static final String EMAIL_OTP_VERIFY = "mail_otp_verify";
    public static final String MOBILE_OTP = "mobile_otp";
    public static final String TYPE = "type";
    public static final String MOBILE = "mobile";
    public static final String MOBILE_NUMBER = "mobileNumber";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String TITLE = "Title";
    public static final String DESCRIPTION = "Descripition!";
    public static final String MPIN = "mpin";
    public static final String SUCCESS = "Success!";
    public static final String SKIP_FACE_ID = "SkipFaceId!";
    public static final String FLOW = "flow";
    public static final String FORGOT = "forgot";
    public static final String NORMAL = "normal";
    public static final String TEMP_PASS = "tempPass";
    public static final String TICKET_NUMBER = "ticket_number";
    public static final String DATA = "data";
    public static final String ON_THIS_DAY = "get_on_this_day";
    public static final String ON_THIS_DAY_TITLE = "on_this_day_title";
    public static final String QUOTE = "quote";
    public static final String AUTHOR = "author";
    public static final String NAME = "name";
    public static final String GENDER = "gender";
    public static final String ID = "id";
    public static final String MALE = "M";

    public static final String DOB = "date_of_birth";
    public static final String FATHER_NAME = "emergency_contact_name";
    public static final String PARENT_EMAIL = "email";
    public static final String STUDENT_ID = "student_id";
    public static final String MESSAGE_SHOWEN_TIME = "message_showed_time";
    public static final String MESSAGE_COUNT = "message_count";

    public static final String ACADEMIC_YEAR_ID = "academicYearId";
    public static final String SCHOOL_ID = "schoolId";
    public static final String STANDARD_ID = "standardId";
    public static final String SECTION_ID = "sectionId";
    public static final String TIME_TABLE_SESSION_ID = "timetableSessionId";

    public static final String ACADEMIC_YEAR = "academic_year";
    public static final String SCHOOL = "school";
    public static final String SCHEDULES = "schedules";
    public static final String LECTURES = "lectures";
    public static final String STANDARD = "standard";
    public static final String SECTION = "section";
    public static final String TIMETABLESESSION = "timetable_session";

    public static final String BACK_FLOW = "back";

    public static final String START_TIME = "start_time";
    public static final String END_TIME = "end_time";

}