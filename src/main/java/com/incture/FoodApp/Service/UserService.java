package com.incture.FoodApp.Service;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.incture.FoodApp.Entity.*;
import com.incture.FoodApp.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class UserService {

    public UserDTO addFeedback(ObjectNode objectNode) {
        Transaction transaction = null;
        MealFeedback mealFeedback = new MealFeedback();
        String breakfastFeedback = " ";
        String lunchFeedback = " ";
        int breakfastRating = 0;
        int lunchRating = 0;

        String date;
        Date date1;  //to store parsed date
        String userId = objectNode.get("userId").asText();
        date = objectNode.get("date").asText();
        try {
            date1 = new SimpleDateFormat("dd/MM/yyyy").parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        int category = objectNode.get("category").asInt();

        UserDTO userDTO = new UserDTO();

        UserPkId userPkId = new UserPkId();
        userPkId.setUserId(userId);
        userPkId.setDate(date1);


        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            MealFeedback mealFeedbackOld=null;
            UserMaster userMaster = (UserMaster) session.get(UserMaster.class, userId);
            if (session.get(MealFeedback.class, userPkId).getBreakfastRating() != 0 || session.get(MealFeedback.class, userPkId).getLunchRating() != 0) {
                 mealFeedbackOld = (MealFeedback) session.get(MealFeedback.class, userPkId);
                if(mealFeedbackOld!=null){
                    Date date2 = new Date(System.currentTimeMillis());
                    mealFeedbackOld.setUpdatedBy(userMaster.getFirstName());
                    mealFeedbackOld.setUpdatedOn(date2);
                    if(category==1)
                    {
                        breakfastRating = objectNode.get("breakfastRating").asInt();
                        if(objectNode.has("breakfastFeedback")){
                            breakfastFeedback = objectNode.get("breakfastFeedback").asText();
                        }
                        mealFeedbackOld.setBreakfastFeedback(breakfastFeedback);
                        mealFeedbackOld.setBreakfastRating(breakfastRating);
                    }
                    if (category == 2) {
                        lunchRating = objectNode.get("lunchRating").asInt();
                        if (objectNode.has("lunchFeedback")) {
                            lunchFeedback = objectNode.get("lunchFeedback").asText();
                        }
                        mealFeedbackOld.setLunchFeedback(lunchFeedback);
                        mealFeedbackOld.setLunchRating(lunchRating);
                    }
                }
                session.saveOrUpdate(mealFeedbackOld);
                transaction.commit();
                System.out.println("Feedback Data Updated");
                session.close();
                userDTO.setMessage("Feedback Updated Successfully");
                userDTO.setStatus(true);
                userDTO.setData(null);
                return userDTO;
            }
            transaction.commit();
            session.close();
        } catch (Exception e) {
            System.out.println(e);
            transaction.rollback();
        }


        if (category == 1) {
            breakfastRating = objectNode.get("breakfastRating").asInt();
            if(objectNode.has("breakfastFeedback")){
            breakfastFeedback = objectNode.get("breakfastFeedback").asText();
            }
        }

        if (category == 2) {
            lunchRating = objectNode.get("lunchRating").asInt();
            if(objectNode.has("lunchFeedback")){
            lunchFeedback = objectNode.get("lunchFeedback").asText();
            }
            try {
                Session session = HibernateUtil.getSessionFactory().openSession();
                transaction = session.beginTransaction();
                UserMaster userMaster = (UserMaster) session.get(UserMaster.class, userId);
                if (session.get(MealFeedback.class, userPkId).getBreakfastRating() != 0) {

                    MealFeedback mealFeedbackOld = (MealFeedback) session.get(MealFeedback.class, userPkId);
                    breakfastFeedback = mealFeedbackOld.getBreakfastFeedback();
                    breakfastRating = mealFeedbackOld.getBreakfastRating();

                }
                //Now setting the Value in mealFeedback
                mealFeedback.setLunchFeedback(lunchFeedback);
                mealFeedback.setBreakfastFeedback(breakfastFeedback);
                mealFeedback.setBreakfastRating(breakfastRating);
                mealFeedback.setLunchRating(lunchRating);

                Date date3 = new Date(System.currentTimeMillis());
                mealFeedback.setCreatedOn(date3);
                mealFeedback.setCreatedBy(userMaster.getFirstName());

                Date dateFake = new Date(0);
                mealFeedback.setUpdatedOn(dateFake);
                mealFeedback.setUpdatedBy(" ");


                mealFeedback.setUserPkId(userPkId);
                mealFeedback.setUserPkId(userPkId);
                session.saveOrUpdate(mealFeedback);
                transaction.commit();
                System.out.println("Feedback Data Saved");
                session.close();
                userDTO.setMessage("Feedback Posted Successfully");
                userDTO.setStatus(true);
                userDTO.setData(null);
                return userDTO;
            } catch (Exception e) {
                System.out.println(e);
                transaction.rollback();
            }
        }
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            UserMaster userMaster = (UserMaster) session.get(UserMaster.class, userId);

            //Now setting the Value in mealFeedback
            mealFeedback.setLunchFeedback(lunchFeedback);
            mealFeedback.setBreakfastFeedback(breakfastFeedback);
            mealFeedback.setBreakfastRating(breakfastRating);
            mealFeedback.setLunchRating(lunchRating);

            Date date3 = new Date(System.currentTimeMillis());
            mealFeedback.setCreatedOn(date3);
            mealFeedback.setCreatedBy(userMaster.getFirstName());


            Date dateFake = new Date(0);
            mealFeedback.setUpdatedOn(dateFake);
            mealFeedback.setUpdatedBy(" ");

            mealFeedback.setUserPkId(userPkId);
            mealFeedback.setUserPkId(userPkId);


            session.saveOrUpdate(mealFeedback);
            transaction.commit();
            System.out.println("Feedback Data Saved");
            session.close();
            userDTO.setMessage("Feedback Posted Successfully");
            userDTO.setStatus(true);
            userDTO.setData(null);
            return userDTO;
        } catch (Exception e) {
            System.out.println(e);
            transaction.rollback();
        }
        userDTO.setStatus(false);
        userDTO.setMessage("Feedback Posting Failed");
        userDTO.setData(null);
        return userDTO;
    }

    public UserDTO getFeedback(String userId, String date, int category) {
        Transaction transaction = null;
        String feedback = " ";
        int rating = 0;
        int meal=0;
        Date date1;  //to store parsed date
        try {
            date1 = new SimpleDateFormat("dd/MM/yyyy").parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        UserDTO userDTO = new UserDTO();

        UserPkId userPkId = new UserPkId();
        userPkId.setUserId(userId);
        userPkId.setDate(date1);
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            MealFeedback mealFeedback = (MealFeedback) session.get(MealFeedback.class, userPkId);
            MealConsent mealConsent=(MealConsent) session.get(MealConsent.class,userPkId);
            if(mealConsent==null)
            {
                meal=0;
            }
            else
            {
                if(category==1)
                    meal=mealConsent.getBreakfastConsent();
                else if (category == 2)
                    meal=mealConsent.getLunchConsent();
            }
            if (category == 1) {
                feedback = mealFeedback.getBreakfastFeedback();
                rating = mealFeedback.getBreakfastRating();
            } else if (category == 2) {
                feedback = mealFeedback.getLunchFeedback();
                rating = mealFeedback.getLunchRating();
            }
            transaction.commit();
            System.out.println("Feedback Data Got");
            session.close();
            userDTO.setMessage("Feedback Fetched Successfully");
            HashMap<HashMap<String, Integer>,Integer> map = new HashMap<>();
            HashMap<String, Integer> internalMap = new HashMap<>();
            internalMap.put(feedback, rating);
            map.put(internalMap,meal);
            userDTO.setStatus(true);
            userDTO.setData(map);
            return userDTO;
        } catch (Exception e) {
            System.out.println(e);
            transaction.rollback();
        }
        userDTO.setStatus(false);
        userDTO.setMessage("Feedback Fetching Failed");
        userDTO.setData(null);
        return userDTO;
    }

    public UserDTO userConsent(ObjectNode objectNode) {
        Transaction transaction = null;
        MealConsent mealConsent = new MealConsent();
        int breakfastConsent = 0;
        int lunchConsent = 0;


        String date;
        Date date1;  //to store parsed date
        String userId = objectNode.get("userId").asText();
        date = objectNode.get("date").asText();
        try {
            date1 = new SimpleDateFormat("dd/MM/yyyy").parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        int category = objectNode.get("category").asInt();

        UserDTO userDTO = new UserDTO();

        UserPkId userPkId = new UserPkId();
        userPkId.setUserId(userId);
        userPkId.setDate(date1);

        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            MealConsent mealConsentOld=null;
            UserMaster userMaster = (UserMaster) session.get(UserMaster.class, userId);
            if (session.get(MealConsent.class, userPkId).getBreakfastConsent() != 0 || session.get(MealConsent.class, userPkId).getLunchConsent() != 0) {
                mealConsentOld = (MealConsent) session.get(MealConsent.class, userPkId);
                if(mealConsentOld!=null){
                    Date date2 = new Date(System.currentTimeMillis());
                    mealConsentOld.setUpdatedBy(userMaster.getFirstName());
                    mealConsentOld.setUpdatedOn(date2);
                    if(category==1)
                    {
                        breakfastConsent = objectNode.get("breakfastConsent").asInt();
                        mealConsentOld.setBreakfastConsent(breakfastConsent);
                    }
                    if (category == 2) {
                        lunchConsent = objectNode.get("lunchConsent").asInt();
                        mealConsentOld.setLunchConsent(lunchConsent);
                    }
                }
                session.saveOrUpdate(mealConsentOld);
                transaction.commit();
                System.out.println("Meal Consent Data Updated");
                session.close();
                userDTO.setMessage("Meal Consent Data Updated Successfully");
                userDTO.setStatus(true);
                userDTO.setData(null);
                return userDTO;
            }
            transaction.commit();
            session.close();
        } catch (Exception e) {
            System.out.println(e);
            transaction.rollback();
        }

        if (category == 1) {
            breakfastConsent = objectNode.get("breakfastConsent").asInt();
        }
        if (category == 2) {
            try {
                lunchConsent = objectNode.get("lunchConsent").asInt();
                Session session = HibernateUtil.getSessionFactory().openSession();
                transaction = session.beginTransaction();
                UserMaster userMaster = (UserMaster) session.get(UserMaster.class, userId);
                if (session.get(MealConsent.class, userPkId).getBreakfastConsent() != 0) {

                    MealConsent mealConsentOld = (MealConsent) session.get(MealConsent.class, userPkId);
                    breakfastConsent = mealConsentOld.getBreakfastConsent();

                }
                mealConsent.setBreakfastConsent(breakfastConsent);
                mealConsent.setLunchConsent(lunchConsent);
                mealConsent.setUserPkId(userPkId);

                Date date3 = new Date(System.currentTimeMillis());
                mealConsent.setCreatedBy(userMaster.getFirstName());
                mealConsent.setCreatedOn(date3);

                Date dateFake = new Date(0);
                mealConsent.setUpdatedBy(" ");
                mealConsent.setUpdatedOn(dateFake);


                session.saveOrUpdate(mealConsent);
                transaction.commit();
                System.out.println("User Consent Saved");
                session.close();
                userDTO.setMessage("User Consent Done Successfully");
                userDTO.setStatus(true);
                userDTO.setData(null);
                return userDTO;
            } catch (Exception e) {
                System.out.println(e);
                transaction.rollback();
            }
        }
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            UserMaster userMaster = (UserMaster) session.get(UserMaster.class, userId);
            mealConsent.setBreakfastConsent(breakfastConsent);
            mealConsent.setLunchConsent(lunchConsent);
            mealConsent.setUserPkId(userPkId);

            Date date3 = new Date(System.currentTimeMillis());
            mealConsent.setCreatedBy(userMaster.getFirstName());
            mealConsent.setCreatedOn(date3);

            Date dateFake = new Date(0);
            mealConsent.setUpdatedBy(" ");
            mealConsent.setUpdatedOn(dateFake);


            session.saveOrUpdate(mealConsent);
            transaction.commit();
            System.out.println("User Consent Saved");
            session.close();
            userDTO.setMessage("User Consent Done Successfully");
            userDTO.setStatus(true);
            userDTO.setData(null);
            return userDTO;
        } catch (Exception e) {
            System.out.println(e);
            transaction.rollback();
        }
        userDTO.setStatus(false);
        userDTO.setMessage("User Consent Failed");
        userDTO.setData(null);
        return userDTO;
    }



