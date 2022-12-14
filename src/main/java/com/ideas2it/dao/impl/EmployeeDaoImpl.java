package com.ideas2it.dao.impl;

import com.ideas2it.dao.EmployeeDao;
import com.ideas2it.hibernateUtil.HibernateFactory;
import com.ideas2it.model.Trainee;
import com.ideas2it.model.Trainer;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import java.sql.SQLException;
import java.util.List;

/**
 * <h2>EmployeDaoImpl</h2>
 * <p>
 * The EmployeeDaoImpl class is implements Employeedao and
 * The class implements an application that
 * defines all methods used in EmployeeController class
 * the medhods perform insert, retrieve, update, and remove object in mysql database
 * it uses hibernate configuration file and perform operations
 * and return data's to EmployeeService
 * </p>
 *
 * @author Yukendiran K
 * @version 1.0
 * @since 2022-08-04
 */
public class EmployeeDaoImpl implements EmployeeDao {

    /**
     * <h1> insertTrainer </h1>
     * <p>
     * Method used to get trainer details from EmployeeService and insert details to traier table
     *
     * @param {@link Trainer} trainer object
     * @return {@link String} returns nothing
     */
    @Override
    public String insertTrainer(Trainer trainer) throws HibernateException, SQLException, NullPointerException {

        Transaction transaction = null;
        String message = "couldn't insert data";

        try (Session session = HibernateFactory.getFactory().openSession()) {

            transaction = session.beginTransaction();
            session.saveOrUpdate(trainer);
            transaction.commit();
            message = "insert successfully";
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
        return message;
    }

    /**
     * <h1> insertTrainee </h1>
     * <p>
     * Method used to get trainee details from EmployeeService and insert details to trainee table
     *
     * @param {@link Trainee} trainee object
     * @return {@link String} returns nothing
     */
    @Override
    public String insertTrainee(Trainee trainee) throws HibernateException, NullPointerException {

        Transaction transaction = null;
        String message = "couldn't insert data";

        try (Session session = HibernateFactory.getFactory().openSession();) {
            transaction = session.beginTransaction();
            session.save(trainee);
            transaction.commit();
            message = "insert successfully";
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
        return message;
    }

    /**
     * <h1> retrieveTrainers</h1>
     * <p>
     * Method used to get all trainer details in trainer table
     *
     * @return {@link List<Trainer>} returns list of trainers Data
     */
    @Override
    public List<Trainer> retrieveTrainers() throws HibernateException, NullPointerException {

        List<Trainer> trainers;
        Transaction transaction = null;

        try (Session session = HibernateFactory.getFactory().openSession()) {
            transaction = session.beginTransaction();
            Criteria criteria = session.createCriteria(Trainer.class)
                    .add(Restrictions.eq("isActive", false))
                    .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            trainers = criteria.list();
        }
        return trainers;
    }

    /**
     * <h1> retrieveTrainer </h1>
     * <p>
     * Method used to get trainer details by using trainer Id
     *
     * @param {@link int} trainerId
     * @return {@link Trainer} returns trainer Data
     */
    @Override
    public Trainer retrieveTrainer(int trainerId) throws HibernateException, NullPointerException {

        Transaction transaction = null;
        Trainer trainer = null;

        try (Session session = HibernateFactory.getFactory().openSession()) {
            transaction = session.beginTransaction();
            trainer = (Trainer) session.get(Trainer.class, trainerId);
            trainer.getTrainees();
            return (!trainer.getIsActive()) ? trainer : null;
        }
    }

    /**
     * <h1> retrieveTrainees </h1>
     * <p>
     * Method used to get all trainee details in trainee table
     *
     * @return {@link List<Trainee>} returns list of trainees Data
     */
    @Override
    public List<Trainee> retrieveTrainees() throws HibernateException {

        List<Trainee> trainees;
        Transaction transaction = null;

        try (Session session = HibernateFactory.getFactory().openSession();) {
            transaction = session.beginTransaction();
            Criteria criteria = session.createCriteria(Trainee.class)
                    .add(Restrictions.eq("isActive", false))
                    .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            trainees = criteria.list();
        }
        return trainees;
    }

    /**
     * <h1> retrieveTrainee </h1>
     * <p>
     * Method used to retrieve trainee details by using trainee Id
     *
     * @param {@link String} trainee Id
     * @return {@link Trainee} returns trainee Data
     */
    @Override
    public Trainee retrieveTrainee(int traineeId) throws HibernateException, NullPointerException {

        Transaction transaction = null;
        Trainee trainee = null;

        try (Session session = HibernateFactory.getFactory().openSession()) {
            transaction = session.beginTransaction();
            trainee = session.get(Trainee.class, traineeId);
            trainee.getTrainers();
            return (!trainee.getIsActive()) ? trainee : null;
        }
    }

    /**
     * <h1> updateTrainer </h1>
     * <p>
     * Method used to update trainer details by using trainer Id
     *
     * @param {@link String} trainer Id
     * @param {@link Trainer} trainer object
     * @return {@link String} returns nothing
     */
    @Override
    public String updateTrainer(int trainerId, Trainer trainer) throws HibernateException, SQLException, NullPointerException {

        Transaction transaction = null;
        String message = "";

        try (Session session = HibernateFactory.getFactory().openSession();) {
            transaction = session.beginTransaction();

            if (!trainer.getIsActive()) {

                Trainer updateTrainer = (Trainer) session.get(Trainer.class, trainerId);
                updateTrainer.setEmployeeName(trainer.getEmployeeName());
                updateTrainer.setEmployeeDateOfBirth(trainer.getEmployeeDateOfBirth());
                updateTrainer.setEmployeeDesignation(trainer.getEmployeeDesignation());
                updateTrainer.setEmployeeMail(trainer.getEmployeeMail());
                updateTrainer.setEmployeeMobileNumber(trainer.getEmployeeMobileNumber());
                updateTrainer.setCurrentAddress(trainer.getCurrentAddress());
                updateTrainer.setAadharCardNumber(trainer.getAadharCardNumber());
                updateTrainer.setPanCardNumber(trainer.getPanCardNumber());
                updateTrainer.setCurrentProject(trainer.getCurrentProject());
                updateTrainer.setAchievement(trainer.getAchievement());
                updateTrainer.setTrainees(trainer.getTrainees());
                transaction.commit();
                message = "updated successfully";

            } else {
                message = "employee is not active";
            }
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
        return message;
    }

    /**
     * <h1> updateTrainee </h1>
     * <p>
     * Method used to update trainee details by using trainee Id
     *
     * @param {@link String} trainee Id
     * @param {@link Trainee} trainee object
     * @return {@link String} returns nothing
     */
    @Override
    public String updateTrainee(int traineeId, Trainee trainee) throws HibernateException, SQLException, NullPointerException {

        Transaction transaction = null;
        String message = "";

        try (Session session = HibernateFactory.getFactory().openSession();) {
            transaction = session.beginTransaction();

            if (!trainee.getIsActive()) {

                Trainee updateTrainee = session.get(Trainee.class, traineeId);
                updateTrainee.setEmployeeName(trainee.getEmployeeName());
                updateTrainee.setEmployeeDateOfBirth(trainee.getEmployeeDateOfBirth());
                updateTrainee.setEmployeeDesignation(trainee.getEmployeeDesignation());
                updateTrainee.setEmployeeMail(trainee.getEmployeeMail());
                updateTrainee.setEmployeeMobileNumber(trainee.getEmployeeMobileNumber());
                updateTrainee.setCurrentAddress(trainee.getCurrentAddress());
                updateTrainee.setAadharCardNumber(trainee.getAadharCardNumber());
                updateTrainee.setPanCardNumber(trainee.getPanCardNumber());
                updateTrainee.setCurrentTask(trainee.getCurrentTask());
                updateTrainee.setCurrentTechknowledge(trainee.getCurrentTechknowledge());
                updateTrainee.setTrainers(trainee.getTrainers());
                transaction.commit();
                message = "updated successfully";

            } else {
                message = "employee is not active";
            }
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
        return message;
    }

    /**
     * <h1> removetrainer </h1>
     * <p>
     * Method used to remove trainer details by using trainer Id and it is a soft delete.
     * It means details is present but trainer not active.
     *
     * @param {@link String} trainer Id
     * @return {@link String} returns message. message contains status of the method.
     */
    @Override
    public String removeTrainer(int trainerId) throws HibernateException, SQLException, NullPointerException {

        Transaction transaction = null;
        String message = "no operation ahead";

        try (Session session = HibernateFactory.getFactory().openSession();) {
            transaction = session.beginTransaction();
            Trainer trainer = (Trainer) session.get(Trainer.class, trainerId);
            trainer.setIsActive(true);
            session.update(trainer);
            System.out.println("update");
            transaction.commit();
            message = "successfully deleted";
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
        return message;
    }

    /**
     * <h1> removeTrainee </h1>
     * <p>
     * Method used to remove trainee details by using trainee Id and it is a soft delete.
     * It means details is present but trainee not active.
     *
     * @param {@link String} trainee Id
     * @return {@link String} returns message. message contains status of the method.
     */
    @Override
    public String removeTrainee(int traineeId) throws HibernateException, SQLException, NullPointerException {

        Transaction transaction = null;
        String message = "no operation ahead";

        try (Session session = HibernateFactory.getFactory().openSession();) {
            transaction = session.beginTransaction();
            Trainee trainee = (Trainee) session.get(Trainee.class, traineeId);
            trainee.setIsActive(true);
            session.update(trainee);
            transaction.commit();
            message = "successfully deleted";
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
        return message;
    }
}


            