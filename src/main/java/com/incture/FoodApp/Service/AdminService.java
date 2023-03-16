package com.incture.FoodApp.Service;

import com.incture.FoodApp.Entity.*;
import com.incture.FoodApp.Repo.ItemRepository;
import com.incture.FoodApp.Repo.MenuRepository;
import com.incture.FoodApp.Repo.RoleRepository;
import com.incture.FoodApp.Repo.UserRepository;
import com.incture.FoodApp.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AdminService {

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    public AdminDTO getAttendance(String date, int category) {
        Transaction transaction = null;
        String userId = " ";
        String emailId = " ";
        Date date1; // to store parsed date
        try {
            date1 = new SimpleDateFormat("dd/MM/yyyy").parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        AdminDTO adminDTO = new AdminDTO();
        //HashMap<HashMap<String, String>,String> map = new HashMap<>();
        HashMap<String, String> attendanceList = new HashMap<>();

        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            String sqlString = "FROM MealConsent as mc";
            Query query = session.createQuery(sqlString);
            // Iterate through stored objects
            for (Iterator it = query.iterate(); it.hasNext(); ) {
                MealConsent mealConsent = (MealConsent) it.next();
                if (category == 1) {
                    if (mealConsent.getBreakfastConsent() > 0 &&
                            mealConsent.getUserPkId().getDate().getDate() == date1.getDate() &&
                            mealConsent.getUserPkId().getDate().getMonth()==date1.getMonth() &&
                            mealConsent.getUserPkId().getDate().getYear()==date1.getYear()) {
                        userId=mealConsent.getUserPkId().getUserId();
                        UserMaster userMaster1 = (UserMaster) session.get(UserMaster.class, userId);
                        emailId = userMaster1.getUserEmail();
                        attendanceList.put(emailId, mealConsent.getCreatedBy());
                    }
                }
                if (category == 2) {
                    if (mealConsent.getLunchConsent() > 0 &&
                            mealConsent.getUserPkId().getDate().getDate() == date1.getDate() &&
                            mealConsent.getUserPkId().getDate().getMonth()==date1.getMonth() &&
                            mealConsent.getUserPkId().getDate().getYear()==date1.getYear()) {
                        userId=mealConsent.getUserPkId().getUserId();
                        UserMaster userMaster1 = (UserMaster) session.get(UserMaster.class, userId);
                        emailId = userMaster1.getUserEmail();
                        attendanceList.put(emailId, mealConsent.getCreatedBy());
                    }
                }
            }
            transaction.commit();
            session.close();
            System.out.println("Attendance Data Fetched");
            adminDTO.setMessage("Attendance Data got Successfully");
            adminDTO.setStatus(true);
            adminDTO.setData(attendanceList);
            return adminDTO;
        } catch (Exception e) {
            System.out.println(e);
            transaction.rollback();
        }
        adminDTO.setMessage("attendance Fetching Failed");
        adminDTO.setStatus(false);
        adminDTO.setData(null);
        return adminDTO;
    }
    public AdminDTO getPrevAttendance(String date, int category) {
        Transaction transaction = null;
        String name=" ";
        String emailId = " ";
        String userId=" ";
        Date date1; // to store parsed date
        try {
            date1 = new SimpleDateFormat("dd/MM/yyyy").parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        AdminDTO adminDTO = new AdminDTO();
        //HashMap<HashMap<String, String>,String> map = new HashMap<>();
        HashMap<String, String> prevattendanceList = new HashMap<>();

        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            String sqlString = "FROM MealAttendance as ma";
            Query query = session.createQuery(sqlString);
            // Iterate through stored objects
            for (Iterator it = query.iterate(); it.hasNext(); ) {
                MealAttendance mealAttendance = (MealAttendance) it.next();
                if (category == 1) {
                    if (mealAttendance.isBreakfastAttendance()  &&
                    		mealAttendance.getUserPkId().getDate().getDate() == date1.getDate() &&
                    		mealAttendance.getUserPkId().getDate().getMonth()==date1.getMonth() &&
                    		mealAttendance.getUserPkId().getDate().getYear()==date1.getYear()) {
                        userId=mealAttendance.getUserPkId().getUserId();
                        UserMaster userMaster1 = (UserMaster) session.get(UserMaster.class, userId);
                        emailId = userMaster1.getUserEmail();
                        name= userMaster1.getFirstName();
                        prevattendanceList.put(emailId, name);
                    }
                }
                if (category == 2) {
                    if (mealAttendance.isLunchAttendance() &&
                    		mealAttendance.getUserPkId().getDate().getDate() == date1.getDate() &&
                    		mealAttendance.getUserPkId().getDate().getMonth()==date1.getMonth() &&
                    		mealAttendance.getUserPkId().getDate().getYear()==date1.getYear()) {
                        userId=mealAttendance.getUserPkId().getUserId();
                        UserMaster userMaster1 = (UserMaster) session.get(UserMaster.class, userId);
                        name=userMaster1.getFirstName()
;                        emailId = userMaster1.getUserEmail();
                        prevattendanceList.put(emailId, name);
                    }
                }
            }
            transaction.commit();
            session.close();
            System.out.println(" previous Attendance Data Fetched");
            adminDTO.setMessage(" previous Attendance Data got Successfully");
            adminDTO.setStatus(true);
            adminDTO.setData(prevattendanceList);
            return adminDTO;
        } catch (Exception e) {
            System.out.println(e);
            transaction.rollback();
        }
        adminDTO.setMessage(" previous attendance Fetching Failed");
        adminDTO.setStatus(false);
        adminDTO.setData(null);
        return adminDTO;
    }

    public AdminDTO addRealAttendance(String adminUserId, String date, HashMap<String, Integer> attendance) {
        Transaction transaction = null;
        String userId = " ";
        int category = 0;
        String adminName = " ";
        Date date1; // to store parsed date
        try {
            date1 = new SimpleDateFormat("dd/MM/yyyy").parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        MealAttendance mealAttendance = new MealAttendance();

        UserPkId userPkId = new UserPkId();
        AdminDTO adminDTO = new AdminDTO();
        for (Map.Entry<String, Integer> mapElement : attendance.entrySet()) {
            userId = mapElement.getKey();
            category = mapElement.getValue();
            userPkId.setUserId(userId);
            userPkId.setDate(date1);
            mealAttendance.setUserPkId(userPkId);
            try {
                Session session = HibernateUtil.getSessionFactory().openSession();
                transaction = session.beginTransaction();
                UserMaster userMaster = (UserMaster) session.get(UserMaster.class, adminUserId);
                adminName = userMaster.getFirstName();
                MealAttendance mealAttendanceOld = session.get(MealAttendance.class, userPkId);
                if (mealAttendanceOld == null) {
                    mealAttendance.setBreakfastAttendance(false);
                    Date date2 = new Date(System.currentTimeMillis());
                    mealAttendance.setCreatedOn(date2);
                } else if (mealAttendanceOld.isBreakfastAttendance()) {
                    mealAttendance.setBreakfastAttendance(true);
                    mealAttendance.setCreatedOn(mealAttendanceOld.getCreatedOn());
                } else {
                    mealAttendance.setBreakfastAttendance(false);
                }
                transaction.commit();
                session.close();
            } catch (Exception e) {
                System.out.println(e);
                transaction.rollback();
            }

            if (category == 1) {
                try {
                    Session session = HibernateUtil.getSessionFactory().openSession();
                    transaction = session.beginTransaction();
                    mealAttendance.setBreakfastAttendance(true);
                    mealAttendance.setLunchAttendance(false);

                    Date date3 = new Date(System.currentTimeMillis());
                    mealAttendance.setCreatedBy(adminName);
                    mealAttendance.setCreatedOn(date3);

                    Date dateFake = new Date(0);
                    mealAttendance.setUpdatedBy(" ");
                    mealAttendance.setUpdatedOn(dateFake);


                    session.saveOrUpdate(mealAttendance);
                    transaction.commit();
                    session.close();
                    System.out.println("Attendance Data Saved into database");
                    adminDTO.setMessage("Attendance Data Saved");
                    adminDTO.setStatus(true);
                    adminDTO.setData(null);
                } catch (Exception e) {
                    System.out.println(e);
                    adminDTO.setMessage("Attendance Data Posting Failed");
                    adminDTO.setStatus(false);
                    adminDTO.setData(null);
                    transaction.rollback();
                    return adminDTO;
                }
            } else if (category == 2) {
                try {
                    Session session = HibernateUtil.getSessionFactory().openSession();
                    transaction = session.beginTransaction();
                    mealAttendance.setLunchAttendance(true);

                    mealAttendance.setCreatedBy(adminName);

                    Date date3 = new Date(System.currentTimeMillis());
                    mealAttendance.setUpdatedBy(adminName);
                    mealAttendance.setUpdatedOn(date3);


                    session.saveOrUpdate(mealAttendance);
                    transaction.commit();
                    session.close();
                    System.out.println("Attendance Data Saved into database");
                    adminDTO.setMessage("Attendance Data Saved");
                    adminDTO.setStatus(true);
                    adminDTO.setData(null);
                } catch (Exception e) {
                    System.out.println(e);
                    adminDTO.setMessage("Attendance Data Posting Failed");
                    adminDTO.setStatus(false);
                    adminDTO.setData(null);
                    transaction.rollback();
                    return adminDTO;
                }
            }
        }
        return adminDTO;
    }

    public AdminDTO getFeedback(String date, int category) {
        Transaction transaction = null;
        Date date1; // to store parsed date
        try {
            date1 = new SimpleDateFormat("dd/MM/yyyy").parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        Date currentDateAndTime=new Date(0);
        AdminDTO adminDTO = new AdminDTO();
        HashMap<Date, HashMap<String, Integer>> feedbackList = new HashMap<>();
        HashMap<String, Integer> internalMap= new HashMap<>();
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            String sqlString = "FROM MealFeedback as mf";
            Query query = session.createQuery(sqlString);
            // Iterate through stored objects
            for (Iterator it = query.iterate(); it.hasNext(); ) {
                if(internalMap.size()>0){
                internalMap.clear();
                }
                MealFeedback mealFeedback = (MealFeedback) it.next();
                if (category == 1) {
                    if (mealFeedback.getBreakfastRating() > 0
                            && mealFeedback.getUserPkId().getDate().getDate() == date1.getDate() &&
                            mealFeedback.getUserPkId().getDate().getMonth() == date1.getMonth() &&
                            mealFeedback.getUserPkId().getDate().getYear() == date1.getYear()) {
                        currentDateAndTime=mealFeedback.getCreatedOn();
                        feedbackList.put(currentDateAndTime, new HashMap<String, Integer>());
                        feedbackList.get(currentDateAndTime).put(mealFeedback.getBreakfastFeedback(), mealFeedback.getBreakfastRating());
                    }
                }
                if (category == 2) {
                    if (mealFeedback.getLunchRating() > 0 &&
                            mealFeedback.getUserPkId().getDate().getDate() == date1.getDate() &&
                            mealFeedback.getUserPkId().getDate().getMonth() == date1.getMonth() &&
                            mealFeedback.getUserPkId().getDate().getYear() == date1.getYear()) {
                        currentDateAndTime=mealFeedback.getUpdatedOn();
                        feedbackList.put(currentDateAndTime, new HashMap<String, Integer>());
                        feedbackList.get(currentDateAndTime).put(mealFeedback.getLunchFeedback(), mealFeedback.getLunchRating());
                    }
                }
            }
            transaction.commit();
            session.close();
            System.out.println("Feedback Data Fetched");
            adminDTO.setMessage("Feedbacks Data got Successfully");
            adminDTO.setStatus(true);
            adminDTO.setData(feedbackList);
            return adminDTO;
        } catch (Exception e) {
            System.out.println(e);
            transaction.rollback();
        }
        adminDTO.setMessage("Feedbacks Fetching Failed");
        adminDTO.setStatus(false);
        adminDTO.setData(null);
        return adminDTO;
    }

    public AdminDTO getFeedbackCount(String date, int category) {
        Transaction transaction = null;
        Date date1; // to store parsed date
        try {
            date1 = new SimpleDateFormat("dd/MM/yyyy").parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        AdminDTO adminDTO = new AdminDTO();
        HashMap<String, Integer> feedbackCount = new HashMap<>();
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            if (category == 1) {
                int totalUserWhoRatedBreakfast = 0;
                int totalUserWhoHaveGivenBreakfastFeedback = 0;
                String sqlString = "FROM MealFeedback as mf";
                Query query = session.createQuery(sqlString);
                // Iterate through stored objects
                for (Iterator it = query.iterate(); it.hasNext(); ) {
                    MealFeedback mealFeedback = (MealFeedback) it.next();
                    if (mealFeedback.getUserPkId().getDate().getDate() == date1.getDate() &&
                            mealFeedback.getUserPkId().getDate().getMonth() == date1.getMonth() &&
                            mealFeedback.getUserPkId().getDate().getYear() == date1.getYear()) {
                        if (mealFeedback.getBreakfastRating() > 0) {
                            totalUserWhoRatedBreakfast = totalUserWhoRatedBreakfast + 1;
                        }
                        if (mealFeedback.getBreakfastFeedback() != null) {
                            if(mealFeedback.getBreakfastFeedback().trim().equals("")){
                                continue;
                            }
                            totalUserWhoHaveGivenBreakfastFeedback = totalUserWhoHaveGivenBreakfastFeedback + 1;
                        }
                    }
                }
                feedbackCount.put("Total user who have given the feedback for Breakfast", totalUserWhoHaveGivenBreakfastFeedback);
                feedbackCount.put("Total user who have given the Rating for Breakfast", totalUserWhoRatedBreakfast);
            }
            if (category == 2) {
                int totalUserWhoRatedLunch = 0;
                int totalUserWhoHaveGivenLunchFeedback = 0;
                String sqlString = "FROM MealFeedback as mf";
                Query query = session.createQuery(sqlString);
                // Iterate through stored objects
                for (Iterator it = query.iterate(); it.hasNext(); ) {
                    MealFeedback mealFeedback = (MealFeedback) it.next();
                    if (mealFeedback.getUserPkId().getDate().getDate() == date1.getDate() &&
                            mealFeedback.getUserPkId().getDate().getMonth() == date1.getMonth() &&
                            mealFeedback.getUserPkId().getDate().getYear() == date1.getYear()) {
                        if (mealFeedback.getLunchRating() > 0) {
                            totalUserWhoRatedLunch =totalUserWhoRatedLunch + 1;
                        }
                        if (mealFeedback.getLunchFeedback() != null) {
                            if(mealFeedback.getLunchFeedback().trim().equals("")){
                                continue;
                            }
                            totalUserWhoHaveGivenLunchFeedback = totalUserWhoHaveGivenLunchFeedback + 1;
                        }
                    }
                }
                feedbackCount.put("Total user who have given the feedback for Lunch", totalUserWhoHaveGivenLunchFeedback);
                feedbackCount.put("Total user who have given the Rating for Lunch", totalUserWhoRatedLunch);
            }
            transaction.commit();
            session.close();
            System.out.println("Feedback Count Data Fetched");
            adminDTO.setMessage("Feedback Count got Successfully");
            adminDTO.setStatus(true);
            adminDTO.setData(feedbackCount);
            return adminDTO;
        } catch (Exception e) {
            System.out.println(e);
            transaction.rollback();
        }
        adminDTO.setMessage("Feedback Count Fetching Failed");
        adminDTO.setStatus(false);
        adminDTO.setData(null);
        return adminDTO;
    }

public HashMap<String, Float> analytics(String date, int category) {
    Transaction transaction = null;
    Date date1; // to store parsed date
    try {
        date1 = new SimpleDateFormat("dd/MM/yyyy").parse(date);
    } catch (ParseException e) {
        throw new RuntimeException(e);
    }

    HashMap<String, Float> map = new HashMap<>();
    int totalEmp = 0;
    float AttendanceForTodayBreakfast = 0.0f;
    float AttendanceForTodayLunch = 0.0f;
    float EmployeeWhoAteTodayInBreakfast = 0.0f;
    float EmployeeWhoAteTodayInLunch = 0.0f;
    float RatingForTodayBreakfast = 0.0f;
    float RatingForTodayLunch = 0.0f;
    try {
        Session session = HibernateUtil.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        String sqlString1 = "FROM UserMaster as um";
        Query query1 = session.createQuery(sqlString1);
        // Iterate through stored objects
        for (Iterator it = query1.iterate(); it.hasNext(); it.next()) {
            totalEmp = totalEmp + 1;
        }
        map.put("Total number of Employees", (float) totalEmp);

        // Attendance Recorded For Today

        if (category == 1) {
            int totalUserWhoOptedForBreakfast = 0;
            String sqlString2 = "FROM MealConsent as mc";
            Query query2 = session.createQuery(sqlString2);
            // Iterate through stored objects
            for (Iterator it = query2.iterate(); it.hasNext(); ) {
                MealConsent mealConsent = (MealConsent) it.next();
                    if (mealConsent.getUserPkId().getDate().getDate() == date1.getDate()
                            && mealConsent.getUserPkId().getDate().getMonth() == date1.getMonth()
                            && mealConsent.getUserPkId().getDate().getYear() == date1.getYear()) {
                        if (mealConsent.getBreakfastConsent() > 0) {
                            totalUserWhoOptedForBreakfast = totalUserWhoOptedForBreakfast + 1;
                        }
                    }
            }
            AttendanceForTodayBreakfast = (float) (totalUserWhoOptedForBreakfast);
            map.put("Attendance For Today's Breakfast", AttendanceForTodayBreakfast);
        } else if (category == 2) {
            int totalUserWhoOptedForLunch = 0;
            String sqlString2 = "FROM MealConsent as mc";
            Query query2 = session.createQuery(sqlString2);
            // Iterate through stored objects
            for (Iterator it = query2.iterate(); it.hasNext(); ) {
                MealConsent mealConsent = (MealConsent) it.next();
                if (mealConsent.getUserPkId().getDate().getDate() == date1.getDate()
                        && mealConsent.getUserPkId().getDate().getMonth() == date1.getMonth()
                        && mealConsent.getUserPkId().getDate().getYear() == date1.getYear()) {
                        if (mealConsent.getLunchConsent() > 0) {
                            totalUserWhoOptedForLunch = totalUserWhoOptedForLunch + 1;
                        }
                    }
            }
            AttendanceForTodayLunch = (float) (totalUserWhoOptedForLunch);
            map.put("Attendance For Today's Lunch", AttendanceForTodayLunch);
        }

        // Number Employee Ate Who ate Today

        if (category == 1) {
            int totalUserWhoAteBreakfast = 0;

            String sqlString3 = "FROM MealAttendance as ma";
            Query query3 = session.createQuery(sqlString3);
            // Iterate through stored objects
            for (Iterator it = query3.iterate(); it.hasNext(); ) {
                MealAttendance mealAttendance = (MealAttendance) it.next();
                    if (mealAttendance.getUserPkId().getDate().getDate() == date1.getDate()
                            && mealAttendance.getUserPkId().getDate().getMonth() == date1.getMonth()
                            && mealAttendance.getUserPkId().getDate().getYear() == date1.getYear()) {
                        if (mealAttendance.isBreakfastAttendance()) {
                            totalUserWhoAteBreakfast = totalUserWhoAteBreakfast + 1;
                        }
                    }
            }
            EmployeeWhoAteTodayInBreakfast = (float) (totalUserWhoAteBreakfast);
            map.put("Employee Who had Today's Breakfast", EmployeeWhoAteTodayInBreakfast);
        }
        if (category == 2) {
            int totalUserWhoAteLunch = 0;

            String sqlString3 = "FROM MealAttendance as ma";
            Query query3 = session.createQuery(sqlString3);
            // Iterate through stored objects
            for (Iterator it = query3.iterate(); it.hasNext(); ) {
                MealAttendance mealAttendance = (MealAttendance) it.next();
                if (mealAttendance.getUserPkId().getDate().getDate() == date1.getDate()
                        && mealAttendance.getUserPkId().getDate().getMonth() == date1.getMonth()
                        && mealAttendance.getUserPkId().getDate().getYear() == date1.getYear()) {
                        if (mealAttendance.isLunchAttendance()) {
                            totalUserWhoAteLunch = totalUserWhoAteLunch + 1;
                        }
                    }
            }
            EmployeeWhoAteTodayInLunch = (float) (totalUserWhoAteLunch);
            map.put("Employee Who had Today's Lunch", EmployeeWhoAteTodayInLunch);
        }

        // Today's Food Rating

        if (category == 1) {
            float breakfastRating = 0.0f;
            float totalUserWhoRatedBreakfast = 0.0f;
            String sqlString4 = "FROM MealFeedback as mf";
            Query query4 = session.createQuery(sqlString4);
            // Iterate through stored objects
            for (Iterator it = query4.iterate(); it.hasNext(); ) {
                MealFeedback mealFeedback = (MealFeedback) it.next();
                    if (mealFeedback.getUserPkId().getDate().getDate() == date1.getDate()
                            && mealFeedback.getUserPkId().getDate().getMonth() == date1.getMonth()
                            && mealFeedback.getUserPkId().getDate().getYear() == date1.getYear()) {
                        if (mealFeedback.getBreakfastRating() > 0) {
                            breakfastRating = breakfastRating + mealFeedback.getBreakfastRating();
                            totalUserWhoRatedBreakfast = totalUserWhoRatedBreakfast + 1;
                        }
                    }
            }
            RatingForTodayBreakfast = (float) breakfastRating / totalUserWhoRatedBreakfast;
            map.put("Average Rating For Today's Breakfast", RatingForTodayBreakfast);

        }

        if (category == 2) {
            float lunchRating = 0.0f;
            float totalUserWhoRatedLunch = 0.0f;
            String sqlString4 = "FROM MealFeedback as mf";
            Query query4 = session.createQuery(sqlString4);
            // Iterate through stored objects
            for (Iterator it = query4.iterate(); it.hasNext(); ) {
                MealFeedback mealFeedback = (MealFeedback) it.next();
                    if (mealFeedback.getUserPkId().getDate().getDate() == date1.getDate()
                            && mealFeedback.getUserPkId().getDate().getMonth() == date1.getMonth()
                            && mealFeedback.getUserPkId().getDate().getYear() == date1.getYear()) {
                        if (mealFeedback.getLunchRating() > 0) {
                            lunchRating = lunchRating + mealFeedback.getLunchRating();
                            totalUserWhoRatedLunch = totalUserWhoRatedLunch + 1;
                        }
                    }
            }
            RatingForTodayLunch = (float) lunchRating / totalUserWhoRatedLunch;
            map.put("Average Rating For Today's Lunch", RatingForTodayLunch);
            ;

        }
        transaction.commit();
        System.out.println("Required User data Passed to Admin");
        session.close();
        return map;
    } catch (Exception e) {
        System.out.println(e);
        transaction.rollback();
    }
    return map;
}

    public HashMap<String, HashMap<Integer, Integer>> graph_AttendanceVsEmployeeAte(String date, int category) {
        Transaction transaction = null;
        Date date1; // to store parsed date
        try {
            date1 = new SimpleDateFormat("dd/MM/yyyy").parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        long MILLIS_IN_A_DAY = 1000 * 60 * 60 * 24; // to be subtract from the given date

        Date[] arrayOfDates = new Date[8]; // to store last 7 days

        HashMap<String, HashMap<Integer, Integer>> map = new HashMap<>();
        HashMap<Integer, Integer> mapInternal = new HashMap<>();

        for (int i = 1; i < 8; i++) {
            arrayOfDates[i] = new Date(date1.getTime() - MILLIS_IN_A_DAY * i);
            if (arrayOfDates[i].getDay() == 0)
                continue;
            if (arrayOfDates[i].getDay() == 6)
                continue;
            String weekDay = getTheWeekDay(arrayOfDates[i].getDay());
            map.put(weekDay, new HashMap<Integer, Integer>());
        }
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();

            if (category == 1) {
                String sqlString1 = "FROM MealConsent as mc";
                Query query1 = session.createQuery(sqlString1);

                String sqlString2 = "FROM MealAttendance as ma";
                Query query2 = session.createQuery(sqlString2);

                for (int i = 1; i < 8; i++) {

                    int totalUserWhoOptedForBreakfast = 0;
                    int totalUserWhoAteBreakfast = 0;
                    mapInternal.clear();

                    // Iterate through stored objects
                    for (Iterator it = query1.iterate(); it.hasNext(); ) {
                        MealConsent mealConsent = (MealConsent) it.next();
                        if (arrayOfDates[i].getDate() == mealConsent.getUserPkId().getDate().getDate()
                                && arrayOfDates[i].getMonth() == mealConsent.getUserPkId().getDate().getMonth()
                                && arrayOfDates[i].getYear() == mealConsent.getUserPkId().getDate().getYear()) {
                            if (mealConsent.getBreakfastConsent() > 0) {
                                totalUserWhoOptedForBreakfast = totalUserWhoOptedForBreakfast + 1;
                            }
                        }
                    }

                    // Iterate through stored objects
                    for (Iterator it = query2.iterate(); it.hasNext(); ) {
                        MealAttendance mealAttendance = (MealAttendance) it.next();
                        if (arrayOfDates[i].getDate() == mealAttendance.getUserPkId().getDate().getDate()
                                && arrayOfDates[i].getMonth() == mealAttendance.getUserPkId().getDate().getMonth()
                                && arrayOfDates[i].getYear() == mealAttendance.getUserPkId().getDate().getYear()) {
                            if (mealAttendance.isBreakfastAttendance()) {
                                totalUserWhoAteBreakfast = totalUserWhoAteBreakfast + 1;
                            }
                        }
                    }
                    mapInternal.put(totalUserWhoOptedForBreakfast, totalUserWhoAteBreakfast);
                    // String weekDay = getTheWeekDay(arrayOfDates[i].getDay());
                    if (arrayOfDates[i].getDay() == 1) {
                        map.get("Monday").put(totalUserWhoOptedForBreakfast, totalUserWhoAteBreakfast);
                    } else if (arrayOfDates[i].getDay() == 2) {
                        map.get("Tuesday").put(totalUserWhoOptedForBreakfast, totalUserWhoAteBreakfast);
                    } else if (arrayOfDates[i].getDay() == 3) {
                        map.get("Wednesday").put(totalUserWhoOptedForBreakfast, totalUserWhoAteBreakfast);
                    } else if (arrayOfDates[i].getDay() == 4) {
                        map.get("Thursday").put(totalUserWhoOptedForBreakfast, totalUserWhoAteBreakfast);
                    } else if (arrayOfDates[i].getDay() == 5) {
                        map.get("Friday").put(totalUserWhoOptedForBreakfast, totalUserWhoAteBreakfast);
                    }
                } // array of dates iterated
            } else if (category == 2) {
                String sqlString1 = "FROM MealConsent as mc";
                Query query1 = session.createQuery(sqlString1);

                String sqlString2 = "FROM MealAttendance as ma";
                Query query2 = session.createQuery(sqlString2);

                for (int i = 1; i < 8; i++) {

                    int totalUserWhoOptedForLunch = 0;
                    int totalUserWhoAteLunch = 0;
                    mapInternal.clear();

                    // Iterate through stored objects
                    for (Iterator it = query1.iterate(); it.hasNext(); ) {
                        MealConsent mealConsent = (MealConsent) it.next();
                        if (arrayOfDates[i].getDate() == mealConsent.getUserPkId().getDate().getDate()
                                && arrayOfDates[i].getMonth() == mealConsent.getUserPkId().getDate().getMonth()
                                && arrayOfDates[i].getYear() == mealConsent.getUserPkId().getDate().getYear()) {
                            if (mealConsent.getLunchConsent() > 0) {
                                totalUserWhoOptedForLunch = totalUserWhoOptedForLunch + 1;
                            }
                        }
                    }
                    // Iterate through stored objects
                    for (Iterator it = query2.iterate(); it.hasNext(); ) {
                        MealAttendance mealAttendance = (MealAttendance) it.next();
                        if (arrayOfDates[i].getDate() == mealAttendance.getUserPkId().getDate().getDate()
                                && arrayOfDates[i].getMonth() == mealAttendance.getUserPkId().getDate().getMonth()
                                && arrayOfDates[i].getYear() == mealAttendance.getUserPkId().getDate().getYear()) {
                            if (mealAttendance.isLunchAttendance()) {
                                totalUserWhoAteLunch = totalUserWhoAteLunch + 1;
                            }
                        }
                    }
                    mapInternal.put(totalUserWhoOptedForLunch, totalUserWhoAteLunch);

                    if (arrayOfDates[i].getDay() == 1) {
                        map.get("Monday").put(totalUserWhoOptedForLunch, totalUserWhoAteLunch);
                    } else if (arrayOfDates[i].getDay() == 2) {
                        map.get("Tuesday").put(totalUserWhoOptedForLunch, totalUserWhoAteLunch);
                    } else if (arrayOfDates[i].getDay() == 3) {
                        map.get("Wednesday").put(totalUserWhoOptedForLunch, totalUserWhoAteLunch);
                    } else if (arrayOfDates[i].getDay() == 4) {
                        map.get("Thursday").put(totalUserWhoOptedForLunch, totalUserWhoAteLunch);
                    } else if (arrayOfDates[i].getDay() == 5) {
                        map.get("Friday").put(totalUserWhoOptedForLunch, totalUserWhoAteLunch);
                    }
                } // array of dates iterated
            }
            transaction.commit();
            session.close();
            System.out.println("Required Attendance Vs Employee Ate Data For Graph Passed to Admin");
            return map;
        } catch (Exception e) {
            System.out.println(e);
            transaction.rollback();
        }
        return map;
    }

    public HashMap<String, Float> graph_DayVsAvgRating(String date, int category) {
        Transaction transaction = null;
        Date date1; // to store parsed date
        try {
            date1 = new SimpleDateFormat("dd/MM/yyyy").parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        long MILLIS_IN_A_DAY = 1000 * 60 * 60 * 24; // to be subtract from the given date

        Date[] arrayOfDates = new Date[8]; // to store last 7 days

        float avgRatingThisWeekForBreakfast = 0.0f;
        float avgRatingThisWeekForLunch = 0.0f;
        HashMap<String, Float> map = new HashMap<>();

        for (int i = 1; i < 8; i++) {
            arrayOfDates[i] = new Date(date1.getTime() - MILLIS_IN_A_DAY * i);
        }
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();

            if (category == 1) {

                String sqlString1 = "FROM MealFeedback as mf";
                Query query1 = session.createQuery(sqlString1);

                for (int i = 1; i < 8; i++) {

                    float breakfastRating = 0.0f;
                    float totalUserWhoRatedBreakfast = 0.0f;

                    // Iterate through stored objects
                    for (Iterator it = query1.iterate(); it.hasNext(); ) {
                        MealFeedback mealFeedback = (MealFeedback) it.next();
                        if (arrayOfDates[i].getDate() == mealFeedback.getUserPkId().getDate().getDate()
                                && arrayOfDates[i].getMonth() == mealFeedback.getUserPkId().getDate().getMonth()
                                && arrayOfDates[i].getYear() == mealFeedback.getUserPkId().getDate().getYear()) {
                            if (mealFeedback.getBreakfastRating() > 0) {
                                breakfastRating = breakfastRating + mealFeedback.getBreakfastRating();
                                totalUserWhoRatedBreakfast = totalUserWhoRatedBreakfast + 1;
                            }
                        }
                    }
                    avgRatingThisWeekForBreakfast = (float) breakfastRating / totalUserWhoRatedBreakfast;
                    String weekDay = getTheWeekDay(arrayOfDates[i].getDay());
                    if (arrayOfDates[i].getDay() == 0)
                        continue;
                    if (arrayOfDates[i].getDay() == 6)
                        continue;
                    map.put(weekDay, avgRatingThisWeekForBreakfast);

                } // array of dates iterated
            } else if (category == 2) {

                String sqlString2 = "FROM MealFeedback as mf";
                Query query2 = session.createQuery(sqlString2);
                for (int i = 1; i < 8; i++) {

                    float lunchRating = 0.0f;
                    float totalUserWhoRatedLunch = 0.0f;

                    // Iterate through stored objects
                    for (Iterator it = query2.iterate(); it.hasNext(); ) {
                        MealFeedback mealFeedback = (MealFeedback) it.next();
                        if (arrayOfDates[i].getDate() == mealFeedback.getUserPkId().getDate().getDate()
                                && arrayOfDates[i].getMonth() == mealFeedback.getUserPkId().getDate().getMonth()
                                && arrayOfDates[i].getYear() == mealFeedback.getUserPkId().getDate().getYear()) {
                            if (mealFeedback.getLunchRating() > 0) {
                                lunchRating = lunchRating + mealFeedback.getLunchRating();
                                totalUserWhoRatedLunch = totalUserWhoRatedLunch + 1;
                            }
                        }
                    }
                    avgRatingThisWeekForLunch = (float) lunchRating / totalUserWhoRatedLunch;
                    String weekDay = getTheWeekDay(arrayOfDates[i].getDay());
                    if (arrayOfDates[i].getDay() == 0)
                        continue;
                    if (arrayOfDates[i].getDay() == 6)
                        continue;
                    map.put(weekDay, avgRatingThisWeekForLunch);

                } // array of dates iterated
            }

            transaction.commit();
            session.close();
            System.out.println("Required Day Vs Average Rating Data For Graph Passed to Admin");
            return map;
        } catch (Exception e) {
            System.out.println(e);
            transaction.rollback();
        }
        return map;
    }

    private String getTheWeekDay(int day) {
        if (day == 0)
            return "Sunday";
        else if (day == 1) {
            return "Monday";
        } else if (day == 2) {
            return "Tuesday";
        } else if (day == 3) {
            return "Wednesday";
        } else if (day == 4) {
            return "Thursday";
        } else if (day == 5) {
            return "Friday";
        } else if (day == 6) {
            return "Saturday";
        } else {
            return "Invalid Day";
        }
    }

    public AdminDTO getAllItems() {
        // TODO Auto-generated method stub

        AdminDTO getItemsResponse = new AdminDTO();
        try {
            getItemsResponse.setStatus(true);
            getItemsResponse.setMessage("Success");
            List<Item> itemList = itemRepository.findAll();
            getItemsResponse.setData(itemList);
            return getItemsResponse;
        } catch (Exception e) {
            System.out.println(e);
            getItemsResponse.setStatus(false);
            getItemsResponse.setMessage("Exception occured!" + e);

        }
        return getItemsResponse;
    }

    public AdminDTO addItem(String itemName) {
        // TODO Auto-generated method stub

        AdminDTO addItemResponse = new AdminDTO();
        try {
            addItemResponse.setStatus(true);
            addItemResponse.setMessage("Success");

            if (itemRepository.findByItemName(itemName) != null) {
                addItemResponse.setData("Item already exist");
            } else {
                Item item = new Item();
                item.setItemName(itemName);
                item.setValid(true);

                itemRepository.save(item);
                addItemResponse.setData("New item added");
            }

            return addItemResponse;
        } catch (Exception e) {
            System.out.println(e);
            addItemResponse.setStatus(false);
            addItemResponse.setMessage("Exception occured!" + e);

        }
        return addItemResponse;
    }

    public AdminDTO setMenu(Menu addMenu) {
        // TODO Auto-generated method stub

        AdminDTO setMenuResponse = new AdminDTO();
        try {
            setMenuResponse.setStatus(true);
            setMenuResponse.setMessage("Success");
            if (menuRepository.findByMenuAndCategory(addMenu.getDate(), addMenu.getMenuCategory()) != null) {
                updateMenu(addMenu);
                setMenuResponse.setData("Menu updated successfully");
            } else {



                Menu menu = new Menu();
                menu.setDate(addMenu.getDate());
                menu.setCurrency(addMenu.getCurrency());
                menu.setMenuCategory(addMenu.getMenuCategory());
                menu.setPrice(addMenu.getPrice());



                addMenu.getItems().stream().forEach(value -> {
                    Item item = itemRepository.getById(value.getItemId());
                    menu.getItems().add(item);
                    item.getMenus().add(menu);
                });



                menuRepository.save(menu);
                setMenuResponse.setData("Menu added");
            }

            return setMenuResponse;
        } catch (Exception e) {
            System.out.println(e);
            setMenuResponse.setStatus(false);
            setMenuResponse.setMessage("Exception occured!" + e);

        }
        return setMenuResponse;
    }

    public AdminDTO updateMenu(Menu editMenu) {
        // TODO Auto-generated method stub

        AdminDTO updateMenuResponse = new AdminDTO();
        try {
            updateMenuResponse.setStatus(true);
            updateMenuResponse.setMessage("Success");

            Menu menu = menuRepository.findByMenuAndCategory(editMenu.getDate(), editMenu.getMenuCategory());

            if (editMenu.getCurrency() != null) {
                menu.setCurrency(editMenu.getCurrency());
            }
            if (editMenu.getPrice() != null) {
                menu.setPrice(editMenu.getPrice());
            }
            if (editMenu.getItems() != null) {
                menu.getItems().clear();
                editMenu.getItems().stream().forEach(value -> {
                    Item item = itemRepository.getById(value.getItemId());
                    menu.getItems().add(item);
                    item.getMenus().add(menu);
                });
            }

            menuRepository.save(menu);

            updateMenuResponse.setData("Menu updated");

            return updateMenuResponse;
        } catch (Exception e) {
            System.out.println(e);
            updateMenuResponse.setStatus(false);
            updateMenuResponse.setMessage("Exception occured!" + e);

        }
        return updateMenuResponse;
    }

    public AdminDTO prevMenu(String category) {
        // TODO Auto-generated method stub
        Transaction transaction = null;

        AdminDTO prevMenuResponse = new AdminDTO();
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            prevMenuResponse.setStatus(true);
            prevMenuResponse.setMessage("Success");

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            Calendar cal = Calendar.getInstance();
            // get starting date
            cal.add(Calendar.DAY_OF_YEAR, -7);

            List<String> dateList = new ArrayList<>();
            // loop adding one day in each iteration
            for (int i = 0; i < 6; i++) {
                cal.add(Calendar.DAY_OF_YEAR, 1);
                System.err.println(sdf.format(cal.getTime()).toString());
                dateList.add(sdf.format(cal.getTime()).toString());
            }

            prevMenuResponse.setData(dateList);
            List prevMenuList = new ArrayList<>();

            dateList.stream().forEach(value -> {
                @SuppressWarnings("deprecation")
                List<?> menu = session.createCriteria(Menu.class).add(Restrictions.eq("menuCategory", category))
                        .add(Restrictions.eq("date", value)).list();
                prevMenuList.add(menu.stream().distinct().collect(Collectors.toList()));
            });
            prevMenuResponse.setData(prevMenuList.stream().distinct().collect(Collectors.toList()));
            session.close();

            return prevMenuResponse;
        } catch (Exception e) {
            System.out.println(e);
            prevMenuResponse.setStatus(false);
            prevMenuResponse.setMessage("Exception occured!" + e);
            transaction.rollback();
        }
        return prevMenuResponse;
    }

    public AdminDTO register(UserMaster user) {

        AdminDTO registerResponse = new AdminDTO();
        try {
            registerResponse.setStatus(true);
            registerResponse.setMessage("Success");

            UserMaster newUser = new UserMaster();
            newUser.setActive(true);
            newUser.setUserEmail(user.getUserEmail());
            newUser.setFirstName(user.getFirstName());
            newUser.setLastName(user.getLastName());
            newUser.setOrganization(user.getOrganization());
            newUser.setUserId(user.getUserId());

            user.getRoles().stream().forEach(value -> {
                RoleMaster role = roleRepository.findByName(value.getName());
                newUser.getRoles().add(role);
                role.getUsers().add(newUser);
            });

            userRepository.save(newUser);

            registerResponse.setData(null);
            return registerResponse;
        } catch (Exception e) {
            System.out.println(e);
            registerResponse.setStatus(false);
            registerResponse.setMessage("Exception occured! " + e);

        }
        return registerResponse;
    }

    public AdminDTO addNewRole(RoleMaster role) {

        AdminDTO addNewRoleResponse = new AdminDTO();
        try {
            addNewRoleResponse.setStatus(true);
            addNewRoleResponse.setMessage("Success");

            RoleMaster newRole = new RoleMaster();
            newRole.setName(role.getName());
            roleRepository.save(newRole);

            addNewRoleResponse.setData("New role created");
            return addNewRoleResponse;
        } catch (Exception e) {
            System.out.println(e);
            addNewRoleResponse.setStatus(false);
            addNewRoleResponse.setMessage("Exception occured! " + e);

        }
        return addNewRoleResponse;
    }

    public AdminDTO updateEmp(UserMaster user) {

        AdminDTO updateEmpResponse = new AdminDTO();
        try {
            updateEmpResponse.setStatus(true);
            updateEmpResponse.setMessage("Success");

            UserMaster updateUser = userRepository.findByUserId(user.getUserId());

            if (user.getUserEmail() != null) {
                updateUser.setUserEmail(user.getUserEmail());
            }
            if (user.getFirstName() != null) {
                updateUser.setFirstName(user.getFirstName());
            }
            if (user.getLastName() != null) {
                updateUser.setLastName(user.getLastName());
            }
            if (user.getOrganization() != null) {
                updateUser.setOrganization(user.getOrganization());
            }
            if (user.isActive() == false) {
                updateUser.setActive(false);
            }

            user.getRoles().stream().forEach(value -> {
                updateUser.getRoles().clear();
                RoleMaster role = roleRepository.findByName(value.getName());
                updateUser.getRoles().add(role);
                role.getUsers().add(updateUser);
            });

            userRepository.save(updateUser);

            updateEmpResponse.setData(null);
            return updateEmpResponse;
        } catch (Exception e) {
            System.out.println(e);
            updateEmpResponse.setStatus(false);
            updateEmpResponse.setMessage("Exception occured! " + e);

        }
        return updateEmpResponse;
    }
}