//    public UserDTO updateFeedback(MealFeedback mealFeedback,int lunchRating,String lunchFeedback) {
//        Transaction transaction=null;
//        MealFeedback mealFeedbackOld=new MealFeedback();
//        UserDTO userDTO=new UserDTO();
//
//        UserPkId userPkId=mealFeedback.getUserPkId();
//        try {
//            Session session= HibernateUtil.getSessionFactory().openSession();
//            transaction=session.beginTransaction();
//            mealFeedbackOld= (MealFeedback) session.get(MealFeedback.class,userPkId);
//            mealFeedbackOld.setLunchRating(lunchRating);
//            mealFeedbackOld.setLunchFeedback(lunchFeedback);
//            session.saveOrUpdate(mealFeedbackOld);
//            transaction.commit();
//            System.out.println("Feedback Data Updated");
//            session.close();
//            userDTO.setMessage("Feedback Posted Successfully");
//            userDTO.setStatus(true);
//            userDTO.setData(null);
//            return userDTO;
//        }
//
//        catch(Exception e) {
//            System.out.println(e);
//            transaction.rollback();
//        }
//        return userDTO;
//    }

    //    public UserFeedback addFeedback(UserFeedback userFeedback) {
//            Transaction transaction=null;
//            try {
//                Session session= HibernateUtil.getSessionFactory().openSession();
//                transaction=session.beginTransaction();
//                session.save(userFeedback);
//                transaction.commit();
//                System.out.println("Feedback Data Saved");
//                session.close();
//                return userFeedback;
//            }catch(Exception e) {
//                System.out.println(e);
//                transaction.rollback();
//            }
//            return userFeedback;
//        }

