/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;

import DAO.DocGiaDAO;
import DTO.DocGiaDTO;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class DocGiaBUS {
    private DocGiaDAO docGiaDAO;
    private ArrayList<DocGiaDTO> listdocGia;
    public DocGiaBUS() {
        docGiaDAO = new DocGiaDAO();
        listdocGia = docGiaDAO.getAll();
    }
    public ArrayList<DocGiaDTO> getAll(){
        return docGiaDAO.getAll();
    }
    
}