//    public HashMap<String, Float> analytics(String date, int category) {
//        Transaction transaction = null;
//        Date date1; // to store parsed date
//        try {
//            date1 = new SimpleDateFormat("dd/MM/yyyy").parse(date);
//        } catch (ParseException e) {
//            throw new RuntimeException(e);
//        }
//        long MILLIS_IN_A_DAY = 1000 * 60 * 60 * 24; // to be subtract from the given date
//
//        Date[] arrayOfDates = new Date[8]; // to store last 7 days
//        for (int i = 1; i < 8; i++) {
//            arrayOfDates[i] = new Date(date1.getTime() - MILLIS_IN_A_DAY * i);
//        }
//
//        HashMap<String, Float> map = new HashMap<>();
//        int totalEmp = 0;
//        float avgAttendanceThisWeekForBreakfast = 0.0f;
//        float avgAttendanceThisWeekForLunch = 0.0f;
//        float avgEmployeeAteThisWeekInBreakfast = 0.0f;
//        float avgEmployeeAteThisWeekInLunch = 0.0f;
//        float avgRatingThisWeekForBreakfast = 0.0f;
//        float avgRatingThisWeekForLunch = 0.0f;
//        try {
//            Session session = HibernateUtil.getSessionFactory().openSession();
//            transaction = session.beginTransaction();
//            String sqlString1 = "FROM UserMaster as um";
//            Query query1 = session.createQuery(sqlString1);
//            // Iterate through stored objects
//            for (Iterator it = query1.iterate(); it.hasNext(); it.next()) {
//                totalEmp = totalEmp + 1;
//            }
//            map.put("Total number of Employees", (float) totalEmp);
//
//            // Average Attendance Recorded This Week
//
//            if (category == 1) {
//                int totalUserWhoOptedForBreakfast = 0;
//                String sqlString2 = "FROM MealConsent as mc";
//                Query query2 = session.createQuery(sqlString2);
//                // Iterate through stored objects
//                for (Iterator it = query2.iterate(); it.hasNext(); ) {
//                    MealConsent mealConsent = (MealConsent) it.next();
//                    for (int i = 1; i < 8; i++) {
//                        if (mealConsent.getUserPkId().getDate().getDate() == arrayOfDates[i].getDate()
//                                && mealConsent.getUserPkId().getDate().getMonth() == arrayOfDates[i].getMonth()
//                                && mealConsent.getUserPkId().getDate().getYear() == arrayOfDates[i].getYear()) {
//                            if (mealConsent.getLunchConsent() > 0) {
//                                totalUserWhoOptedForBreakfast = totalUserWhoOptedForBreakfast + 1;
//                            }
//                        }
//                    }
//                }
//                avgAttendanceThisWeekForBreakfast = (float) (totalUserWhoOptedForBreakfast / 5.0);
//                map.put("Average Attendance Recorded This Week For Breakfast", avgAttendanceThisWeekForBreakfast);
//            } else if (category == 2) {
//                int totalUserWhoOptedForLunch = 0;
//                String sqlString2 = "FROM MealConsent as mc";
//                Query query2 = session.createQuery(sqlString2);
//                // Iterate through stored objects
//                for (Iterator it = query2.iterate(); it.hasNext(); ) {
//                    MealConsent mealConsent = (MealConsent) it.next();
//                    for (int i = 1; i < 8; i++) {
//                        if (mealConsent.getUserPkId().getDate().getDate() == arrayOfDates[i].getDate()
//                                && mealConsent.getUserPkId().getDate().getMonth() == arrayOfDates[i].getMonth()
//                                && mealConsent.getUserPkId().getDate().getYear() == arrayOfDates[i].getYear()) {
//                            if (mealConsent.getLunchConsent() > 0) {
//                                totalUserWhoOptedForLunch = totalUserWhoOptedForLunch + 1;
//                            }
//                        }
//                    }
//                }
//                avgAttendanceThisWeekForLunch = (float) (totalUserWhoOptedForLunch / 5.0);
//                map.put("Average Attendance Recorded This Week For Lunch", avgAttendanceThisWeekForLunch);
//            }
//
//            // Average Number Employee Ate This Week
//
//            if (category == 1) {
//                int totalUserWhoAteBreakfast = 0;
//
//                String sqlString3 = "FROM MealAttendance as ma";
//                Query query3 = session.createQuery(sqlString3);
//                // Iterate through stored objects
//                for (Iterator it = query3.iterate(); it.hasNext(); ) {
//                    MealAttendance mealAttendance = (MealAttendance) it.next();
//                    for (int i = 1; i < 8; i++) {
//                        if (mealAttendance.getUserPkId().getDate().getDate() == arrayOfDates[i].getDate()
//                                && mealAttendance.getUserPkId().getDate().getMonth() == arrayOfDates[i].getMonth()
//                                && mealAttendance.getUserPkId().getDate().getYear() == arrayOfDates[i].getYear()) {
//                            if (mealAttendance.isBreakfastAttendance()) {
//                                totalUserWhoAteBreakfast = totalUserWhoAteBreakfast + 1;
//                            }
//                        }
//                    }
//                }
//                avgEmployeeAteThisWeekInBreakfast = (float) (totalUserWhoAteBreakfast / 5.0);
//                map.put("Average Number Employee Ate This Week In Breakfast", avgEmployeeAteThisWeekInBreakfast);
//            }
//            if (category == 2) {
//                int totalUserWhoAteLunch = 0;
//
//                String sqlString3 = "FROM MealAttendance as ma";
//                Query query3 = session.createQuery(sqlString3);
//                // Iterate through stored objects
//                for (Iterator it = query3.iterate(); it.hasNext(); ) {
//                    MealAttendance mealAttendance = (MealAttendance) it.next();
//                    for (int i = 1; i < 8; i++) {
//                        if (mealAttendance.getUserPkId().getDate().getDate() == arrayOfDates[i].getDate()
//                                && mealAttendance.getUserPkId().getDate().getMonth() == arrayOfDates[i].getMonth()
//                                && mealAttendance.getUserPkId().getDate().getYear() == arrayOfDates[i].getYear()) {
//                            if (mealAttendance.isLunchAttendance()) {
//                                totalUserWhoAteLunch = totalUserWhoAteLunch + 1;
//                            }
//                        }
//                    }
//                }
//                avgEmployeeAteThisWeekInLunch = (float) (totalUserWhoAteLunch / 5.0);
//                map.put("Average Number Employee Ate This Week In Lunch", avgEmployeeAteThisWeekInLunch);
//            }
//
//            // Average Food Rating This Week
//
//            if (category == 1) {
//                float breakfastRating = 0.0f;
//                float totalUserWhoRatedBreakfast = 0.0f;
//                String sqlString4 = "FROM MealFeedback as mf";
//                Query query4 = session.createQuery(sqlString4);
//                // Iterate through stored objects
//                for (Iterator it = query4.iterate(); it.hasNext(); ) {
//                    MealFeedback mealFeedback = (MealFeedback) it.next();
//                    for (int i = 1; i < 8; i++) {
//                        if (mealFeedback.getUserPkId().getDate().getDate() == arrayOfDates[i].getDate()
//                                && mealFeedback.getUserPkId().getDate().getMonth() == arrayOfDates[i].getMonth()
//                                && mealFeedback.getUserPkId().getDate().getYear() == arrayOfDates[i].getYear()) {
//                            if (mealFeedback.getBreakfastRating() > 0) {
//                                breakfastRating = breakfastRating + mealFeedback.getBreakfastRating();
//                                totalUserWhoRatedBreakfast = totalUserWhoRatedBreakfast + 1;
//                            }
//                        }
//                    }
//                }
//                avgRatingThisWeekForBreakfast = (float) breakfastRating / totalUserWhoRatedBreakfast;
//                map.put("Average Food Rating This Week For Breakfast", avgRatingThisWeekForBreakfast);
//
//            }
//
//            if (category == 2) {
//                float lunchRating = 0.0f;
//                float totalUserWhoRatedLunch = 0.0f;
//                String sqlString4 = "FROM MealFeedback as mf";
//                Query query4 = session.createQuery(sqlString4);
//                // Iterate through stored objects
//                for (Iterator it = query4.iterate(); it.hasNext(); ) {
//                    MealFeedback mealFeedback = (MealFeedback) it.next();
//                    for (int i = 1; i < 8; i++) {
//                        if (mealFeedback.getUserPkId().getDate().getDate() == arrayOfDates[i].getDate()
//                                && mealFeedback.getUserPkId().getDate().getMonth() == arrayOfDates[i].getMonth()
//                                && mealFeedback.getUserPkId().getDate().getYear() == arrayOfDates[i].getYear()) {
//                            if (mealFeedback.getLunchRating() > 0) {
//                                lunchRating = lunchRating + mealFeedback.getLunchRating();
//                                totalUserWhoRatedLunch = totalUserWhoRatedLunch + 1;
//                            }
//                        }
//                    }
//                }
//                avgRatingThisWeekForLunch = (float) lunchRating / totalUserWhoRatedLunch;
//                map.put("Average Food Rating This Week For Lunch", avgRatingThisWeekForLunch);
//                ;
//
//            }
//            transaction.commit();
//            System.out.println("Required User data Passed to Admin");
//            session.close();
//            return map;
//        } catch (Exception e) {
//            System.out.println(e);
//            transaction.rollback();
//        }
//        return map;
//    }
