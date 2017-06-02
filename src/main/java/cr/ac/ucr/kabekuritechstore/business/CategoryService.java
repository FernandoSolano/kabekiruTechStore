package cr.ac.ucr.kabekuritechstore.business;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cr.ac.ucr.kabekuritechstore.data.CategoryDao;
import cr.ac.ucr.kabekuritechstore.domain.Category;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryDao categoryDao;
	
	public List<Category> findAll(){
		return categoryDao.findAll();
	}
	
	public Category insertCategory(Category category){
		return categoryDao.insertCategory(category);
	}
	
	public void deleteCategoryById(int id) throws SQLException {
		categoryDao.deleteCategoryById(id);
	}
    
	public void updateCategoria(Category category) throws SQLException {
		categoryDao.updateCategoria(category);
	}
	
	public List<Category> findCategoriesByName(String name) {
		return categoryDao.findCategoriesByName(name);
	}
	
	public Category findCategoryByID(int id) {
		return categoryDao.findCategoryByID(id);
	}
}
