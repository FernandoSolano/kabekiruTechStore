package cr.ac.ucr.kabekuritechstore.business;

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
}
