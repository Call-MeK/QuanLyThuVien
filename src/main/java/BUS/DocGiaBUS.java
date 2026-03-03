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

    public ArrayList<DocGiaDTO> getAll() {
        return docGiaDAO.getAll();
    }

    public ArrayList<DocGiaDTO> getListDocGia() {
        return listdocGia;
    }

    public boolean hasMaDocGia(String maDocGia) {
        for (DocGiaDTO dg : listdocGia) {
            if (dg.getMaDocGia().equals(maDocGia)) {
                return true;
            }
        }
        return false;
    }

    public String addDocGia(DocGiaDTO dg) {
        if (hasMaDocGia(dg.getMaDocGia())) {
            return "Mã độc giả đã tồn tại";
        }
        if (docGiaDAO.add(dg)) {
            listdocGia.add(dg);
            return "Thêm thành công";
        }
        return "Thêm thất bại";
    }

    public String updateDocGia(DocGiaDTO dg) {
        if (!hasMaDocGia(dg.getMaDocGia())) {
            return "Mã độc giả không tồn tại";
        }
        if (docGiaDAO.update(dg)) {
            for (int i = 0; i < listdocGia.size(); i++) {
                if (listdocGia.get(i).getMaDocGia().equals(dg.getMaDocGia())) {
                    listdocGia.set(i, dg);
                    break;
                }
            }
            return "Cập nhật thành công";
        }
        return "Cập nhật thất bại";
    }

    public String deleteDocGia(String maDocGia) {
        if (!hasMaDocGia(maDocGia)) {
            return "Mã độc giả không tồn tại";
        }
        if (docGiaDAO.delete(maDocGia)) {
            listdocGia.removeIf(dg -> dg.getMaDocGia().equals(maDocGia));
            return "Xóa thành công";
        }
        return "Xóa thất bại";
    }

    public String softDeleteDocGia(String maDocGia) {
        if (!hasMaDocGia(maDocGia)) {
            return "Mã độc giả không tồn tại";
        }
        if (docGiaDAO.softdelete(maDocGia)) {
            for (DocGiaDTO dg : listdocGia) {
                if (dg.getMaDocGia().equals(maDocGia)) {
                    dg.setIsDeleted(true);
                    dg.setNgayXoa(java.time.LocalDate.now().toString());
                    break;
                }
            }
            return "Xóa mềm thành công";
        }
        return "Xóa mềm thất bại";
    }
}
