/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dao;

import java.util.List;
import org.bson.types.ObjectId;
import org.entity.Report;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.mongodb.morphia.query.UpdateResults;

/**
 *
 * @author KhanhDN
 */
public class ReportDAO extends AbstractDAO{
    private static final ReportDAO instance = new ReportDAO();
    
    private ReportDAO(){
        datastore.ensureIndexes();
    }
    
    public static final ReportDAO getInstance() {
        return instance;
    }
    
    public Report getReport(String reportId) throws DAOException{
        try {
            Query<Report> query = datastore.createQuery(Report.class).field("_id").equal(new ObjectId(reportId));
            Report report = query.get();

            return report;
        } catch (Exception ex) {
        }
        return null;
    }
    
    public List<Report> getAllReport() throws DAOException{
        try{
            Query<Report> query = datastore.createQuery(Report.class);
            return query.asList();
        }catch (Exception ex){
        }
        return null;
    }
    
    public boolean updateReportStatus(String reportId, int flag){
        try {
            Query<Report> query = datastore.createQuery(Report.class).field("_id").equal(new ObjectId(reportId));
            UpdateOperations<Report> updateO = datastore.createUpdateOperations(Report.class).set("status", flag);
            UpdateResults result = datastore.update(query, updateO);
            return result.getUpdatedCount() == 1;
        } catch (Exception ex) {
        }
        return false;
    }
    
    public boolean updateAdminReport(String reportId, String adminId, long time){
        try {
            Query<Report> query = datastore.createQuery(Report.class).field("_id").equal(new ObjectId(reportId));
            UpdateOperations<Report> updateO = datastore.createUpdateOperations(Report.class).set("verify_by", adminId).set("verify_time", time);
            UpdateResults result = datastore.update(query, updateO);
            return result.getUpdatedCount() == 1;
        } catch (Exception ex) {
        }
        return false;
    }
    
    public long getNumberReport() {
        Query<Report> query = datastore.createQuery(Report.class);
        return query.countAll();
    }
    
    public Report getReportByRecipe(String recipeId) throws DAOException{
        try {
            Query<Report> query = datastore.createQuery(Report.class).field("recipe").equal(recipeId);
            Report report = query.get();

            return report;
        } catch (Exception ex) {
        }
        return null;
    }
}