//    public UserMaster userStatusBreakfast(String userId, Date date, String org) {
//        Transaction transaction=null;
//        BreakfastAttendance breakfastAttendance=new BreakfastAttendance();
//        breakfastAttendance.setUserId(userId);
//        breakfastAttendance.setDate(date);
//        breakfastAttendance.setOptedIn(true);
//        breakfastAttendance.setPresent(false);
//       // breakfastAttendance.setOrganisation(org);
//
//
//        UserMaster userMaster =new UserMaster();
//        try {
//            Session session= HibernateUtil.getSessionFactory().openSession();
//            transaction=session.beginTransaction();
//            userMaster = (UserMaster) session.get(UserMaster.class, userId);
//            breakfastAttendance.setOrganisation(userMaster.getOrganization());
//            session.save(breakfastAttendance);
//            transaction.commit();
//            System.out.println("Breakfast Attendance Data Saved");
//            session.close();
//           // return userMaster;
//        }catch(Exception e) {
//            System.out.println(e);
//            transaction.rollback();
//        }
//
//        return userMaster;
//    }
//
//    public UserMaster userStatusLunch(String userId, Date date, String org) {
//        Transaction transaction=null;
//        LunchAttendance lunchAttendance=new LunchAttendance();
//        lunchAttendance.setDate(date);
//        lunchAttendance.setUserId(userId);
//        lunchAttendance.setOptedIn(true);
//        lunchAttendance.setPresent(false);
//      //  lunchAttendance.setOrganisation(org);
//
//
//        UserMaster userMaster =new UserMaster();
//
//        try {
//            Session session= HibernateUtil.getSessionFactory().openSession();
//            transaction=session.beginTransaction();
//            userMaster = (UserMaster) session.get(UserMaster.class, userId);
//            lunchAttendance.setOrganisation(userMaster.getOrganization());
//            session.save(lunchAttendance);
//            transaction.commit();
//            System.out.println("Lunch Attendance Data Saved");
//            session.close();
//           // return userMaster;
//        }catch(Exception e) {
//            System.out.println(e);
//            transaction.rollback();
//        }
//
//        return userMaster;
//    }

}

